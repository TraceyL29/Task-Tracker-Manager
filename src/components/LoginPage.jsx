import axios from "axios";
import React, {useState} from 'react';
import api from "../api/api";



const LoginPage = ({onLogin}) => {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  })

const [errors, setErrors] = useState({})
const [isLoading, setLoading] = useState(false); 
const [loginError, setLoginError] = useState('');


const handleInputChange = (e) =>{
  const {name, value} = e.target;
  setFormData(prev => ({...prev, 
    [name]: value
    }));

  if(errors[name]){
    setErrors(prev => ({ ...prev, [name]: null }));
  }
}

const validateForm = () => {
  const newErrors = {}

  if(!formData.email)
    newErrors.email = 'Email is required';
  else if(!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email))
    newErrors.email= 'Please enter a valid email!'

  if(!formData.password)
    newErrors.password = 'Password is required';

return newErrors;
}

const handleSubmit = async() => {
  const error = validateForm();
  setLoading(true);
  if(Object.keys(error).length > 0){
    setErrors(error)
    return; 
  }

  try{
    const response = await onLogin(formData);
    if(loginError != null)
      setLoginError("")

    setLoading(false);
  }
  catch(e){
    setLoginError(e.response.data)
  }finally{
    setLoading(false);
  }

}

const handleKeyPress = (e) => {
  if(e.key === 'Enter'){
    e.preventDefault();
    handleSubmit();
  }
}





  return(
    <div className="min-h-screen flex items-center justify-center gap-2 bg-gray-50 p-4">
      <div className="w-full max-w-sm bg-white ">
        <div className="rounded-xl  ">
          <div className="text-center m-[18px]">
            <h1 className="text-2xl font-bold text-gray-900 mb-2">Sign in </h1>
                               {loginError && (
                                 <div className="mb-6 p-3 bg-red-50 border  text-red-600 text-sm rounded-lg">
                  <p className="text-red-500 text-sm mt-1">{loginError}</p>
                   </div>
                  )}
            <div className="space-y-5">
              <div>
                <input type="email" name="email" value={formData.email} onChange={handleInputChange} onKeyPress={handleKeyPress}
                  disabled={isLoading} className={`w-full px-4 py-3 mb-6 border rounded-lg focus:ring-1 focus:ring-gray-900 focus:border-gray-900 transition-colors
                   ${errors.email ? 'border-red-300' : 'border-gray-300'
                 } ${isLoading ? 'bg-gray-100 cursor-not-allowed' : ''}`} placeholder="Email"/>
                 {errors.email && (
                  <p className="text-red-500 text-sm mt-1">{errors.email}</p>
                  )}
               </div>
             </div>

             <div className="space-y-5">
              <div>
                <input type="password" name="password" value={formData.password} onChange={handleInputChange} onKeyPress={handleKeyPress}
                  disabled={isLoading} className={`w-full px-4 py-3 border rounded-lg focus:ring-1 focus:ring-gray-900 focus:border-gray-900 transition-colors
                   ${errors.password ? 'border-red-300' : 'border-gray-300'
                 } ${isLoading ? 'bg-gray-100 cursor-not-allowed' : ''}`} placeholder="Password"/>
                 {errors.password && (
                  <p className="text-red-500 text-sm mt-1">{errors.password}</p>
                  )}
               </div>
             </div>
             <div>
              <button type="button" onClick={handleSubmit} disabled={isLoading} className="w-full font-medium py-3 rounded-lg transition-colors">SignIn 
            </button>
          </div>
          <div className="mt-6 text-center space-y-2">
            <a href="#" className="block text-sm text-gray-600 hover:text-gray-900 transition-colors">
              Forgot your password?
            </a>
            <div className="text-sm text-gray-500">
              New user?{' '}
              <a href="#" className="text-gray-900 hover:underline">
                Create account
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
 );

}

export default LoginPage;

