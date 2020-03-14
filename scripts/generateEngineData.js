async function generateEngineSqlInsert(metadata) {
  const utils = require("./utils");
  const services = require("./services");
  const engineUUIDs = require("./json/uuid_for_engines");
  const vehiclesUUIDs = require("./json/uuid_for_vehicles");
  const InsertGenerator = require("./insertGenerator").InsertGenerator;

  const engines = [];

  for (let i = 0; i < vehiclesUUIDs.length; i++) {
    const engineObj = {
      id: vehiclesUUIDs[i],
      fuel_type: utils.getRandomValueFromArray(Object.keys(metadata.fuelType)),
      power: services.powerService.getRandomPowerOutput(),
      euro_standard: utils.getRandomValueFromArray(
        Object.keys(metadata.euroStandard)
      )
    };

    engines.push(engineObj);
  }

  const generator = new InsertGenerator(
    `${services.API_PREFIX}engines`,
    engines
  );
  return generator.generateDataSql();
}

module.exports = generateEngineSqlInsert;
