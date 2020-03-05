async function generateVehiclesSqlInsert() {
    const utils = require('./utils');
    const vehiclesUUIDs = require('./json/uuid_for_vehicles');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const services = require('./services');
    const makerService = services.makerService;
    const bodyStyleService = services.bodyStyleService;
    const driveService = services.driveService;
    const colorService = services.colorService;
    const gearService = services.gearsService;

    const makersIds = await makerService.loadAllMakers().then(data => {
        return data.map(maker => maker.id);
    });
    const bodyStyles = await bodyStyleService.loadAllBodyStyles();
    const drives = await driveService.loadAllDrives();
    const colors = await colorService.loadAllColors().then(data => {
        return data.map(item => Number(item.id))
    });
    const gears = await gearService.loadAllGears();

    const vehicles = [];

    for (let i = 0; i < vehiclesUUIDs.length; i++) {
        const vehiclesUUD = vehiclesUUIDs[i];
        const makerId = utils.getRandomValueFromArray(makersIds);
        const modelIds = await makerService.getMakerById(makerId).then(data => {
            return data.models.map((model) => {
                return model.id;
            });
        });

        const vehicleObj = {
            id: vehiclesUUD,
            vehicle_type: "CAR",
            body_style: utils.getRandomValueFromArray(Object.values(bodyStyles)),
            doors: utils.getRandomInt(2, 8),
            drive: utils.getRandomValueFromArray(Object.values(drives)),
            has_accident: 0,
            exterior_color_id: utils.getRandomValueFromArray(colors),
            interior_color_id: utils.getRandomValueFromArray(colors),
            mileage: utils.getRandomInt(5000, 300000),
            month: utils.getRandomInt(1, 13),
            year: utils.getRandomInt(1930, 2020),
            owners: utils.getRandomInt(1, 10),
            seats: utils.getRandomInt(1, 13),
            state: utils.getRandomValueFromArray(["NEW", "USED"]),
            transmission: utils.getRandomValueFromArray(Object.values(gears)),
            maker_id: makerId,
            model_id: utils.getRandomValueFromArray(modelIds),
        };

        vehicles.push(vehicleObj);
    }

    const generator = new InsertGenerator("vehicles", vehicles);
    return generator.generateDataSql();
}

module.exports = generateVehiclesSqlInsert;