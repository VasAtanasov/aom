import React, { Component, Fragment } from 'react';
import { NavLink } from 'react-router-dom';
import {
    Navbar,
    Brand,
    Menu,
    ToggleCheckbox,
    ToggleButton,
    NavIcon,
    StyledLink
} from './style';
import links from '../../util/links';

const ToggleIcon = () => {
    return (
        <Fragment>
            <ToggleCheckbox type="checkbox" id="menu-btn" />
            <ToggleButton htmlFor="menu-btn"><NavIcon /></ToggleButton>
        </Fragment>
    )
};

class Navigation extends Component {

    state = {
        classes: {
            'scrolled': false,
            'sleep': false,
            'awake': false
        }
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
        const currentScrollY = window.scrollY;
        const classes = this.state.classes;

        if (currentScrollY > 150) {
            if (!classes.scrolled) {
                classes.scrolled = true;
            }
        }
        if (currentScrollY < 150) {
            if (classes.scrolled) {
                classes.scrolled = false;
                classes.sleep = false;
            }
        }
        if (currentScrollY > 350) {
            if (!classes.awake) {
                classes.awake = true;
            }
        }
        if (currentScrollY < 350) {
            if (classes.awake) {
                classes.awake = false;
                classes.sleep = true;
            }
        }

        this.setState({ classes })
    };

    render() {
        const classes = this.state.classes;

        const navLinks = links.map((link, index) => (
            <StyledLink key={`${link.name}_${index}`}>
                <NavLink to={link.to} >
                    {link.name}
                </NavLink>
            </StyledLink>
        ));

        return (
            <Navbar {...classes}>
                <NavLink to="/">
                    <Brand>
                        Auto<span>house</span>
                    </Brand>
                </NavLink>
                <ToggleIcon />
                <Menu>
                    {navLinks}
                </Menu>
            </Navbar>
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