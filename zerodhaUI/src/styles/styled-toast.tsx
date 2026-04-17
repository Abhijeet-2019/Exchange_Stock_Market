import styled from "styled-components";
import React from 'react';
import { ToastContainer, Slide } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

 

const StyledToastContainer = styled(ToastContainer).attrs({
  // custom props
})`
  .Toastify__toast--info {
      background: blue;
  }
    .Toastify__toast--success {
      background: green;
  }
    .Toastify__toast--warning {
      background: yellow;
  }
    .Toastify__toast--error {
      background: Orange;
  } 
`;

 

export const CustomToastContainer = () => {
    return (
        <StyledToastContainer
            position="top-right"
            autoClose={1000}
            hideProgressBar={true}
            transition={Slide}
            newestOnTop={false}
            closeOnClick
            rtl={false}
            pauseOnFocusLoss
            draggable={false}
            pauseOnHover/>   
 );
};