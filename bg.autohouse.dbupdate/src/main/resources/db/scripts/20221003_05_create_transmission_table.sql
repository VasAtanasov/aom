DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'transmission'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
        CREATE TABLE `transmission`
        (
            `id`      BIGINT                                 NOT NULL,
            `name`    VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
            `trim_id` VARCHAR(12) COLLATE utf8mb4_unicode_ci NOT NULL,
            PRIMARY KEY (`id`),
            KEY `FK_TRANSMISSIONS_TRIMS_ID` (`trim_id`),
            CONSTRAINT `FK_TRANSMISSIONS_TRIMS_ID` FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`)
        ) ENGINE = InnoDB
          DEFAULT CHARSET = utf8mb4
          COLLATE = utf8mb4_unicode_ci;

        CREATE INDEX TRANSMISSION_NAME ON `transmission` (name);

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //