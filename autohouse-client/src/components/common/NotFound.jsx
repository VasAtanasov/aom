import React from 'react';
import styled from 'styled-components';

const ErrorPageContainer = styled.div`
    width: 100vw;
    height: 100vh;
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    & .error-bg-container {
        width: 300px;
        height: 300px;
    }

    & .error_bg {
        background-image: url(/images/error_page.gif);
        width: 100%;
        height: 100%;
        background-position: center;
        background-repeat: no-repeat;

        & h1 {
            font-size: 80px;
            text-align: center;
        }

        & h3 {
            font-size: 80px;
        }
    }

    & .message-container .text-message {
        color: red;
        font-size: 40px;
    }
`;


const ErrorPage = () => (
    <ErrorPageContainer>
        <div className="error-bg-container">
            <div className="error_bg">
                <h1 className="text-center">404</h1>
            </div>
        </div>
        <div className="message-container text-center">
            <h2 className="text-message">Not found</h2>
        </div>
    </ErrorPageContainer>
)

export default ErrorPage;