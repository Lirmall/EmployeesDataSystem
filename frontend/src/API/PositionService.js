import axios from "axios";
import {BASE_URL} from "../utils/constants";

export default class PositionService {
    static async getAll() {
        try {
            const response = await axios.get(BASE_URL + '/positions',
                {headers: {
                        'Authorization': localStorage.getItem("jwtToken")
                    }})
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }

    static async getById(id) {
        try {
            const response = await axios.get('/positions/' + id,
                {headers: {
                        'Authorization': localStorage.getItem("jwtToken")
                    }})
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }

    static async getByFilter(id) {
        try {
            const response = await axios.get('/positions/' + id,
                {headers: {
                        'Authorization': localStorage.getItem("jwtToken")
                    }})
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }
}