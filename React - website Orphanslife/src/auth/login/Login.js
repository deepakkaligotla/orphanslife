import axios from "axios";
import React, { useState } from "react";
import Popup from 'reactjs-popup';
import { Button, Col, Container, Form, FormGroup, FormLabel, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import './css/style.css'

function Login() {

    const loginAPI = 'http://192.168.0.14:4000/adminlogin';
    const navigate = useNavigate();
    var otp, userOTP=0, otpExpire, validateOTPTime, otpSentTime;
    const [open] = useState(false);
    
    const submitLoginForm = (event) => {
        event.preventDefault();
        const formElement = document.querySelector('#loginForm');
        const formData = new FormData(formElement);
        const formDataJSON = Object.fromEntries(formData);
        const btnPointer = document.querySelector('#login-btn');
        btnPointer.innerHTML = 'Please wait..';
        btnPointer.setAttribute('disabled', true);
        axios.post(loginAPI, formDataJSON).then((response) => {
            otpTimer()
            btnPointer.innerHTML = 'Login';
            btnPointer.removeAttribute('disabled');
            const profile = response.data.data[0];
            const token = profile.role;
            if(response.data.data[0]!=null) {
                otpSentTime = (new Date().getMinutes()*60)+new Date().getSeconds();
                localStorage.clear();
                localStorage.setItem('user-token', token);
                otp=response.data.otp;
                console.log('OTP sent to user - '+otp)
                return;
            }
        }).catch((error) => {
            console.log(error)
                alert('Oops! Provided email or password is incorrect');
                return;
        });
    }

    function validateOTP() {
        userOTP = document.getElementById('validate-otp').value
        console.log('User Entered OTP - '+userOTP)
        validateOTPTime = (new Date().getMinutes()*60)+ new Date().getSeconds();
        otpExpire = validateOTPTime - otpSentTime
        if(otpExpire<120) {
            if(otp==userOTP) {
                setTimeout(() => {
                    navigate('/home');
                }, 500);
            } else if(otp!=userOTP) {
                alert("Invalid OTP please check again");
            }
        } else if(otpExpire>120) {
            alert("OTP Expired please try again!!!");
        }
    }

    function otpTimer() {
        let circularProgress = document.querySelector('.circular-progress'),
        progressValue = document.querySelector('.progress-value')
        let progressStartValue = 120, progressEndValue = 0, speed = 1000
        let progress = setInterval(() => {
            progressStartValue--
            progressValue.textContent = `${progressStartValue} sec left`
            circularProgress.style.background = `conic-gradient(#7d2ae8 ${progressStartValue * 3}deg, #ededed 0deg)`
            if(progressStartValue === progressEndValue){
                clearInterval(progress);
            }    
        }, speed);
    }

    return (
        <>
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
                            <Popup open={open} trigger={<Button type="submit" className="btn btn-success" id="login-btn">Login</Button>} modal nested>
                            <div className="otpWindow">
                                <div>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                </button>
                                </div>
                                <div className="modal-dialog" role="document">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h5 className="modal-title">OTP Sent to Email</h5>
                                        </div>
                                        <div className="modal-body">
                                            <input type={'text'} id={'validate-otp'} name="otp" required/>
                                            <br/>
                                            <div className="circular-container" id="circular-container">
                                                <div className="circular-progress">
                                                    <span className="progress-value">120 sec left</span>
                                                </div>
                                            </div>
                                            <br/>
                                            <button className="btn btn-success" id="login-btn" onClick={validateOTP}>Validate OTP</button>
                                        </div>
                                        <br/>
                                    </div>
                                </div>
                            </div>
                        </Popup>
                        </Form>
                    </Col>
                </Row>
				<img src="images/kids_jumping.gif" alt="kids jumping"></img>
            </Container>
        </>
    );
}

export default Login;