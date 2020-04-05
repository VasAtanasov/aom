package bg.autohouse.util;

import bg.autohouse.data.models.geo.GeoLocation;
import java.util.List;
import java.util.Objects;
import javax.persistence.Query;

public class GeoLocationUtils {

  private static final double M_PER_DEGREE = 111045;
  public static final int DEFAULT_RADIUS = 5000;

  private GeoLocationUtils() {
    // utility
  }

  public static GeoLocation centralLocation(List<GeoLocation> geoLocations) {
    Objects.requireNonNull(geoLocations);
    if (geoLocations.isEmpty()) {
      return null;
    }
    int locationCount = geoLocations.size();

    if (locationCount == 1) {
      return geoLocations.iterator().next();
    }

    double x = 0;
    double y = 0;
    double z = 0;

    for (GeoLocation geoLocation : geoLocations) {
      double latitude = Math.toRadians(geoLocation.getLatitude()) * Math.PI / 180;
      double longitude = Math.toRadians(geoLocation.getLongitude()) * Math.PI / 180;

      x += Math.cos(latitude) * Math.cos(longitude);
      y += Math.cos(latitude) * Math.sin(longitude);
      z += Math.sin(latitude);
    }

    x = x / locationCount;
    y = y / locationCount;
    z = z / locationCount;

    double centralLongitude = Math.atan2(y, x);
    double centralSquareRoot = Math.sqrt(x * x + y * y);
    double centralLatitude = Math.atan2(z, centralSquareRoot);

    double midPointLatitude = Math.toDegrees(centralLatitude * 180 / Math.PI);
    double midPointLongitude = Math.toDegrees(centralLongitude * 180 / Math.PI);
    return new GeoLocation(midPointLatitude, midPointLongitude);
  }
  /*
   * Fast nearest-location finder for SQL (MySQL, PostgreSQL, SQL Server)
   * Source: http://www.plumislandmedia.net/mysql/haversine-mysql-nearest-loc/
   */
  public static String locationFilterSuffix(String locationEntityLabel) {
    return String.format(
        "%1$s.latitude "
            + "    BETWEEN :latpoint  - (:radius / :distance_unit) "
            + "        AND :latpoint  + (:radius / :distance_unit) "
            + "AND %1$s.longitude "
            + "    BETWEEN :longpoint - (:radius / (:distance_unit * COS(RADIANS(:latpoint)))) "
            + "        AND :longpoint + (:radius / (:distance_unit * COS(RADIANS(:latpoint)))) "
            + "AND :radius >= (:distance_unit "
            + "         * DEGREES(ACOS(COS(RADIANS(:latpoint)) "
            + "         * COS(RADIANS(%1$s.latitude)) "
            + "         * COS(RADIANS(:longpoint - %1$s.longitude)) "
            + "         + SIN(RADIANS(:latpoint)) "
            + "         * SIN(RADIANS(%1$s.latitude)))))",
        locationEntityLabel);
  }

  public static void addLocationParamsToQuery(
      Query query, GeoLocation location, Integer radiusInMetres) {
    query.setParameter(
        "radius", radiusInMetres != null ? (double) radiusInMetres : (double) DEFAULT_RADIUS);
    query.setParameter("distance_unit", M_PER_DEGREE);
    query.setParameter("latpoint", location.getLatitude());
    query.setParameter("longpoint", location.getLongitude());
  }
}
