import React, {useEffect, useMemo, useState} from 'react';
import MyInput from "../../components/UI/input/MyInput";
import style from "./SalaryPage.module.css";
import {Dropdown} from "react-bootstrap";
import classes from "../../components/UI/dropdown/MyDropdown.module.css";
import {useFetching} from "../../components/hooks/useFetching";
import EmployeeService from "../../API/EmployeeService";
import MyButton from "../../components/UI/button/MyButton";
import axios from "axios";

const SalaryPage = () => {

    const [employees, setEmployees] = useState([])

    const [fetchEmployees, isLoading] = useFetching(async () => {
        const response = await EmployeeService.getAll();
        setEmployees(response.data)
    })

    useEffect(() => {
        fetchEmployees();
    }, [])

    const [periodStart, setPeriodStart] = useState(null)
    const [periodEnd, setPeriodEnd] = useState(null)

    const [salaryByEmployeeOnPeriod, setSalaryByEmployeeOnPeriod] = useState(
        {
            secondName: '',
            firstName: '',
            thirdName: '',
            birthdayDate: null,

            periodStart: null,
            periodEnd: null

    })

    const setEmployeeParams = (empl) => {
        setSalaryByEmployeeOnPeriod((lastVal) => ({...lastVal, secondName: empl.secondName,
            firstName: empl.firstName, thirdName: empl.thirdName, birthdayDate: empl.birthday}))
    }

    const [salary, setSalary] = useState({
        nameOfSalary: '',
        salary: 0
    })

    const getSalaryOnPeriod = (e) => {
        e.preventDefault()

        sendData(testUrl, salaryByEmployeeOnPeriod).then((result) => {
            setSalary(result)
        })
    }

    const sendData = async (url, data) => {
        const response = await axios.post(url, data)
        const result = response.data;
        console.log("data ", data)
        console.log("url ", url)
        return result;
    }

    const testUrl = '/salary/salaryOnPeriodByEmployee'

    return (
        <div>
            <form>
                <div key="perStInput" style={{display: "flex", textAlign: "left"}}>
                    <span className={style.textName}>Period start</span>
                    <MyInput
                        style={{display: "flex"}}
                        key="periodStartInput"
                        onChange={e => setSalaryByEmployeeOnPeriod((lastVal) => ({...lastVal, periodStart: e.target.value}))}
                        type="date"
                        placeholder="Period start"
                    />
                </div>
                <div key="perEnInput" style={{display: "flex", textAlign: "left"}}>
                    <span className={style.textName}>Period end</span>
                    <MyInput
                        style={{display: "flex"}}
                        key="periodEndInput"
                        onChange={e => setSalaryByEmployeeOnPeriod((lastVal) => ({...lastVal, periodEnd: e.target.value}))}
                        type="date"
                        placeholder="Period start"
                    />
                </div>
                <div key="employeeChose" style={{display: "flex", textAlign: "left"}}>
                    <span className={style.textName}>Employee</span>
                    <Dropdown key="worktypesDropdownFilter" className="mt-2 mb-2" >
                        <Dropdown.Toggle
                            className={classes.myDropdown}>{salaryByEmployeeOnPeriod?.secondName/* + " " + salaryByEmployeeOnPeriod?.firstName + " " + salaryByEmployeeOnPeriod?.thirdName*/ || 'Select employee'}</Dropdown.Toggle>
                        <Dropdown.Menu className={classes.myDropdownMenu}>
                            {employees.map(empl =>
                                <p key={"dropdownPTagEmpl" + empl.id} style={{margin: "15px 0"}}>
                                    <Dropdown.Item
                                        style={{ backgroundColor: 'white' }}
                                        className={classes.myDropdown}
                                        key={empl.id}
                                        onClick={() => {
                                            setEmployeeParams(empl);
                                                console.log("empl ", empl)
                                        }}
                                    >
                                        {empl.secondName + " " + empl.firstName + " " + empl.thirdName}
                                    </Dropdown.Item>
                                </p>
                            )}
                        </Dropdown.Menu>
                    </Dropdown>
                </div>
            </form>
            <MyButton style={{marginTop: "4px"}} onClick={getSalaryOnPeriod}>Get salary</MyButton>
            {salary.nameOfSalary
                ?<div>
                <span>Salary of {salary.nameOfSalary} is {salary.salary}</span>
                </div>
                : <h1>Wait for choice</h1>
            }
        </div>
    );
};

export default SalaryPage;