import React, {useEffect, useState} from 'react';
import MyInput from "../input/MyInput";
import MyButton from "../button/MyButton";
import axios from "axios";
import {useFetching} from "../../hooks/useFetching";
import WorktypeService from "../../../API/WorktypeService";
import {Dropdown} from "react-bootstrap";
import classes from "../dropdown/MyDropdown.module.css"

const PositionForm = ({create}) => {
    const [position, setPosition] = useState({name: '', salary: '', worktype: ''})

    const [worktypes, setWorktypes] = useState([])

    const [fetchWorktypes, isLoading] = useFetching(async () => {
        const response = await WorktypeService.getAll();
        setWorktypes(response.data)
    })

    useEffect(() => {
        fetchWorktypes();
    }, [])

    const addNewPosition = (e) => {
        e.preventDefault()

        const newPosition = {
            ...position, id: Date.now()
        }

        sendData2(testUrl, newPosition)

        // create(newPosition)
        setPosition({name: '', salary: '', worktype: ''})
    }

    const sendWithFetch = async (url, data) => {
        const response = await fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })

        console.log(data)
        console.log(url)
        return await response.json()
    }

    const sendData2 = async (url, data) => {
        const response = axios.post(url, data)
        console.log(data)
        console.log(url)
        return await response
    }

    const testUrl = '/positions/new'

    return (
        <form>
            <MyInput
                value={position.name}
                onChange={e => setPosition({...position, name: e.target.value})}
                type="text"
                placeholder="Position name"/>
            <MyInput
                value={position.salary}
                onChange={e => setPosition({...position, salary: e.target.value})}
                type="number"
                placeholder="Position salary"/>
            <Dropdown className="mt-2 mb-2">
                <Dropdown.Toggle
                    className={classes.myDropdown}>{position.worktype || 'Select worktype'}</Dropdown.Toggle>
                <Dropdown.Menu className={classes.myDropdownMenu}>
                    {worktypes.map(worktype =>
                        <p style={{margin: "15px 0"}}>
                            <Dropdown.Item
                                className={classes.myDropdown}
                                key={worktype.id}
                                onClick={() => setPosition({...position, worktype: worktype.name})}
                            >
                                {worktype.name}</Dropdown.Item>
                        </p>
                    )}
                </Dropdown.Menu>
            </Dropdown>
            <MyButton style={{marginTop: "4px"}} onClick={addNewPosition}>Add position</MyButton>
        </form>

    );
};

export default PositionForm;