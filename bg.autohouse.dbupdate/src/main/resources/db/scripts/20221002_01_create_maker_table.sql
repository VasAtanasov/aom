DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'maker'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
        CREATE TABLE IF NOT EXISTS maker
        (
            id          BIGINT     NOT NULL PRIMARY KEY AUTO_INCREMENT,
            uid         BINARY(16) NOT NULL,
            external_id VARCHAR(12),
            name        VARCHAR(255)
        ) ENGINE = InnoDB
          DEFAULT CHARSET = utf8mb4
          COLLATE = utf8mb4_unicode_ci;

        CREATE INDEX MAKER_EXTERNAL_ID ON maker (external_id);
        CREATE INDEX MAKER_NAME ON maker (name);
        CREATE INDEX MAKER_UID ON maker (uid);

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //