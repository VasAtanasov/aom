import styled from 'styled-components';

export const FormGroup = styled.div`
    position: relative;
    width: 100%;
    display: block;
`;

export const Button = styled.button`
    display: block !important;
    cursor: pointer;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    -ms-border-radius: 3px;
    border-radius: 3px;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
    font-size: 14px;
    padding: .375rem .75rem;
    text-align: center;
`;

export const PrimaryButton = styled(Button)`
    background: #fc983c;
    border: 1px solid #fc983c;
    color: #fff;
`;

export const SecondaryButton = styled(Button)`
    color: #6c757d;
    border-color:  #6c757d;
`;