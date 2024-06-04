package com.et.geodesy.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class GeodsyDistanceUtils {

    /**
     *
     *
     * @param lonA A longitude
     * @param latA A latitude
     * @param lonB B longitude
     * @param latB B latitude
     * @param newScale The result is kept to decimal places
     * @return distant （m）
     */
    public static double getDistance(Double lonA, Double latA, Double lonB, Double latB,int newScale) {
        GlobalCoordinates source = new GlobalCoordinates(latA, lonA);
        GlobalCoordinates target = new GlobalCoordinates(latB, lonB);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target);
        double distance = geoCurve.getEllipsoidalDistance();
        BigDecimal distanceBig = new BigDecimal(distance).setScale(newScale, RoundingMode.UP);
        return distanceBig.doubleValue();
    }

}