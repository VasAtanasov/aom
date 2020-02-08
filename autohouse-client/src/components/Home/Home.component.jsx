
import React from 'react';
import { StyledContainer } from './Home.styles.js';

const MyComponent = ({ weCool }) => (
    <StyledContainer>
        Hello welcome to the child component.
        Are we cool? {weCool && 'You bet'}
    </StyledContainer>
);

export default MyComponent;