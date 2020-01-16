import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';

import Home from '../home/Home'
import './App.css';
import Layout from '../../hoc/Layout'
import About from '../about/About';
import Contacts from '../contacts/Contacts';


const App = () => {

    return (
        <Layout>
            <Switch>
                <Redirect exact from="/" to="/home" />
                <Route path={["/home", "/"]} exact component={Home} />
                <Route path="/about" exact component={About} />
                <Route path="/contacts" exact component={Contacts} />
            </Switch>
        </Layout>
    );
}

export default App;
