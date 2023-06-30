import React, {useState} from 'react';
import MyInput from "../components/UI/input/MyInput";
import MyButton from "../components/UI/button/MyButton";
import axios from "axios";

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const login = async (event) => {
        event.preventDefault();
        const response = await axios.post("/login", {username, password});
        console.log("1 - response");
        console.log(response);
        console.log("2 - response data headers");

        console.log(response.headers.authorization);
        localStorage.setItem("jwtToken", response.headers.authorization)

        console.log("from local storage")
        console.log(localStorage.getItem("jwtToken"))
    };



    return (
        <div>
            <h1>Welcome! </h1>
            <h1>Enter your username</h1>
            <h1>and password to log in</h1>
            <form>
                <MyInput type="text" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Insert login"/>
                <MyInput type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Insert password"/>
                <MyButton onClick={login}>Log in</MyButton>
            </form>
        </div>
    );
};

export default LoginPage;