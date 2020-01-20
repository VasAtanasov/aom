import React from 'react';
import { Row, Col } from 'antd';
import Checkbox from './Checkbox';

const CheckboxCriteria = (props) => {
    const styledCompClass = props.className;
    const criteria = props.criteria;

    return (
        <Row type="flex" className={styledCompClass}>
            {criteria.map((cr, idx) => (
                <Col key={cr.name + cr.id + idx} xs={24} sm={12} lg={8}>
                    <Checkbox id={cr.id} text={cr.name} />
                </Col>
            ))}
        </Row>
    )
}

export default CheckboxCriteria;