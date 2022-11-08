import React, {useEffect, useState} from "react";
import {useFetching} from "../components/hooks/useFetching";
import PositionService from "../API/PositionService";
import MyButton from "../components/UI/button/MyButton";
import PositionAddModal from "../components/UI/myModal/PositionAddModal";
import PositionForm from "../components/UI/forms/PositionForm";
import PositionsTable from "../components/UI/tables/PositionsTable";

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
        setPositions(response.data)
    })

    useEffect(() => {
        fetchPositions();
    }, [])

    const [selectedSortPositions, setSelectedSortPositions] = useState('')

    const removePositionFromArray = (position) => {
        setPositions(positions.filter(p => p.id !== position.id))
    }

    const addPositionToArray = (newPosition) => {
        // setPositions([...positions, newPosition])
        setModalAdd(false)
    }

//------------------------------------------------------------


    return (
        <div className="App">

            {/*<PositionForm create={createPosition}/>*/}
            <MyButton style={{marginTop: 30}} onClick={() => setModalAdd(true)}>
                Add position by modal
            </MyButton>
            <PositionAddModal key="modalAddWithPositionForm" visible={modalAdd} setVisible={setModalAdd}>
                <PositionForm setModal={setModalAdd}/>
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