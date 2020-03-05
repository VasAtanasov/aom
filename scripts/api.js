const api = (function () {
    const baseHost = 'localhost:8007/';
    const baseRestApiUrl = `api`;

    const baseUrl = `${baseRestApiUrl}`;

    const all = function (endpoint) {
        return `${baseUrl}${endpoint}/all`;
    };

    const byId = function (endpoint, id) {
        return `${baseUrl}${endpoint}/${id}`;
    };

    const makersAll = () => {
        return all('/makers');
    };

    const makerById = (id) => {
        return byId('/makers', id);
    };

    const fuelTypesAll = () => {
        return all('/fuelTypes');
    };
    const gearsAll = () => {
        return all('/gears');
    };

    const featuresAll = () => {
        return all('/features');
    };

    const bodyStylesAll = () => {
        return all('/bodyStyles');
    };

    const drivetrainAll = () => {
        return all('/drivetrain');
    };

    const orderByAll = () => {
        return all('/orderBy');
    };

    const euroStandardAll = () => {
        return all('/euroStandard');
    };

    const locationsAll = () => {
        return all('/locations');
    };

    const colorsAll = () => {
        return all('/colors');
    };

    return {
        makersAll,
        makerById,
        fuelTypesAll,
        gearsAll,
        featuresAll,
        bodyStylesAll,
        drivetrainAll,
        orderByAll,
        euroStandardAll,
        locationsAll,
        colorsAll,
    }
})();

module.exports = api;