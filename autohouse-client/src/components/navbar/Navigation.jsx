import React, { Component } from 'react';
import './navigation.css'
import { NavLink } from 'react-router-dom';

class Navigation extends Component {

    state = {
        auth: false,
        slide: 100,  // How much should the Navbar slide up or down
        lastScrollY: 0,  // Keep track of current position in state
    };

    componentDidMount() {
        // When this component mounts, begin listening for scroll changes
        window.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        // If this component is unmounted, stop listening
        window.removeEventListener('scroll', this.handleScroll);
    }

    handleScroll = () => {
        const { lastScrollY } = this.state;
        const currentScrollY = window.scrollY;


        if (currentScrollY > lastScrollY) {
            this.setState({ slide: '-58px' });
        } else {
            this.setState({ slide: '0px' });
        }
        this.setState({ lastScrollY: currentScrollY });
    };

    render() {
        const { brand, links } = this.props;

        const navLinks = links.map((link, index) => (
            <li key={`${link.name}_${index}`}><NavLink to={link.to} activeClassName="active-link">{link.name}</NavLink></li>
        ));

        return (
            <header className="header" style={{
                transform: `translate(0, ${this.state.slide})`,
                transition: 'transform 90ms linear',
            }} >
                <NavLink className="logo" to={brand.to}>{brand.name}</NavLink>
                <input className="menu-btn" type="checkbox" id="menu-btn" />
                <label className="menu-icon" htmlFor="menu-btn"><span className="navicon"></span></label>
                <ul className="menu">
                    {navLinks}
                </ul>
            </header>
        )
    }

}

export default Navigation;


// const Navigation = ({ brand, links }) => {

//     const navLinks = links.map((link, index) => (
//         <li key={`${link.name}_${index}`}><NavLink to={link.to} activeClassName="active-link">{link.name}</NavLink></li>
//     ))

//     return (
//         <header className="header">
//             <NavLink className="logo" to={brand.to}>{brand.name}</NavLink>
//             <input className="menu-btn" type="checkbox" id="menu-btn" />
//             <label className="menu-icon" htmlFor="menu-btn"><span className="navicon"></span></label>
//             <ul className="menu">
//                 {navLinks}
//             </ul>
//         </header>
//     )
// }

// export default Navigation;