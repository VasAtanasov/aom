DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'engine'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
        CREATE TABLE `engine`
        (
            `id`          VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
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

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //