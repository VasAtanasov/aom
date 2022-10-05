-- liquibase formatted sql

-- changeset vasil:00030
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'car';

-- comment: Create table for car entity

CREATE TABLE `car`
(
    `id`       VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    `year`     INT DEFAULT NULL,
    `model_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_CARS_MODELS_ID` (`model_id`),
    CONSTRAINT `FK_CARS_MODELS_ID` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX CAR_YEAR ON car (year);

-- rollback ALTER TABLE `car` DROP CONSTRAINT FK_CARS_MODELS_ID;
-- rollback DROP INDEX CAR_YEAR on `car`;
-- rollback DROP TABLE IF EXISTS `car`;