-- liquibase formatted sql

-- changeset vasil:00050
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'engine';

-- comment: Create table for engine entity

CREATE TABLE `engine`
(
    `id`          BIGINT                                 NOT NULL,
    `is_standard` BIT(1)                                  DEFAULT NULL,
    `name`        VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `trim_id`     VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_ENGINES_TRIMS_ID` (`trim_id`),
    CONSTRAINT `FK_ENGINES_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX ENGINE_NAME ON `engine` (name);
CREATE INDEX ENGINE_IS_STANDARD ON `engine` (is_standard);

-- rollback ALTER TABLE `engine` DROP CONSTRAINT FK_ENGINES_TRIMS_ID;
-- rollback DROP INDEX ENGINE_NAME on `engine`;
-- rollback DROP INDEX ENGINE_IS_STANDARD on `engine`;
-- rollback DROP TABLE IF EXISTS `engine`;