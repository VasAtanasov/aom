import React from 'react';
import styled from 'styled-components';
import { Container } from '../../util/commonWrappers';

const FooterContainer = styled.footer`
    background-color: #262626;
    
    & div {
        color: white;
        height: 30vh;
    }
`;

const Footer = () => {
    return (
        <FooterContainer>
            <Container>
            </Container>
        </FooterContainer>
    )
}

export default Footer;