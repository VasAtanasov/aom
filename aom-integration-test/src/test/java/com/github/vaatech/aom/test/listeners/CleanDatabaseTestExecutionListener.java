package com.github.vaatech.aom.test.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class CleanDatabaseTestExecutionListener extends AbstractTestExecutionListener {

    private static final Logger LOGGER =
            LogManager.getFormatterLogger(CleanDatabaseTestExecutionListener.class);

    @Autowired
    private Flyway flyway;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        try {
            initBeans(testContext);
            resetDb();
        } catch (Exception ex) {
            LOGGER.error("Database reset failed", ex);
        }
    }

    private void resetDb() {
        flyway.clean();
        flyway.migrate();
    }

    @Override
    public int getOrder() {
        // Ensures that this TestExecutionListener is run before
        // SqlScriptExecutionTestListener which handles @Sql.
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private void initBeans(TestContext testContext) {
        testContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
    }
}
