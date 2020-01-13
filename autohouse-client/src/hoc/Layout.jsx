import React, { Fragment } from 'react';
import Navigation from '../components/navbar/Navigation';
import Footer from '../components/footer/Footer';

const navLinks = [
    { name: "Home", to: "/" },
    { name: "About", to: "/about" },
    { name: "Contact", to: "/contact" }
];

const brand = { name: "Autohouse", to: "/" };


const Layout = (props) => {

    return (
        <Fragment>
            <Navigation brand={brand} links={navLinks} />
            {props.children}
            <Footer />
        </Fragment>
    );
}

export default Layout;