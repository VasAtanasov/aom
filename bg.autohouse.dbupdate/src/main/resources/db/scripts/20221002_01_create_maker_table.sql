DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'maker'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
        CREATE TABLE `maker`
        (
            `id`   VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
            `name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
            PRIMARY KEY (`id`)
        ) ENGINE = InnoDB
          DEFAULT CHARSET = utf8mb4
          COLLATE = utf8mb4_unicode_ci;

        CREATE INDEX MAKER_NAME ON maker (name);

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //