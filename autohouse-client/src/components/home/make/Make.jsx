import React from 'react';
import styled from 'styled-components';

const MakeCardContainer = styled.div`
    cursor: pointer;
    width: 150px;
    margin: 0 auto;
    padding: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    border: 1px solid transparent;
    box-shadow: none;
    transition: 0.3s;

    &:hover {
        background-color: white;
        -webkit-box-shadow: 0px 0px 15px rgba(85, 90, 96, 0.25);
        -moz-box-shadow: 0px 0px 15px rgba(85, 90, 96, 0.25);
        box-shadow: 0px 0px 15px rgba(85, 90, 96, 0.25);
    }

    &.hidable {
        opacity: 0;
        max-height: 0;
        display: none;
    }

    & img {
        display: inline-block;
        margin: 0 auto;
        width: 80px;
        height: 80px;
    }
`;

const Make = ({ imgSrc }) => (
    <MakeCardContainer>
        <img src={imgSrc} alt="make icon" />
    </MakeCardContainer>
)

export default Make;