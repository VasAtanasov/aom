import styled from 'styled-components';
import { Row, Col, Typography, Radio } from 'antd';
import {
    HERO_MAIN_HEIGHT,
    MD_VIEW,
    LG_VIEW,
    HERO_MAIN_RESPONSIVE,
    DEFAULT_COLOR
} from '../../../util/constants';

const { Title } = Typography;

export const QuickSearchContainer = styled(Row)`
    min-height: ${HERO_MAIN_HEIGHT};

    @media (max-width: ${LG_VIEW}) {
        padding: 5rem 0;
        min-height: ${HERO_MAIN_RESPONSIVE};
    }
`;

export const HomeTitle = styled(Title)`
    color: #ffffff !important;

    @media (max-width: ${MD_VIEW}) {
        display: none;
    }
`;

export const HomeSubTitle = styled(HomeTitle)`

    @media (max-width: ${LG_VIEW}) {
        display: none;
    }
`;

export const SearchForm = styled(Row)`
    background-color: white;
    padding: 1rem;
`;

export const SearchFormHeader = styled(Row)`
    
`;

export const Tab = styled(Col)`
    background-color: white;
`;

export const TabTitle = styled.div`
    text-align: center;
    text-transform: uppercase;
    font-weight: 700 !important;
    margin: 0 !important;
    padding: 0.3rem;
`;

export const RadioGroup = styled(Radio.Group)`
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

export const SelectContainer = styled(Col)`
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

export const SearchButtonContainer = styled(Col)`
    padding: 1.5rem 0.5rem 0.5rem ! important;

    & .search-button {
        height: 40px !important;
    }
`;