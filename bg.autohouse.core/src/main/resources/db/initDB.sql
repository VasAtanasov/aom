--CREATE DATABASE  IF NOT EXISTS `testdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
--USE `testdb`;
--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS testdb.invoice;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE testdb.invoice
(
    `id`           int NOT NULL AUTO_INCREMENT,
    `amount`       decimal(19, 2) DEFAULT NULL,
    `currency_id`  int            DEFAULT NULL,
    `invoice_date` date           DEFAULT NULL,
    `account_date` date           DEFAULT NULL,
    `node_id`      int            DEFAULT NULL,
    `note`         text,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS testdb.currency;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE testdb.currency
(
    `id`   int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `exchange_rate`
--
DROP TABLE IF EXISTS testdb.exchange_rate;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

create table if not exists testdb.exchange_rate
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    from_currency_id
    int
    not
    null,
    to_currency_id
    int
    not
    null,
    exchange_rate
    decimal
(
    7,
    2
) not null,
    from_date datetime not null,
    currency_units_from int not null default 1,
    PRIMARY KEY
(
    `id`
),
    UNIQUE KEY `uk_exRateDate`
(
    `from_date`,
    `from_currency_id`,
    `to_currency_id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--CREATE DATABASE  IF NOT EXISTS testdb2 /*!40100 DEFAULT CHARACTER SET utf8 */;
--USE testdb2;
--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS testdb2.invoice;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE testdb2.invoice
(
    `id`           int NOT NULL AUTO_INCREMENT,
    `amount`       decimal(19, 2) DEFAULT NULL,
    `currency_id`  int            DEFAULT NULL,
    `invoice_date` date           DEFAULT NULL,
    `account_date` date           DEFAULT NULL,
    `node_id`      int            DEFAULT NULL,
    `note`         text,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS testdb2.currency;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE testdb2.currency
(
    `id`   int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exchange_rate`
--
DROP TABLE IF EXISTS testdb2.exchange_rate;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

create table if not exists testdb2.exchange_rate
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    from_currency_id
    int
    not
    null,
    to_currency_id
    int
    not
    null,
    exchange_rate
    decimal
(
    7,
    2
) not null,
    from_date datetime not null,
    currency_units_from int not null default 1,
    PRIMARY KEY
(
    `id`
),
    UNIQUE KEY `uk_exRateDate`
(
    `from_date`,
    `from_currency_id`,
    `to_currency_id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
