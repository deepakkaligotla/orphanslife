import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Route, Routes, useNavigate, Link } from 'react-router-dom';
import Login from './auth/login/Login';
import Auth from './auth/Auth';
import App from './App';
import ProtectedRoute from './util/ProtectedRoute';
import Home from './components/Home';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
	<React.StrictMode>
		<BrowserRouter basename={'/'}>
			<Routes>
				<Route path='/auth' element={<Auth />}>
					<Route path='login' element={<Login />} />
				</Route>
				<Route path="/" element={<App/>}>
					<Route path="/*" element={
						<ProtectedRoute>
							<Home/>
						</ProtectedRoute>
					} />
				</Route>
			</Routes>
		</BrowserRouter>
	</React.StrictMode>
);
reportWebVitals();
