import axios from "axios";

export default class WorktypeService {
    static async getAll() {
        try {
            const response = await axios.get('/worktypes')
            return response;
        }catch (e) {
            console.log(e.message)
        }
    }
}