import React, { Fragment } from "react";
import { Home } from "./components";
import Theme from "./Theme";
import GlobalStyles from './utils/styles/global';

const pageToShow = pageName => {
    if (pageName === "Home") return <Home />;
    return <div>Not Found</div>;
};

const App = ({ pageName }) => {
    return (
        <Theme>
            <GlobalStyles />
            <Fragment>{
                pageToShow(pageName)}
            </Fragment>
        </Theme>
    )
};

export default App;
