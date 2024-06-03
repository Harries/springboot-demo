package com.et.geotools.IO;


import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.util.Assert;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>StringToken 特殊、自定义字符串解析Reader</p>
 * @author Appleyk
 * @blob https://blog.csdn.net/appleyk
 * @date Created on 上午 2018年10月25日09:22:41
 */
public class StringTokenReader {

    private static final String EMPTY = "EMPTY";
    private static final String COMMA = ",";
    private static final String L_PAREN = "(";
    private static final String R_PAREN = ")";
    private static final String NAN_SYMBOL = "NaN";
    private static final String Z = "Z";
    private static final String M = "M";
    private static final String ZM = "ZM";
    private static final String POINT = "POINT";
    private static final String LINESTRING = "LINESTRING";
    private static final String LINEARRING = "LINEARRING";
    private static final String POLYGON = "POLYGON";
    private static final String MULTI_POINT = "MULTIPOINT";
    private static final String MULTI_LINESTRING = "MULTILINESTRING";
    private static final String MULTI_POLYGON = "MULTIPOLYGON";

    private GeometryFactory geometryFactory;
    private PrecisionModel precisionModel;
    private StreamTokenizer tokenizer;

    /**
     * Not yet used (useful if we want to read Z, M and ZM WKT)
     */
    private boolean z;
    private boolean m;

    /**
     * Creates a reader that creates objects using the default
     * {@link GeometryFactory}.
     */
    public StringTokenReader() {
        this(new GeometryFactory());
    }

    /**
     * Creates a reader that creates objects using the given
     * {@link GeometryFactory}.
     *
     * @param geometryFactory the factory used to create <code>Geometry</code>s.
     */
    public StringTokenReader(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
        precisionModel = geometryFactory.getPrecisionModel();
    }

    /**
     * Reads a Well-Known Text representation of a {@link Geometry} from a
     * {@link String}.
     *
     * @param wellKnownText one or more &lt;Geometry Tagged Text&gt; strings (see the
     *                      OpenGIS Simple Features Specification) separated by whitespace
     * @return a <code>Geometry</code> specified by <code>wellKnownText</code>
     * @throws ParseException if a parsing problem occurs
     */
    public Geometry read(String wellKnownText) throws ParseException {
        StringReader reader = new StringReader(wellKnownText);
        try {
            return read(reader);
        } finally {
            reader.close();
        }
    }

    /**
     * Reads a Well-Known Text representation of a {@link Geometry} from a
     * {@link Reader}.
     *
     * @param reader a Reader which will return a &lt;Geometry Tagged Text&gt;
     *               string (see the OpenGIS Simple Features Specification)
     * @return a <code>Geometry</code> read from <code>reader</code>
     * @throws ParseException if a parsing problem occurs
     */
    public Geometry read(Reader reader) throws ParseException {
        tokenizer = new StreamTokenizer(reader);
        // set tokenizer to NOT parse numbers
        tokenizer.resetSyntax();
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars(128 + 32, 255);
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('-', '-');
        tokenizer.wordChars('+', '+');
        tokenizer.wordChars('.', '.');
        tokenizer.whitespaceChars(0, ' ');
        tokenizer.commentChar('#');
        z = false;
        m = false;
        try {
            return readGeometryTaggedText();
        } catch (IOException e) {
            throw new ParseException(e.toString());
        }
    }

    /**
     * Returns the next array of <code>Coordinate</code>s in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next element returned by the stream should be L_PAREN (the beginning of
     * "(x1 y1, x2 y2, ..., xn yn)") or EMPTY.
     *
     * @return the next array of <code>Coordinate</code>s in the stream, or an
     * empty array if EMPTY is the next element returned by the stream.
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if an unexpected token was encountered
     */
    private Coordinate[] getCoordinates() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (EMPTY.equals(nextToken)) {
            return new Coordinate[]{};
        }
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(getPreciseCoordinate());
        nextToken = getNextCloserOrComma();
        while (COMMA.equals(nextToken)) {
            coordinates.add(getPreciseCoordinate());
            nextToken = getNextCloserOrComma();
        }
        Coordinate[] array = new Coordinate[coordinates.size()];
        return  coordinates.toArray(array);
    }

    private Coordinate[] getCoordinatesNoLeftParen() throws IOException, ParseException {
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(getPreciseCoordinate());
        String nextToken = getNextCloserOrComma();
        while (COMMA.equals(nextToken)) {
            coordinates.add(getPreciseCoordinate());
            nextToken = getNextCloserOrComma();
        }
        Coordinate[] array = new Coordinate[coordinates.size()];
        return  coordinates.toArray(array);
    }

    private Coordinate getPreciseCoordinate() throws IOException, ParseException {
        Coordinate coordinate = new Coordinate();
        coordinate.setX(getNextNumber());
        coordinate.setY(getNextNumber());
        if (isNumberNext()) {
            coordinate.setZ(getNextNumber());
        }
        if (isNumberNext()) {
            // ignore M value
            getNextNumber();
        }
        precisionModel.makePrecise(coordinate);
        return coordinate;
    }

    private boolean isNumberNext() throws IOException {
        int type = tokenizer.nextToken();
        tokenizer.pushBack();
        return type == StreamTokenizer.TT_WORD;
    }

    /**
     * Parses the next number in the stream. Numbers with exponents are handled.
     * <tt>NaN</tt> values are handled correctly, and the case of the "NaN"
     * symbol is not significant.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next token must be a number.
     *
     * @return the next number in the stream
     * @throws ParseException if the next token is not a valid number
     * @throws IOException    if an I/O error occurs
     */
    private double getNextNumber() throws IOException, ParseException {
        int type = tokenizer.nextToken();
        switch (type) {
            case StreamTokenizer.TT_WORD: {
                if (tokenizer.sval.equalsIgnoreCase(NAN_SYMBOL)) {
                    return Double.NaN;
                } else {
                    try {
                        return Double.parseDouble(tokenizer.sval);
                    } catch (NumberFormatException ex) {
                        parseErrorWithLine("Invalid number: " + tokenizer.sval);
                    }
                }
                break;
            }
            default:{

            }
        }
        parseErrorExpected("number");
        return 0.0;
    }

    /**
     * Returns the next EMPTY or L_PAREN in the stream as uppercase text.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next token must be EMPTY or L_PAREN.
     *
     * @return the next EMPTY or L_PAREN in the stream as uppercase text.
     * @throws ParseException if the next token is not EMPTY or L_PAREN
     * @throws IOException    if an I/O error occurs
     */
    private String getNextEmptyOrOpener() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (Z.equalsIgnoreCase(nextWord)) {
            z = true;
            nextWord = getNextWord();
        } else if (M.equalsIgnoreCase(nextWord)) {
            m = true;
            nextWord = getNextWord();
        } else if (ZM.equalsIgnoreCase(nextWord)) {
            z = true;
            m = true;
            nextWord = getNextWord();
        }
        if (EMPTY.equals(nextWord) || L_PAREN.equals(nextWord)) {
            return nextWord;
        }
        parseErrorExpected(EMPTY + " or " + L_PAREN);
        return null;
    }

    /**
     * Returns the next R_PAREN or COMMA in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next token must be R_PAREN or COMMA.
     *
     * @return the next R_PAREN or COMMA in the stream
     * @throws ParseException if the next token is not R_PAREN or COMMA
     * @throws IOException    if an I/O error occurs
     */
    private String getNextCloserOrComma() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (COMMA.equals(nextWord) || R_PAREN.equals(nextWord)) {
            return nextWord;
        }
        parseErrorExpected(COMMA + " or " + R_PAREN);
        return null;
    }

    /**
     * Returns the next R_PAREN in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next token must be R_PAREN.
     *
     * @return the next R_PAREN in the stream
     * @throws ParseException if the next token is not R_PAREN
     * @throws IOException    if an I/O error occurs
     */
    private String getNextCloser() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (R_PAREN.equals(nextWord)) {
            return nextWord;
        }
        parseErrorExpected(R_PAREN);
        return null;
    }

    /**
     * Returns the next word in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next token must be a word.
     *
     * @return the next word in the stream as uppercase text
     * @throws ParseException if the next token is not a word
     * @throws IOException    if an I/O error occurs
     */
    private String getNextWord() throws IOException, ParseException {
        int type = tokenizer.nextToken();
        switch (type) {
            case StreamTokenizer.TT_WORD:

                String word = tokenizer.sval;
                if (word.equalsIgnoreCase(EMPTY)){
                    return EMPTY;
                }

                return word;

            case '(':
                return L_PAREN;
            case ')':
                return R_PAREN;
            case ',':
                return COMMA;
            default:{

            }
        }
        parseErrorExpected("word");
        return null;
    }

    /**
     * Returns the next word in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next token must be a word.
     *
     * @return the next word in the stream as uppercase text
     * @throws ParseException if the next token is not a word
     * @throws IOException    if an I/O error occurs
     */
    private String lookaheadWord() throws IOException, ParseException {
        String nextWord = getNextWord();
        tokenizer.pushBack();
        return nextWord;
    }

    /**
     * Throws a formatted ParseException reporting that the current token was
     * unexpected.
     *
     * @param expected a description of what was expected
     * @throws ParseException AssertionFailedException if an invalid token is encountered
     */
    private void parseErrorExpected(String expected) throws ParseException {
        // throws Asserts for tokens that should never be seen
        if (tokenizer.ttype == StreamTokenizer.TT_NUMBER){
            Assert.shouldNeverReachHere("Unexpected NUMBER token");
        }
        if (tokenizer.ttype == StreamTokenizer.TT_EOL){
            Assert.shouldNeverReachHere("Unexpected EOL token");
        }

        String tokenStr = tokenString();
        parseErrorWithLine("Expected " + expected + " but found " + tokenStr);
    }

    private void parseErrorWithLine(String msg) throws ParseException {
        throw new ParseException(msg + " (line " + tokenizer.lineno() + ")");
    }

    /**
     * Gets a description of the current token
     *
     * @return a description of the current token
     */
    private String tokenString() {
        switch (tokenizer.ttype) {
            case StreamTokenizer.TT_NUMBER:
                return "<NUMBER>";
            case StreamTokenizer.TT_EOL:
                return "End-of-Line";
            case StreamTokenizer.TT_EOF:
                return "End-of-Stream";
            case StreamTokenizer.TT_WORD:
                return "'" + tokenizer.sval + "'";
            default:{

            }
        }
        return "'" + (char) tokenizer.ttype + "'";
    }

    /**
     * Creates a <code>Geometry</code> using the next token in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;Geometry Tagged Text&gt;.
     *
     * @return a <code>Geometry</code> specified by the next token in the stream
     * @throws ParseException if the coordinates used to create a <code>Polygon</code>
     *                        shell and holes do not form closed linestrings, or if an
     *                        unexpected token was encountered
     * @throws IOException    if an I/O error occurs
     */
    private Geometry readGeometryTaggedText() throws IOException, ParseException {

        String type;
        try {
            type = getNextWord();
            if(type == null){
                return  null;
            }
            type= type.toUpperCase();

            if (type.endsWith(Z)){
                z = true;
            }
            if (type.endsWith(M)){
                m = true;
            }
        } catch (IOException e) {
            return null;
        }

        if (type.startsWith(POINT)) {
            return readPointText();
        } else if (type.startsWith(LINESTRING)) {
            return readLineStringText();
        } else if (type.startsWith(LINEARRING)) {
            return readLinearRingText();
        } else if (type.startsWith(POLYGON)) {
            return readPolygonText();
        } else if (type.startsWith(MULTI_POINT)) {
            return readMultiPointText();
        } else if (type.startsWith(MULTI_LINESTRING)) {
            return readMultiLineStringText();
        } else if (type.startsWith(MULTI_POLYGON)) {
            return readMultiPolygonText();
        }
        parseErrorWithLine("Unknown geometry type: " + type);
        return null;
    }

    /**
     * Creates a <code>Point</code> using the next token in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;Point Text&gt;.
     *
     * @return a <code>Point</code> specified by the next token in the stream
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if an unexpected token was encountered
     */
    private Point readPointText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (EMPTY.equals(nextToken)) {
            return geometryFactory
                    .createPoint(geometryFactory.getCoordinateSequenceFactory().create(new Coordinate[]{}));
        }
        Point point = geometryFactory.createPoint(getPreciseCoordinate());
        getNextCloser();
        return point;
    }

    /**
     * Creates a <code>LineString</code> using the next token in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;LineString Text&gt;.
     *
     * @return a <code>LineString</code> specified by the next token in the
     * stream
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if an unexpected token was encountered
     */
    private LineString readLineStringText() throws IOException, ParseException {
        return geometryFactory.createLineString(getCoordinates());
    }

    /**
     * Creates a <code>LinearRing</code> using the next token in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;LineString Text&gt;.
     *
     * @return a <code>LinearRing</code> specified by the next token in the
     * stream
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if the coordinates used to create the <code>LinearRing</code>
     *                        do not form a closed linestring, or if an unexpected token
     *                        was encountered
     */
    private LinearRing readLinearRingText() throws IOException, ParseException {
        return geometryFactory.createLinearRing(getCoordinates());
    }

    /*
     * private MultiPoint OLDreadMultiPointText() throws IOException,
     * ParseException { return
     * geometryFactory.createMultiPoint(toPoints(getCoordinates())); }
     */

    private static final boolean ALLOW_OLD_JTS_MULTIPOINT_SYNTAX = true;

    /**
     * Creates a <code>MultiPoint</code> using the next tokens in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;MultiPoint Text&gt;.
     *
     * @return a <code>MultiPoint</code> specified by the next token in the
     * stream
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if an unexpected token was encountered
     */
    private MultiPoint readMultiPointText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (EMPTY.equals(nextToken)) {
            return geometryFactory.createMultiPoint(new Point[0]);
        }

        // check for old-style JTS syntax and parse it if present
        // MD 2009-02-21 - this is only provided for backwards compatibility for
        // a few versions
        if (ALLOW_OLD_JTS_MULTIPOINT_SYNTAX) {
            String nextWord = lookaheadWord();
            if (!nextWord.equals(L_PAREN)) {
                return geometryFactory.createMultiPoint(toPoints(getCoordinatesNoLeftParen()));
            }
        }

        List<Point> points = new ArrayList<>();
        Point point = readPointText();
        points.add(point);
        nextToken = getNextCloserOrComma();
        while (COMMA.equals(nextToken)) {
            point = readPointText();
            points.add(point);
            nextToken = getNextCloserOrComma();
        }
        Point[] array = new Point[points.size()];
        return geometryFactory.createMultiPoint( points.toArray(array));
    }

    /**
     * Creates an array of <code>Point</code>s having the given
     * <code>Coordinate</code> s.
     *
     * @param coordinates the <code>Coordinate</code>s with which to create the
     *                    <code>Point</code>s
     * @return <code>Point</code>s created using this <code>WKTReader</code> s
     * <code>GeometryFactory</code>
     */
    private Point[] toPoints(Coordinate[] coordinates) {
        List<Point> points = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            points.add(geometryFactory.createPoint(coordinate));
        }
        return points.toArray(new Point[]{});
    }

    /**
     * Creates a <code>Polygon</code> using the next token in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;Polygon Text&gt;.
     *
     * @return a <code>Polygon</code> specified by the next token in the stream
     * @throws ParseException if the coordinates used to create the <code>Polygon</code>
     *                        shell and holes do not form closed linestrings, or if an
     *                        unexpected token was encountered.
     * @throws IOException    if an I/O error occurs
     */
    private Polygon readPolygonText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (EMPTY.equals(nextToken)) {
            return geometryFactory.createPolygon(geometryFactory.createLinearRing(new Coordinate[]{}),
                    new LinearRing[]{});
        }
        List<LinearRing> holes = new ArrayList<>();
        LinearRing shell = readLinearRingText();
        nextToken = getNextCloserOrComma();
        while (COMMA.equals(nextToken)) {
            LinearRing hole = readLinearRingText();
            holes.add(hole);
            nextToken = getNextCloserOrComma();
        }
        LinearRing[] array = new LinearRing[holes.size()];
        return geometryFactory.createPolygon(shell, holes.toArray(array));
    }

    /**
     * Creates a <code>MultiLineString</code> using the next token in the
     * stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;MultiLineString Text&gt;.
     *
     * @return a <code>MultiLineString</code> specified by the next token in the
     * stream
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if an unexpected token was encountered
     */
    private MultiLineString readMultiLineStringText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (EMPTY.equals(nextToken)) {
            return geometryFactory.createMultiLineString(new LineString[]{});
        }
        List<LineString> lineStrings = new ArrayList<>();
        LineString lineString = readLineStringText();
        lineStrings.add(lineString);
        nextToken = getNextCloserOrComma();
        while (COMMA.equals(nextToken)) {
            lineString = readLineStringText();
            lineStrings.add(lineString);
            nextToken = getNextCloserOrComma();
        }
        LineString[] array = new LineString[lineStrings.size()];
        return geometryFactory.createMultiLineString(lineStrings.toArray(array));
    }

    /**
     * Creates a <code>MultiPolygon</code> using the next token in the stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;MultiPolygon Text&gt;.
     *
     * @return a <code>MultiPolygon</code> specified by the next token in the
     * stream, or if if the coordinates used to create the
     * <code>Polygon</code> shells and holes do not form closed
     * linestrings.
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if an unexpected token was encountered
     */
    private MultiPolygon readMultiPolygonText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (EMPTY.equals(nextToken)) {
            return new MultiPolygon(null, geometryFactory);
        }
        List<Polygon> polygons = new ArrayList<>();
        Polygon polygon = readPolygonText();
        polygons.add(polygon);
        nextToken = getNextCloserOrComma();
        while (COMMA.equals(nextToken)) {
            polygon = readPolygonText();
            polygons.add(polygon);
            nextToken = getNextCloserOrComma();
        }
        Polygon[] array = new Polygon[polygons.size()];
        return geometryFactory.createMultiPolygon(polygons.toArray(array));
    }

    /**
     * Creates a <code>GeometryCollection</code> using the next token in the
     * stream.
     * <p>
     * tokenizer tokenizer over a stream of text in Well-known Text format. The
     * next tokens must form a &lt;GeometryCollection Text&gt;.
     *
     * @return a <code>GeometryCollection</code> specified by the next token in
     * the stream
     * @throws ParseException if the coordinates used to create a <code>Polygon</code>
     *                        shell and holes do not form closed linestrings, or if an
     *                        unexpected token was encountered
     * @throws IOException    if an I/O error occurs
     */
    private GeometryCollection readGeometryCollectionText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (EMPTY.equals(nextToken)) {
            return geometryFactory.createGeometryCollection(new Geometry[]{});
        }
        List<Geometry> geometries = new ArrayList<>();
        Geometry geometry = readGeometryTaggedText();
        geometries.add(geometry);
        nextToken = getNextCloserOrComma();
        while (COMMA.equals(nextToken)) {
            geometry = readGeometryTaggedText();
            geometries.add(geometry);
            nextToken = getNextCloserOrComma();
        }
        Geometry[] array = new Geometry[geometries.size()];
        return geometryFactory.createGeometryCollection(geometries.toArray(array));
    }


}
