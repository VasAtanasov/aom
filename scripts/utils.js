const http = require('./requester').http;

const baseUrl = 'http://localhost:8007/';

const getRandomInt = function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min; //The maximum is exclusive and the minimum is inclusive
};

function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

const httpGET = async function (endPoint) {
    const response = await http.get(`${baseUrl}${endPoint}`);
    return await http.getJsonFromResponse(response);
};

const getRandomValueFromArray = (array) => {
    const index = getRandomInt(0, array.length);
    return array[index];
};

function addDays(date, days) {
    const result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

const jsDateToMySqlDate = (date) => {
    return date.toISOString().slice(0, 19).replace('T', ' ');
};

const getCreatedOnDate = () => {
    let date = new Date();
    return jsDateToMySqlDate(date);
};

const printSqlResult = async (func) => {
    console.log(await func())
};

module.exports = {
    baseUrl,
    getRandomInt,
    getRandomArbitrary,
    getRandomValueFromArray,
    httpGET,
    addDays,
    jsDateToMySqlDate,
    printSqlResult,
    getCreatedOnDate,
};