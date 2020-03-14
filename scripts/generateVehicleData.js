async function generateVehiclesSqlInsert(metadata, makers) {
  const utils = require("./utils");
  const vehiclesUUIDs = require("./json/uuid_for_vehicles");
  const InsertGenerator = require("./insertGenerator").InsertGenerator;
  const services = require("./services");
  const http = require("./requester").http;

  const makerService = services.makerService;
  const bodyStyleService = services.bodyStyleService;
  const driveService = services.driveService;
  const colorService = services.colorService;
  const gearService = services.gearsService;

  const makersIds = makers.map(maker => maker.id);

  const vehicles = [];

  for (let i = 0; i < vehiclesUUIDs.length; i++) {
    const vehiclesUUD = vehiclesUUIDs[i];
    const makerId = utils.getRandomValueFromArray(makersIds);
    const http = require("./requester").http;
    const modelIds = await http
      .get(`${services.BASE_URL}/makers/${makerId}/models`)
      .then(data => {
        let models = (data.data.models = data.data.data.models);
        return models.map(model => {
          return model.id;
        });
      });

    const vehicleObj = {
      id: vehiclesUUD,
      body_style: utils.getRandomValueFromArray(
        Object.keys(metadata.bodyStyle)
      ),
      doors: utils.getRandomInt(2, 8),
      drive: utils.getRandomValueFromArray(Object.keys(metadata.drive)),
      has_accident: 0,
      color: utils.getRandomValueFromArray(Object.keys(metadata.color)),
      mileage: utils.getRandomInt(5000, 300000),
      month: utils.getRandomInt(1, 13),
      year: utils.getRandomInt(1930, 2020),
      seats: utils.getRandomInt(1, 13),
      state: utils.getRandomValueFromArray(Object.keys(metadata.state)),
      transmission: utils.getRandomValueFromArray(
        Object.keys(metadata.transmission)
      ),
      upholstery: utils.getRandomValueFromArray(
        Object.keys(metadata.upholstery)
      ),
      maker_id: makerId,
      model_id: utils.getRandomValueFromArray(modelIds)
    };

    vehicles.push(vehicleObj);
  }

  const generator = new InsertGenerator(
    `${services.API_PREFIX}vehicles`,
    vehicles
  );
  return generator.generateDataSql();
}

module.exports = generateVehiclesSqlInsert;
