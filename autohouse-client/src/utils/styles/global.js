import { createGlobalStyle } from 'styled-components/macro';

const defaultColorHover = 'rgba(247, 183, 29, 0.3)';

export default createGlobalStyle`

    *,
    *:before,
    *:after {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline;
        color: #000;
    }

    /* HTML5 display-role reset for older browsers */
    article, aside, details, figcaption, figure, 
    footer, header, hgroup, menu, nav, section {
        display: block;
    }
    
    body {
        line-height: 1;
        background-color: #f7f7f7 !important;
    }

    ol, ul {
        list-style: none;
    }
    
    blockquote, q {
        quotes: none;
    }

    blockquote:before, blockquote:after,
    q:before, q:after {
        content: '';
        content: none;
    }

    table {
        border-collapse: collapse;
        border-spacing: 0;
    }

    body {
        font-family: 'Open Sans', sans-serif;
        font-size: 13px;
        line-height: 1.4;
        font-weight: 400;
        background-color: #f4f4f4;
    }

    code {
        font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New',
        monospace;
    }

    a {
        -webkit-transition: .3s all ease;
        -o-transition: .3s all ease;
        transition: .3s all ease;
        color: #000;
    }

    a:hover, a:focus {
        text-decoration: none;
        outline: none !important;
    }

    h1, h2, h3, h4, h5,
    .h1, .h2, .h3, .h4, .h5 {
        line-height: 1.5;
        font-weight: 400;
    }
    
    & .ant-select-dropdown-menu-item:hover:not(.ant-select-dropdown-menu-item-disabled) {
        background-color: ${defaultColorHover} !important;
    }

    & .ant-select-dropdown-menu-item-active:not(.ant-select-dropdown-menu-item-disabled) {
        background-color: ${defaultColorHover} !important;
    }

    html {
        --antd-wave-shadow-color: ${props => props.theme.colors.defaultColor} !important;
    }

`;