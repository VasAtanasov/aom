package bg.autohouse.util.common.datetime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utils which provides methods to work with DateTime.
 */
public final class DateTimeUtils
{
    private static final String NOW = "now";
    private static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private DateTimeUtils() {
    }

    public static ZonedDateTime currentInUtc() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    public static ZonedDateTime fromZonedString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        if (NOW.equalsIgnoreCase(value)) {
            return ZonedDateTime.now();
        }
        return ZonedDateTime.parse(value, getDateTimeFormatter());
    }

    //output time in UTC
    public static String toUtcString(ZonedDateTime value) {
        if (value == null) {
            return "";
        }
        final LocalDateTime utcDateTime =
                LocalDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC);
        return DateTimeFormatter.ofPattern(ISO_DATE_FORMAT).format(utcDateTime);
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    }

}