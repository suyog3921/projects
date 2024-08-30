import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { toast } from "react-toastify"
import './LoginRegister.css'
import { userRegister } from "../../Services/User"
import { useTranslation } from 'react-i18next';


function Register() {
  // create state members
  const { t, i18n } = useTranslation();
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  // get a hook to navigate
  const navigate = useNavigate()

  const onCancel = () => {
    navigate('/login')
  }

  const isValidEmail = () => {
    return email.includes('@')
  }

  const onRegister = async () => {
    if (firstName.length === 0) {
      toast.warning(t('enterFirstName'));
    } else if (lastName.length === 0) {
      toast.warning(t('enterLastName'));
    } else if (email.length === 0) {
      toast.warning(t('enterEmail'));
    } else if (!isValidEmail()) {
      toast.warning(t('invalidEmail'));
    } else if (password.length === 0) {
      toast.warning(t('enterPassword'));
    } else if (confirmPassword.length === 0) {
      toast.warning(t('confirmPassword'));
    } else if (password !== confirmPassword) {
      toast.warning(t('passwordMismatch'));
    } else {
      const result = await userRegister(firstName, lastName, email, password);
      if (result['status'] === 200) {
        toast.success(t('registrationSuccess'));
        navigate('/login');
      } else {
        toast.error(t('registrationFailed'));
      }
    }
  };

  const onBack = () => {
    navigate(-1); // Navigate to the previous page
  }
  const toggleLanguage = () => {
    const newLang = i18n.language === 'en' ? 'hi' : 'en';
    i18n.changeLanguage(newLang);
  };

  return (
    <div className='form-container'>
      <div className='form'>
        <h2 className='page-header'>{t('register')}</h2>
        <div className='mb-3'>
          <label htmlFor='firstName'>{t('firstName')}</label>
          <input
            onChange={(e) => setFirstName(e.target.value)}
            type='text'
            className='form-control'
            id='firstName'
          />
        </div>
        <div className='mb-3'>
          <label htmlFor='lastName'>{t('lastName')}</label>
          <input
            onChange={(e) => setLastName(e.target.value)}
            type='text'
            className='form-control'
            id='lastName'
          />
        </div>
        <div className='mb-3'>
          <label htmlFor='email'>{t('email')}</label>
          <input
            onChange={(e) => setEmail(e.target.value)}
            type='email'
            className='form-control'
            id='email'
          />
        </div>
        <div className='mb-3'>
          <label htmlFor='password'>{t('password')}</label>
          <input
            onChange={(e) => setPassword(e.target.value)}
            type='password'
            className='form-control'
            id='password'
          />
        </div>
        <div className='mb-3'>
          <label htmlFor='confirmPassword'>{t('confirmPassword')}</label>
          <input
            onChange={(e) => setConfirmPassword(e.target.value)}
            type='password'
            className='form-control'
            id='confirmPassword'
          />
        </div>
        <div className='mb-3'>
          {t('alreadyHaveAccount')} <Link to='/login'>{t('Login')}</Link>
        </div>
        <div className='mb-3'>
          <button onClick={toggleLanguage} className='btn btn-info'>
            {t('languageToggle')}
          </button>
        </div>
        <div className='button-container'>
          <button onClick={onRegister} className='btn btn-success'>{t('registerButton')}</button>
          <button onClick={onCancel} className='btn btn-danger'>{t('cancelButton')}</button>
          <button onClick={onBack} className='btn btn-secondary'>{t('backButton')}</button>
        </div>
      </div>
    </div>
  );
}

export default Register
