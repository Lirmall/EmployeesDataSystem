import React, {useCallback, useEffect, useReducer, useState} from "react";
import {useFetching} from "../components/hooks/useFetching";
import PositionService from "../API/PositionService";
import MyButton from "../components/UI/button/MyButton";
import PositionAddModal from "../components/UI/myModal/PositionAddModal";
import PositionForm from "../components/UI/forms/PositionForm";
import PositionsTable from "../components/UI/tables/PositionsTable";
import App from "../App";
import {useHistory} from "react-router-dom";
import {ButtonGroup} from "reactstrap";

function PositionsChosePage() {
    const router = useHistory();

    return (
        <div className="App">
            <ButtonGroup>
                <MyButton style={{marginRight: 5}} type="button" onClick={() => router.push('/positions/all')}>Download all positions</MyButton>
                <MyButton type="button">Download positions by server filter</MyButton>
            </ButtonGroup>
        </div>
    );
}

export default PositionsChosePage;