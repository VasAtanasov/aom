package bg.autohouse.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Clock {

  public static long now() {
    return System.currentTimeMillis();
  }
}
