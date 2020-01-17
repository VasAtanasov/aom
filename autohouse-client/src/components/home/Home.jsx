import React, { Fragment } from 'react';
import styled from 'styled-components';
import { Tabs } from 'antd';
import HeroWrap from '../hero/Hero';
import BrowseByMake from './make/BrowseByMake';
import QuickSearch from './search/QuickSearch';
import BrowseByBody from './body/BrowseByBody';
import BrowsByPrice from './price/BrowsByPrice';
import BrowsByTown from './town/BrowsByTown';
import BrowsByMostPopular from './popular/BrowsByMostPopular';
import BrowsByContainer from './BrowsByContainer';
import { DEFAULT_COLOR } from '../../util/constants';

const { TabPane } = Tabs;
const bgImage = '/images/bg_6.jpg';

const HomeSection = styled.section`
    border-bottom: 1px solid #e2e2e2;
`;

const HomeSectionWhite = styled(HomeSection)`
    background-color: white;
`;

const TabsContainer = styled(Tabs)`

    & .ant-tabs-bar {
        margin-top: 20px;
        margin-bottom: 20px;
        border-bottom: 0px;

        & .ant-tabs-nav-container {

            & .ant-tabs-nav-wrap {

                & .ant-tabs-nav-scroll {
                    display: flex;
                    justify-content: center;

                    & .ant-tabs-nav {

                         & .ant-tabs-tab {
                            :hover {
                                color: ${DEFAULT_COLOR};
                            }
                        }
                        
                        & .ant-tabs-tab.ant-tabs-tab-active {
                            color: ${DEFAULT_COLOR};
                        }

                        & .ant-tabs-ink-bar {
                            background-color: ${DEFAULT_COLOR};
                        }
                    }
                }
            }
        }
    }
`;

const Home = () => {

    return (
        <Fragment>
            <HeroWrap backgroundImage={bgImage}>
                <QuickSearch />
            </HeroWrap>
            <HomeSectionWhite>
                <BrowsByContainer title="Browse by most popular">
                    <TabsContainer defaultActiveKey="1">
                        <TabPane tab="Body type" key="1">
                            <BrowseByBody />
                        </TabPane>
                        <TabPane tab="Model" key="2">
                            <BrowsByMostPopular />
                        </TabPane>
                    </TabsContainer>
                </BrowsByContainer>
            </HomeSectionWhite>
            <HomeSection>
                <BrowsByContainer title="Price ranges">
                    <BrowsByPrice />
                </BrowsByContainer>
            </HomeSection>
            <HomeSectionWhite>
                <BrowsByContainer title="Most popular car makes">
                    <BrowseByMake />
                </BrowsByContainer>
            </HomeSectionWhite>
            <HomeSection>
                <BrowsByContainer title="Browse by town">
                    <BrowsByTown />
                </BrowsByContainer>
            </HomeSection>
        </Fragment>
    )
}

export default Home;