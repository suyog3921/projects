import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { toast } from "react-toastify"

function Login(){
    // create state members
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  // get the navigate object
  const navigate = useNavigate()

  // use dispatch to update global state
  //const dispatch = useDispatch()

  const onLogin = async () => {
    // client side validation
    if (email.length === 0) {
      toast.warning('enter email')
    } else if (password.length === 0) {
      toast.warning('enter password')
    } else {
      // const result = await login(email, password)
      // if (result['status'] === 'success') {
      //   // read the token
      //   // const token = result['data']['token']
      //   // const name = result['data']['name']
      //   const { token, name } = result['data']

      //   // set the data in session storage
      //   // sessionStorage.token = token
      //   // sessionStorage.name = name

      //   // sessionStorage['token'] = token
      //   // sessionStorage['name'] = name

      //   sessionStorage.setItem('token', token)
      //   sessionStorage.setItem('name', name)

      //   // set the login status to true
      //   dispatch(loginAction())

      //   toast.success('welcome to the application')
        navigate('/home')
      // } else {
      //   toast.error('invalid email or password')
      // }
    }
  }
    return(
        <div>
        <h2 className='page-header'>Login</h2>
  
        <div className='row'>
          <div className='col'></div>
  
          <div className='col'>
            <div className='form'>
              <div className='mb-3'>
                <label htmlFor=''>Email</label>
                <input
                  onChange={(e) => {
                    setEmail(e.target.value)
                  }}
                  type='email'
                  className='form-control'
                />
              </div>
              <div className='mb-3'>
                <label htmlFor=''>Password</label>
                <input
                  onChange={(e) => {
                    setPassword(e.target.value)
                  }}
                  type='password'
                  className='form-control'
                />
              </div>
              <div className='mb-3'>
                <div>
                  Dont have an account yet?{' '}
                  <Link to='/register'>Register here</Link>
                </div>
                <button onClick={onLogin}
                 className='mt-2 btn btn-success'>
                  Login
                </button>
              </div>
            </div>
          </div>
  
          <div className='col'></div>
        </div>
      </div>
    )
}

export default Login