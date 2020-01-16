import React, { Fragment } from 'react';
import styled from 'styled-components';
// import { Select, Row, Col, Typography, Radio, Button } from 'antd';
// import { Container } from '../../util/commonWrappers';

import HeroWrap from '../hero/Hero';
import BrowseByMake from './make/BrowseByMake';
import bgImage from '../../resources/images/bg_6.jpg';
import QuickSearch from './search/QuickSearch';
import BrowseByBody from './body/BrowseByBody';


const HomeSection = styled.section`
    border-bottom: 1px solid #e2e2e2;
`;

const HomeSectionWhite = styled(HomeSection)`
    background-color: white;
`;

// const HomeSectionGrey = styled(HomeSection)`
//      background-color: #f7f7f7;
// `;

const Home = () => {

    return (
        <Fragment>
            <HeroWrap backgroundImage={bgImage}>
                <QuickSearch />
            </HeroWrap>
            <HomeSectionWhite>
                <BrowseByBody />
            </HomeSectionWhite>
            <HomeSectionWhite>
                <BrowseByMake />
            </HomeSectionWhite>
        </Fragment>
    )
}

export default Home;