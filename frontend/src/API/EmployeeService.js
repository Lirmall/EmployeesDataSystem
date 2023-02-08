import axios from "axios";

export default class EmployeeService {
    static async getAll() {
        try {
            const response = await axios.get('/employees')
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }
}