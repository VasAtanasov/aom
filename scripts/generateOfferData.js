async function generateOfferSqlInsert() {
    const createdONDatesArray = require('./json/created_on_dates');
    const utils = require('./utils');
    const usersUUIDs = require('./json/uuid_for_users');
    const vehiclesUUIDs = require('./json/uuid_for_vehicles');
    const descriptions = require('./json/descriptions_json');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const services = require('./services');
    const locationService = services.locationService;

    const locationsIds = await locationService.getAllLocations().then(data => {
        return data.map(location => location.id);
    });

    const offers = [];

    const baseImageUrl = '/images/offers/cars/201909-12';

    for (let i = 0; i < vehiclesUUIDs.length; i++) {
        const sqlDate = utils.getRandomValueFromArray(createdONDatesArray);

        const futureDate = (sqlDate, daysForward) => {
            if (utils.getRandomValueFromArray([true, false])) {
                const date = new Date(Date.parse(sqlDate));
                const futureDate = utils.addDays(date, daysForward);
                return utils.jsDateToMySqlDate(futureDate);
            }
            return sqlDate;
        };

        const UUID = vehiclesUUIDs[i];
        const offerObj = {
            id: UUID,
            created_on: sqlDate,
            thumbnail: `${baseImageUrl}/${UUID}/thumbnail.jpg`,
            description: descriptions[i],
            hit_count: utils.getRandomInt(0, 5000),
            is_active: 1,
            is_deleted: 0,
            is_expired: 0,
            modified_on: futureDate(sqlDate, utils.getRandomInt(1, 30)),
            price: utils.getRandomInt(1, 500000),
            price_modified_on: futureDate(sqlDate, utils.getRandomInt(1, 30)),
            valid_until: futureDate(sqlDate, 90),
            location_id: utils.getRandomValueFromArray(locationsIds),
            user_id: utils.getRandomValueFromArray(usersUUIDs),
        };

        offers.push(offerObj)
    }

    const generator = new InsertGenerator("offers", offers);
    return generator.generateDataSql();
}

module.exports = generateOfferSqlInsert;

