import axios from "axios";
import config from "../config";
import { toast } from "react-toastify";

export async function userLogin(email, password){
    const body = {
        email, password
    }
    try{
        const response = await axios.post(`${config.url}/users/signin`,body);
        console.log(response)
        return response;
    }catch(error){
        console.log('error-response', error);
        toast.error(error.response.data.message)
    }
}

export async function userRegister(firstName, lastName, email, password){
    const body = {
        firstName, lastName, email, password
    }
    const response = await axios.post(`${config.url}/users/signup`, body)
    return response.data
}