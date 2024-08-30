
// import React, { useState } from 'react';
// import './UpdateMovieModal.css';

// const UpdateMovieModal = ({ movie, onClose, onUpdate }) => {
//   const formatTime = (time) => {
//     if (typeof time === 'object') {
//       const { hour = '00', minute = '00', second = '00' } = time;
//       return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}`;
//     }
//     return time;
//   };

//   const [updatedMovie, setUpdatedMovie] = useState({
//     mname: movie.mname,
//     mdescription: movie.mdescription,
//     mrating: movie.mrating,
//     movieImageName: movie.movieImageName,
//     showTimes: movie.showTimes.map(showTime => ({
//       showStartTime: formatTime(showTime.showStartTime),
//       showEndTime: formatTime(showTime.showEndTime),
//       showDate: showTime.showDate,
//     })),
//   });

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setUpdatedMovie({ ...updatedMovie, [name]: value });
//   };

//   const handleShowTimeChange = (index, field, value) => {
//     const newShowTimes = [...updatedMovie.showTimes];
//     newShowTimes[index][field] = value;
//     setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
//   };

//   const handleShowTimeTimeChange = (index, timeField, value, part) => {
//     const newShowTimes = [...updatedMovie.showTimes];
//     const time = newShowTimes[index][timeField].split(':');

//     if (part === 'hour') {
//       time[0] = value.padStart(2, '0');
//     } else if (part === 'minute') {
//       time[1] = value.padStart(2, '0');
//     } else if (part === 'second') {
//       time[2] = value.padStart(2, '0');
//     }

//     newShowTimes[index][timeField] = time.join(':');
//     setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
//   };

//   const handleAddShowTime = () => {
//     const newShowTimes = [
//       ...updatedMovie.showTimes,
//       { showStartTime: '00:00:00', showEndTime: '00:00:00', showDate: '' },
//     ];
//     setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
//   };

//   const handleRemoveShowTime = (index) => {
//     const newShowTimes = updatedMovie.showTimes.filter((_, i) => i !== index);
//     setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
//   };

//   const handleSubmit = (e) => {
//     e.preventDefault();
//     onUpdate(movie.id, updatedMovie);
//   };

//   return (
//     <div className="modal-overlay">
//       <div className="modal-content">
//         <h2>Update Movie</h2>
//         <form onSubmit={handleSubmit}>
//           <div className="form-group">
//             <label>Name</label>
//             <input
//               type="text"
//               name="mname"
//               value={updatedMovie.mname}
//               onChange={handleChange}
//             />
//           </div>
//           <div className="form-group">
//             <label>Description</label>
//             <textarea
//               name="mdescription"
//               value={updatedMovie.mdescription}
//               onChange={handleChange}
//             />
//           </div>
//           <div className="form-group">
//             <label>Rating</label>
//             <input
//               type="number"
//               name="mrating"
//               value={updatedMovie.mrating}
//               onChange={handleChange}
//               min="0"
//               max="10"
//             />
//           </div>

//           <div className="form-group showtimes-table-wrapper">
//             <h4>Showtimes</h4>
//             <button className='btns' type="button" onClick={handleAddShowTime}>Add Showtime</button>
//             <table className="showtimes-table">
//               <thead>
//                 <tr>
//                   <th>Show Date</th>
//                   <th>Start Time</th>
//                   <th>End Time</th>
//                   <th>Actions</th>
//                 </tr>
//               </thead>
//               <tbody>
//                 {updatedMovie.showTimes.map((showTime, index) => (
//                   <tr key={index}>
//                     <td>
//                       <input
//                         type="date"
//                         className="date"
//                         value={showTime.showDate}
//                         onChange={(e) =>
//                           handleShowTimeChange(index, 'showDate', e.target.value)
//                         }
//                       />
//                     </td>
//                     <td>
//                       <div className="time-inputs">
//                         <input
//                           type="number"
//                           value={showTime.showStartTime.split(':')[0]}
//                           onChange={(e) =>
//                             handleShowTimeTimeChange(index, 'showStartTime', e.target.value, 'hour')
//                           }
//                           min="0"
//                           max="23"
//                         />
//                         <span>:</span>
//                         <input
//                           type="number"
//                           value={showTime.showStartTime.split(':')[1]}
//                           onChange={(e) =>
//                             handleShowTimeTimeChange(index, 'showStartTime', e.target.value, 'minute')
//                           }
//                           min="0"
//                           max="59"
//                         />
//                       </div>
//                     </td>
//                     <td>
//                       <div className="time-inputs">
//                         <input
//                           type="number"
//                           value={showTime.showEndTime.split(':')[0]}
//                           onChange={(e) =>
//                             handleShowTimeTimeChange(index, 'showEndTime', e.target.value, 'hour')
//                           }
//                           min="0"
//                           max="23"
//                         />
//                         <span>:</span>
//                         <input
//                           type="number"
//                           value={showTime.showEndTime.split(':')[1]}
//                           onChange={(e) =>
//                             handleShowTimeTimeChange(index, 'showEndTime', e.target.value, 'minute')
//                           }
//                           min="0"
//                           max="59"
//                         />
//                       </div>
//                     </td>
//                     <td>
//                       <button className='btns' type="button" onClick={() => handleRemoveShowTime(index)}>
//                         Remove
//                       </button>
//                     </td>
//                   </tr>
//                 ))}
//               </tbody>
//             </table>
//           </div>

//           <div className="modal-actions">
//             <button type="submit">Save</button>
//             <button type="button" onClick={onClose}>Cancel</button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// };

// export default UpdateMovieModal;

import React, { useState } from 'react';
import './UpdateMovieModal.css';
import config from '../../config';
import axios from 'axios';

const UpdateMovieModal = ({ movie, onClose, onUpdate }) => {
  const formatTime = (time) => {
    if (typeof time === 'object') {
      const { hour = '00', minute = '00', second = '00' } = time;
      return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}`;
    }
    return time;
  };

  const [updatedMovie, setUpdatedMovie] = useState({
    mname: movie.mname,
    mdescription: movie.mdescription,
    mrating: movie.mrating,
    movieImageName: movie.movieImageName,
    showTimes: movie.showTimes.map(showTime => ({
      showStartTime: formatTime(showTime.showStartTime),
      showEndTime: formatTime(showTime.showEndTime),
      showDate: showTime.showDate,
    })),
  });

  const [newImage, setNewImage] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedMovie({ ...updatedMovie, [name]: value });
  };

  const handleImageChange = (e) => {
    setNewImage(e.target.files[0]);
  };

  const handleShowTimeChange = (index, field, value) => {
    const newShowTimes = [...updatedMovie.showTimes];
    newShowTimes[index][field] = value;
    setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
  };

  const handleShowTimeTimeChange = (index, timeField, value, part) => {
    const newShowTimes = [...updatedMovie.showTimes];
    const time = newShowTimes[index][timeField].split(':');

    if (part === 'hour') {
      time[0] = value.padStart(2, '0');
    } else if (part === 'minute') {
      time[1] = value.padStart(2, '0');
    } else if (part === 'second') {
      time[2] = value.padStart(2, '0');
    }

    newShowTimes[index][timeField] = time.join(':');
    setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
  };

  const handleAddShowTime = () => {
    const newShowTimes = [
      ...updatedMovie.showTimes,
      { showStartTime: '00:00:00', showEndTime: '00:00:00', showDate: '' },
    ];
    setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
  };

  const handleRemoveShowTime = (index) => {
    const newShowTimes = updatedMovie.showTimes.filter((_, i) => i !== index);
    setUpdatedMovie({ ...updatedMovie, showTimes: newShowTimes });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let updatedImageName = updatedMovie.movieImageName;
    // If a new image is selected, upload it first
    if (newImage) {
      const formData = new FormData();
      formData.append('movieImage', newImage);
      const token = sessionStorage.getItem('token')
      try {
       const response = await axios.post(`${config.url}/moviestest/image/${movie.id}`, formData, {
          headers: {
            'Authorization': token, // Include the token in the Authorization header
            'Content-Type': 'multipart/form-data' // This header is automatically set when using FormData
          }
        });
        console.log(response)
        updatedImageName = response.data.imageName;
      } catch (error) {
        console.error('Error uploading image:', error);
      }
    }
    const finalUpdatedMovie = {
      ...updatedMovie,
      movieImageName: updatedImageName, // Use the updated image name if new image was uploaded
    };
    // Then, update the movie details
    onUpdate(movie.id, finalUpdatedMovie);
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Update Movie</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Name</label>
            <input
              type="text"
              name="mname"
              value={updatedMovie.mname}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Description</label>
            <textarea
              name="mdescription"
              value={updatedMovie.mdescription}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Rating</label>
            <input
              type="number"
              name="mrating"
              value={updatedMovie.mrating}
              onChange={handleChange}
              min="0"
              max="10"
            />
          </div>

          <div className="form-group">
            <label>Update Movie Image</label>
            <input
              type="file"
              accept="image/*"
              onChange={handleImageChange}
            />
          </div>

          <div className="form-group showtimes-table-wrapper">
            <h4>Showtimes</h4>
            <button className="btns" type="button" onClick={handleAddShowTime}>Add Showtime</button>
            <table className="showtimes-table">
              <thead>
                <tr>
                  <th>Show Date</th>
                  <th>Start Time</th>
                  <th>End Time</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {updatedMovie.showTimes.map((showTime, index) => (
                  <tr key={index}>
                    <td>
                      <input
                        type="date"
                        className="date"
                        value={showTime.showDate}
                        onChange={(e) =>
                          handleShowTimeChange(index, 'showDate', e.target.value)
                        }
                      />
                    </td>
                    <td>
                      <div className="time-inputs">
                        <input
                          type="number"
                          value={showTime.showStartTime.split(':')[0]}
                          onChange={(e) =>
                            handleShowTimeTimeChange(index, 'showStartTime', e.target.value, 'hour')
                          }
                          min="0"
                          max="23"
                        />
                        <span>:</span>
                        <input
                          type="number"
                          value={showTime.showStartTime.split(':')[1]}
                          onChange={(e) =>
                            handleShowTimeTimeChange(index, 'showStartTime', e.target.value, 'minute')
                          }
                          min="0"
                          max="59"
                        />
                      </div>
                    </td>
                    <td>
                      <div className="time-inputs">
                        <input
                          type="number"
                          value={showTime.showEndTime.split(':')[0]}
                          onChange={(e) =>
                            handleShowTimeTimeChange(index, 'showEndTime', e.target.value, 'hour')
                          }
                          min="0"
                          max="23"
                        />
                        <span>:</span>
                        <input
                          type="number"
                          value={showTime.showEndTime.split(':')[1]}
                          onChange={(e) =>
                            handleShowTimeTimeChange(index, 'showEndTime', e.target.value, 'minute')
                          }
                          min="0"
                          max="59"
                        />
                      </div>
                    </td>
                    <td>
                      <button className="btns" type="button" onClick={() => handleRemoveShowTime(index)}>
                        Remove
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="modal-actions">
            <button type="submit">Save</button>
            <button type="button" onClick={onClose}>Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateMovieModal;
