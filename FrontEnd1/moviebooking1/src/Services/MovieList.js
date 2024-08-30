import axios from "axios";
import config from "../config";
import { toast } from "react-toastify";

export async function fetchMovieDetails(pageNumber){     
    try{
        const response = await axios.get(`${config.url}/moviestest/pagenumber/${pageNumber}`);
        return response.data;
    }catch(error){
        console.log('error-response', error);
        // toast.error(error.response.data.message)
    }
}

export async function fetchMovieDetailsByid(id){     
    try{
        const response = await axios.get(`${config.url}/moviestest/${id}`);
        return response.data;
    }catch(error){
        console.log('error-response', error);
        // toast.error(error.response.data.message)
    }
}



// export async function fetchSliderDetails(firstName, lastName, email, password){
//     const body = {
//         firstName, lastName, email, password
//     }
//     const response = await axios.post(`${config.url}/users/signup`, body)
//     return response.data
// }