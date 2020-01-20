import React, { useState, useEffect } from 'react';
import { Select, Col, Radio } from 'antd';
import { Container } from '../../../util/commonWrappers';
import {
    QuickSearchContainer,
    HomeTitle,
    HomeSubTitle,
    SearchFormHeader,
    Tab,
    TabTitle,
    SearchForm,
    RadioGroup,
    SelectContainer,
    SearchButtonContainer,
} from './style';
import StyledButton from '../../common/StyledButton';
import http from '../../../util/requester'

const { Option } = Select;

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

    const [makes, setMakes] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);

            const response = await http.get({
                url: "/makers/all"
            });

            setMakes(response.data);
            setIsLoading(false);
        }
        fetchData();
    }, [])

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
                                        {makes.map((makeObj, idx) => (
                                            <Option key={`${makeObj.name}_${makeObj.id}_${idx}`} value={makeObj.name}>{makeObj.prettyName}</Option>
                                        ))}

                                    </Select>
                                </SelectContainer>
                            ))}
                            <SearchButtonContainer span={24}>
                                <StyledButton className="search-button">
                                    Search
                                </StyledButton>
                            </SearchButtonContainer>
                        </SearchForm>
                    </Container>
                </Col>
            </QuickSearchContainer>
        </Container>
    )

}

export default QuickSearch;