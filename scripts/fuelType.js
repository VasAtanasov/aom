const fuelTypes = require('./fuelTypes');

const mapName = function (str) {
    return str.replace(/\//g, '_').toUpperCase()
};

fuelTypes.forEach(type => {
    const enumString = `${mapName(type.name)}("${type.name}"),`;
    console.log(enumString)
});