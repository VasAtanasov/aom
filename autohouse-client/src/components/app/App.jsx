import React from 'react';
import { Switch, Route } from 'react-router-dom';

import Home from '../home/Home'
import './App.css';
import Layout from '../../hoc/Layout'
import About from '../about/About';
import NotFound from '../common/NotFound'
import Contacts from '../contacts/Contacts';


const App = () => {

    return (
        <Layout>
            <Switch>
                <Route path={["/home", "/"]} exact component={Home} />
                <Route path="/about" exact component={About} />
                <Route path="/contacts" exact component={Contacts} />
                <Route component={NotFound} />
            </Switch>
        </Layout>
    );
}

export default App;
