package com.github.vaatech.aom.core.util.logging;

public class ApplicationLoggerFactory {
  private ApplicationLoggerFactory() {}

  public static Logger getLogger(String name) {
    return new LoggerImpl(name);
  }

  public static Logger getLogger(Class<?> clazz) {
    return new LoggerImpl(clazz);
  }
}
