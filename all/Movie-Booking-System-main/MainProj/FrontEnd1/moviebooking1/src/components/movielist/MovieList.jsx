// import Navbar from "../navbar/Navbar"
// import Footer from "../footer/Footer"
// import { Link } from "react-router-dom"
// // import aquaman from "../../pages/moviePng/aquaman.jpg"
// // import avengers from "../../pages/moviePng/avengers.jpg"
// // import conjuring from "../../pages/moviePng/conjuring.jpg"
// // import image from "../../pages/moviePng/image.png"
// // import justiceLeague from "../../pages/moviePng/justiceLeague.jpg"
// // import missionImpossible from "../../pages/moviePng/missionImpossible.jpg"
// // import shawshankredeemption from "../../pages/moviePng/shawshankredeemption.jpg"
// // import spiderman from "../../pages/moviePng/spiderman.jpg"
// // import titanic from "./moviePng/titanic.jpg"
// import './MovieList.css';
// import MovieSlider from "./MovieSlider"
// import {homeData} from "../../dummyData"
// import { useEffect, useState } from "react"
// import './MovieSlider.css'
// import { fetchMovieDetails } from "../../Services/MovieList"
// import config from "../../config";

// // const [items, setItems] = useState(homeData)
// // const movieList = [
// //     { image: aquaman, title: "Aquaman", languages: ["English", "Spanish"] },
// //     { image: avengers, title: "Avengers", languages: ["English", "French"] },
// //     { image: image, title: "Matrix", languages: ["English", "German"] },
// //     { image: conjuring, title: "Conjuring", languages: ["English"] },
// //     { image: justiceLeague, title: "Justice League", languages: ["English", "Italian"] },
// //     { image: missionImpossible, title: "Mission Impossible", languages: ["English", "Japanese"] },
// //     { image: shawshankredeemption, title: "Shawshank Redemption", languages: ["English", "Chinese"] },
// //     { image: spiderman, title: "Spiderman", languages: ["English", "Korean"] },
// // ];
// function MovieList(){
//     const [movieList, setMovieList] = useState([]);
//     const [pageNo, setPageNo] = useState('0');
//     useEffect(()=>{
//         const fetchData = async () => {
//             try {
//                 const data = await fetchMovieDetails(pageNo);  // Await the promise
//                 console.log('Fetched data:', data);  // Log the resolved data
//                 if (Array.isArray(data)) {  // Ensure the data is an array
//                     setMovieList(data);  // Update the state with movie data
//                 } else {
//                     console.error("Expected array but got", data);
//                 }
//             } catch (error) {
//                 console.error("Error fetching movie details:", error);
//             }
//         };

//         fetchData();  // Call the async function to fetch data
//     },[pageNo]);

//     return(
//         <div>
//         <MovieSlider items={homeData}/>
//         <Navbar />
//         <h2 className="page-header">MovieList</h2>
//         <div className="container">
//             <div className="row">
//             {movieList.map((movie, index) => (
//                         <MovieCard 
//                             key={index} 
//                             image={`${config.url}/moviestest/image/${movie.id}`} 
//                             title={movie.mname} 
//                             languages={["English, Hindi"]} 
//                         />
//                     ))}
//             </div>
//         </div>
//         <Footer />
//     </div>
//     )
// }
// const MovieCard = ({ image, title, languages }) => {
//     return (
//         <div className="col-md-3 mb-4 col-sm-6">
//             <Link to="/movieDetails">
//                 <div className="card myCard">
//                     <img className="card-img-top" src={image} alt={title} />
//                     <div className="card-body">
//                     </div>
//                 </div>
//                         <div className="card-body-text card-body-title">{title}</div>
//                         <div className="card-body-text card-body-lan">{languages.join(', ')}</div>
//             </Link>
//         </div>
//     );
// };

// export default MovieList

import { useEffect, useState } from "react";
import Navbar from "../navbar/Navbar";
import Footer from "../footer/Footer";
import { Link } from "react-router-dom";
import MovieSlider from "./MovieSlider";
import { homeData } from "../../dummyData";
import { fetchMovieDetails } from "../../Services/MovieList";
import config from "../../config";
import './MovieList.css';
import './MovieSlider.css';

function MovieList() {
    const [movieList, setMovieList] = useState([]);
    const [pageNo, setPageNo] = useState(0); // Start with page 0
    const [totalPages, setTotalPages] = useState(1); // Set total pages (if known)

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await fetchMovieDetails(pageNo);  // Fetch data for the current page
                console.log('Fetched data:', data);
                if (Array.isArray(data.movies)) {  // Adjust based on the actual structure of your data
                    setMovieList(data.movies);  // Update the state with movie data
                    setTotalPages(Math.ceil(data.totalMovies / 4));  // Set total pages (if available)
                } else {
                    console.error("Expected array but got", data);
                }
            } catch (error) {
                console.error("Error fetching movie details:", error);
            }
        };

        fetchData();  // Fetch data on page change
    }, [pageNo]);

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setPageNo(newPage);  // Update the page number
        }
    };

    return (
        <div>
            <MovieSlider items={homeData} />
            <Navbar />
            <h2 className="page-header">MovieList</h2>
            <div className="container">
                <div className="row">
                    {movieList.map((movie, index) => (
                        <MovieCard 
                            key={index} 
                            image={`${config.url}/moviestest/image/${movie.id}`} 
                            title={movie.mname} 
                            languages={["English", "Hindi"]} 
                            id={movie.id}
                        />
                    ))}
                </div>
                <div className="pagination">
                    <button 
                        onClick={() => handlePageChange(pageNo - 1)}
                        disabled={pageNo === 0}
                        style={{
                            opacity: pageNo === 0 ? 0 : 1,
                        }}
                    >
                        Previous
                    </button>
                    <span>Page {pageNo + 1} of {totalPages}</span>
                    <button 
                        onClick={() => handlePageChange(pageNo + 1)}
                        disabled={pageNo >= totalPages - 1}
                        style={{
                            opacity: pageNo >= totalPages - 1 ? 0 : 1,
                        }}
                    >
                        Next
                    </button>
                </div>
            </div>
            <Footer />
        </div>
    );
}

const MovieCard = ({ image, title, languages, id }) => {
    return (
        <div className="col-md-3 mb-4 col-sm-6">
            <Link to={`/movieBooking/${id}`}>
                <div className="card myCard">
                    <img className="card-img-top" src={image} alt={title} />
                    <div className="card-body">
                        {/* Add any additional content here if needed */}
                    </div>
                </div>
                <div className="card-body-text card-body-title">{title}</div>
                <div className="card-body-text card-body-lan">{languages.join(', ')}</div>
            </Link>
        </div>
    );
};

export default MovieList;
