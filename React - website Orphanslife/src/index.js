import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './Auth/login/Login';
import Auth from './Auth/Auth';
import App from './App.js';
import ProtectedRoute from './util/ProtectedRoute';
import Home from './Routes/Home';
import { Box, Grid } from '@mui/material';
import './Asset/Css/Body.css'
import { Paper } from '@mui/material'
import { styled } from '@mui/material/styles';

const root = ReactDOM.createRoot(document.getElementById('root'));
const Item = styled(Paper)(({ theme }) => ({
	backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'white',
	...theme.typography.body2,
	padding: theme.spacing(1),
	textAlign: 'center',
  }));

root.render(
	<React.Fragment>
		<Box className="indexGridBox" sx={{ flexGrow: 1}}>
			<Grid className="indexGridContainer" container spacing={3} style={{marginLeft: '1%',marginTop: '1%', marginRight:'1%'}} sx={{ width: '100%' }}>
				<Item sx={{width:'inherit'}}>
				<React.StrictMode>
					<BrowserRouter basename={'/'}>
						<Routes>
							<Route path='/auth' element={<Auth />}>
								<Route path='login' element={<Login />} />
							</Route>
							<Route path="/" element={<App />}>
								<Route path="/*" element={
									<ProtectedRoute>
										<Home />
									</ProtectedRoute>
								} />
							</Route>
						</Routes>
					</BrowserRouter>
				</React.StrictMode>
				</Item>
			</Grid>
		</Box>
	</React.Fragment>
);
reportWebVitals();
