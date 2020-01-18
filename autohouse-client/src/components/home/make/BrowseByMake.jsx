import React, { useState } from 'react';
import styled from 'styled-components';
import { Col } from 'antd';
import Make from './Make';
import { DEFAULT_COLOR } from '../../../util/constants';

const BrowseByMakeContainer = styled.div`

    & label.toggle {
        cursor: pointer;
        color: ${DEFAULT_COLOR};
        display: inline-block;
        letter-spacing: 1.1;
        font-weight: 500;   
        font-size: 15px;
        margin-top: 2rem;
    }

    & input.toggle[type=checkbox] {
        display: none;
    }

    & input.toggle[type=checkbox] + .toggled {
        max-height: 120px;
    }

    & input.toggle[type=checkbox]:checked + .toggled {
        max-height: 1400px;
    }

    & input.toggle[type=checkbox]:checked + .toggled .hidable {
        display: flex;
        opacity: 1;
        max-height: 1400px;
    }
`;

const MakesContainer = styled.div`

    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    overflow: hidden;
    padding: 4px 4px;
`;

const ToggleButton = styled(Col)`
    display: flex !important;
    justify-content: center !important;

    & label {
        flex: 0 0 60%;
        color: #fff !important;
        background-color: ${DEFAULT_COLOR};
        border-color: ${DEFAULT_COLOR};
        line-height: 1.499;
        position: relative;
        line-height: 1.499;
        position: relative;
        white-space: nowrap;
        text-align: center;
        background-image: none;
        border: 1px solid transparent;
        transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
        user-select: none;
        touch-action: manipulation;
        padding: 5px 15px;
        font-size: 14px;
        border-radius: 4px;
    }

    & label:hover,
    & label:focus {
        background-color: white;
        color: ${DEFAULT_COLOR} !important;
        border-color: rgba(247, 183, 29, 1);
    }

    & label:active {
        background-color: ${DEFAULT_COLOR};
        border-color: ${DEFAULT_COLOR};
    }
`;

const BrowseByMake = () => {

    const [toggle, setToggle] = useState(false);
    const pathToImages = '/images/makes/';
    const makes = [
        "audi",
        "bmw",
        "citroen",
        "dacia",
        "fiat",
        "ford",
        "hyundai",
        "kia",
        "land-rover",
        "mazda",
        "mercedes-benz",
        "mini",
        "mistubishi",
        "nissan",
        "opel",
        "peugeot",
        "porsche",
        "renault",
        "seat",
        "skoda",
        "tesla",
        "toyota",
        "volkswagen",
        "volvo"
    ];
    const extensionPng = ".png";

    return (
        <BrowseByMakeContainer>
            <input type="checkbox" className="toggle" id="makes-toggle" />
            <MakesContainer span={24} className="toggled">
                {makes.map((make, idx) => {
                    if (idx < 6) {
                        return (
                            <Make key={idx} imgSrc={`${pathToImages}${make}${extensionPng}`} />
                        )
                    } else {
                        return (
                            <Make key={idx} imgSrc={`${pathToImages}${make}${extensionPng}`} className="hidable" />
                        )
                    }
                })}
            </MakesContainer>
            <ToggleButton span={24}>
                <label htmlFor="makes-toggle" className="toggle" onClick={() => setToggle(!toggle)}>
                    {toggle ? "View less makes" : "View more makes"}
                </label>
            </ToggleButton>
        </BrowseByMakeContainer>
    )
}

export default BrowseByMake;
