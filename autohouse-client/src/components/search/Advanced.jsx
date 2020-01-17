import React from 'react';
import styled from "styled-components";
import { Container } from '../../util/commonWrappers';
import { Row, Col, Typography, Button, Collapse } from 'antd';
import { DEFAULT_COLOR } from '../../util/constants';

const { Title } = Typography;
const { Panel } = Collapse;

const CarSearchContainer = styled.div`
    padding-top: 80px;
`;

const ButtonWrapper = styled(Col)`

    & .ant-btn {
        width: 100%;
        height: 40px;
        font-weight: bold;
        text-transform: uppercase;
        border: 1px solid ${DEFAULT_COLOR};
        transition: all 0.2s;

        :active {
            color: ${DEFAULT_COLOR};
        }

        :focus {
            border-color: ${DEFAULT_COLOR};
        }

        span {
            color: ${DEFAULT_COLOR};
        }

        :hover {
            background-color: ${DEFAULT_COLOR};
            border-color: ${DEFAULT_COLOR};

            span {
                color: white;
            }
        }

          
        &.search-button {
            height: 48px;
            background-color: ${DEFAULT_COLOR};

            span {
                color: white;
            }

            :hover {
                background-color: white;

                span {
                    color: ${DEFAULT_COLOR};
                }
            }
        }
    }
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
            <ButtonWrapper>
                <Button className="ghost">Reset all filters</Button>
            </ButtonWrapper>
        </Col>
    </Row>
);

const text = `
  A dog is a type of domesticated animal.
  Known for its loyalty and faithfulness,
  it can be found as a welcome guest in many households across the world.
`;

const MainSection = () => {

    const searchSections = [
        { title: "Make & model", isActive: true },
        { title: "Fuel type", isActive: true },
        { title: "Transmission", isActive: true },
        { title: "Mileage", isActive: true },
        { title: "First registration", isActive: true },
        { title: "Engine size", isActive: true },
        { title: "Power", isActive: false },
        { title: "Body type", isActive: false },
        { title: "Price", isActive: false },
        { title: "Equipment", isActive: false },
        { title: "Colour", isActive: false },
        { title: "Origin country", isActive: false },
        { title: "Seller", isActive: false },
        { title: "Damage", isActive: false },
        { title: "Emission standard", isActive: false },
        { title: "CO2 emission", isActive: false },
    ];

    return (
        <Row className="search-options" type="flex" justify="space-between" align="middle">
            {searchSections.map((section, idx) => (
                <Col span={24}>
                    <Collapse
                        key={section.title + idx}
                        defaultActiveKey={section.isActive ? 1 : 0}
                    >
                        <Panel header={section.title} key="1">
                            {text}
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
            <ButtonWrapper span={24} md={12} lg={6}>
                <Button className="search-button">Show 2311 vehicles</Button>
            </ButtonWrapper>
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