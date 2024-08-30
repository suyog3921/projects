// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './MoviesListPage.css';
// import config from '../../config';
// import { toast } from 'react-toastify';
// import UpdateMovieModal from './UpdateMovieModal';

// const MoviesListPage = () => {
//   const [movies, setMovies] = useState([{
//     movieImageName: '',
//     isDeleted: false,
//     showTimes: [{
//       showStartTime: '',
//       showEndTime: '',
//       showDate: '',
//       seats: [] // Ensure seats is an array
//     }],
//     mname: '',
//     mdescription: '',
//     mrating: 0
//   }]);
//   const [expandedMovieIndex, setExpandedMovieIndex] = useState(null);
//   const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
//   const [movieToUpdate, setMovieToUpdate] = useState(null);

//   useEffect(() => {
//     const fetchMovies = async () => {
//       const token = sessionStorage.getItem('token');
//       try {
//         const response = await axios.post('http://localhost:8080/admin/moviestest', {}, {
//           headers: {
//             Authorization: token // Include the token in the Authorization header
//           }
//         });
//         setMovies(response.data);
//         console.log(response)
//       } catch (error) {
//         if (error.response.status === 401) {
//           toast.error("You are not admin")
//         }
//         console.error('Error fetching movies:', error);
//       }
//     };

//     fetchMovies();
//   }, []);

//   const toggleShowtimes = (index) => {
//     setExpandedMovieIndex(expandedMovieIndex === index ? null : index);
//   };
//   const handleUpdate = (id, updatedMovieData) => {
//     const token = sessionStorage.getItem('token');
//     axios.put(`http://localhost:8080/moviestest/update/${id}`, updatedMovieData, {
//       headers: {
//         Authorization: token // Include the token in the Authorization header
//       }
//     })
//     .then(() => {
//       toast.success('Movie updated successfully!');
//       setMovies(movies.map(movie => movie.id === id ? { ...movie, ...updatedMovieData } : movie));
//       setIsUpdateModalOpen(false);
//     })
//     .catch(error => {
//       console.error('Error updating movie:', error);
//       toast.error('Failed to update movie.');
//     });
//   };
  

//   const handleDelete = async (id) => {
//     try {
//       const token = sessionStorage.getItem('token');
//       await axios.delete(`http://localhost:8080/moviestest/delete/${id}`, {
//         headers: {
//           Authorization: token // Include the token in the Authorization header
//         }
//       });
//       toast.success('Movie deleted successfully!');
//       setMovies(movies.filter(movie => movie.id !== id));
//     } catch (error) {
//       console.error('Error deleting movie:', error);
//       toast.error('Failed to delete movie.');
//     }
//   };

//   const openUpdateModal = (movie) => {
//     setMovieToUpdate(movie);
//     setIsUpdateModalOpen(true);
//   };

//   return (
//     <div className="form-container">
//       <div className="page-header">All Movies</div>

//       {movies.length === 0 ? (
//         <p>No movies found.</p>
//       ) : (
//         <table className="movies-table">
//           <thead>
//             <tr>
//               <th>Image</th>
//               <th>Name</th>
//               <th>Description</th>
//               <th>Rating</th>
//               <th>Actions</th>
//             </tr>
//           </thead>
//           <tbody>
//             {movies.map((movie, index) => (
//               <tr key={index}>
//                 <td>
//                   {movie.id ? (
//                     <img
//                       src={`${config.url}/moviestest/image/${movie.id}`}
//                       alt={movie.movieImageName}
//                       style={{ width: '100px', height: 'auto' }}
//                     />
//                   ) : (
//                     'No Image'
//                   )}
//                 </td>
//                 <td>{movie.mname}</td>
//                 <td>{movie.mdescription}</td>
//                 <td>{movie.mrating}</td>
//                 <td>
//                   <button
//                     className="toggle-button"
//                     onClick={() => toggleShowtimes(index)}
//                   >
//                     {expandedMovieIndex === index ? 'Hide Showtimes' : 'View Showtimes'}
//                   </button>

//                   <button
//                     className="update-button"
//                     onClick={() => openUpdateModal(movie)}
//                   >
//                     Update
//                   </button>

//                   <button
//                     className="delete-button"
//                     onClick={() => handleDelete(movie.id)}
//                   >
//                     Delete
//                   </button>

//                   {expandedMovieIndex === index && (
//                     <div className="dropdown-showtimes">
//                       {movie.showTimes.map((showTime, idx) => (
//                         <div className="showtime-item" key={idx}>
//                           <p><strong>Date:</strong> {showTime.showDate}</p>
//                           <p><strong>Start:</strong> {showTime.showStartTime}</p>
//                           <p><strong>End:</strong> {showTime.showEndTime}</p>
//                           {/* <p><strong>Seats:</strong> {showTime.seats.length}</p> */}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </td>
//               </tr>
//             ))}
//           </tbody>
//         </table>
//       )}

//       {isUpdateModalOpen && (
//         <UpdateMovieModal
//           movie={movieToUpdate}
//           onClose={() => setIsUpdateModalOpen(false)}
//           onUpdate={handleUpdate}
//         />
//       )}
//     </div>
//   );
// };

// export default MoviesListPage;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './MoviesListPage.css';
import config from '../../config';
import { toast } from 'react-toastify';
import UpdateMovieModal from './UpdateMovieModal';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

const MoviesListPage = () => {
  const navigate = useNavigate(); // Create navigate function
  const [movies, setMovies] = useState([{
    movieImageName: '',
    isDeleted: false,
    showTimes: [{
      showStartTime: '',
      showEndTime: '',
      showDate: '',
      seats: [] // Ensure seats is an array
    }],
    mname: '',
    mdescription: '',
    mrating: 0
  }]);
  const [expandedMovieIndex, setExpandedMovieIndex] = useState(null);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [movieToUpdate, setMovieToUpdate] = useState(null);

  useEffect(() => {
    const fetchMovies = async () => {
      const token = sessionStorage.getItem('token');
      try {
        const response = await axios.post('http://localhost:8080/admin/moviestest', {}, {
          headers: {
            Authorization: token // Include the token in the Authorization header
          }
        });
        setMovies(response.data);
        console.log(response)
      } catch (error) {
        if (error.response.status === 401) {
          toast.error("You are not admin")
        }
        console.error('Error fetching movies:', error);
      }
    };

    fetchMovies();
  }, []);

  const toggleShowtimes = (index) => {
    setExpandedMovieIndex(expandedMovieIndex === index ? null : index);
  };

  const handleUpdate = (id, updatedMovieData) => {
    const token = sessionStorage.getItem('token');
    axios.put(`http://localhost:8080/moviestest/update/${id}`, updatedMovieData, {
      headers: {
        Authorization: token // Include the token in the Authorization header
      }
    })
    .then(() => {
      toast.success('Movie updated successfully!');
      setMovies(movies.map(movie => movie.id === id ? { ...movie, ...updatedMovieData } : movie));
      setIsUpdateModalOpen(false);
    })
    .catch(error => {
      console.error('Error updating movie:', error);
      toast.error('Failed to update movie.');
    });
  };

  const handleDelete = async (id) => {
    try {
      const token = sessionStorage.getItem('token');
      await axios.delete(`http://localhost:8080/moviestest/delete/${id}`, {
        headers: {
          Authorization: token // Include the token in the Authorization header
        }
      });
      toast.success('Movie deleted successfully!');
      setMovies(movies.filter(movie => movie.id !== id));
    } catch (error) {
      console.error('Error deleting movie:', error);
      toast.error('Failed to delete movie.');
    }
  };

  const openUpdateModal = (movie) => {
    setMovieToUpdate(movie);
    setIsUpdateModalOpen(true);
  };

  return (
    <div className="form-container">
      <div className="page-header">All Movies</div>

      {movies.length === 0 ? (
        <p>No movies found.</p>
      ) : (
        <table className="movies-table">
          <thead>
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Description</th>
              <th>Rating</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {movies.map((movie, index) => (
              <tr key={index}>
                <td>
                  {movie.id ? (
                    <img
                      src={`${config.url}/moviestest/image/${movie.id}`}
                      alt={movie.movieImageName}
                      style={{ width: '100px', height: 'auto' }}
                    />
                  ) : (
                    'No Image'
                  )}
                </td>
                <td>{movie.mname}</td>
                <td>{movie.mdescription}</td>
                <td>{movie.mrating}</td>
                <td>
                  <button
                    className="toggle-button"
                    onClick={() => toggleShowtimes(index)}
                  >
                    {expandedMovieIndex === index ? 'Hide Showtimes' : 'View Showtimes'}
                  </button>

                  <button
                    className="update-button"
                    onClick={() => openUpdateModal(movie)}
                  >
                    Update
                  </button>

                  <button
                    className="delete-button"
                    onClick={() => handleDelete(movie.id)}
                  >
                    Delete
                  </button>

                  {expandedMovieIndex === index && (
                    <div className="dropdown-showtimes">
                      {movie.showTimes.map((showTime, idx) => (
                        <div className="showtime-item" key={idx}>
                          <p><strong>Date:</strong> {showTime.showDate}</p>
                          <p><strong>Start:</strong> {showTime.showStartTime}</p>
                          <p><strong>End:</strong> {showTime.showEndTime}</p>
                          {/* <p><strong>Seats:</strong> {showTime.seats.length}</p> */}
                        </div>
                      ))}
                    </div>
                  )}
                </td>
              </tr>
            ))}
      <tr>
        <td>
        
      <button  onClick={() => navigate(-1)}>Back</button> {/* Back button */}

        </td>

      </tr>
          </tbody>
        </table>
        
      )}

      {isUpdateModalOpen && (
        <UpdateMovieModal
          movie={movieToUpdate}
          onClose={() => setIsUpdateModalOpen(false)}
          onUpdate={handleUpdate}
        />
      )}

    </div>
  );
};

export default MoviesListPage;
