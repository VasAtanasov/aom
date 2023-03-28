DROP PROCEDURE IF EXISTS ddl_vehicle_tables;

DELIMITER //

CREATE PROCEDURE ddl_vehicle_tables()
BEGIN

    CREATE TABLE IF NOT EXISTS `maker`
    (
        `id`   int NOT NULL AUTO_INCREMENT,
        `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
        PRIMARY KEY (`id`),
        KEY `INDEX_MAKER_NAME` (`name`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

    CREATE TABLE IF NOT EXISTS `model`
    (
        `id`       int NOT NULL AUTO_INCREMENT,
        `name`     varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
        `maker_id` int NOT NULL,
        PRIMARY KEY (`id`),
        KEY `INDEX_MODEL_NAME` (`name`),
        KEY `FK_MODEL_TO_MAKE_ID` (`maker_id`),
        CONSTRAINT `FK_MODEL_TO_MAKE_ID` FOREIGN KEY (`maker_id`) REFERENCES `maker` (`id`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

    CREATE TABLE IF NOT EXISTS `model_year`
    (
        `id`       int NOT NULL AUTO_INCREMENT,
        `year`     int NOT NULL,
        `model_id` int NOT NULL,
        PRIMARY KEY (`id`),
        KEY `INDEX_MODEL_YEAR_YEAR` (`year`),
        KEY `FK_CARS_TO_MODELS_ID` (`model_id`),
        CONSTRAINT `FK_CARS_TO_MODELS_ID` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

    CREATE TABLE IF NOT EXISTS `trim`
    (
        `id`     int                                     NOT NULL AUTO_INCREMENT,
        `trim`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
        `model_year_id` int                                     NOT NULL,
        PRIMARY KEY (`id`),
        KEY `INDEX_TRIM_NAME` (`trim`),
        KEY `FK_TRIMS_TO_CARS_ID` (`model_year_id`),
        CONSTRAINT `FK_TRIMS_TO_CARS_ID` FOREIGN KEY (`model_year_id`) REFERENCES `model_year` (`id`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

    CREATE TABLE IF NOT EXISTS `engine`
    (
        `id`          int NOT NULL AUTO_INCREMENT,
        `is_standard` bit                                     DEFAULT NULL,
        `name`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
        `trim_id`     int NOT NULL,
        PRIMARY KEY (`id`),
        KEY `INDEX_ENGINE_NAME` (`name`),
        KEY `INDEX_ENGINE_IS_STANDARD` (`is_standard`),
        KEY `FK_ENGINES_TO_TRIMS_ID` (`trim_id`),
        CONSTRAINT `FK_ENGINES_TO_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

    CREATE TABLE IF NOT EXISTS `transmission`
    (
        `id`      int NOT NULL AUTO_INCREMENT,
        `name`    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
        PRIMARY KEY (`id`),
        KEY `INDEX_TRANSMISSION_NAME` (`name`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

    CREATE TABLE IF NOT EXISTS trim_transmission
    (
        `transmission_id` INT NOT NULL,
        `trim_id`         INT NOT NULL,
        PRIMARY KEY (`transmission_id`, `trim_id`),
        CONSTRAINT `FK_TRITRA_ON_TRANSMISSION` FOREIGN KEY (`transmission_id`) REFERENCES `transmission` (`id`),
        CONSTRAINT `FK_TRITRA_ON_TRIM` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)

    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

    CREATE TABLE IF NOT EXISTS `option`
    (
        `id`        int NOT NULL AUTO_INCREMENT,
        `active`    bit                                     DEFAULT NULL,
        `important` bit                                     DEFAULT NULL,
        `name`      varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
        `trim_id`   int NOT NULL,
        PRIMARY KEY (`id`),
        KEY `INDEX_OPTION_NAME` (`name`),
        KEY `FK_OPTIONS_TO_TRIMS_ID` (`trim_id`),
        CONSTRAINT `FK_OPTIONS_TO_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8mb4
      COLLATE = utf8mb4_unicode_ci;

END
//

DELIMITER ;

CALL ddl_vehicle_tables();

DROP PROCEDURE ddl_vehicle_tables;
