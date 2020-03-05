const axios = require('axios');
const http = (() => {

    const call = function (url, data, headers, method) {
        data = data || {};
        headers = headers || {};
        // url = baseUrl + url;

        return axios({
            url: url,
            method: method,
            headers: headers,
            data: data
        });
    };

    const get = function (url, data, headers) {
        return call(url, data, headers, 'GET');
    };

    const post = function (url, data, headers) {
        return call(url, data, headers, 'POST');
    };

    const put = function (url, data, headers) {
        return call(url, data, headers, 'PUT');
    };

    const del = function (url, data, headers) {
        return call(url, data, headers, 'DELETE');
    };

    const getJsonFromResponse = function (res) {
        return res.data;
    };

    return {
        get,
        post,
        put,
        del,
        getJsonFromResponse
    };

})();

module.exports = {
    http
};