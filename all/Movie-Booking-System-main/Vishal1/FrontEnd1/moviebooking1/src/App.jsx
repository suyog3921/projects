import logo from './logo.svg';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import Home from './pages/Home';
import MovieList from './pages/MovieList';
import MovieBooking from './pages/MovieBooking';
import MovieDetails from './pages/MoviesDetails';

function App() {
  return <div className='container'>
<Routes>  
  {/* <Route path="" element={<Login />}/> */}
  <Route path="login" element={<Login />}/>
  <Route path="register" element={<Register />}/>
  <Route path="" element={<Home />}/>
  <Route path="home" element={<Home />}/>
  <Route path="movieList" element={<MovieList />}/>
  <Route path="movieBooking" element={<MovieBooking />}/>
  <Route path="movieDetails" element={<MovieDetails />}/>
</Routes>
<ToastContainer />
  </div>
}

export default App;
