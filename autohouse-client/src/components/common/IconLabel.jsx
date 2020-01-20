import React from './react';

const IconLabel = ({ iconClass, text }) => (
    <label>
        <i className={iconClass} />
        <span>{text}</span>
    </label>
)

export default IconLabel;