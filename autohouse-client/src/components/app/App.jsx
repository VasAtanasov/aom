import React from 'react';
import { Switch, Route } from 'react-router-dom';

import Home from '../home/Home'
import './App.css';
import Layout from '../../hoc/Layout'


const App = () => {

    return (
        <Layout>
            <Switch>
                <Route path="/" exact component={Home}/>
            </Switch>
        </Layout>
    );
}

export default App;
