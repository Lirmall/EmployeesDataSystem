import React, {useState, useEffect, useMemo} from 'react';
import {useHistory} from "react-router-dom";
import {useFetching} from "../../components/hooks/useFetching";
import WorktypeService from "../../API/WorktypeService";
import MyInput from "../../components/UI/input/MyInput";
import {Dropdown} from "react-bootstrap";
import classes from "../../components/UI/dropdown/MyDropdown.module.css";
import PositionsTable from "../../components/UI/tables/PositionsTable";
import MyButton from "../../components/UI/button/MyButton";
import axios from "axios";
import style from "./ServerFilterPositionsPage.module.css"

const operations = [':', '>', '<']

const columnsName = ['id', 'name', 'salary', 'worktype'];

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
        fieldName: 'salary',
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

    const positionSearchModel = useMemo(() => {
        return {
            specifications: [positionNameFilter, positionSalaryFilter/*, positionWorktypeFilter*/].filter((v) => v.fieldValue),
            pageSettings: {pageSettings}
        }
    }, [positionNameFilter, positionSalaryFilter, /*positionWorktypeFilter,*/ pageSettings]);

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

        sendFilter(testUrl, positionSearchModel).then((result) => {
            setPositions(result.items);
        })
    }

    const sendFilter = async (url, data) => {
        const response = await axios.post(url, data);
        const result = response.data;
//         console.log(result)
        return result;
    }

    const testUrl = '/positions/filter'

    return (
        <div>
        <form>
            <div key="filterNameInput" style={{display: "flex", textAlign: "left"}}>
                <span className={style.textName}>Position name</span>
                <MyInput style={{display: "flex"}}
                         key ="positionFilterNameInput"
                         onChange={e => setPositionNameFilter((lastVal) => ({...lastVal, fieldValue: e.target.value}))}
                         type="text"
                         placeholder="Position name"
                />
            </div>
            <div key="filterSalaryInput" style={{display: "flex"}}>
                <span className={style.textName}>Position salary</span>
                <Dropdown key="Operations-1" className={"mt-2 mb-2 " + style.myDropDown}>
                    <Dropdown.Toggle className={classes.myDropdown}>
                        { positionSalaryFilter.operation }
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        {operations.map(op =>
                            <p key={"operation-" + op} style={{margin: "15px 0"}}>
                                <Dropdown.Item
                                    style={{ backgroundColor: 'white' }}
                                    className={classes.myDropdown}
                                    key={op}
                                    onClick={() =>  {
                                        setPositionSalaryFilter((lastVal) => ({...lastVal, operation: op}))
                                    }}
                                >
                                    {op}
                                </Dropdown.Item>
                            </p>
                        )}
                    </Dropdown.Menu>
                </Dropdown>
                <MyInput style={{display: "flex"}}
                         key ="positionFilterSalaryInput"
                         onChange={e => setPositionSalaryFilter((lastVal) => ({...positionSalaryFilter, fieldValue: e.target.value}))}
                         type="number"
                         placeholder="Salary"
                />
            </div>
            {/*<Dropdown key="worktypesDropdownFilter" className="mt-2 mb-2">*/}
            {/*    <Dropdown.Toggle*/}
            {/*        className={classes.myDropdown}>{positionWorktypeFilter?.fieldValue?.name || 'Select worktype'}</Dropdown.Toggle>*/}
            {/*    <Dropdown.Menu className={classes.myDropdownMenu}>*/}
            {/*        {worktypes.map(worktype =>*/}
            {/*            <p key={"dropdownPTagFilter" + worktype.id} style={{margin: "15px 0"}}>*/}
            {/*                <Dropdown.Item*/}
            {/*                    style={{ backgroundColor: 'white' }}*/}
            {/*                    className={classes.myDropdown}*/}
            {/*                    key={worktype.id}*/}
            {/*                    onClick={() => setPositionWorktypeFilter({...positionWorktypeFilter, fieldValue: worktype})}*/}
            {/*                >*/}
            {/*                    {worktype.name}*/}
            {/*                </Dropdown.Item>*/}
            {/*            </p>*/}
            {/*        )}*/}
            {/*    </Dropdown.Menu>*/}
            {/*</Dropdown>*/}
            <div>Pagination</div>
            <div style={{ display: 'flex' }}>
                <MyInput style={{display: "flex"}}
                         key ="positionFilterSalaryInput2"
                         onChange={e => setPageSettings((lastVal) => ({...lastVal, pages:  Number(e.target.value)}))}
                         type="number"
                         placeholder="pages"
                />
                <MyInput style={{display: "flex"}}
                         key ="positionFilterSalaryInput3"
                         onChange={e => setPageSettings((lastVal) => ({...lastVal, limit: Number(e.target.value)}))}
                         type="number"
                         placeholder="limit"
                />
                <Dropdown key="Operations-1" className={"mt-2 mb-2 " + style.myDropDown}>
                    <Dropdown.Toggle className={classes.myDropdown}>
                        { pageSettings.sortColumn }
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        {columnsName.map(v =>
                            <p key={"operation-" + v} style={{margin: "15px 0"}}>
                                <Dropdown.Item
                                    className={classes.myDropdown}
                                    style={{ backgroundColor: 'white' }}
                                    key={v}
                                    onClick={() =>  {
                                        setPageSettings((lastVal) => ({...lastVal, sortColumn: v}))
                                    }}
                                >
                                    {v}
                                </Dropdown.Item>
                            </p>
                        )}
                    </Dropdown.Menu>
                </Dropdown>
            </div>
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