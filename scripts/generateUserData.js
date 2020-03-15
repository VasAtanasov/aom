async function generateUserSqlInsert() {
  const utils = require("./utils");
  const usersUUIDs = await require("./json/uuid_for_users");
  const usersArray = await require("./json/users_data");
  const InsertGenerator = require("./insertGenerator").InsertGenerator;
  const services = require("./services");

  const users = usersArray.map(user => {
    return { id: user.id };
  });

  const generator = new InsertGenerator(`${services.API_PREFIX}users`, users);
  return generator.generateDataSql();
}

module.exports = generateUserSqlInsert;
