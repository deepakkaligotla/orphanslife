import React, { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import Footer from "./Layouts/footer/Footer";
import Navbar from "./Layouts/navbar/Navigationbar";
import { useTheme, ThemeProvider, createTheme } from '@mui/material/styles';import IconButton from '@mui/material/IconButton';
import Box from '@mui/material/Box';
import Brightness4Icon from '@mui/icons-material/Brightness4';
import Brightness7Icon from '@mui/icons-material/Brightness7';

const ColorModeContext = React.createContext({ toggleColorMode: () => {} });

function MyTheme() {

	const theme = useTheme();
	const colorMode = React.useContext(ColorModeContext);

	return (
	  <Box
		sx={{
		  display: 'flex',
		  width: 'auto',
		  alignItems: 'right',
		  justifyContent: 'right',
		  bgcolor: 'background.default',
		  color: 'text.primary'
		}}
	  >
		{theme.palette.mode} mode
		<IconButton sx={{ ml: 1 }} onClick={colorMode.toggleColorMode} color="inherit">
		  {theme.palette.mode === 'dark' ? <Brightness7Icon /> : <Brightness4Icon />}
		</IconButton>
	  </Box>
	);
}

function App() {

	const [isLoggedIn, setIsLoggedIn] = useState(false);

  const checkUserToken = () => {
      const userToken = localStorage.getItem('user-token');
      if (!userToken || userToken === 'undefined') {
          setIsLoggedIn(false);
      }
      setIsLoggedIn(true);
  }

  const [mode, setMode] = React.useState('light');
  const colorMode = React.useMemo(
    () => ({
      toggleColorMode: () => {
        setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
      },
    }),
    [],
  );

  const theme = React.useMemo(
    () =>
      createTheme({
        palette: {
          mode,
        },
      }),
    [mode],
  );
  useEffect(() => {
      checkUserToken();
  }, [isLoggedIn]);
	
	return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
		    <React.Fragment>
		      {isLoggedIn && <Navbar />}
                <MyTheme/>
                <Outlet/>
		      {isLoggedIn && <Footer />}
		    </React.Fragment>
      </ThemeProvider>
    </ColorModeContext.Provider>
	);
}

export default App;
