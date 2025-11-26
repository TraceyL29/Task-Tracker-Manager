import React, { useState, useEffect } from "react";
import  LoginPage from "./components/LoginPage";
import  TaskManager from "./components/TaskManager";
import  authService from "./services/authService";



const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null); 
   const [successLogin, setSuccessLogin] = useState('');


    useEffect(() => {
      checkAuthentication()
    },[])


  const handleLogIn = async(credential) => {
    try{
      const response = await authService.login(credential);

      if(response){
        setIsAuthenticated(true);
        setUser(response)
        setSuccessLogin("Login Successfully")
      }
    }catch(error){
        console.log(error); 
        throw error; 
    }
  }


  const handleLogOut = async(credential) => {
    try{
      await authService.logout(credential)
      setIsAuthenticated(false);
      setUser(null);
    }catch(error){
      console.error('Logout error:', error);
    }
  }


  const checkAuthentication = async() => {
    try{
      const user = localStorage.getItem('mockUser')
      const isLoggedIn = localStorage.getItem('isLoggedIn');

      if(user && isLoggedIn === 'true'){
        setIsAuthenticated(true);
        setUser(JSON.parse(user));
      }
    }
    catch(error){
      console.log(error)
    }finally{
      setLoading(false)
    }
  }


  if(loading){
    return(
      <div className="min-h-screen bg-gray-100 p-8 flex items-center justify-center">
        <div className="flex flex-col bg-gray-100 border border-black-300 shadow-md p-4 rounded">
          <div>...Loading...</div>
        </div>
      </div>
      )
  }

   return(
    <>
      {
        !isAuthenticated ? (
          <LoginPage onLogin = {handleLogIn}/>
          ) : (
          <TaskManager user= {user} onLogout = {handleLogOut}  successLogin={successLogin}/>
          )
      }
    </>
    )



}


export default App;