DROP PROCEDURE IF EXISTS executeDbUpdate //

CREATE PROCEDURE executeDbUpdate()
BEGIN

    IF NOT EXISTS(SELECT *
                  FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE LOWER(TABLE_NAME) = 'model'
                    AND TABLE_SCHEMA = DATABASE())
    THEN
        CREATE TABLE IF NOT EXISTS model
        (
            id          BIGINT     NOT NULL PRIMARY KEY AUTO_INCREMENT,
            uid         BINARY(16) NOT NULL,
            external_id VARCHAR(12),
            name        VARCHAR(32),
            maker_id    BIGINT     NOT NULL
        ) ENGINE = InnoDB
          DEFAULT CHARSET = utf8mb4
          COLLATE = utf8mb4_unicode_ci;

        CREATE INDEX MODEL_EXTERNAL_ID ON model (external_id);
        CREATE INDEX MODEL_NAME ON model (name);
        CREATE INDEX MODEL_UID ON model (uid);

        ALTER TABLE model
            ADD CONSTRAINT FK_MODEL_MAKE_ID FOREIGN KEY (maker_id) REFERENCES maker (id)
                ON DELETE CASCADE;

    END IF;

END //

CALL executeDbUpdate() //

DROP PROCEDURE executeDbUpdate //