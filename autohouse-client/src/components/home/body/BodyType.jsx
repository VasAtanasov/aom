import React, { useState } from 'react';
import styled from 'styled-components';
import { DEFAULT_COLOR } from '../../../util/constants';

const BodyTypeCardComponent = (props) => {
    const styledCompClass = props.className;
    const imgSrc = props.imageSrc;
    const hooImgSrc = props.hoverImageSrc;
    const bodyType = props.bodyType;

    const [display, setDisplay] = useState('none');

    return (
        <div className={styledCompClass}
            onMouseEnter={() => setDisplay('block')}
            onMouseLeave={() => setDisplay('none')}
        >
            <img src={imgSrc} className="preloader" alt={bodyType} />
            <img src={hooImgSrc} className="preloader" alt={bodyType} />
            <div className="body-type-image" style={{ backgroundImage: `url(${imgSrc})` }}>
                <span className="overlay" style={{ backgroundImage: `url(${hooImgSrc})`, display: display }}></span>
            </div>
            <span className="caption">{bodyType}</span>
        </div>
    )
}

const BodyTypeCard = styled(BodyTypeCardComponent)`
    cursor: pointer;
    display: flex;
    position: relative;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;

    & .preloader {
        display: none;
    }

    & .body-type-image {
        position: relative;
        width: 100%;
        height: 130px;
        background-size: contain;
        background-repeat: no-repeat;
        background-position: center;

        & .overlay {
            position: absolute;
            width: 100%;
            height: 130px;
            cursor: pointer;
            display: none;
            background-size: contain;
            background-repeat: no-repeat;
            background-position: center;
        }
    }

    & span.caption {
        display: inline-block;
        text-align: center;
        color: #555a60;
        margin: auto;
        font-weight: 600;
        letter-spacing: 1.1px;
        text-transform: uppercase;
        transition: 0.3s;
    }

    :hover span.caption {
        color: ${DEFAULT_COLOR};
        letter-spacing: 3px;
        font-weight: 800;
    }
`;

export default BodyTypeCard;