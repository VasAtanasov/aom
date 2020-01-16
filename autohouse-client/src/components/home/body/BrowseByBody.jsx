import React from 'react';
import styled from 'styled-components';
import { Row, Col, } from 'antd';
import SectionTitle from '../SectionTitle';
import BodyTypeCard from './BodyType';
import HomeSectionContainer from '../HomeSectionContainer'
import { HOME_SECTION_CONTAINER_SIZE } from '../../../util/constants'

const BodyCardContainer = styled(Col)`
    margin-bottom: 10px;
    padding-right: 15px !important;
    padding-left: 15px !important;
`;

const BrowseByBody = () => {
    const pathToImages = '/images/body-styles/';
    const carImageSuffix = "-angled";
    const bodyStyles = [
        "sedan",
        "coupe",
        "wagon",
        "convertible",
        "crossover",
        "luxury",
        "sport",
        "suv",
        "cpo",
        "hybrid",
        "minivan",
        "truck",
    ];
    const extensionJpg = ".jpg";

    return (
        <HomeSectionContainer>
            <SectionTitle title="Browse by body type" />
            <Row type="flex" style={HOME_SECTION_CONTAINER_SIZE}>
                {bodyStyles.map((body, idx) => (
                    <BodyCardContainer
                        key={body + idx}
                        span={12}
                        lg={6}
                        md={8}
                    >
                        <BodyTypeCard
                            bodyType={body}
                            imageSrc={`${pathToImages}${body}${extensionJpg}`}
                            hoverImageSrc={`${pathToImages}${body}${carImageSuffix}${extensionJpg}`}
                        />
                    </BodyCardContainer>
                ))}
            </Row>
        </HomeSectionContainer>
    )
}

export default BrowseByBody;