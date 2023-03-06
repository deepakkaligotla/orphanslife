import React from 'react';
import {BrowserRouter as Router, Route, Link, Routes} from 'react-router-dom';
import Home from './Home';

function Menu() {

return (
    <center>
            <hr></hr>
            <nav>
                <ul>
                    <Link to={"/home"}> Menu </Link>
                </ul>
            </nav>
            <hr></hr>
            <Routes>
                <Route path="/Home" element={<Home/>}></Route>
            </Routes>
          <hr></hr>
    </center>)
}

export default Menu;