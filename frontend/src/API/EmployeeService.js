import axios from "axios";
import {BASE_URL} from "../utils/constants";

export default class EmployeeService {
    static async getAll() {
        try {
            const response = await axios.get(BASE_URL + '/employees',
                {headers: {
                        'Authorization': localStorage.getItem("jwtToken")
                    }})
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }
}