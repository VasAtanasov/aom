async function generateImagesSqlInsert() {
    const utils = require('./utils');
    const offersIDs = require('./json/uuid_for_vehicles');
    const imagesLinksOriginal = require('./json/image_url');
    const imagesUUIDsOriginal = require('./json/imagesUUIDs');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const services = require('./services');

    // const imagesUUIDs = JSON.parse(JSON.stringify(imagesUUIDsOriginal));
    // const imagesLinks = JSON.parse(JSON.stringify(imagesLinksOriginal));

    const imagesUUIDs = [...imagesUUIDsOriginal];
    const imagesLinks = [...imagesLinksOriginal];

    const images = [];

    for (let i = 0; i < offersIDs.length; i++) {
        const offerID = offersIDs[i];
        for (let j = 0; j < 6; j++) {
            const imageLink = imagesLinks.shift();

            const imageObj = {
                id: imagesUUIDs.shift(),
                thumbnail_url: imageLink,
                url: imageLink,
                offer_id: offerID,
            };

            images.push(imageObj);
        }

    }

    const generator = new InsertGenerator("images", images);
    return generator.generateDataSql();
}


module.exports = generateImagesSqlInsert;