import React, { useState, useEffect, useRef } from "react";


import { Button } from "react-bootstrap";
import './LoginModal.css';
import styled from "styled-components";
import SignUpModal from "./SignUpModal";
import { User } from "../domains/User"


const ButtonPanel = styled.label`
   padding-top: 20px; 
   padding-left: 15px;     
   flex-flow: column;   
   align: left
  `;

interface LoginModalProps {
    show: boolean;
    onClose: () => void;
    showModelForInValidUser: () => void;
    // onLogin: (email: string, password: string ) => void;
    onLogin: (user: User) => void
}

const LoginModal = (props: LoginModalProps) => {

    const textInput = useRef<HTMLInputElement>
        (document.createElement("input"));

    const [showSignUpModal, setSignUpModal] = useState(false);
    const [hide, setHide] = useState(true);
    const displaySignUpModal = () => {
        setSignUpModal(true);
    };

    const closeSignUpModal = () => {
        setSignUpModal(false);
    }

    useEffect(() => {
        textInput.current.focus();
        textInput.current.style.border = "6px solid blue";  // Set blue border

    }, []);

    const [email, setEmail] = useState('abhijeet.mohanty001@gmail.com');
    const [password, setPassword] = useState('aaa');
    const [user, setUser] = useState<User>();
    const [loginFailed, setLoginFailed] = useState(false);


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        checkIfUserValid();
    };

    const checkIfUserValid = async (): Promise<any> => {
        props.onClose();
    }

    if (!props.show) {
        return null;
    }


    return (
        <div className="Model-Backdrop"  >
            <div className="Modal-content" onClick={(e) => e.stopPropagation()}
                style={{ display: !showSignUpModal ? "block" : "none" }}  >
                {loginFailed ?
                    (<small>In valid Credentials please re-try </small>)
                    : (<h2>Login Please</h2>)
                }

                <form onSubmit={handleSubmit}>
                    <div className="Form-group">
                        <label htmlFor="username">Email:</label>
                        <input className="form-control"
                            type="text"
                            ref={textInput}
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="Form-group">
                        <label htmlFor="password">Password:</label>
                        <input className="form-control"
                            type="text"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <ButtonPanel>
                        <Button onClick={handleSubmit}>  Login</Button>
                    </ButtonPanel>
                    <ButtonPanel>
                        <Button onClick={displaySignUpModal}>SignUp</Button>
                    </ButtonPanel>
                </form>
            </div>
            <div className="SignUp-Modal-content" onClick={(e) => e.stopPropagation()}
                style={{ display: !showSignUpModal ? "none" : "block" }}>
                <SignUpModal
                    show={showSignUpModal}
                    onClose={closeSignUpModal}
                />
            </div>
        </div>
    )
}

export default LoginModal