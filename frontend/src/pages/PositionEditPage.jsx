import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {useFetching} from "../components/hooks/useFetching";
import PositionService from "../API/PositionService";
import PositionEditForm from "../components/UI/forms/PositionEditForm";

function PositionEditPage() {
    const params = useParams();

    const [position, setPosition] = useState({})

    const [fetchPositionById, isLoading, error] = useFetching( async (id) => {

        console.log("response position")
        console.log(position)

        const response = await PositionService.getById(id)
        setPosition(response.data);
    })

    useEffect(() => {
        const id = params.id;
        fetchPositionById(id)
    }, [])

    console.log("position")
    console.log(position)

    return (
        <div>
            <h1>
                Working position edit page with position ID = {position.id}
            </h1>
            <h2>{position.id} {position.name} {position.salary} {position.worktype}</h2>
        </div>
    );
};

export default PositionEditPage;