import React from 'react';
import {ButtonGroup} from "reactstrap";
import MyButton from "../components/UI/button/MyButton";
import {useHistory} from "react-router-dom";

const Home = () => {
    const router = useHistory();
    return (
        <div>
            <ButtonGroup style={{marginTop: 5, display: "flex", textAlign: "right"}}>
                <MyButton onClick={() => router.push('/positions')}>Positions</MyButton>
            </ButtonGroup>
        </div>
    );
};

export default Home;