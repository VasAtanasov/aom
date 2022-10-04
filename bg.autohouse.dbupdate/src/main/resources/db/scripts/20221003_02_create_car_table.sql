DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'car'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
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

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //