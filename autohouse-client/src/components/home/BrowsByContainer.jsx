import React from 'react';
import styled from 'styled-components';
import SectionTitle from './SectionTitle';
import { Row } from 'antd';
import { Container } from '../../util/commonWrappers';
import { HOME_SECTION_CONTAINER_SIZE } from '../../util/constants';

const HomeSectionContainer = styled(Container)`
    overflow: hidden;
    padding: 40px 0!important;
    padding-bottom: 15px;
`;

const BrowsByContainer = ({ title, children }) => (
    <HomeSectionContainer>
        <SectionTitle title={title} />
        <Row type="flex" style={HOME_SECTION_CONTAINER_SIZE}>
            {children}
        </Row>
    </HomeSectionContainer>
);

export default BrowsByContainer;