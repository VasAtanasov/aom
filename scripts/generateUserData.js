async function generateUserSqlInsert(seller) {
  const utils = require("./utils");
  const usersUUIDs = await require("./json/uuid_for_users");
  const usersArray = await require("./json/users_data");
  const InsertGenerator = require("./insertGenerator").InsertGenerator;
  const services = require("./services");

  const users = usersArray.map(user => {
    return {
      id: user.id,
      username: user.username,
      password: user.password,
      phone_number: user.phone_number,
      email: user.email,
      first_name: user.first_name,
      last_name: user.last_name,
      seller: utils.getRandomValueFromArray(Object.keys(seller))
    };
  });

  const generator = new InsertGenerator(`${services.API_PREFIX}users`, users);
  return generator.generateDataSql();
}

module.exports = generateUserSqlInsert;
