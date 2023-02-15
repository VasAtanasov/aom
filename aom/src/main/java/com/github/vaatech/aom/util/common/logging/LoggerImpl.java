package com.github.vaatech.aom.util.common.logging;

import org.slf4j.LoggerFactory;

class LoggerImpl implements Logger {
  private static final String PREFIX = "bg.autohouse.";

  private final org.slf4j.Logger logger;

  public LoggerImpl(String name) {
    logger = LoggerFactory.getLogger(PREFIX + name);
  }

  public LoggerImpl(Class<?> clazz) {
    logger = LoggerFactory.getLogger(clazz);
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  @Override
  public void debug(String msg) {
    logger.debug(msg);
  }

  @Override
  public void debug(String format, Object... arguments) {
    logger.debug(format, arguments);
  }

  @Override
  public void debug(String msg, Throwable throwable) {
    logger.debug(msg, throwable);
  }

  @Override
  public void error(String msg) {
    logger.error(msg);
  }

  @Override
  public void error(String format, Object... arguments) {
    logger.error(format, arguments);
  }

  @Override
  public void error(String msg, Throwable throwable) {
    logger.error(msg, throwable);
  }

  @Override
  public void info(String msg) {
    logger.info(msg);
  }

  @Override
  public void info(String format, Object... arguments) {
    logger.info(format, arguments);
  }

  @Override
  public void info(String msg, Throwable throwable) {
    logger.info(msg, throwable);
  }

  @Override
  public void trace(String msg) {
    logger.trace(msg);
  }

  @Override
  public void trace(String format, Object... arguments) {
    logger.trace(format, arguments);
  }

  @Override
  public void trace(String msg, Throwable throwable) {
    logger.trace(msg, throwable);
  }

  @Override
  public void warn(String msg) {
    logger.warn(msg);
  }

  @Override
  public void warn(String format, Object... arguments) {
    logger.warn(format, arguments);
  }

  @Override
  public void warn(String msg, Throwable throwable) {
    logger.warn(msg, throwable);
  }
}
