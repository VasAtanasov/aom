package bg.autohouse.util;

import java.util.Calendar;
import java.util.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Clock {

  public static long now() {
    return System.currentTimeMillis();
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
}
