import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';

const resources = {
    en: {
        translation: {
            "register": "Register",
            "firstName": "First Name",
            "lastName": "Last Name",
            "email": "Email",
            "password": "Password",
            "confirmPassword": "Confirm Password",
            "alreadyHaveAccount": "Already have an account? Login here",
            "registerButton": "Register",
            "cancelButton": "Cancel",
            "backButton": "Back",
            "languageToggle": "Switch to Hindi",

        }
    },
    hi: {
        translation: {
            "register": "रजिस्टर करें",
            "firstName": "पहला नाम",
            "lastName": "अंतिम नाम",
            "email": "ईमेल",
            "password": "पासवर्ड",
            "confirmPassword": "पासवर्ड की पुष्टि करें",
            "alreadyHaveAccount": "पहले से ही खाता है? यहां लॉगिन करें",
            "registerButton": "रजिस्टर करें",
            "cancelButton": "रद्द करें",
            "backButton": "वापस",
            "languageToggle": "अंग्रेजी में बदलें",

        }
    }
};

i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        resources,
        fallbackLng: 'en',
        interpolation: {
            escapeValue: false,
        }
    });

export default i18n;
