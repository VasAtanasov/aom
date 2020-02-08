import React, { useState } from 'react';
import ChildComponent from './Home.component'

const Home = ({ }) => {

    const [weCool, setWeCool] = useState(true);

    return (
        <ChildComponent weCool={weCool} />
    )
}

export default Home;