import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/app/App';
import * as serviceWorker from './serviceWorker';
import { BrowserRouter } from 'react-router-dom';
import 'antd/dist/antd.css';
import GlobalStyles from './globalStyles';

ReactDOM.render(
    <BrowserRouter>
        <GlobalStyles />
        <App />
    </BrowserRouter>,
    document.getElementById('root')
);

serviceWorker.unregister();
