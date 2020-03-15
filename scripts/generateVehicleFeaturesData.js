async function generateVehiclesFeaturesSqlInserts(metadata) {
  const utils = require("./utils");
  const vehiclesUUIDs = require("./json/uuid_for_vehicles");
  const InsertGenerator = require("./insertGenerator").InsertGenerator;
  const services = require("./services");

  const featureService = require("./services").featureService;
  const features = Object.keys(metadata.feature);

  const relationRecord = [];

  vehiclesUUIDs.forEach(uuid => {
    const featureCount = utils.getRandomInt(5, 15);
    for (let i = 0; i < featureCount; i++) {
      const randomIndex = utils.getRandomInt(0, features.length);

      const featuresObj = {
        vehicle_id: uuid,
        feature: features[randomIndex]
      };

      relationRecord.push(featuresObj);
    }
  });

  const generator = new InsertGenerator(`vehicle_feature`, relationRecord);
  return generator.generateDataSql();
}

module.exports = generateVehiclesFeaturesSqlInserts;
