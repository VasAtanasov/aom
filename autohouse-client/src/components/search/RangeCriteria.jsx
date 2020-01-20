import React, { useState } from 'react';
import { Row, Col } from 'antd';
import StyledSelect from './StyledSelect';

const RangeCriteria = (props) => {

    const { criteria, className } = props;

    const [criteriaFrom, setCriteriaFrom] = useState(criteria[0].id);
    const [criteriaTo, setCriteriaTo] = useState(criteria[criteria.length - 1].id);

    return (
        <Row type="flex" className={className}>
            <Col xs={12} sm={8} md={6}>
                <StyledSelect
                    name="From"
                    options={criteria.filter(({ id }) => id <= criteriaTo)}
                    handleChange={setCriteriaFrom}
                />

            </Col>
            <Col xs={12} sm={8} md={6}>
                <StyledSelect
                    name="To"
                    options={criteria.filter(({ id }) => id >= criteriaFrom)}
                    handleChange={setCriteriaTo}
                />
            </Col>
        </Row>
    )
}

export default RangeCriteria;