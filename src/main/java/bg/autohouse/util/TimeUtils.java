package bg.autohouse.util;

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
}
