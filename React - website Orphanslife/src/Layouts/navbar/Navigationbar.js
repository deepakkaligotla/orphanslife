import React, { useState } from "react";
import { Nav } from "react-bootstrap";
import Navbar from 'react-bootstrap/Navbar';
import { useNavigate, Link } from "react-router-dom";
import "./Navbar.css";
import { navItems } from "./NavItems";
import Button from "./Button";
import { Paper } from '@mui/material'
import { styled } from '@mui/material/styles';
import { Box, Grid } from '@mui/material';
import '../../Asset/Css/Body.css'

const Navigationbar = () => {

    const [dropdown, setDropdown] = useState(false);
    const navigate = useNavigate();
    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'white',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
    }));

    return (
        <React.Fragment>
            <Box className="navGridBox" sx={{ flexGrow: 1 }}>
                <Grid className="navGridContainer" container style={{ marginLeft: '1%', marginTop: '1%' }} sx={{ width: '99%' }}>
                    <Grid item sx={{ width: 'inherit' }} direction="row" justifyContent="center" alignItems="center">
                        <Item sx={{ width: 'inherit' }} style={{ marginRight: '3%' }} >
                            <Navbar bg="dark" expand="lg" className="navbar-dark" style={{ flexDirection: 'column' }}>
                                <Navbar.Brand style={{ color: 'white' }}>
                                    Orphanage Management System
                                </Navbar.Brand>
                                <div>
                                    <Navbar.Toggle aria-controls="basic-navbar-nav" style={{ color: 'black', width: '10%' }}>Menu</Navbar.Toggle>
                                </div>
                                <div style={{ justifyContent: "center", alignItems: "center" }}>
                                    <Navbar.Collapse id="basic-navbar-nav">
                                        <Nav className="ms-auto">
                                            <ul className="nav-items">{navItems.map((item) => {
                                                return (
                                                    <li key={item.id} className={item.cName}>
                                                        <Link to={item.path}>{item.title}</Link>
                                                    </li>);
                                            })}
                                            </ul>
                                            <Button />
                                        </Nav>
                                    </Navbar.Collapse>
                                </div>
                            </Navbar>
                        </Item>
                    </Grid>
                </Grid>
            </Box >
        </React.Fragment >
    );
}

export default Navigationbar;