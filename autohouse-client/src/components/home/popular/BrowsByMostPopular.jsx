import React from 'react';
import styled from 'styled-components';
import { Col, } from 'antd';
import PopularCard from './PopularCard'

const MostPopularCardContainer = styled(Col)`
    & .ant-card {
        margin: 10px !important;

        :hover {
            border-color: rgba(0, 0, 0, 0.15);
        }

        & .ant-card-body {
            padding: 15px 24px;
            
            & .ant-card-meta {
                & .ant-card-meta-detail {
                    & .ant-card-meta-description {

                    }
                }
            }
            
        }
    }
`;

const BrowsByMostPopular = () => {
    const pathToImages = '/images/popular/';
    const searches = [
        {
            id: "vw-golf",
            name: "VW Golf",
            offersCount: "62 383"
        },
        {
            id: "audi-a4",
            name: "Audi A4",
            offersCount: "13 734"
        },
        {
            id: "audi-a6",
            name: "Audi A6",
            offersCount: "9 520"
        },
        {
            id: "audi-a3",
            name: "Audi A3",
            offersCount: "18 533"
        },
        {
            id: "bmw-3er",
            name: "BMW 3er",
            offersCount: "14 070"
        },
        {
            id: "opel-astra",
            name: "Opel Astra",
            offersCount: "15 598"
        },
        {
            id: "mercedes-benz-c",
            name: "Mercedes - Benz C",
            offersCount: "18 650"
        },
        {
            id: "mercedes-benz-e",
            name: "Mercedes - Benz E",
            offersCount: "9 959"
        },
    ]
    const extensionJpg = ".jpg";

    return searches.map((obj, idx) => (
        <MostPopularCardContainer
            key={obj.id}
            xs={12}
            sm={12}
            md={8}
            lg={6}
        >
            <PopularCard
                name={obj.name}
                offersCount={obj.offersCount}
                imageSrc={`${pathToImages}${obj.id}${extensionJpg}`}
            />
        </MostPopularCardContainer>
    ));
}

export default BrowsByMostPopular;