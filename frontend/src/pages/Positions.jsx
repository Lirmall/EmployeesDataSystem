import React, {useCallback, useEffect, useReducer, useState} from "react";
import {useFetching} from "../components/hooks/useFetching";
import PositionService from "../API/PositionService";
import MyButton from "../components/UI/button/MyButton";
import PositionAddModal from "../components/UI/myModal/PositionAddModal";
import PositionForm from "../components/UI/forms/PositionForm";
import PositionsTable from "../components/UI/tables/PositionsTable";
import App from "../App";

function Positions() {
    // const [positions, setPositions] = useState([
    //     {id: 1, name: 'Mechanic', worktype: {id: 1, name: 'Hourly'}, salary: 200},
    //     // {id: 2, name: 'Master mechanic', worktype: {id: 1, name: 'Salary'}, salary: 20000}
    // ])

    const [positions, setPositions] = useState([])
    const [modalEdit, setModalEdit] = useState(false);
    const [modalAdd, setModalAdd] = useState(false);

    const [fetchPositions, isLoading] = useFetching(async () => {
        const response = await PositionService.getAll();
        console.log("fetched!")

        setPositions(response.data)

        console.log("\"1\" " + positions.length)
    })

    useEffect(() => {
        fetchPositions();

        console.log("\"2\" " + positions.length)

    }, [])

    const [selectedSortPositions, setSelectedSortPositions] = useState('')

    const removePositionFromArray = (position) => {
        setPositions(positions.filter(p => p.id !== position.id))
    }

    console.log("\"3\" " + positions.length)

    const addPositionToArray = () => {
        // setPositions([...positions, newPosition])
        setModalAdd(false)
        downloadFromServer()
        update()

        console.log("\"4\" " + positions.length)
    }

    console.log("\"5\" " + positions.length)

    function update() {
        // eslint-disable-next-line no-undef
        ReactDOM.render(<App/>, document.getElementById("root"))
    }

    async function downloadFromServer() {
        const response = await PositionService.getAll();
        console.log("fetched2!")

        setPositions(response.data)

        console.log("\"6\" " + positions.length)
        console.log("\"7\" " + response.data.length)
    }

//------------------------------------------------------------


    return (
        <div className="App">
            <MyButton style={{marginTop: 30}} onClick={() => setModalAdd(true)}>
                Add position by modal
            </MyButton>
            <PositionAddModal key="modalAddWithPositionForm" visible={modalAdd} setVisible={setModalAdd}>
                <PositionForm setModal={setModalAdd} addToArray={addPositionToArray}/>
            </PositionAddModal>

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
                        <h1 style={{margin: '15px 0', textAlign: 'center'}}>Positions not found</h1>
                        <MyButton type="button">Download positions</MyButton>
                    </div>
            }

        </div>
    );
}

export default Positions;