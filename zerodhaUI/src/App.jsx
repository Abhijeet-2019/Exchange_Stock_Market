import { useState, useEffect } from 'react'
import AppRoutes from './routes/AppRoutes';
import './App.css'
import { CustomToastContainer } from "./styles/styled-toast";
import { useAuth } from "./AuthContext";
function App() {
const { user,  } = useAuth();

  useEffect(() => {
    console.log("The user details are=="+user.email);
  }, [])

  return (
    <div className="App">
      <div className="header-container">
        <div className="left">Indian Bazzar</div>
        <div className="right">
          <p>Welcome {user.email}</p>
        </div>
      </div>
      <AppRoutes />
      <CustomToastContainer />
    </div>
  );
}

export default App
