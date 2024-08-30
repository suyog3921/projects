import './App.css';
import { Route, Routes } from 'react-router-dom';

import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import MovieList from './components/movielist/MovieList';
import HomePage from './home/HomePage'
import MovieBooking from './pages/MovieBooking';
import MovieDetails from './pages/MoviesDetails';
import TheaterList from './pages/TheaterList';
import Login from './components/LoginRegister/Login';
import Register from './components/LoginRegister/Register';
import AdminPage from './pages/AdminPage';
import MoviesListPage from './components/AdminMovieDetails/MoviesListPage';
import SinglePage from './components/watch/SinglePage';


function App() {
  return <div className='container-fluid'>
<Routes>  
  {/* <Route path="" element={<Login />}/> */}
  <Route path="login" element={<Login />}/>
  <Route path="register" element={<Register />}/>
  <Route path="" element={<HomePage />}/>
  <Route path="home" element={<HomePage/>}/>
  <Route path="movieList" element={<MovieList/>}/>
  <Route path="theaterList" element={<TheaterList/>}/>
  <Route path="movieBooking/:id" element={<MovieBooking />}/>
  <Route path="movieDetails" element={<MovieDetails />}/>
  {/* <PrivateRoute path="admin" component={<AdminPage/>} /> */}
  {/* <Route path="admin" element={<PrivateRoute element={<AdminPage/>} />} /> */}

  <Route path='admin' element={<AdminPage/>}/>
  <Route path='adminMovieList' element={<MoviesListPage/>}/>
  <Route path='singlepage/:id' element={<SinglePage/>}/>


  
</Routes>
<ToastContainer />
  </div>
}

export default App;
