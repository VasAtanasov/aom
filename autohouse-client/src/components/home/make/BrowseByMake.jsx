import React, { useState } from 'react';
import styled from 'styled-components';
import { Row, Col, } from 'antd';

import Make from './Make'
import SectionTitle from '../SectionTitle'
import { Container } from '../../../util/commonWrappers';
import { DEFAULT_COLOR } from '../../../util/constants'

import make01 from '../../../resources/images/makes/audi.png'
import make02 from '../../../resources/images/makes/bmw.png'
import make03 from '../../../resources/images/makes/citroen.png'
import make04 from '../../../resources/images/makes/dacia.png'
import make05 from '../../../resources/images/makes/fiat.png'
import make06 from '../../../resources/images/makes/ford.png'
import make07 from '../../../resources/images/makes/hyundai.png'
// import make08 from '../../../resources/images/makes/jeep.png'
import make09 from '../../../resources/images/makes/kia.png'
import make10 from '../../../resources/images/makes/land-rover.png'
import make11 from '../../../resources/images/makes/mazda.png'
import make12 from '../../../resources/images/makes/mercedes-benz.png'
import make13 from '../../../resources/images/makes/mini.png'
import make14 from '../../../resources/images/makes/mistubishi.png'
import make15 from '../../../resources/images/makes/nissan.png'
import make16 from '../../../resources/images/makes/opel.png'
import make17 from '../../../resources/images/makes/peugeot.png'
import make18 from '../../../resources/images/makes/porsche.png'
import make19 from '../../../resources/images/makes/renault.png'
import make20 from '../../../resources/images/makes/seat.png'
import make21 from '../../../resources/images/makes/skoda.png'
import make22 from '../../../resources/images/makes/tesla.png'
import make23 from '../../../resources/images/makes/toyota.png'
import make24 from '../../../resources/images/makes/volkswagen.png'
import make25 from '../../../resources/images/makes/volvo.png'

const imgArray = [
    make01,
    make02,
    make03,
    make04,
    make05,
    make06,
    make07,
    // make08,
    make09,
    make10,
    make11,
    make12,
    make13,
    make14,
    make15,
    make16,
    make17,
    make18,
    make19,
    make20,
    make21,
    make22,
    make23,
    make24,
    make25
]

const BrowseByMakeContainer = styled(Container)`
    overflow: hidden;
    padding: 40px 0!important;
    padding-bottom: 15px;

    & label.toggle {
        cursor: pointer;
        color: ${DEFAULT_COLOR};
        display: inline-block;
        letter-spacing: 1.1;
        font-weight: 500;
        font-size: 15px;
        padding-left: 15px;
        margin-top: 0.7rem;
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
    max-width: 1000px;
    margin: 0 auto;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    overflow: hidden;
    padding: 4px 4px;
`;

const BrowseByMake = () => {

    const [toggle, setToggle] = useState(false);

    return (
        <BrowseByMakeContainer>
            <SectionTitle title="Most popular car makes" />
            <Row type="flex" >
                <input type="checkbox" className="toggle" id="makes-toggle" />
                <MakesContainer span={24} className="toggled">
                    {imgArray.map((el, idx) => {
                        if (idx < 6) {
                            return (
                                <Make key={idx} imgSrc={el} />
                            )
                        } else {
                            return (
                                <Make key={idx} imgSrc={el} className="hidable" />
                            )
                        }
                    })}
                </MakesContainer>
            </Row>
            <Row>
                <Col span={24}>
                    <label htmlFor="makes-toggle" className="toggle" onClick={() => setToggle(!toggle)}>
                        {toggle ? "View less makes" : "View more makes"}
                    </label>
                </Col>
            </Row>
        </BrowseByMakeContainer>
    )
}

export default BrowseByMake;