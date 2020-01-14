import React, { Fragment } from 'react';
import styled from 'styled-components';

import HeroWrap from '../hero/Hero';
import Select from '../common/Select'

import {
    HERO_MAIN_HEIGHT,
    LG_VIEW,
    HERO_MAIN_RESPONSIVE
} from '../../util/constants';

import bgImage from '../../resources/images/bg_3.jpg';
import Radio from '../common/Radio';
import { PrimaryButton } from '../common/commonStyles';

const HomeSearchContainer = styled.section`
    margin-top: 3.5rem;
    width: 100%;
    height: ${HERO_MAIN_HEIGHT};
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    @media (max-width: ${LG_VIEW}) {
        height: ${HERO_MAIN_RESPONSIVE};
    }
`;

const SearchSelectContainer = styled.div`
    flex: 0 0 30%;
    margin-top: 1rem;
`;

const QuickSearchMain = styled.main`
    background: rgba(0, 0, 0, 0.50);
    border: 1px solid rgba(255, 255, 255, 0.35);
    border-radius: 5px;
    padding: 20px 20px;
    animation: fadeIn ease-in;
    list-style-type: none;
    min-width: 80%;


    @media (min-width: ${LG_VIEW}) {
        min-width: 800px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        margin: 0;
    }
`;

const QuickSearchHeaderSection = styled.div`
    flex: 0 0 100%;
    margin-bottom: 0.5rem;

    @media (min-width: ${LG_VIEW}) {
        flex: 0 0 50%;
    }
`;

const QuickSearchHeaderTitle = styled(QuickSearchHeaderSection)`
    color: white;
    font-size: 24px;
    z-index: 1;
    text-align: center;

    @media (min-width: ${LG_VIEW}) {
        font-size: 32px;
    }
`;

const QuickSearchHeaderAd = styled(QuickSearchHeaderSection)`
    display: flex;
    align-items: center;

    & label {
        color: white;
        font-size: 16px;
        border-radius: 50px;
    }
`;

const QuickSearchHeader = styled.header`
    display: flex;
    flex-wrap: wrap;
    min-width: 80%;
    margin-bottom: 1rem;
    
    @media (min-width: ${LG_VIEW}) {
        min-width: 800px;
        flex-wrap: nowrap;

    }
`;

const QuickSearchAction = styled.div`
    z-index: 1;

    & ${PrimaryButton} {
        margin: 2rem;
        font-size: 16px;
        padding: 0.7rem 1.5rem;
        border-radius: 50px;
    }

`;

const Home = () => {
    return (
        <Fragment>
            <HeroWrap backgroundImage={bgImage}>
                <HomeSearchContainer>
                    <QuickSearchHeader>
                        <QuickSearchHeaderTitle as="h2">
                            Find your next car
                        </QuickSearchHeaderTitle>
                        <QuickSearchHeaderAd>
                            <Radio elemId={"new"} name={"add"} text={"New"} />
                            <Radio elemId={"used"} name={"add"} text={"Used"} />
                            <Radio elemId={"all_adds"} name={"add"} text={"All"} />
                        </QuickSearchHeaderAd>
                    </QuickSearchHeader>
                    <QuickSearchMain>
                        <SearchSelectContainer>
                            <Select
                                label="React Select"
                                placeholder="Pick one"
                                options={[
                                    { value: 'Rock' },
                                    { value: 'Paper' },
                                    { value: 'Scissors' }
                                ]}
                            />
                        </SearchSelectContainer>
                        <SearchSelectContainer>
                            <Select
                                label="React Select"
                                placeholder="Pick one"
                                options={[
                                    { value: 'Rock' },
                                    { value: 'Paper' },
                                    { value: 'Scissors' }
                                ]}
                            />
                        </SearchSelectContainer>
                        <SearchSelectContainer>
                            <Select
                                label="React Select"
                                placeholder="Pick one"
                                options={[
                                    { value: 'Rock' },
                                    { value: 'Paper' },
                                    { value: 'Scissors' }
                                ]}
                            />
                        </SearchSelectContainer>
                        <SearchSelectContainer>
                            <Select
                                label="React Select"
                                placeholder="Pick one"
                                options={[
                                    { value: 'Rock' },
                                    { value: 'Paper' },
                                    { value: 'Scissors' }
                                ]}
                            />
                        </SearchSelectContainer>
                        <SearchSelectContainer>
                            <Select
                                label="React Select"
                                placeholder="Pick one"
                                options={[
                                    { value: 'Rock' },
                                    { value: 'Paper' },
                                    { value: 'Scissors' }
                                ]}
                            />
                        </SearchSelectContainer>
                        <SearchSelectContainer>
                            <Select
                                label="React Select"
                                placeholder="Pick one"
                                options={[
                                    { value: 'Rock' },
                                    { value: 'Paper' },
                                    { value: 'Scissors' }
                                ]}
                            />
                        </SearchSelectContainer>
                    </QuickSearchMain>
                    <QuickSearchAction>
                        <PrimaryButton>
                            Show me 199,727 Cars
                        </PrimaryButton>
                    </QuickSearchAction>
                </HomeSearchContainer>
            </HeroWrap>
        </Fragment>
    )
}

export default Home;