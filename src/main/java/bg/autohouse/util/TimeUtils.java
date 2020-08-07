package bg.autohouse.util;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class TimeUtils {

  public Date now() {
    return new Date();
  }

  public static Date dateOf(long milliseconds) {
    return new Date(milliseconds);
  }

}
