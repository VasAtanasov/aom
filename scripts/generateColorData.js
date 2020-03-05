async function generateColorSqlInsert() {
    const utils = require('./utils');
    const services = require('./services');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const colors = [
        "Black",
        "Gray",
        "Cream",
        "Light Brown",
        "Dark Brown",
        "Dark Red",
        "Red",
        "Dark Blue",
        "Light Blue",
        "White",
        "Orange",
        "Silver",
        "Gold",
    ];

    const colorsObj = [];

    for (let i = 0; i < colors.length; i++) {

        const engineObj = {
            id: i + 1,
            name: colors[i],
        };

        colorsObj.push(engineObj);
    }

    const generator = new InsertGenerator("colors", colorsObj);
    return generator.generateDataSql();
}


module.exports = generateColorSqlInsert;





