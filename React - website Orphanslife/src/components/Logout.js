import React from 'react';
import { useNavigate } from "react-router-dom";

export const Logout=()=> {
    const navigate = useNavigate();
    localStorage.clear();
    window.location.replace("/auth/login")

    return <h3>Logged Out Successfully, Thank you for visiting us!!!</h3>
}

export default Logout;