import React from 'react';
import styled from 'styled-components';
import { Select, Row, Col, Typography, Radio, Button } from 'antd';
import { Container } from '../../../util/commonWrappers';
import {
    HERO_MAIN_HEIGHT,
    MD_VIEW,
    LG_VIEW,
    HERO_MAIN_RESPONSIVE,
    DEFAULT_COLOR
} from '../../../util/constants';

const { Option } = Select;
const { Title } = Typography;

const QuickSearchContainer = styled(Row)`
    min-height: ${HERO_MAIN_HEIGHT};

    @media (max-width: ${LG_VIEW}) {
        padding: 5rem 0;
        min-height: ${HERO_MAIN_RESPONSIVE};
    }
`;

const HomeTitle = styled(Title)`
    color: #ffffff !important;

    @media (max-width: ${MD_VIEW}) {
        display: none;
    }
`;

const HomeSubTitle = styled(HomeTitle)`

    @media (max-width: ${LG_VIEW}) {
        display: none;
    }
`;

const SearchForm = styled(Row)`
    background-color: white;
    padding: 1rem;
`;

const SearchFormHeader = styled(Row)`
    
`;

const Tab = styled(Col)`
    background-color: white;
`;

const TabTitle = styled.div`
    text-align: center;
    text-transform: uppercase;
    font-weight: 700 !important;
    margin: 0 !important;
    padding: 0.3rem;
`;

const RadioGroup = styled(Radio.Group)`
    width: 100%;
    display: flex !important;

    & .ant-radio-button-wrapper {
        flex: 0 0 33%;
        text-align: center;
        text-transform: uppercase;
        font-size: 12px;
        font-weight: 500;
        letter-spacing: 1.2px;
    }

    & .ant-radio-button-wrapper-checked span,
    & .ant-radio-button-wrapper-checked:active span,
    & .ant-radio-button-wrapper-checked:hover span {
        color: #fff !important;
        text-transform: up
    }

    & .ant-radio-button-wrapper-checked:not(.ant-radio-button-wrapper-disabled) {
        background: ${DEFAULT_COLOR} !important;
        border-color: ${DEFAULT_COLOR} !important;
        -webkit-box-shadow: -1px 0 0 0 ${DEFAULT_COLOR} !important;
                box-shadow: -1px 0 0 0 ${DEFAULT_COLOR} !important;
    }

    & .ant-radio-button-wrapper-checked:not(.ant-radio-button-wrapper-disabled)::before {
        background-color: ${DEFAULT_COLOR} !important;
    }

    & .ant-radio-button-wrapper-checked:not(.ant-radio-button-wrapper-disabled):first-child {
        border-color: ${DEFAULT_COLOR} !important;
        -webkit-box-shadow: none !important;
                box-shadow: none !important;
    }

    &.ant-radio-button-wrapper-checked:not(.ant-radio-button-wrapper-disabled):hover {
        border-color: ${DEFAULT_COLOR} !important;
        -webkit-box-shadow: -1px 0 0 0 ${DEFAULT_COLOR} !important;
                box-shadow: -1px 0 0 0 ${DEFAULT_COLOR} !important;
    }
    
    & .ant-radio-button-wrapper-checked:not(.ant-radio-button-wrapper-disabled):active {
        background: ${DEFAULT_COLOR} !important;
        border-color: ${DEFAULT_COLOR} !important;
    }
`;

const SelectContainer = styled(Col)`
    padding: 0.5rem ! important;

    & .ant-select-selection:hover {
        border-color: ${DEFAULT_COLOR};
        border-right-width: 1px !important;
    }

    & .ant-select-focused .ant-select-selection,
    & .ant-select-selection:focus,
    & .ant-select-selection:active {
        border-color: ${DEFAULT_COLOR};
        -webkit-box-shadow: 0 0 0 2px rgba(247, 183, 29, 0.2);
                box-shadow: 0 0 0 2px rgba(247, 183, 29, 0.2);
    }
`;

const SearchButtonContainer = styled(Col)`
    padding: 2rem 0 0.5rem ! important;

    & .ant-btn-primary {
        text-transform: uppercase;
        font-weight: 500;
        letter-spacing: 1.2px;

        & span {
            color: #fff !important;
        }

        background-color: ${DEFAULT_COLOR};
        border-color: ${DEFAULT_COLOR};
    }

    & .ant-btn-primary:hover,
    & .ant-btn-primary:focus {
        background-color: rgba(247, 183, 29, 0.6);
        border-color: rgba(247, 183, 29, 1);
    }

    & .ant-btn-primary:active,
    & .ant-btn-primary.active {
        background-color: ${DEFAULT_COLOR};
        border-color: ${DEFAULT_COLOR};
    }
`;

const selects = [
    "Make",
    "Model",
    "Fuel type",
    "Transmission",
    "Year",
    "Price",
]

function onChange(value) {
    console.log(`selected ${value}`);
}

function onBlur() {
    console.log('blur');
}

function onFocus() {
    console.log('focus');
}

function onSearch(val) {
    console.log('search:', val);
}

const QuickSearch = () => {

    return (
        <Container>
            <QuickSearchContainer type={"flex"} justify={"center"} align={"middle"}>
                <Col span={24} order={1} lg={{ span: 12, order: 2 }} style={{ color: 'white', textAlign: 'center' }}>
                    <Container>
                        <HomeTitle level={2}>Now it's easy for you to buy a car</HomeTitle>
                        <HomeSubTitle level={4}>Search multiple new & used cars in one easy search</HomeSubTitle>
                    </Container>
                </Col>
                <Col span={24} order={2} lg={{ span: 12, order: 1 }} >
                    <Container>
                        <SearchFormHeader type={"flex"}>
                            <Tab span={24} sm={12}>
                                <TabTitle>Search Offer</TabTitle>
                            </Tab>
                        </SearchFormHeader>
                        <SearchForm type={"flex"} >
                            <SelectContainer span={24}>
                                <RadioGroup defaultValue="a" buttonStyle="solid">
                                    <Radio.Button defaultChecked value="a">All</Radio.Button>
                                    <Radio.Button value="b">New</Radio.Button>
                                    <Radio.Button value="c">Used</Radio.Button>
                                </RadioGroup>
                            </SelectContainer>
                            {selects.map((name, idx) => (
                                <SelectContainer span={24} sm={12} key={name + idx}>
                                    <Select
                                        showSearch
                                        style={{ width: "100%" }}
                                        placeholder={name}
                                        optionFilterProp="children"
                                        onChange={onChange}
                                        onFocus={onFocus}
                                        onBlur={onBlur}
                                        onSearch={onSearch}
                                        filterOption={(input, option) =>
                                            option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                        }
                                    >
                                        <Option value="jack">Jack</Option>
                                        <Option value="lucy">Lucy</Option>
                                        <Option value="tom">Tom</Option>
                                    </Select>
                                </SelectContainer>
                            ))}
                            <SearchButtonContainer span={24}>
                                <Button type="primary" block>
                                    Search
                                        </Button>
                            </SearchButtonContainer>
                        </SearchForm>
                    </Container>
                </Col>
            </QuickSearchContainer>
        </Container>
    )

}

export default QuickSearch;