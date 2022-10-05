-- liquibase formatted sql

-- changeset vasil:00040
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'trim';

-- comment: Create table for trim entity

CREATE TABLE `trim`
(
    `id`     VARCHAR(12) COLLATE utf8mb4_unicode_ci  NOT NULL,
    `trim`   VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `car_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci  NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_TRIMS_CARS_ID` (`car_id`),
    CONSTRAINT `FK_TRIMS_CARS_ID` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX TRIM_NAME ON `trim` (trim);

-- rollback ALTER TABLE `trim` DROP CONSTRAINT FK_TRIMS_CARS_ID;
-- rollback DROP INDEX TRIM_NAME on `trim`;
-- rollback DROP TABLE IF EXISTS `trim`;