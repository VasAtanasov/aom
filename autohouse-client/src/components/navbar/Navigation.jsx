import React, { Fragment, useState, useEffect } from 'react';
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
import { routes } from '../../components/app/App';

const ToggleIcon = () => {
    return (
        <Fragment>
            <ToggleCheckbox type="checkbox" id="menu-btn" />
            <ToggleButton htmlFor="menu-btn"><NavIcon /></ToggleButton>
        </Fragment>
    )
};

const Navigation = () => {

    const [scrolled, setScrolled] = useState(false);
    const [awake, setAwake] = useState(false);
    const [sleep, setSleep] = useState(false);

    useEffect(() => {

        const handleScroll = () => {
            const currentScrollY = window.scrollY;

            if (currentScrollY > 100) {
                if (!scrolled) {
                    setScrolled(true);
                }
            }
            if (currentScrollY < 100) {
                if (scrolled) {
                    setScrolled(false);
                    setSleep(false)
                }
            }
            if (currentScrollY > 250) {
                if (!awake) {
                    setAwake(true);
                }
            }
            if (currentScrollY < 250) {
                if (awake) {
                    setAwake(false);
                    setSleep(true);
                }
            }
        };

        window.addEventListener('scroll', handleScroll);

        return () => {
            window.removeEventListener('scroll', handleScroll);
        }

    }, [scrolled, awake, sleep]);

    const navLinks = routes.map((link, index) => (
        <StyledLink key={`${link.name}_${index}`}>
            <NavLink to={link.path} >
                {link.name}
            </NavLink>
        </StyledLink>
    ));

    return (
        <Navbar scrolled={scrolled} awake={awake} sleep={sleep}>
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

export default Navigation;