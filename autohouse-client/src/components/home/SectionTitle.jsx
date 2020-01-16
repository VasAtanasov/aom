import React from 'react';
import styled from 'styled-components';
import { Row, Col, Typography } from 'antd';
const { Title } = Typography;

const HomeSectionTitleRow = styled(Row)`
    margin-right: 0;
    margin-left: 0;
    padding-right: 0;
    padding-left: 0;
`;

const TitleLine = styled.div`
    border: 1px solid #dee2e6!important
`;

const SectionTitleText = styled(Title)`
    text-align: center;
    padding: 0 10px;
    margin: 0;
    font-size: 24px;
    font-weight: bold;
`;

const StrickThroughTitle = styled(Row)`
    
    & ${TitleLine} {
        flex: 1 0 5px;
        height: 2px;
        background: lightgrey;
    }
`;

const SectionTitle = ({ title }) => {
    return (
        <HomeSectionTitleRow type="flex">
            <Col span={24}>
                <StrickThroughTitle type="flex" align="middle">
                    <TitleLine />
                    <SectionTitleText level={3}>{title}</SectionTitleText>
                    <TitleLine />
                </StrickThroughTitle>
            </Col>
        </HomeSectionTitleRow>
    )
}

export default SectionTitle