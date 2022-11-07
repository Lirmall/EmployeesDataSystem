import axios from "axios";

export default class PositionService {
    static async getAll() {
        try {
            const response = await axios.get('/positions')
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }

    static async getById(id) {
        try {
            const response = await axios.get('/positions/' + id)
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }
}