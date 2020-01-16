import React from 'react';
import { Card } from 'antd';

const { Meta } = Card;

const PriceCard = (props) => {

    const priceTag = props.price;

    const bodyTypes = [
        "Sedans",
        "SUVs",
        "Wagons",
        "All"
    ]

    return (
        <Card hoverable title={`Under ${priceTag} EUR`}>
            <Meta
                description={(
                    <ul>
                        {bodyTypes.map((body, idx) => (
                            <li key={body + idx}>{body} under {priceTag} EUR</li>
                        ))}
                    </ul>
                )}
            />
        </Card>
    )
}

export default PriceCard;