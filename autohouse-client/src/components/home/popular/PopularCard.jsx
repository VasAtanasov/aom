import React from 'react';
import { Card } from 'antd';

const { Meta } = Card;

const PopularCard = (props) => {
    const name = props.name;
    const offersCount = props.offersCount;
    const imageSrc = props.imageSrc;

    return (
        <Card hoverable cover={
            <img
                alt="example"
                src={imageSrc}
            />
        }>
            <Meta
                title={name}
                description={`${offersCount} offers`}
            />
        </Card>
    )
}

export default PopularCard;