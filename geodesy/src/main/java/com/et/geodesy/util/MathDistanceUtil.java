package com.et.geodesy.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

/**
 *
 *
 * <p>formula：S=R·arccos[cosβ1·cosβ·2cos(α1-α2)+sinβ1·sinβ2]
 */
@UtilityClass
public class MathDistanceUtil {

  private static final double EARTH_RADIUS = 6371393;

  private static final double DEGREES_TO_RADIANS = 0.017453292519943295;

  /**
   * Calculate according to formula
   *
   * @param longitude1
   * @param latitude1
   * @param longitude2
   * @param latitude2
   * @return
   */
  public static double getDistance(
      Double longitude1, Double latitude1, Double longitude2, Double latitude2) {
    double radiansLongitude1 = toRadians(longitude1);
    double radiansLatitude1 = toRadians(latitude1);
    double radiansLongitude2 = toRadians(longitude2);
    double radiansLatitude2 = Math.toRadians(latitude2);

    final double cos =
        BigDecimal.valueOf(Math.cos(radiansLatitude1))
            .multiply(BigDecimal.valueOf(Math.cos(radiansLatitude2)))
            .multiply(
                BigDecimal.valueOf(
                    Math.cos(
                        BigDecimal.valueOf(radiansLongitude1)
                            .subtract(BigDecimal.valueOf(radiansLongitude2))
                            .doubleValue())))
            .add(
                BigDecimal.valueOf(Math.sin(radiansLatitude1))
                    .multiply(BigDecimal.valueOf(Math.sin(radiansLatitude2))))
            .doubleValue();

    double acos = Math.acos(cos);
    return BigDecimal.valueOf(EARTH_RADIUS).multiply(BigDecimal.valueOf(acos)).doubleValue();
  }

  /**
   * refer：{@link Math#toRadians(double)}
   *
   * @param value value
   * @return {double}
   */
  private static double toRadians(double value) {
    return BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(DEGREES_TO_RADIANS)).doubleValue();
  }


}
