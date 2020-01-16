import React from 'react';
import styled from 'styled-components';

import {
    HERO_MAIN_HEIGHT,
    LG_VIEW,
    MD_VIEW,
    HERO_MAIN_RESPONSIVE
} from '../../util/constants';

const Overlay = styled.div`
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    content: '';
    opacity: .5;
    background: #000000;
    min-height: ${HERO_MAIN_HEIGHT};

    @media (max-width: ${LG_VIEW}) {
        min-height: ${HERO_MAIN_RESPONSIVE};
    }
`;

const Wrapper = styled.div`
    width: 100%;
    min-height: ${HERO_MAIN_HEIGHT};
    position: relative;
    background-size: cover;
    background-repeat: no-repeat;
    background-position: top center;
    background-image: ${({ url }) => `url(${url})`};


    p {
        font-size: 32px;
    }

    @media (max-width: ${LG_VIEW}) {
        min-height: ${HERO_MAIN_RESPONSIVE};

        p {
            font-size: 20px;
        }
    }

    @media (max-width: ${MD_VIEW}) {
        p {
            font-size: 16px;
        }
    }
`;


const HeroWrap = ({ backgroundImage, children }) => {

    return (
        <Wrapper url={backgroundImage} >
            <Overlay />
            {children}
        </Wrapper>
    );
}

export default HeroWrap;