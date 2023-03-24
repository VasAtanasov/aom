package com.github.vaatech.aom.test;

import java.lang.annotation.*;

/**
 * Annotate JUnit test methods with this class in order to assert SQL query operations executed by
 * Hibernate. The main purpose of that is to address so called "N+1" issues.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface HibernateStatisticsAssertions {

  // Maximum number of queries; a value less than zero accounts for undefined.
  int maxQueries() default Integer.MAX_VALUE;

  // Exact count of queries; a value less than zero accounts for undefined.
  int queryCount() default -1;
}
