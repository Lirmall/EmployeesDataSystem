import React, {useState, useEffect} from 'react';
import {useHistory} from "react-router-dom";
import {useFetching} from "../../components/hooks/useFetching";
import WorktypeService from "../../API/WorktypeService";
import MyInput from "../../components/UI/input/MyInput";
import {Dropdown} from "react-bootstrap";
import classes from "../../components/UI/dropdown/MyDropdown.module.css";
import PositionsTable from "../../components/UI/tables/PositionsTable";
import MyButton from "../../components/UI/button/MyButton";
import axios from "axios";

function ServerFilterPositionsPage() {
    const router = useHistory()

    const [positions, setPositions] = useState([])

    const [positionNameFilter, setPositionNameFilter] = useState({
        fieldName: 'name',
        operation: ':',
        fieldValue: '',
        orPredicate: false
    })

    const [positionSalaryFilter, setPositionSalaryFilter] = useState({
        fieldName: 'name',
        operation: ':',
        fieldValue: 0,
        orPredicate: false
    })

    const [positionWorktypeFilter, setPositionWorktypeFilter] = useState({
        fieldName: 'worktype',
        operation: ':',
        fieldValue: {},
        orPredicate: false
    })

    const [pageSettings, setPageSettings] = useState({
        pages: 0,
        limit: 5,
        sortColumn: 'id'
    })

    const [positionSearchModel, setPositionSearchModel] = useState({
        specifications: [{
            fieldName: '',
            operation: ':',
            fieldValue: '',
            orPredicate: false
        }],
        pageSettings: {pageSettings}
    })

    const addSpecificationToModel = (specification) => {
        setPositionSearchModel({...positionSearchModel, specifications: [positionSearchModel.specifications, specification]})

    }

    const [worktypes, setWorktypes] = useState([])

    const [fetchWorktypes, isLoading] = useFetching(async () => {
        const response = await WorktypeService.getAll();
        setWorktypes(response.data)
    })

    useEffect(() => {
        fetchWorktypes();
    }, [])

    const removePositionFromArray = (position) => {
        setPositions(positions.filter(p => p.id !== position.id))
    }

    const getPositionsByFilter = (e) => {
        e.preventDefault()

        const response = sendFilter(testUrl, positionSearchModel).then(() => {
            setPositions(response.data)
        })
    }

    const sendFilter = async (url, data) => {
        const response = await axios.post(url, data)
        console.log(data)
        console.log(url)
        return response;
    }

    const testUrl = '/positions/filter'

    return (
        <div>
        <form>
            <div key="filterNameInput" style={{display: "flex", textAlign: "left"}}>
                Position name
            <MyInput style={{display: "flex"}}
                key ="positionFilterNameInput"
                onChange={e => setPositionNameFilter({...positionNameFilter, fieldValue: e.target.value})}
                type="text"
                placeholder="Position name"
            />
            </div>
            <div key="filterSalaryInput" style={{display: "flex"}}>
                Position salary
            <MyInput style={{display: "flex"}}
                key ="positionFilterSalaryInput"
                onChange={e => setPositionNameFilter({...positionSalaryFilter, fieldValue: e.target.value})}
                type="number"
                placeholder="Salary"
            />
            </div>
            {/*<Dropdown key="worktypesDropdownFilter" className="mt-2 mb-2">*/}
            {/*    <Dropdown.Toggle*/}
            {/*        className={classes.myDropdown}>{positionWorktypeFilter.fieldValue || 'Select worktype'}</Dropdown.Toggle>*/}
            {/*    <Dropdown.Menu className={classes.myDropdownMenu}>*/}
            {/*        {worktypes.map(worktype =>*/}
            {/*            <p key={"dropdownPTagFilter" + worktype.id} style={{margin: "15px 0"}}>*/}
            {/*                <Dropdown.Item*/}
            {/*                    className={classes.myDropdown}*/}
            {/*                    key={worktype.id}*/}
            {/*                    onClick={() => setPositionWorktypeFilter({...positionWorktypeFilter, fieldValue: worktype})}*/}
            {/*                >*/}
            {/*                    {worktype.name}</Dropdown.Item>*/}
            {/*            </p>*/}
            {/*        )}*/}
            {/*    </Dropdown.Menu>*/}
            {/*</Dropdown>*/}
        </form>
            <MyButton onClick={getPositionsByFilter}>Search</MyButton>
            {
                positions.length !== 0
                    ?
                    <div>
                        <h1 style={{margin: '15px 0', textAlign: 'center'}}>Positions</h1>
                        <PositionsTable
                            key="positionTableOnPositionsPage"
                            positions={positions}
                            removePositionFromArray={removePositionFromArray}
                        />
                    </div>
                    :
                    <div>
                        <h1 style={{margin: '15px 0', textAlign: 'center'}}>Use filter form for filter positions</h1>
                    </div>
            }
        </div>
    );
};

export default ServerFilterPositionsPage;