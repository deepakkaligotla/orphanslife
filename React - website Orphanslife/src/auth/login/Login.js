import axios from "axios";
import React from "react";
import { Button, Col, Container, Form, FormGroup, FormLabel, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

function Login() {

    const loginAPI = 'http://orphanslife.in:4000/adminlogin';
    const navigate = useNavigate();

    const submitLoginForm = (event) => {
        event.preventDefault();
        const formElement = document.querySelector('#loginForm');
        const formData = new FormData(formElement);
        const formDataJSON = Object.fromEntries(formData);
        const btnPointer = document.querySelector('#login-btn');
        btnPointer.innerHTML = 'Please wait..';
        btnPointer.setAttribute('disabled', true);
        axios.post(loginAPI, formDataJSON).then((response) => {
            btnPointer.innerHTML = 'Login';
            btnPointer.removeAttribute('disabled');
            const profile = response.data.data[0];
            const token = profile.role;
            if (!token) {
                alert('Unable to login. Please try after some time.');
                return;
            }
            localStorage.clear();
            localStorage.setItem('user-token', token);
            setTimeout(() => {
                navigate('/home');
            }, 500);

        }).catch((error) => {
            btnPointer.innerHTML = 'Login';
            btnPointer.removeAttribute('disabled');
            alert("Oops! Provided email or password is incorrect");
        });
    }

    return (
        <React.Fragment>
            <Container className="my-5">
                <h2 className="fw-normal mb-5">Login To Orphanage Management System</h2>
                <Row>
                    <Col md={{span: 6}}>
                        <Form id="loginForm" onSubmit={submitLoginForm}>
                            <FormGroup className="mb-3">
                                <FormLabel htmlFor={'login-email'}>Email</FormLabel>
                                <input type={'text'} className="form-control" id={'login-email'} name="email" required />
                            </FormGroup>
                            <FormGroup className="mb-3">
                                <FormLabel htmlFor={'login-password'}>Password</FormLabel>
                                <input type={'password'} className="form-control" id={'login-password'} name="password" required />
                            </FormGroup>
                            <Button type="submit" className="btn btn-success" id="login-btn">Login</Button>
                        </Form>
                    </Col>
                </Row>

				<img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/kids_jumping_crop.gif" alt="kids jumping"></img>
            </Container>
        </React.Fragment>
    );
}

export default Login;