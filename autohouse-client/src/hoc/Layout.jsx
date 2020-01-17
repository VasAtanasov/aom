import React, { Fragment } from 'react';
import Navigation from '../components/navbar/Navigation';
import Footer from '../components/footer/Footer';

const Layout = (props) => {

    return (
        <Fragment>
            <Navigation />
            <main>
                {props.children}
            </main>
            <Footer />
        </Fragment>
    );
}

export default Layout;