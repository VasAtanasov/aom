package bg.autohouse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtils {

  public Date now() {
    return new Date();
  }

  public static Date dateOf(long milliseconds) {
    return new Date(milliseconds);
  }

  public static Integer calculateAge(Date dob) {
    Calendar birthDay = Calendar.getInstance();
    birthDay.setTimeInMillis(dob.getTime());
    // create calendar object for current day
    long currentTime = System.currentTimeMillis();
    Calendar now = Calendar.getInstance();
    now.setTimeInMillis(currentTime);
    // Get difference between years
    return now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
  }

  private static final String FORMAT = "dd_MM_yyyy";
  public static final String DATE_HOUR_FORMAT = "dd/MM/yyyy HH:mm";
  public static final String DATE_HOUR_FILE_FORMAT = "dd_MM_yyyy_HH_mm";
  public static final String DATE_FORMAT = "dd/MM/yyyy";

  public static Date parseDate(final String date) throws Exception {
    return parse(date, getSdfDate());
  }

  public static Date parseDate(final String date, final String format) {
    return parse(date, new SimpleDateFormat(format));
  }

  public static Date parseDate(final Object date, final String format) {
    if (date == null) {
      return null;
    }
    return parse(date.toString(), new SimpleDateFormat(format));
  }

  public static String formatDate() {
    return format(now(), getSdfDate());
  }

  public static String formatDate(final Date date) {
    return format(date, getSdfDate());
  }

  public static String formatDate(final Date date, final String format) {
    return format(date, new SimpleDateFormat(format));
  }

  public static Date parse(final String date, final SimpleDateFormat sdf) {
    try {
      return date == null ? null : sdf.parse(date);
    } catch (final ParseException e) {
      return null;
    }
  }

  public static String format(final Date date, final SimpleDateFormat sdf) {
    return date == null ? "" : sdf.format(date);
  }

  public static String formatShortDate(final Date date, final SimpleDateFormat sdf) {
    final String fecha = format(date, sdf);
    return fecha.toUpperCase().charAt(0) + fecha.substring(1, fecha.length());
  }

  public static String formatShortDate(final String date, final SimpleDateFormat sdf)
      throws Exception {
    return formatShortDate(parse(date, sdf), sdf);
  }

  private static SimpleDateFormat getSdfDate() {
    return new SimpleDateFormat(FORMAT);
  }

  public static Double getDifferenceToday(final int hour, final Date currentStateDate) {
    final Date tmp = now();
    long delta = tmp.getTime() - currentStateDate.getTime();
    return (double) (delta /= 1000D * 60D * 60D);
  }
}
