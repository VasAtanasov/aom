import React from 'react';
import styled from 'styled-components';
import { FormGroup, SecondaryButton } from './commonStyles';

export const InputHidden = styled.input`
    display: none;

    :checked ~ label {
        color: white;
        background-color: #fc983c;
        border: 1px solid #fc983c;
    }
`;
const Radio = ({ elemId, name, text, isChecked }) => {
    return (
        <FormGroup>
            <InputHidden id={elemId} name={name} type="radio" />
            <SecondaryButton as='label' htmlFor={elemId}>{text}</SecondaryButton>
        </FormGroup>
    )
}

export default Radio;