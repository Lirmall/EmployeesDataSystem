import React from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import About from "../pages/About";
import Positions from "../pages/Positions";
import Error from "../pages/Error";
import PositionEditPage from "../pages/PositionEditPage";
import Home from "../pages/Home";
import AllPositions from "../pages/AllPositions";
import PositionsChosePage from "../pages/PositionsChosePage";
import ServerFilterPositionsPage from "../pages/positionsPages/ServerFilterPositionsPage";
import SalaryPage from "../pages/salaryPage/SalaryPage";
import LoginPage from "../pages/LoginPage";

const AppRouter = () => {
    return (
        <Switch>
            <Route exact path="/">
                <Home/>
            </Route>
            <Route exact path="/login">
                <LoginPage/>
            </Route>
            <Route path="/about">
                <About/>
            </Route>
            {/*<Route exact path="/positions">*/}
            {/*    <Positions/>*/}
            {/*</Route>*/}
            <Route exact path="/positions">
                <PositionsChosePage/>
            </Route>
            <Route exact path="/positions/all">
                <AllPositions/>
            </Route>
            <Route exact path="/positions/filter">
                <ServerFilterPositionsPage/>
            </Route>
            <Route exact path="/positions/:id">
                <PositionEditPage/>
            </Route>
            <Route exact path="/salary">
                <SalaryPage/>
            </Route>
            <Route path="/error">
                <Error/>
            </Route>
            <Redirect to="/error"/>
        </Switch>
    );
};

export default AppRouter;