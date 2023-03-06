import React, { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import Footer from "./portal/footer/Footer";
import Navbar from "./portal/navbar/Navigationbar";

function App() {

	const [isLoggedIn, setIsLoggedIn] = useState(false);

    const checkUserToken = () => {
        const userToken = localStorage.getItem('user-token');
        if (!userToken || userToken === 'undefined') {
            setIsLoggedIn(false);
        }
        setIsLoggedIn(true);
    }

    useEffect(() => {
        checkUserToken();
    }, [isLoggedIn]);
	
	return (
		<React.Fragment>
			{isLoggedIn && <Navbar />}
            <Outlet />
			{isLoggedIn && <Footer />}
		</React.Fragment>
	);
}

export default App;
