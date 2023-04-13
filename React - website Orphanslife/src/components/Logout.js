import React from 'react';

export const Logout=()=> {
    localStorage.clear();
    window.location.replace("/auth/login")

    return (<React.Fragment>
        <h3>Logged Out Successfully, Thank you for visiting us!!!</h3>
    </React.Fragment>)
}

export default Logout;