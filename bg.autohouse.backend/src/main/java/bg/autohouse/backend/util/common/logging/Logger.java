package bg.autohouse.backend.util.common.logging;

public interface Logger {
  boolean isDebugEnabled();

  void debug(String msg);

  void debug(String format, Object... arguments);

  void debug(String msg, Throwable throwable);

  void error(String msg);

  void error(String format, Object... arguments);

  void error(String msg, Throwable throwable);

  void info(String msg);

  void info(String format, Object... arguments);

  void info(String msg, Throwable throwable);

  void trace(String msg);

  void trace(String format, Object... arguments);

  void trace(String msg, Throwable throwable);

  void warn(String msg);

  void warn(String format, Object... arguments);

  void warn(String msg, Throwable throwable);
}
