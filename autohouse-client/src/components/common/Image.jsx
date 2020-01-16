import React from 'react';

const Image = (props) => {
    let source = props.imageIsRelative ?
        'img/' + props.imageSource :
        '/' + props.imageSource;

    return (
        <img src={source} alt="" />
    )
}

export default Image;