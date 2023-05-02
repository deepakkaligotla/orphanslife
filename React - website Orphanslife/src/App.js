import React, { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import Footer from "./Layouts/footer/Footer";
import Navbar from "./Layouts/navbar/Navigationbar";
import { useTheme, ThemeProvider, createTheme } from '@mui/material/styles';
import { Box, Grid } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import Brightness4Icon from '@mui/icons-material/Brightness4';
import Brightness7Icon from '@mui/icons-material/Brightness7';
import { Paper } from '@mui/material'
import { styled } from '@mui/material/styles';
import './Asset/Css/Body.css'

const ColorModeContext = React.createContext({ toggleColorMode: () => { } });

function CustomTheme() {
  const theme = useTheme();
  const colorMode = React.useContext(ColorModeContext);

  return (
    <Box
      sx={{
        width: '4%',
        bgcolor: 'background.default',
        color: 'text.primary',
        marginTop:'1%',
        marginLeft: '1%'
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

  useEffect(() => {
    checkUserToken();
  }, [isLoggedIn]);

  //Custom Theme
  const [mode, setMode] = React.useState('dark');
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

  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'white',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
  }));

  return (

    <React.Fragment>
      <Box className="appGridBox" sx={{ flexGrow: 1, width: '100%' }}>
        <Grid className="appGridContainer" container spacing={3} style={{marginLeft: '1%',marginTop: '1%', marginRight:'1%'}} sx={{ width: '100%' }}>
            <ColorModeContext.Provider value={colorMode}>
              <ThemeProvider theme={theme}>
                {isLoggedIn && <Navbar />}
                <CustomTheme />
                <Outlet />
                {isLoggedIn && <Footer />}
              </ThemeProvider>
            </ColorModeContext.Provider>
        </Grid>
      </Box>
    </React.Fragment>
  );
}

export default App;
