async function generateUserSqlInsert() {
    const utils = require('./utils');
    const usersUUIDs = await require('./json/uuid_for_users');
    const usersArray = await require('./json/users_data');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const generator = new InsertGenerator("users", usersArray);
    return generator.generateDataSql();
}

module.exports = generateUserSqlInsert;