import React from 'react';
import styled from "styled-components";
import { DEFAULT_COLOR } from '../../util/constants';
import { Button } from 'antd';

const ButtonWrapper = styled.div`

    & .ant-btn {
        width: 100%;
        height: 40px;
        font-weight: bold;
        text-transform: uppercase;
        border: 1px solid ${DEFAULT_COLOR};
        transition: all 0.2s;

        :active {
            color: ${DEFAULT_COLOR};
        }

        :focus {
            border-color: ${DEFAULT_COLOR};
        }

        span {
            color: ${DEFAULT_COLOR};
        }

        :hover {
            background-color: ${DEFAULT_COLOR};
            border-color: ${DEFAULT_COLOR};

            span {
                color: white;
            }
        }

          
        &.search-button {
            height: 48px;
            background-color: ${DEFAULT_COLOR};


            span, 
            i::before {
                color: white;
            }

            :hover {
                background-color: white;

                span,
                i::before {
                    color: ${DEFAULT_COLOR};
                }
            }
        }
    }
`;

const StyledButton = ({ children, className }) => (
    <ButtonWrapper>
        <Button className={className}>
            {children}
        </Button>
    </ButtonWrapper>
);

export default StyledButton;