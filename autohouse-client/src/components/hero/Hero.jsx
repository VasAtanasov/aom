import React from 'react';
import styled from 'styled-components';
import { Parallax } from 'react-parallax';

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
    opacity: .6;
    background: #000000;
    height: ${HERO_MAIN_HEIGHT};

    @media (max-width: ${LG_VIEW}) {
        height: ${HERO_MAIN_RESPONSIVE};
    }
`;

const Wrapper = styled.div`
    width: 100%;
    height: ${HERO_MAIN_HEIGHT};
    position: inherit;
    background-size: cover;
    background-repeat: no-repeat;
    background-position: top center;

    p {
        font-size: 32px;
    }

    @media (max-width: ${LG_VIEW}) {
        height: ${HERO_MAIN_RESPONSIVE};

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
        <Parallax
            blur={1}
            bgImage={backgroundImage}
            bgImageAlt="hero"
            strength={300}
        >
            <Wrapper >
                <Overlay />
                {children}
            </Wrapper>
        </Parallax>

    );
}

export default HeroWrap;