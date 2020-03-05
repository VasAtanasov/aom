async function generateEngineSqlInsert() {
    const utils = require('./utils');
    const services = require('./services');
    const engineUUIDs = require('./json/uuid_for_engines');
    const vehiclesUUIDs = require('./json/uuid_for_vehicles');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    function getRandomEngineCC() {
        return Number(utils.getRandomArbitrary(1.0, 5.5).toFixed(1))
    }


    const euroStandards = await services.euroStandardService.getAllEuroStandards();
    const fuelTypes = await services.fuelTypeService.loadAllFuelTypes();

    const engines = [];

    for (let i = 0; i < vehiclesUUIDs.length; i++) {
        const engineObj = {
            id: vehiclesUUIDs[i],
            modification: getRandomEngineCC(),
            cylinders: utils.getRandomValueFromArray(services.cylinderService.cylindersCount),
            euro_standard: utils.getRandomValueFromArray(Object.values(euroStandards)),
            fuel_type: utils.getRandomValueFromArray(Object.values(fuelTypes)),
            power: services.powerService.getRandomPowerOutput(),
            power_type: "HP",
        };

        engines.push(engineObj);
    }

    const generator = new InsertGenerator("engines", engines);
    return generator.generateDataSql();
}

module.exports = generateEngineSqlInsert;





