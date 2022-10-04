DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'trim'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
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

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //