import React, { useState } from "react";
import { Nav } from "react-bootstrap";
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import { useNavigate, Link } from "react-router-dom";
import "./Navbar.css";
import { navItems } from "./NavItems";
import Button from "./Button";

const Navigationbar = () => {
    
    const [dropdown, setDropdown] = useState(false);
    const navigate = useNavigate();

    return (
        <React.Fragment>
            <Navbar bg="dark" expand="lg" className="navbar-dark">
                <Container>
                    <Navbar.Brand style={{color:'white'}}>
                        Orphanage Management System
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="ms-auto">
                        <ul className="nav-items">{navItems.map((item) => {
                        return (
                            <li key={item.id} className={item.cName}>
                             <Link to={item.path}>{item.title}</Link>
                            </li>);
                        })}
                        </ul>
                        <Button/>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </React.Fragment>
    );
}

export default Navigationbar;