import React from 'react';
import styled from 'styled-components';
import { Col } from 'antd';
import BodyTypeCard from './BodyType';

const BodyCardContainer = styled(Col)`
    margin-bottom: 10px;
    padding-right: 15px !important;
    padding-left: 15px !important;
`;

const BrowseByBody = () => {
    const pathToImages = '/images/body-styles/';
    const carImageSuffix = "-angled";
    const bodyStyles = [
        {
            bodyStyle: "sedan",
            links: [
                "https://www.dropbox.com/s/82k6tyzc7ba4zyd/sedan.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/xmco66rrmv884fc/sedan-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "coupe",
            links: [
                "https://www.dropbox.com/s/6hani3j4xpv9uot/coupe.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/bg5krobynln70cq/coupe-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "wagon",
            links: [
                "https://www.dropbox.com/s/vghvgskehjtmfl1/wagon.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/ef9bs49deszxybo/wagon-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "convertible",
            links: [
                "https://www.dropbox.com/s/2mkir0vzig3aje2/convertible.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/hawfb4pcklmrd4o/convertible-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "crossover",
            links: [
                "https://www.dropbox.com/s/klwv4of3umhs68q/crossover.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/z3g3vcv0xy8ozcz/crossover-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "luxury",
            links: [
                "https://www.dropbox.com/s/jp9ziwsrmlj2ybz/luxury.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/bwnt7ybycpe6cqc/luxury-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "sport",
            links: [
                "https://www.dropbox.com/s/43yllrxfjbalez3/sport.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/m14cy2c5uucvnir/sport-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "suv",
            links: [
                "https://www.dropbox.com/s/gskjkecu0z65g8q/suv.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/gw6iiox8c8186ye/suv-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "cpo",
            links: [
                "https://www.dropbox.com/s/5u86k156axd52i3/cpo.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/ecsbd2tacxnd57p/cpo-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "hybrid",
            links: [
                "https://www.dropbox.com/s/lxjrcju4o2rsya6/hybrid.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/ykha5y3l3jqgvt0/hybrid-angled.jpg?dl=0&raw=1",
            ]
        },
        {
            bodyStyle: "minivan",
            links: [
                "https://www.dropbox.com/s/ciwas65xqsv92fc/minivan.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/pxef6jy3uj5ohwz/minivan-angled.jpg?dl=0&raw=1",

            ]
        },
        {
            bodyStyle: "truck",
            links: [
                "https://www.dropbox.com/s/ghsufczdbc5u376/truck.jpg?dl=0&raw=1",
                "https://www.dropbox.com/s/99gsgl0kdu1z7y1/truck-angled.jpg?dl=0&raw=1",
            ]
        },
    ];
    const extensionJpg = ".jpg";

    return bodyStyles.map((obj, idx) => (
        <BodyCardContainer
            key={obj.bodyStyle + idx}
            span={12}
            lg={6}
            md={8}
        >
            <BodyTypeCard
                bodyType={obj.bodyStyle}
                imageSrc={`${pathToImages}${obj.bodyStyle}${extensionJpg}`}
                hoverImageSrc={`${pathToImages}${obj.bodyStyle}${carImageSuffix}${extensionJpg}`}
            />
        </BodyCardContainer>
    ));

}

export default BrowseByBody;