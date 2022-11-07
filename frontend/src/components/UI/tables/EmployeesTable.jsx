import {React, useState, useEffect} from 'react';
import {ButtonGroup, Table} from "reactstrap";
import classes from "./Table.module.css";
import {Link} from "react-router-dom";
import MyButton from "../button/MyButton";
import axios from "axios";
import PositionEditModal from "../myModal/PositionEditModal";
import MyInput from "../input/MyInput";
import {Dropdown} from "react-bootstrap";
import {useFetching} from "../../hooks/useFetching";
import WorktypeService from "../../../API/WorktypeService";
import modalClasses from "../dropdown/MyDropdown.module.css"
import PositionEditForm from "../forms/PositionEditForm";
import PositionService from "../../../API/PositionService";

const EmployeesTable = ({positions}) => {
    const [position, setPosition] = useState({name: '', salary: '', worktype: ''})

    const [modalEdit, setModalEditVisible] = useState(false);

    const [worktypes, setWorktypes] = useState([])

    const [fetchWorktypes, isLoading] = useFetching(async () => {
        const response = await WorktypeService.getAll();
        setWorktypes(response.data)
    })

    useEffect(() => {
        fetchWorktypes();
    }, [])

    const deletePosition = async (id) => {
        await fetch('/positions/' + id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedPositions = [...this.state.positions].filter(i => i.id !== id);
            this.setState({positions: updatedPositions})
        });
    }

    const editPosition = (e) => {
        e.preventDefault()

        const data = {...position}

        sendEditedData(testUrl + position.id, data)
    }

    const sendEditedData = async (url, data) => {
        const response = axios.patch(url, data)
        console.log(data)
        console.log(url)
        return await response
    }

    const testUrl = '/positions/'

    const [positionById, setPositionById] = useFetching(async (id) => {
        const response = await PositionService.getById(id);
        setPositionById(response.data)
    })

    const positionsList = positions.map(pos => {
        return <tr key={pos.id}>
            <td style={{whiteSpace: 'nowrap'}}>{pos.name}</td>
            <td>{pos.salary}</td>
            <td>{pos.worktype.name}</td>
            <td>
                <PositionEditModal visible={modalEdit} setVisible={setModalEditVisible}>
                    <PositionEditForm posit={pos}/>
                </PositionEditModal>
                <PositionEditForm posit={pos}/>
                <ButtonGroup>
                    {/* tag={Link} to={"/positions/" + pos.id}*/}
                    <MyButton style={{marginRight: 2}} size="sm" color="primary"
                              tag={Link} to={"/positions/" + position.name}
                              onClick={() => setModalEditVisible(true)}
                    >
                        Edit
                    </MyButton>
                    <MyButton style={{color: 'red'}} onClick={() => deletePosition(pos.id)}>Delete</MyButton>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <Table className={classes.table}>
            <thead>
            <tr>
                <th width="30%">Name</th>
                <th width="20%">Salary</th>
                <th width="20%">Worktype</th>
                <th width="15%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {positionsList}
            </tbody>
        </Table>
    );
};

export default EmployeesTable;