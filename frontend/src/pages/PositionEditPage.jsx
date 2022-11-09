import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {useFetching} from "../components/hooks/useFetching";
import PositionService from "../API/PositionService";
import PositionEditForm from "../components/UI/forms/PositionEditForm";
import PositionEditForm2 from "../components/UI/forms/PositionEditForm2";

function PositionEditPage() {
    const params = useParams();
    console.log("params", params)

    const [position, setPosition] = useState({})

    const [fetchPositionById, isLoading, error] = useFetching(async (id) => {

        console.log("response position PositionEditPage fetchPositionById")
        console.log(position)

        const response = await PositionService.getById(id)

        console.log("response position with data PositionEditPage fetchPositionById")
        console.log(response.data)

        setPosition(response.data);

    })

    useEffect(() => {
        const id = params.id;
        fetchPositionById(id)
    }, [])

    console.log("created position PositionEditPage")
    console.log(position)

    return (
        <div>
            <h1>Working position edit page with position ID = {position.id}</h1>
            {position
                ? <div>
                    {position.id} {position.name} {position.salary} {position.worktype?.name}
                    <PositionEditForm2 id={params.id}/>
                </div>
                : <h1>Loading editing form...</h1>
            }
        </div>
    );
};

export default PositionEditPage;