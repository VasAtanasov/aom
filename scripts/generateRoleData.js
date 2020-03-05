async function generateRoleSqlInsert() {
    const rolesArray = await require('./json/role_data');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;
    const generator = new InsertGenerator("roles", rolesArray);
    return generator.generateDataSql();
}

module.exports = generateRoleSqlInsert;