import { LG_VIEW } from '../../util/constants';
import styled, { css } from 'styled-components';

export const Brand = styled.span`
    display: block;
    float: left;
    font-size: 25px;
    padding: 10px 20px;
    text-decoration: none;
    text-transform: uppercase;
    font-weight: 800;

    @media (max-width: ${LG_VIEW}) {
        color: #fff;
    }

    & span {
        color: #f7b71d;
    }
`;

export const NavIcon = styled.span`
    background: rgba(255, 255, 255, 0.5);
    display: block;
    height: 2px;
    position: relative;
    transition: background .2s ease-out;
    width: 18px;

    &:before {
        top: 5px;
    }

    &:after {
        top: -5px;
    }

    &:before,
    &:after {
        background: rgba(255, 255, 255, 0.5);
        content: '';
        display: block;
        height: 100%;
        position: absolute;
        transition: all .2s ease-out;
        width: 100%;
    }
`;

export const Menu = styled.ul`
    clear: both;
    max-height: 0;
    transition: max-height .2s ease-out;
    margin: 0;
    padding: 0;
    list-style: none;
    overflow: hidden;
    background-color: #fff;

    @media (min-width: ${LG_VIEW}) {
        clear: none;
        float: right;
        max-height: none;
    }
`;

export const ToggleButton = styled.label`
    cursor: pointer;
    float: right;
    padding: 28px 20px;
    position: relative;
    user-select: none;

    @media (min-width: ${LG_VIEW}) {
        display: none;
    }
`;

export const ToggleCheckbox = styled.input`
    display: none;

    :hover {
        color: #f7b71d;
        background-color: #f4f4f4;
    }

    :checked~${Menu} {
        max-height: 240px;
    }

    :checked~${ToggleButton} ${NavIcon} {
        background: transparent;
    }

    :checked~${ToggleButton} ${NavIcon}:before {
        transform: rotate(-45deg);
    }

    :checked~${ToggleButton} ${NavIcon}:after {
        transform: rotate(45deg);
    }

    :checked~${ToggleButton}:not(.steps) ${NavIcon}:before {
        top: 0;
    }
  
    :checked~${ToggleButton}:not(.steps) ${NavIcon}:after {
        top: 0;
    }
`;
export const Navbar = styled.header`
    background-color: #fff;
    box-shadow: 1px 1px 4px 0 rgba(0, 0, 0, .1);
    position: absolute;
    width: 100%;
    z-index: 3;

    @media (max-width: ${LG_VIEW}) {
        background: #000000 !important;
    }

    ${({ scrolled }) => scrolled && css`
        position: fixed;
        margin-top: -100px;
        -webkit-box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
        box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);

        ${({ awake }) => awake && css`            
            margin-top: 0;
            -webkit-transition: .3s all ease-out;
            -o-transition: .3s all ease-out;
            transition: .3s all ease-out;`
        }

        ${({ sleep }) => sleep && css`            
            -webkit-transition: .3s all ease-out;
            -o-transition: .3s all ease-out;
            transition: .3s all ease-out;`
        }`
    }

    & ul {
        margin: 0;
        padding: 0;
        list-style: none;
        overflow: hidden;
        background-color: #fff;

        & a {
            display: block;
            padding: 20px 20px;
            text-decoration: none;
            text-transform: uppercase;
            font-weight: 600;

            &:hover {
                color: #f7b71d;
                background-color: #f4f4f4;
            }
        }
    }
`;

export const StyledLink = styled.li`


    a {
        display: block;
        padding: 20px 20px;
        text-decoration: none;
        text-transform: uppercase;
        font-weight: 600;
    }

    a.active {
        color: #fff;
        background-color: #f7b71d;
    }

    a:hover {
        color: #f7b71d;
        background-color: #f4f4f4;
    }

    @media (min-width: ${LG_VIEW}) {
        float: left;
    }
`;