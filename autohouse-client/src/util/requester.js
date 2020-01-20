import axios from 'axios'
import { API_BASE_URL } from './constants'

const http = (() => {

    const call = function (url, data, params, headers, method) {
        data = data || {};
        params = params || {};
        headers = headers || { 'Content-Type': 'application/json' };

        return axios({
            url: API_BASE_URL + url,
            method: method,
            headers: {},
            params: params,
            data: data,
        });
    };

    const get = function ({ url, data, params, headers }) {
        return call(url, data, params, headers, "GET");
    };

    const post = function ({ url, data, params, headers }) {
        return call(url, data, params, headers, "POST");
    };

    const put = function ({ url, data, params, headers }) {
        return call(url, data, params, headers, "PUT");
    };

    const del = function ({ url, data, params, headers }) {
        return call(url, data, params, headers, "DELETE");
    };

    return {
        get,
        post,
        put,
        del
    };

})();

export default http;