package com.github.vaatech.aom.test;

import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public abstract class HibernateStatisticsExtension implements AfterEachCallback {

  private HibernateStatisticsAssertions assertions;

  protected abstract Statistics getStatistics();

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    assertions = context.getRequiredTestMethod().getAnnotation(HibernateStatisticsAssertions.class);
    if (assertions != null) {
      checkAssertions(getStatistics());
    }
  }

  protected void checkAssertions(final Statistics stats) {
    if (assertions.maxQueries() >= 0) {
      HibernateStatisticsHelper.assertCurrentQueryCountAtMost(stats, assertions.maxQueries());
    }
    if (assertions.queryCount() >= 0) {
      HibernateStatisticsHelper.assertCurrentQueryCount(stats, assertions.queryCount());
    }
  }
}
