-- liquibase formatted sql

-- changeset vasil:00060
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'transmission';

-- comment: Create table for transmission entity

CREATE TABLE `transmission`
(
    `id`      BIGINT                                 NOT NULL,
    `name`    VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `trim_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_TRANSMISSIONS_TRIMS_ID` (`trim_id`),
    CONSTRAINT `FK_TRANSMISSIONS_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX TRANSMISSION_NAME ON `transmission` (name);

-- rollback ALTER TABLE `transmission` DROP CONSTRAINT FK_TRANSMISSIONS_TRIMS_ID;
-- rollback DROP INDEX TRANSMISSION_NAME on `transmission`;
-- rollback DROP TABLE IF EXISTS `transmission`;