import React from "react";
import { Home } from "./components";

const pageToShow = pageName => {
    if (pageName === "Home") return <Home />;
    return <div>Not Found</div>;
};

const App = ({ pageName }) => {
    return (
        <div>{pageToShow(pageName)}</div>
    )
};

export default App;
