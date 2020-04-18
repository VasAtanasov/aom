package bg.autohouse.util;

public final class DebugProfileUtil {

  public static long elapsedTime(long startTime) {
    return System.currentTimeMillis() - startTime;
  }

  public static double elapsedTimeInSeconds(long startTime) {
    return (double) elapsedTime(startTime) / 1000.0;
  }

  public static long currentHeapSizeMb() {
    return Runtime.getRuntime().totalMemory() / (1024 * 1024);
  }

  private static long maxHeapSizeMb() {
    return Runtime.getRuntime().maxMemory() / (1024 * 1024);
  }

  private static long freeHeapSizeMb() {
    return Runtime.getRuntime().freeMemory() / (1024 * 1024);
  }

  public static String memoryStats() {
    return "Current heap: "
        + currentHeapSizeMb()
        + "mb and max : "
        + maxHeapSizeMb()
        + "mb, leaving free: "
        + freeHeapSizeMb()
        + "mb";
  }
}
