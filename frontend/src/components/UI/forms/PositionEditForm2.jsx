import React, {useEffect, useState} from 'react';
import MyInput from "../input/MyInput";
import MyButton from "../button/MyButton";
import axios from "axios";
import {useFetching} from "../../hooks/useFetching";
import WorktypeService from "../../../API/WorktypeService";
import {Dropdown} from "react-bootstrap";
import classes from "../dropdown/MyDropdown.module.css"
import PositionService from "../../../API/PositionService";
import {useHistory} from "react-router-dom";

const PositionEditForm2 = ({id}) => {
    console.log("id in PositionEditForm2")
    console.log(id);
    const router = useHistory();

    const [position, setPosition] = useState({
        id: 0,
        name: '',
        salary: 0,
        worktype: {
            id: 0,
            name: ''
        }
    })

    const [worktypes, setWorktypes] = useState([])

    const [fetchWorktypes, isLoading] = useFetching(async () => {
        const response = await WorktypeService.getAll();
        setWorktypes(response.data)
    })

    const [fetchPosition, isLoadingPosition] = useFetching(async (positionId) => {
        const response = await PositionService.getById(positionId);
        console.log("response.data",response.data)
        setPosition(response.data)
    })

    useEffect(() => {
        fetchWorktypes();
    }, [])

    useEffect(() => {
        if(id) {
            fetchPosition(id);
        }
    }, [id])


    const testUrl = '/positions/'

    const editPosition = (e) => {
        e.preventDefault()

        const editedPosition = {
            ...position, id: position.id
        }

        console.log("Click")
        console.log(editedPosition)
        sendEditedData(testUrl + position.id, editedPosition).then(router.goBack())
    }

    const sendEditedData = async (url, data) => {
        const response = axios.patch(url, data)
        console.log(data)
        console.log(url)
        return await response
    }

    return (
        <div>
        {position.id
            ? <form key="positions-Edit-Form">
                <MyInput
                    key="positionNameInput"
                    type="text"
                    defaultValue={position.name}
                    onChange={e => setPosition({...position, name: e.target.value})}
                />
                <MyInput
                    key="positionSalaryInput"
                    type="number"
                    defaultValue={position.salary}
                    onChange={e => setPosition({...position, salary: Number(e.target.value)})}
                />
                <Dropdown key="worktypesDropdown" className="mt-2 mb-2">
                    <Dropdown.Toggle
                        className={classes.myDropdown}>{position.worktype?.name || 'Select worktype'}</Dropdown.Toggle>
                    <Dropdown.Menu className={classes.myDropdownMenu}>
                        {worktypes.map(worktype =>
                            <p key={"dropdownPTag" + worktype.id}
                               style={{margin: "15px 0"}}>
                                <Dropdown.Item
                                    className={classes.myDropdown}
                                    key={worktype.id}
                                    defaultValue={position.worktype?.name}
                                    onClick={() => setPosition({...position, worktype: worktype})}
                                >
                                    {worktype.name}
                                </Dropdown.Item>
                            </p>
                        )}
                    </Dropdown.Menu>
                </Dropdown>
                <MyButton style={{marginTop: "4px"}} type="button" onClick={editPosition}>Save changes</MyButton>
                <MyButton style={{marginTop: "4px", marginLeft: "5px", color: "red"}} type="button" onClick={router.goBack}>Back</MyButton>
            </form>
            : <h1>Downloading edit form, please, wait...</h1>
        }
        </div>
    );
}
    ;

    export default PositionEditForm2;