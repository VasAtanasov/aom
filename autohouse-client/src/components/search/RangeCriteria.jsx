import React from 'react';
import { Row, Col } from 'antd';
import StyledSelect from './StyledSelect';

const RangeCriteria = (props) => {
    const styledCompClass = props.className;
    const criteria = props.criteria;

    return (
        <Row type="flex" className={styledCompClass}>
            <Col xs={12} sm={8} md={6}>
                <StyledSelect name="From" options={criteria} />
            </Col>
            <Col xs={12} sm={8} md={6}>
                <StyledSelect name="To" options={criteria} />
            </Col>
        </Row>
    )
}

export default RangeCriteria;