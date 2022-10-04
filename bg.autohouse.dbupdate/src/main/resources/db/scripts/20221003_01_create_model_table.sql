DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'model'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
        CREATE TABLE `model`
        (
            `id`       VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
            `name`     VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
            `maker_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
            PRIMARY KEY (`id`),
            KEY `FK_MODEL_MAKE_ID` (`maker_id`),
            CONSTRAINT `FK_MODEL_MAKE_ID` FOREIGN KEY (`maker_id`) REFERENCES `maker` (`id`) ON DELETE CASCADE
        ) ENGINE = InnoDB
          DEFAULT CHARSET = utf8mb4
          COLLATE = utf8mb4_unicode_ci;

        CREATE INDEX MODEL_NAME ON model (name);

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //