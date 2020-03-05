async function generateUserRoleSqlInsert() {
    const utils = require('./utils');
    const usersUUIDs = await require('./json/uuid_for_users');
    const usersArray = await require('./json/users_data');
    const rolesArray = await require('./json/role_data');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;


    const userRole = [];

    for (let i = 0; i < rolesArray.length; i++) {
        const role = rolesArray[i];
        const userObj = {
            role_id: role.id,
            user_id: "3c1bd01a-7cce-4650-9ed1-5d389fd21848"
        };

        userRole.push(userObj);
    }

    for (let i = 1; i < usersUUIDs.length; i++) {
        const usersUUID = usersUUIDs[i];
        const userObj = {
            role_id: 4,
            user_id: usersUUID
        };

        userRole.push(userObj);
    }

    const generator = new InsertGenerator("users_roles", userRole);
    return generator.generateDataSql();
}

module.exports = generateUserRoleSqlInsert;