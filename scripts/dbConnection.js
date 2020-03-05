const mysql = require('promise-mysql');

const dbConfig = {
    user: "root",
    password: "1234",
    database: "autohouse_db",
    host: "localhost",
    connectionLimit: 10,
    multipleStatements: true
};

module.exports = async () => {
    try {
        let pool;
        let con;
        if (pool) con = pool.getConnection();
        else {
            pool = await mysql.createPool(dbConfig);
            con = pool.getConnection();
        }
        return con;
    } catch (ex) {
        throw ex;
    }
};