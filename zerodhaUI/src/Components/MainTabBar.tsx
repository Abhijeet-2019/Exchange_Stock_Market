import React from "react"
import { Nav, Navbar } from 'react-bootstrap'
import { NavLink } from "react-router-dom";

export const MainTabBar = () => {
        const linkStyle = ({ isActive }: { isActive: boolean }) => ({
        color: isActive ? 'white' : '#dbd4d4', // Red if active, dark gray if not
        textDecoration: 'none'
    });
    
    return (
        <div>         
            <Navbar className="navbar navbar-expand-lg navbar-light bg-example border-bottom border-red" style={{ height: 60 }}>
                <Nav>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mrx-auto" navbar>
                         <NavLink className="nav-link" style={linkStyle} to="/market"> Market </NavLink>
                        <NavLink className="nav-link" style={linkStyle} to="/portfolio"> Portfolio</NavLink>
                        <NavLink className="nav-link" style={linkStyle} to="/orders"> Orders</NavLink>
                        <NavLink className="nav-link" style={linkStyle} to="/home"> Home</NavLink>
                        </Nav>
                    </Navbar.Collapse>
                </Nav>
            </Navbar>
        </div>
    );
}