import './LoginRegister.css'
import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { toast } from "react-toastify"
import { userLogin } from '../../Services/User'
function Login() {
  // create state members
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const navigate = useNavigate()

  const onLogin = async () => {
    // client side validation
    if (email.length === 0) {
      toast.warning('enter email')
    } else if (password.length === 0) {
      toast.warning('enter password')
    } else {
      const result = await userLogin(email, password)
      // console.log(result['message'])
      if (result !== undefined && result.status === 200) {

        sessionStorage.setItem('name', result.data.firstName)
        sessionStorage.setItem('userId', result.data.id)
        sessionStorage.setItem('token', `Bearer ${result.data.jwt}`)

        // dispatch(loginAction())
        toast.success(`welcome to the application, ${result.data['firstName']}`)
        console.log(result.data)
        if(result.data.role === 'ROLE_USER')
          navigate('/home')
        if(result.data.role === 'ROLE_ADMIN'){
          navigate('/admin')
        }
       } 

  }
}

  const onBack = () => {
    navigate('/home'); // Navigate to the previous page
  }

  return (
    <div className='form-container'>
      <div className='form'>
        <h2 className='page-header'>Login</h2>
        <div className='mb-3'>
          <label htmlFor='email'>Email</label>
          <input
            onChange={(e) => setEmail(e.target.value)}
            type='email'
            className='form-control'
            id='email'
          />
        </div>
        <div className='mb-3'>
          <label htmlFor='password'>Password</label>
          <input
            onChange={(e) => setPassword(e.target.value)}
            type='password'
            className='form-control'
            id='password'
          />
        </div>
        <div className='mb-3'>
          <div>
            Don't have an account yet? <Link to='/register'>Register here</Link>
          </div>
        </div>
        <div className='button-container'>
          <button onClick={onLogin} className='btn btn-success'>Login</button>
          <button onClick={onBack} className='btn btn-danger'>Back</button>
        </div>
      </div>
    </div>
  )
}

export default Login
