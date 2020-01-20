import React from 'react';
import styled from "styled-components";

const CheckBoxContainer = styled.div`
    padding: 0 0 10px 0;

    & input[type="checkbox"] {
        display:none;
    }

    & input[type="checkbox"] + label {
        display: block;
        position: relative;
        padding-left: 25px;
        margin-bottom: 20px;
        font-size: 0.9rem;
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
    }

    & input[type="checkbox"] + label:last-child {
        margin-bottom: 0;
    }

    & input[type="checkbox"] + label:before {
        content: '';
        display: block;
        width: 16px;
        height: 16px;
        border: 2px solid #fc983c;
        position: absolute;
        left: 0;
        top: 3px;
        opacity: .6;
        -webkit-transition: all .12s, border-color .08s;
        transition: all .12s, border-color .08s;
    }

    & input[type="checkbox"]:checked + label:before {
        width: 10px;
        top: -2px;
        left: 5px;
        border-radius: 0;
        border: 2px solid #3ca826;
        background-color: transparent;
        opacity: 1;
        border-top-color: transparent;
        border-left-color: transparent;
        -webkit-transform: rotate(45deg);
        transform: rotate(45deg);
    }
`;

const Checkbox = ({ id, text }) => (
    <CheckBoxContainer>
        <input type="checkbox" id={`${id}_${text}`} />
        <label htmlFor={`${id}_${text}`}>{text}</label>
    </CheckBoxContainer>
)

export default Checkbox;