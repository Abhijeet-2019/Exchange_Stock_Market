import { isLabelWithInternallyDisabledControl } from "@testing-library/user-event/dist/utils";
import React, { useState } from "react";
import { Button } from "react-bootstrap";
import './LoginModal.css';
import styled from "styled-components";
import { Col, Row, Form } from "react-bootstrap";
import { User } from "../domains/User";
import { UserDetails } from "../domains/UserDetails";
// import { signUpUserDetails } from "../service/HttpService";
import LoginModal from "./LoginModal";


const ButtonPanel = styled.label`
   padding-top: 20px; 
   padding-left: 15px;     
   flex-flow: column;   
   align: left
  `;

interface SignUpModalProps {
    show: boolean;
    onClose: () => void;
}

const SignUpModal = (props: SignUpModalProps) => {

    const [displayLoginPanel, setDisplayLoginPanel] = useState(true)
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [address, setAddress] = useState('');
    const [cellNo, setCellNo] = useState('');
    const [userName, setUserName] = useState('');
    const [city, setCity] = useState('');
    const [state, setState] = useState('');
    const [pinCode, setPinCode] = useState(0);
    const [amountInWallet, setAmountiNwallet] = useState(0);
    const [userAutorities ,setUserAuthorities] = useState(''); 


    const addCustomerDetails = (e: React.FormEvent) => {
        if (!validateUserCredentials()) {
            console.log("Error")
            return;
        }

        const userDetails: UserDetails = {
            address, cellNo, city, state, pinCode, amountInWallet, userAutorities
        }

        const requestBody: User =
        {
            email, password, userName, lastLoginTime: getCurrentDate(), create_time : getCurrentDate(),valid: false, userDetails
        };
        console.log("The Request Body---->" + requestBody);
        // const result = signUpUserDetails(requestBody);
        // console.log(" The result " + result);
        props.onClose();
    }
    const closeSignUp = (e: React.FormEvent) => {
        props.onClose();
    }

    function validateUserCredentials() {
        let isValidRequest = true;
        if (email == "" || email == undefined) {
            alert("Please enter a valid email id")
            isValidRequest = false;
        }
        return isValidRequest;
    }

    function getCurrentDate() {
        const currentDate = new Date();
        const formattedDate = currentDate.toISOString();
        console.log(formattedDate);
        return formattedDate
    }

    return (
        <div>
            <h2>Sign up  Please</h2>
            < Form onSubmit={addCustomerDetails}>
                <Form.Group as={Row}>
                    <Col sm="6">
                        <label htmlFor="email">Email * :</label>
                        <input className="form-control"
                            type="text"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required />
                    </Col>
                    <Col sm="6">
                        <label htmlFor="email">Password *:</label>
                        <input className="form-control"
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required />
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm="6">
                        <label htmlFor="Name">User Name *:</label>
                        <input className="form-control"
                            type="text"
                            id="userName"
                            value={userName}
                            onChange={(e) => setUserName(e.target.value)}
                            required />
                    </Col>
                    <Col sm="6">
                        <label htmlFor="cellNo">Cell No *:</label>
                        <input className="form-control"
                            type="texy"
                            id="cellNo"
                            value={cellNo}
                            onChange={(e) => setCellNo(e.target.value)}
                            required />
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm="12">
                        <label htmlFor="address">Address Please :</label>
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm="12">
                        <input className="form-control"
                            type="text"
                            id="address"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            required />
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm="4">
                        <label htmlFor="Name">City *:</label>
                        <input className="form-control"
                            type="text"
                            id="city"
                            value={city}
                            onChange={(e) => setCity(e.target.value)}
                            required />
                    </Col>
                    <Col sm="4">
                        <label htmlFor="cellNo">State *:</label>
                        <input className="form-control"
                            type="text"
                            id="state"
                            value={state}
                            onChange={(e) => setState(e.target.value)}
                            required />
                    </Col>
                    <Col sm="4">
                        <label htmlFor="pinCode">PinCode *:</label>
                        <input className="form-control"
                            type="number"
                            id="pinCode"
                            value={pinCode}
                            onChange={(e) => setPinCode(e.target.valueAsNumber)}
                            required />
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm="4">
                        <label htmlFor="amountInWallet">Add Amount In Wallet (INR) *:</label>
                        <input className="form-control"
                            type="number"
                            id="amountInWallet"
                            value={amountInWallet}
                            onChange={(e) => setAmountiNwallet(e.target.valueAsNumber)}
                            required />
                    </Col>
                    <Col sm="4">
                        <label htmlFor="userAutorities">User Roles (separate by , ) *</label>
                        <input className="form-control"
                            type="text"
                            id="userAutorities"
                            value={userAutorities}
                            onChange={(e) => setUserAuthorities(e.target.value)}
                            required />
                    </Col>
                    <Col sm="2" >
                        <ButtonPanel>
                            <Button onClick={addCustomerDetails}>  Login</Button> </ButtonPanel>
                    </Col>
                    <Col sm="2" >
                        <ButtonPanel>
                            <Button onClick={closeSignUp}> Back</Button> </ButtonPanel>
                    </Col>
                </Form.Group>
            </Form>
        </div >
    )

}
export default SignUpModal