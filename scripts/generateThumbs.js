(async function () {
    const vehiclesUUIDs = await require('./json/uuid_for_vehicles');

    const imageFolder = 'C:\\Users\\vasat\\Desktop\\SpringProject\\offers-thumbs';
    const offersFolder = 'C:\\Users\\vasat\\Desktop\\GitHub\\SoftUni-Spring-Project-November-2019\\autohouse\\src\\main\\resources\\static\\images\\offers\\cars\\201909-12';

    const fs = require('fs');
    const files = await fs.readdirSync(imageFolder);

    console.log(vehiclesUUIDs.length === files.length);

    for (let i = 0; i < vehiclesUUIDs.length; i++) {
        const vehiclesUUID = vehiclesUUIDs[i];
        const file = files[i];
        fs.renameSync(
            `${imageFolder}\\${file}`,
            `${offersFolder}\\${vehiclesUUID}\\thumbnail.jpg`
        )
    }

})();