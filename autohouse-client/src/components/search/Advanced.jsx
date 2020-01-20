import React from 'react';
import styled from "styled-components";
import { Container } from '../../util/commonWrappers';
import { Row, Col, Typography, Collapse } from 'antd';
// import { DEFAULT_COLOR } from '../../util/constants';
import CheckboxCriteria from './CheckboxCriteria'
import RangeCriteria from './RangeCriteria'
import configObj from '../../configObj';
import StyledButton from '../common/StyledButton';
// import IconLabel from '../common/IconLabel'

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
            <StyledButton>
                Reset all filters
            </StyledButton>
        </Col>
    </Row>
);

const IconLabel = ({ iconClass, text }) => (
    <label>
        <i className={iconClass}></i>
        <span>{text}</span>
    </label>
);

const IconLabelWrapper = styled.div`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    
    & span {
        color: #555a60;
    }

    & i::before {
        margin-left: 0;
        margin-right: 8px;
        font-weight: 400;
        font-size: 22px;
        color: #555a60;
    }
`;

const MainSection = () => {
    const metadata = configObj.metadata;
    const searchCriteriaNamesForCheckboxCriteria = metadata.searchCriteriaNamesForCheckboxCriteria;
    // const makeModel = { title: "Make & model", isActive: true, key: "" };

    const genExtra = ({ iconClass, text }) => (
        <IconLabelWrapper>
            <IconLabel iconClass={iconClass} text={text} />
        </IconLabelWrapper>
    );

    const searchSections = [
        { title: "Fuel type", isActive: true, key: "fuelTypes", "iconClass": "flaticon-gas-station" },
        { title: "Transmission", isActive: true, key: "transmissionTypes", "iconClass": "flaticon-shifter" },
        { title: "Mileage", isActive: true, key: "mileages", "iconClass": "flaticon-road" },
        { title: "First registration", isActive: true, key: "registrationYears", "iconClass": "flaticon-car-key" },
        { title: "Power", isActive: false, key: "horsePowers", "iconClass": "flaticon-engine" },
        { title: "Body type", isActive: false, key: "bodyTypes", "iconClass": "flaticon-convertible" },
        { title: "Price", isActive: false, key: "prices", "iconClass": "flaticon-coin" },
        { title: "Equipment", isActive: false, key: "equipments", "iconClass": "flaticon-turbo-engine" },
        { title: "Colour", isActive: false, key: "bodyColors", "iconClass": "flaticon-contrast" },
        { title: "Damage", isActive: false, key: "damageTypes", "iconClass": "flaticon-car-accident" },
        { title: "Emission standard", isActive: false, key: "effluentStandards", "iconClass": "flaticon-smoke" },
        { title: "Sear count", isActive: false, key: "seatCount", "iconClass": "flaticon-car-door" },
    ];

    return (
        <Row className="search-options" type="flex" justify="space-between" align="middle">
            {searchSections.map((section, idx) => (
                <Col key={section.title + idx} span={24}>
                    <Collapse
                        key={section.title + idx}
                        defaultActiveKey={section.isActive ? 1 : 0}
                    >
                        <Panel header={genExtra({ iconClass: section.iconClass, text: section.title })} key="1">
                            {searchCriteriaNamesForCheckboxCriteria.includes(section.key)
                                ? <CheckboxCriteria criteria={metadata[section.key]} />
                                : <RangeCriteria criteria={metadata[section.key]} />}
                        </Panel>
                    </Collapse>
                </Col>
            ))}
        </Row>
    )
}

const IconContainer = styled(Col)`
    i:before {
        margin-left: 10px;
        font-size: 25px;
    }
`;

const FooterSection = () => {

    return (
        <Row className="footer" type="flex" justify="end" align="middle">
            <Col span={24} md={12} lg={8}>
                <StyledButton className="search-button">
                    <Row type="flex" justify="center" align="middle">
                        <Col>
                            <span>Show 2311 vehicles</span>
                        </Col>
                        <IconContainer>
                            <i className="flaticon-searching-car"></i>
                        </IconContainer>
                    </Row>
                </StyledButton>
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