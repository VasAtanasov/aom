-- liquibase formatted sql

-- changeset vasil:00010
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'maker';

-- comment: Create table for maker entity

CREATE TABLE `maker`
(
    `id`   VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX MAKER_NAME ON maker (name);

-- rollback DROP INDEX MAKER_NAME on `maker`;
-- rollback DROP TABLE IF EXISTS `maker`;