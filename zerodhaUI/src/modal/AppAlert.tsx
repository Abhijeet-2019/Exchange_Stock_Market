import React from "react";
import { toast } from 'react-toastify';


export interface AppAlertProps {
    header: string
    content: string;
}

export const AppAlert = (props: AppAlertProps) => {
    return (        
        <div>        
            <h4>{props.header}</h4>
            <p>{props.content}</p>
        </div>
    )
};

export const ToastSuccessOptions = {
    type: toast.TYPE.SUCCESS,
    position: toast.POSITION.TOP_RIGHT,
    // autoClose: 10000,
    hideProgressBar: false,
    closeOnClick: false,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
} 

export const ToastErrorOptions = {
    type: toast.TYPE.ERROR,
    position: toast.POSITION.TOP_RIGHT,
    autoClose: 10000,
   hideProgressBar: false,
    closeOnClick: false,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
} 

export const ToastErrorColOptions = {
    type: toast.TYPE.ERROR,
    position: toast.POSITION.TOP_CENTER,
    autoClose: 10000,
    hideProgressBar: false,
    closeOnClick: false,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
} 

export const ToastInfoOptions = {
    type: toast.TYPE.INFO,
    position: toast.POSITION.TOP_RIGHT,
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: false,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
}