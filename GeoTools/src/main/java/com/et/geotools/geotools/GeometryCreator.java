package com.et.geotools.geotools;

import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * <p>自定义几何对象构造器</p>
 * @author Appleyk
 * @blob https://blog.csdn.net/appleyk
 * @date Created on 上午 11:54 2018-10-12
 */
public class GeometryCreator {


    private static GeometryCreator geometryCreator = null;
    private static GeometryFactory geometryFactory = new GeometryFactory();

    /**
     * 设置保留6位小数，否则GeometryJSON默认保留4位小数
     */
    private static GeometryJSON geometryJson = new GeometryJSON(6);


    private GeometryCreator() {
    }

    /**
     * 返回本类的唯一实例
     * @return
     */
    public static GeometryCreator getInstance() {
        if (geometryCreator == null) {
            return new GeometryCreator();
        }
        return geometryCreator;
    }


    /**
     * 1.1根据X，Y坐标构建一个几何对象： 点 【Point】
     * @param x
     * @param y
     * @return
     */
    public  Point createPoint(double x,double y){
        Coordinate coord = new Coordinate(x, y);
        return  geometryFactory.createPoint(coord);
    }

    /**
     * 1.2根据几何对象的WKT描述【String】创建几何对象： 点 【Point】
     * @return
     * @throws ParseException
     */
    public Point createPointByWKT(String PointWKT) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        return (Point) reader.read(PointWKT);
    }

    /**
     * 1.3根据几何对象的WKT描述【String】创建几何对象：多点 【MultiPoint】
     * @return
     * @throws ParseException
     */
    public MultiPoint createMulPointByWKT(String MPointWKT)throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        return (MultiPoint) reader.read(MPointWKT);
    }

    /**
     * 2.1根据两点 创建几何对象：线 【LineString】
     * @param ax 第一个点的x坐标
     * @param ay 第一个点的y坐标
     * @param bx 第二个点的x坐标
     * @param by 第二个点的y坐标
     * @return
     */
    public LineString createLine(double ax,double ay,double bx,double by){
        Coordinate[] coords  = new Coordinate[] {new Coordinate(ax, ay), new Coordinate(bx, by)};
        return  geometryFactory.createLineString(coords);
    }

    /**
     * 2.2根据线的WKT描述创建几何对象：线 【LineString】
     * @param LineStringWKT
     * @return
     * @throws ParseException
     */
    public LineString createLineByWKT(String LineStringWKT) throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        return (LineString) reader.read(LineStringWKT);
    }

    /**
     * 2.3根据点组合的线数组，创建几何对象：多线 【MultiLineString】
     * @param list
     * @return
     */
    public MultiLineString createMLine(List<Coordinate[]> list){

        if(list == null){
            return null;
        }

        LineString[] lineStrings = new LineString[list.size()];
        int i = 0;
        for (Coordinate[] coordinates : list) {
            lineStrings[i] = geometryFactory.createLineString(coordinates);
        }

        return geometryFactory.createMultiLineString(lineStrings);
    }


    /**
     * 2.4根据几何对象的WKT描述【String】创建几何对象 ： 多线【MultiLineString】
     * @param MLineStringWKT
     * @return
     * @throws ParseException
     */
    public MultiLineString createMLineByWKT(String MLineStringWKT)throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        return (MultiLineString) reader.read(MLineStringWKT);
    }


    /**
     * 3.1 根据几何对象的WKT描述【String】创建几何对象：多边形 【Polygon】
     * @param PolygonWKT
     * @return
     * @throws ParseException
     */
    public Polygon createPolygonByWKT(String PolygonWKT) throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        return (Polygon) reader.read(PolygonWKT);
    }

    /**
     * 3.2 根据几何对象的WKT描述【String】创建几何对象： 多多边形 【MultiPolygon】
     * @param MPolygonWKT
     * @return
     * @throws ParseException
     */
    public MultiPolygon createMulPolygonByWKT(String MPolygonWKT) throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        return  (MultiPolygon) reader.read(MPolygonWKT);
    }

    /**
     * 根据多边形数组 进行多多边形的创建
     * @param polygons
     * @return
     * @throws ParseException
     */
    public MultiPolygon createMulPolygonByPolygon(Polygon[] polygons) throws ParseException{
        return geometryFactory.createMultiPolygon(polygons);
    }

    /**
     * 4.1 根据几何对象数组，创建几何对象集合：【GeometryCollection】
     * @return
     * @throws ParseException
     */
    public GeometryCollection createGeoCollect(Geometry[] geoArray) throws ParseException{
        return geometryFactory.createGeometryCollection(geoArray);
    }

    /**
     * 5.1 根据圆点以及半径创建几何对象：特殊的多边形--圆 【Polygon】
     * @param x 圆点x坐标
     * @param y 圆点y坐标
     * @param radius 半径
     * @return
     */
    public Polygon createCircle(double x, double y, final double radius){

        //圆上面的点个数
        final int sides = 32;
        Coordinate[]  coords = new Coordinate[sides+1];
        for( int i = 0; i < sides; i++){
            double angle = ((double) i / (double) sides) * Math.PI * 2.0;
            double dx = Math.cos( angle ) * radius;
            double dy = Math.sin( angle ) * radius;
            coords[i] = new Coordinate( (double) x + dx, (double) y + dy );
        }
        coords[sides] = coords[0];
        //线性环
        LinearRing ring = geometryFactory.createLinearRing(coords);
        return geometryFactory.createPolygon(ring, null);
    }


    /**
     * 6.1 根据WKT创建环
     * @param ringWKT
     * @return
     * @throws ParseException
     */
    public LinearRing createLinearRingByWKT(String ringWKT) throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        return (LinearRing) reader.read(ringWKT);
    }

    /**
     * 几何对象转GeoJson对象
     * @param geometry
     * @return
     * @throws Exception
     */
    public static String geometryToGeoJson(Geometry geometry) throws Exception {
        if (geometry == null) {
            return null;
        }
        StringWriter writer = new StringWriter();
        geometryJson.write(geometry, writer);
        String geojson = writer.toString();
        writer.close();
        return geojson;
    }

    /**
     * GeoJson转几何对象
     * @param geojson
     * @return
     * @throws Exception
     */
    public static Geometry geoJsonToGeometry(String geojson) throws Exception {
        return geometryJson.read(new StringReader(geojson));
    }

}
