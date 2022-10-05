-- liquibase formatted sql

-- changeset vasil:00020
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'model';

-- comment: Create table for model entity

CREATE TABLE `model`
(
    `id`       VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name`     VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `maker_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_MODEL_MAKE_ID` (`maker_id`),
    CONSTRAINT `FK_MODEL_MAKE_ID` FOREIGN KEY (`maker_id`) REFERENCES `maker` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX MODEL_NAME ON model (name);

-- rollback ALTER TABLE `model` DROP CONSTRAINT FK_MODEL_MAKE_ID;
-- rollback DROP INDEX MODEL_NAME on `model`;
-- rollback DROP TABLE IF EXISTS `model`;