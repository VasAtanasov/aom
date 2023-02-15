-- liquibase formatted sql

-- changeset vasil:20221010_010
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'maker';

-- comment: Create table for maker entity

CREATE IF NOT EXISTS TABLE `maker`
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


-- changeset vasil:20221010_020
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'model';

-- comment: Create table for model entity

CREATE IF NOT EXISTS TABLE `model`
(
    `id`       VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name`     VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `maker_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY        `FK_MODEL_MAKE_ID` (`maker_id`),
    CONSTRAINT `FK_MODEL_MAKE_ID` FOREIGN KEY (`maker_id`) REFERENCES `maker` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX MODEL_NAME ON model (name);

-- rollback ALTER TABLE `model` DROP CONSTRAINT FK_MODEL_MAKE_ID;
-- rollback DROP INDEX MODEL_NAME on `model`;
-- rollback DROP TABLE IF EXISTS `model`;


-- changeset vasil:20221010_030
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'car';

-- comment: Create table for car entity

CREATE IF NOT EXISTS TABLE `car`
(
    `id`       VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    `year`     INT DEFAULT NULL,
    `model_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY        `FK_CARS_MODELS_ID` (`model_id`),
    CONSTRAINT `FK_CARS_MODELS_ID` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX CAR_YEAR ON car (year);

-- rollback ALTER TABLE `car` DROP CONSTRAINT FK_CARS_MODELS_ID;
-- rollback DROP INDEX CAR_YEAR on `car`;
-- rollback DROP TABLE IF EXISTS `car`;


-- changeset vasil:20221010_040
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'trim';

-- comment: Create table for trim entity

CREATE IF NOT EXISTS TABLE `trim`
(
    `id`     VARCHAR(12) COLLATE utf8mb4_unicode_ci  NOT NULL,
    `trim`   VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `car_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci  NOT NULL,
    PRIMARY KEY (`id`),
    KEY      `FK_TRIMS_CARS_ID` (`car_id`),
    CONSTRAINT `FK_TRIMS_CARS_ID` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE IF NOT EXISTS INDEX TRIM_NAME ON `trim` (trim);

-- rollback ALTER TABLE `trim` DROP CONSTRAINT FK_TRIMS_CARS_ID;
-- rollback DROP INDEX TRIM_NAME on `trim`;
-- rollback DROP TABLE IF EXISTS `trim`;


-- changeset vasil:20221010_050
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'engine';

-- comment: Create table for engine entity

CREATE IF NOT EXISTS TABLE `engine`
(
    `id`          BIGINT                                 NOT NULL,
    `is_standard` BIT(1)                                  DEFAULT NULL,
    `name`        VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `trim_id`     VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY           `FK_ENGINES_TRIMS_ID` (`trim_id`),
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


-- changeset vasil:20221010_060
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'transmission';

-- comment: Create table for transmission entity

CREATE IF NOT EXISTS TABLE `transmission`
(
    `id`      BIGINT                                 NOT NULL,
    `name`    VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `trim_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY       `FK_TRANSMISSIONS_TRIMS_ID` (`trim_id`),
    CONSTRAINT `FK_TRANSMISSIONS_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX TRANSMISSION_NAME ON `transmission` (name);

-- rollback ALTER TABLE `transmission` DROP CONSTRAINT FK_TRANSMISSIONS_TRIMS_ID;
-- rollback DROP INDEX TRANSMISSION_NAME on `transmission`;
-- rollback DROP TABLE IF EXISTS `transmission`;


-- changeset vasil:20221010_070
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'option';

-- comment: Create table for option entity

CREATE IF NOT EXISTS TABLE `option`
(
    `id`        BIGINT                                 NOT NULL,
    `active`    BIT(1)                                  DEFAULT NULL,
    `important` BIT(1)                                  DEFAULT NULL,
    `name`      VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `trim_id`   VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY         `FK_OPTIONS_TRIMS_ID` (`trim_id`),
    CONSTRAINT `FK_OPTIONS_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX OPTION_NAME ON `option` (name);

-- rollback ALTER TABLE `option` DROP CONSTRAINT FK_OPTIONS_TRIMS_ID;
-- rollback DROP INDEX OPTION_NAME on `option`;
-- rollback DROP TABLE IF EXISTS `option`;