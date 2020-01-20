import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';

import Home from '../home/Home'
import './App.css';
import Layout from '../../hoc/Layout'
import Contacts from '../contacts/Contacts';
import Advanced from '../search/Advanced';
import NotFound from '../common/NotFound';

export const routes = [
    {
        path: "/home",
        component: Home,
        name: "Home"
    },
    {
        path: "/findoffer",
        component: Advanced,
        name: "Find Offer"
    },
    {
        path: "/contacts",
        component: Contacts,
        name: "Contacts"
    }
];

const App = () => {

    return (
        <Layout>
            <Switch>
                <Redirect exact from="/" to="/home" />
                {routes.map((routObj, idx) => (
                    <Route exact key={idx} path={routObj.path} component={routObj.component} />
                ))}
                <Route component={NotFound} />
            </Switch>
        </Layout>
    );
}

export default App;
