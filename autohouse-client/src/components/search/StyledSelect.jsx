import React from 'react';
import styled from "styled-components";
import { DEFAULT_COLOR } from '../../util/constants';
import { Select } from 'antd';

const { Option } = Select;

const SelectWrapper = styled.div`
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

const StyledSelect = ({ name, options }) => (
    <SelectWrapper>
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
            {options.map(({ id, name }, idx) => (
                <Option key={name + idx} value={id}>{name}</Option>
            ))}
        </Select>
    </SelectWrapper>
)

export default StyledSelect;