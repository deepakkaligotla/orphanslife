import React, { useState } from "react";
import { Nav } from "react-bootstrap";
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import { useNavigate, Link } from "react-router-dom";
import "./Navbar.css";
import { navItems } from "./NavItems";
import Button from "./Button";
import Dropdown from "./Dropdown";

const Navigationbar = () => {
    
    const [dropdown, setDropdown] = useState(false);
    const navigate = useNavigate();

    return (
        <React.Fragment>
            <Navbar bg="dark" expand="lg" className="navbar-dark">
                <Container>
                    <Navbar.Brand>
                        <img className="navbar-logo" src="../../images/donate" onClick={() => navigate("/home")} width="300px" alt="kids jumping"></img><br></br>
                        Orphanage Management System
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="ms-auto">
                        <ul className="nav-items">{navItems.map((item) => {
                            if (item.title === "Menu") {
                                return (
                                    <li key={item.id}
                                        className={item.cName}
                                        onMouseEnter={() => setDropdown(true)}
                                        onMouseLeave={() => setDropdown(false)}>
                        
                                     <Link to={item.path}>{item.title}</Link>
                                        {dropdown && <Dropdown />}
                                    </li>
                                );
                            } 
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