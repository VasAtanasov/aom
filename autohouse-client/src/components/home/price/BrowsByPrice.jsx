import React from 'react';
import styled from 'styled-components';
import { Col } from 'antd';
import { DEFAULT_COLOR } from '../../../util/constants'
import PriceCard from './PriceCard'

const PriceTagContainer = styled(Col)`

    & .ant-card {
        margin: 10px !important;
        cursor: default;

        & .ant-card-head .ant-card-head-wrapper .ant-card-head-title  {
            text-align: center;
            cursor: pointer;
            transition: 0.2s;
            
            :hover {
                color: ${DEFAULT_COLOR}
            }
        }

        & .ant-card-meta .ant-card-meta-detail .ant-card-meta-description {
            font-size: 13px;
            font-weight: 500;

            li {
                cursor: pointer;
                margin-bottom: 7px;
                text-align: center;
                transition: 0.2s;

                :hover {
                    color: ${DEFAULT_COLOR}
                }
            }

        }
    }
    
`;

const BrowsByPrice = () => {
    const pathToImages = '/images/by-price/';
    const imgPrefix = 'car-'
    const prices = [
        { id: 1, price: "1,000" },
        { id: 2, price: "2,000" },
        { id: 3, price: "3,000" },
        { id: 4, price: "4,000" },
        { id: 5, price: "5,000" },
        { id: 6, price: "10,000" },
        { id: 7, price: "15,000" },
        { id: 8, price: "20,000" },
    ];
    const extensionJpg = ".jpg";

    return prices.map((tag, idx) => (
        <PriceTagContainer
            key={tag.id}
            xs={24}
            sm={12}
            md={8}
            lg={6}
        >
            <PriceCard
                price={tag.price}
                id={tag.id}
                imageSrc={`${pathToImages}${imgPrefix}${tag.id}${extensionJpg}`}
            />
        </PriceTagContainer>
    ));
}

export default BrowsByPrice;