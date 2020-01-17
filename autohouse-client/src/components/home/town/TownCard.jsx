import React from 'react';
import { Card } from 'antd';

const TownCard = (props) => {
    const townName = props.townName;
    const imageUrl = props.imageSrc;

    return (
        <Card
            hoverable
            cover={(
                <div className="city-card">
                    <img className="preloader" src={imageUrl} alt={townName} />
                    <div className="city-card-image-container" style={{ backgroundImage: `url(${imageUrl})` }}>
                        <h3 className="city-card-title">{townName}</h3>
                    </div>
                </div>
            )}
        />
    )
}

export default TownCard;