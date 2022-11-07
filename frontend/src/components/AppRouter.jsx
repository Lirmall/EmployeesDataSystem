import React from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import About from "../pages/About";
import Positions from "../pages/Positions";
import Error from "../pages/Error";
import PositionEditForm from "./UI/forms/PositionEditForm";
import PositionEditPage from "../pages/PositionEditPage";

const AppRouter = () => {
    return (
        <Switch>
            <Route path="/about">
                <About/>
            </Route>
            <Route exact path="/positions">
                <Positions/>
            </Route>
            <Route exact path="/positions/:id">
                <PositionEditPage/>
            </Route>
            <Route path="/error">
                <Error/>
            </Route>
            <Redirect to="/error"/>
        </Switch>
    );
};

export default AppRouter;