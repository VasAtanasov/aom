package com.github.vaatech.aom.core;

import org.hibernate.dialect.MySQLDialect;

public class MySQL8UnicodeDialect extends MySQLDialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
    }
}
