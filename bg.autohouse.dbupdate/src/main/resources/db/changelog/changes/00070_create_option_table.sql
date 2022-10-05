-- liquibase formatted sql

-- changeset vasil:00070
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'option';

-- comment: Create table for option entity

CREATE TABLE `option`
(
    `id`        BIGINT                                 NOT NULL,
    `active`    BIT(1)                                  DEFAULT NULL,
    `important` BIT(1)                                  DEFAULT NULL,
    `name`      VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `trim_id`   VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_OPTIONS_TRIMS_ID` (`trim_id`),
    CONSTRAINT `FK_OPTIONS_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX OPTION_NAME ON `option` (name);

-- rollback ALTER TABLE `option` DROP CONSTRAINT FK_OPTIONS_TRIMS_ID;
-- rollback DROP INDEX OPTION_NAME on `option`;
-- rollback DROP TABLE IF EXISTS `option`;