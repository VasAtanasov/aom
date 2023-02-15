package bg.autohouse.backend.util.common.logging;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TimeSpentLogger {
  private final Logger logger;

  private final List<TimeSpent> timeIntervals = new ArrayList<>();

  private TimeSpentLogger(Logger logger) {
    this.logger = logger;
  }

  public static TimeSpentLogger with(Logger logger) {
    return new TimeSpentLogger(logger);
  }

  public void check(String logDefinition) {
    if (!logger.isDebugEnabled()) {
      return;
    }

    timeIntervals.add(new TimeSpent(System.currentTimeMillis(), logDefinition));
  }

  public void finish() {
    if (!logger.isDebugEnabled()) {
      return;
    }

    if (!timeIntervals.isEmpty()) {
      long startTime = timeIntervals.get(0).getStartTime();
      long globalEndTime = System.currentTimeMillis();
      long globalElapsedTime = globalEndTime - startTime;

      StringBuilder buffer = new StringBuilder("\n");

      for (int i = 1; i < timeIntervals.size(); i++) {
        formatTime(
            buffer,
            startTime,
            timeIntervals.get(i).getStartTime(),
            globalElapsedTime,
            timeIntervals.get(i - 1).getLogDefinition());
        startTime = timeIntervals.get(i).getStartTime();
      }

      // get the last one
      formatTime(
          buffer,
          startTime,
          globalEndTime,
          globalElapsedTime,
          timeIntervals.get(timeIntervals.size() - 1).getLogDefinition());

      // print total sum if more than one
      if (timeIntervals.size() > 1) {
        formatTime(
            buffer, timeIntervals.get(0).getStartTime(), globalEndTime, globalElapsedTime, "total");
      }

      logger.debug(buffer.toString());
    } else {
      logger.debug("Error. Logger not started");
    }

    reset();
  }

  public void reset() {
    timeIntervals.clear();
  }

  private void formatTime(
      StringBuilder buffer,
      long startTime,
      long endTime,
      long globalElapsedTime,
      String logDefinition) {
    long elapsedTime = endTime - startTime;

    int millis = (int) elapsedTime % 1000;
    int seconds = (int) (elapsedTime / 1000) % 60;
    int minutes = (int) (elapsedTime / (60 * 1000)) % 60;
    int hours = (int) elapsedTime / (60 * 60 * 1000);

    NumberFormat hmsFormat = new DecimalFormat("00");
    String hoursStr = hmsFormat.format(hours);
    String minutesStr = hmsFormat.format(minutes);
    String secondsStr = hmsFormat.format(seconds);

    NumberFormat millisFormat = new DecimalFormat("000");
    String millisStr = millisFormat.format(millis);

    buffer.append(hoursStr);
    buffer.append(":");
    buffer.append(minutesStr);
    buffer.append(":");
    buffer.append(secondsStr);
    buffer.append(".");
    buffer.append(millisStr);

    buffer.append("\t");

    if (elapsedTime != globalElapsedTime) {
      String percentage = millisFormat.format(elapsedTime * 100 / globalElapsedTime);
      buffer.append(percentage);
      buffer.append("%");
      buffer.append("\t");
    }

    buffer.append(logDefinition);
    buffer.append("\n");
  }

  private static class TimeSpent {
    private long startTime;
    private final String logDefinition;

    public TimeSpent(long startTime, String logDefinition) {
      this.startTime = startTime;
      this.logDefinition = logDefinition;
    }

    public long getStartTime() {
      return startTime;
    }

    public void setStartTime(long startTime) {
      this.startTime = startTime;
    }

    public String getLogDefinition() {
      return logDefinition;
    }
  }
}
