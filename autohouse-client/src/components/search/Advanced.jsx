import React from 'react';
import styled from "styled-components";
import { Container } from '../../util/commonWrappers';
import { Row, Col, Typography, Collapse } from 'antd';
import { DEFAULT_COLOR } from '../../util/constants';
import CheckboxCriteria from './CheckboxCriteria'
import configObj from '../../configObj';
import Button from '../common/Button';

const { Title } = Typography;
const { Panel } = Collapse;

const CarSearchContainer = styled.div`
    padding-top: 80px;
`;

const AdvancedSearch = styled.div`

    & .header {
        margin-bottom: 20px;
    }

    & .search-options {

        & .ant-collapse {
            width: 100%;
            margin-bottom: 10px;
            background-color: white;
            border: none;
            border-radius: 0;

            & .ant-collapse-item {
                border: none;

                & .ant-collapse-header {
                    font-size: 128%;
                    font-weight: bold;
                }

                & .ant-collapse-content {
                    border: none;

                    & .ant-collapse-content-box {

                    }
                }
            }
        }

    }

    & .footer {
        position: sticky;
        position: -webkit-sticky;
        bottom: 0;
        background-color: rgba(247, 247, 247, 0.8);
        padding: 20px 0;
    }

`;

const HeaderSection = () => (
    <Row className="header" type="flex" align="middle">
        <Col span={24} lg={18}>
            <Title level={2}>Advanced search</Title>
        </Col>
        <Col span={24} lg={6}>
            <Button text={"Reset all filters"} />
        </Col>
    </Row>
);

const MainSection = () => {
    const metadata = configObj.metadata;

    const searchSections = [
        // { title: "Make & model", isActive: true, key: "", type: CheckboxCriteria, },
        { title: "Fuel type", isActive: true, key: "fuelTypes", type: "searchCriteriaNamesForCheckboxCriteria", },
        { title: "Transmission", isActive: true, key: "transmissionTypes", type: "searchCriteriaNamesForCheckboxCriteria", },
        // { title: "Mileage", isActive: true, key: "", type: CheckboxCriteria, },
        // { title: "First registration", isActive: true, key: "", type: CheckboxCriteria, },
        // { title: "Engine size", isActive: true, key: "", type: CheckboxCriteria, },
        // { title: "Power", isActive: false, key: "", type: CheckboxCriteria, },
        { title: "Body type", isActive: false, key: "bodyTypes", type: "searchCriteriaNamesForCheckboxCriteria", },
        // { title: "Price", isActive: false, key: "", type: CheckboxCriteria, },
        { title: "Equipment", isActive: false, key: "equipments", type: "searchCriteriaNamesForCheckboxCriteria", },
        { title: "Colour", isActive: false, key: "bodyColors", type: "searchCriteriaNamesForCheckboxCriteria", },
        { title: "Origin country", isActive: false, key: "originCountries", type: "searchCriteriaNamesForCheckboxCriteria", },
        { title: "Damage", isActive: false, key: "damageTypes", type: "searchCriteriaNamesForCheckboxCriteria", },
        // { title: "Emission standard", isActive: false, key: "effluentStandards", type: CheckboxCriteria, },
        // { title: "CO2 emission", isActive: false, key: "", type: CheckboxCriteria, },
    ];

    return (
        <Row className="search-options" type="flex" justify="space-between" align="middle">
            {searchSections.map((section, idx) => (
                <Col key={section.title + idx} span={24}>
                    <Collapse
                        key={section.title + idx}
                        defaultActiveKey={section.isActive ? 1 : 0}
                    >
                        <Panel header={section.title} key="1">
                            <CheckboxCriteria criteria={metadata[section.key]} />
                        </Panel>
                    </Collapse>
                </Col>
            ))}
        </Row>
    )
}

const FooterSection = () => {

    return (
        <Row className="footer" type="flex" justify="end" align="middle">
            <Col span={24} md={12} lg={6}>
                <Button className="search-button" text={"Show 2311 vehicles"} />
            </Col>
        </Row>
    )
}

const Advanced = () => {

    return (
        <CarSearchContainer>
            <Container>
                <AdvancedSearch>
                    <HeaderSection />
                    <MainSection />
                    <FooterSection />
                </AdvancedSearch>
            </Container>
        </CarSearchContainer>
    )
}

export default Advanced;