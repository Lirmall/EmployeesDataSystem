import React, {useEffect, useState} from 'react';
import MyInput from "../input/MyInput";
import MyButton from "../button/MyButton";
import axios from "axios";
import {useFetching} from "../../hooks/useFetching";
import WorktypeService from "../../../API/WorktypeService";
import {Dropdown} from "react-bootstrap";
import classes from "../dropdown/MyDropdown.module.css"

const PositionEditForm = (props) => {
    console.log("props in PositionEditForm", props)
    // return false;

    const [position, setPosition] = useState({
        id: props.posit.id,
        name: props.posit.name,
        salary: props.posit.salary,
        worktype: props.posit.worktype.name})

    const [worktypes, setWorktypes] = useState([])

    const [fetchWorktypes, isLoading] = useFetching(async () => {
        const response = await WorktypeService.getAll();
        setWorktypes(response.data)
    })

    useEffect(() => {
        fetchWorktypes();
    }, [])

    const testUrl = '/positions/'

    const editPosition = (e) => {
        e.preventDefault()

        const editedPosition = {
            ...position, id: position.id
        }

        console.log("Click")
        console.log(editedPosition)
        sendEditedData(testUrl + position.id, editedPosition)
    }

    const sendEditedData = async (url, data) => {
        const response = axios.patch(url, data)
        console.log(data)
        console.log(url)
        return await response
    }

    return (
        <form key="positions-Edit-Form">
            <MyInput
                key ="positionNameInput"
                type="text"
                defaultValue={position.name}
                onChange={e => setPosition({...position, name: e.target.value})}
            />
            <MyInput
                key ="positionSalaryInput"
                type="number"
                defaultValue={position.salary}
                onChange={e => setPosition({...position, salary: e.target.value})}
            />
            <Dropdown key="worktypesDropdown" className="mt-2 mb-2">
                <Dropdown.Toggle
                    className={classes.myDropdown}>{position.worktype || 'Select worktype'}</Dropdown.Toggle>
                <Dropdown.Menu className={classes.myDropdownMenu}>
                    {worktypes.map(worktype =>
                        <p key="dropdownPTag" style={{margin: "15px 0"}}>
                            <Dropdown.Item
                                className={classes.myDropdown}
                                key={worktype.id}
                                defaultValue={position.worktype.name}
                                onClick={() => setPosition({...position, worktype: worktype.name})}
                            >
                                {worktype.name}
                            </Dropdown.Item>
                        </p>
                    )}
                </Dropdown.Menu>
            </Dropdown>
            <MyButton style={{marginTop: "4px"}} type="button" onClick={editPosition}>Save changes</MyButton>
        </form>

    );
};

export default PositionEditForm;