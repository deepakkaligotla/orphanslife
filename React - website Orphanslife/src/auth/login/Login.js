import axios from "axios";
import React, { useState } from "react";
import Popup from 'reactjs-popup';
import { Box, Paper, Radio, RadioGroup, FormControlLabel, TextField, Grid, FormControl, MenuItem, Select, InputLabel } from '@mui/material'
import { styled } from '@mui/material/styles';
import { LocalizationProvider, DateCalendar, DayCalendarSkeleton } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { useNavigate } from "react-router-dom";
import { pink } from '@mui/material/colors';
import './css/style.css'
import '../../Asset/Css/Body.css'
import dayjs from 'dayjs';
import kids_jumping from '../../Asset/images/kids_jumping.gif'
import form_background from '../../Asset/images/form_background.jpeg'
import login_background from '../../Asset/images/login_background.jpg'

function getRandomNumber(min, max) {
    return Math.round(Math.random() * (max - min) + min);
}

function fakeFetch(date, { signal }) {
    return new Promise((resolve, reject) => {
        const timeout = setTimeout(() => {
            const daysInMonth = date.daysInMonth();
            const daysToHighlight = [1, 2, 3].map(() => getRandomNumber(1, daysInMonth));

            resolve({ daysToHighlight });
        }, 500);

        signal.onabort = () => {
            clearTimeout(timeout);
            reject(new DOMException('aborted', 'AbortError'));
        };
    });
}

function Login() {

    const [localIP, setLocalIP] = useState('http://192.168.0.14:4000/')
    localStorage.setItem("localIP", localIP)
    const navigate = useNavigate();
    var otp, userOTP = 0, otpExpire, validateOTPTime, otpSentTime;
    const [open] = useState(false);
    const [cred, setCred] = useState({ email: '', password: '' })
    const [signUp, setSignUp] = useState({ whoAmI: '', adminRole: '', name: '', dob: '', gender: '', mobile: '', email: '', password: '', confirmPassword: '' })
    const initialValue = dayjs(new Date());
    const requestAbortController = React.useRef(null);
    const [isLoading, setIsLoading] = React.useState(false);
    const [highlightedDays, setHighlightedDays] = React.useState([1, 2, 15]);
    const [selectedGender, setSelectedGender] = React.useState('Male');

    const signUpHandleChange = (args) => {
        console.log("args.target.id = " + args.target.id)
        var filledSignUpData = { ...signUp };

        if (args.target.id == "adminRole") {
            filledSignUpData['adminRole'] = args.target.value;
        } if (args.target.id === "name") {
            filledSignUpData['name'] = args.target.value;
        } if (args.target.id === "dob") {
            filledSignUpData['dob'] = args.target.value;
        } if (args.target.id === "gender") {
            filledSignUpData['gender'] = args.target.value;
        } if (args.target.id === "mobile") {
            filledSignUpData['mobile'] = args.target.value;
        } if (args.target.id === "email") {
            filledSignUpData['email'] = args.target.value;
        } if (args.target.id === "password") {
            filledSignUpData['password'] = args.target.value;
        } if (args.target.id === "confirmPassword") {
            filledSignUpData['confirmPassword'] = args.target.value;
        }
        setSignUp(filledSignUpData);
        console.log(signUp)
    }

    function magic() {
        if (signUp['whoAmI'] == "Admin") {
            document.getElementById('adminRole').style.visibility = "visible";
        } else document.getElementById('adminRole').style.visibility = "hidden";
    }

    const loginHandleChange = (args) => {
        var filledLoginCred = { ...cred };
        if (args.target.id === 'login-email') {
            filledLoginCred['email'] = args.target.value;
        }
        if (args.target.id === 'login-password') {
            filledLoginCred['password'] = args.target.value;
        }
        setCred(filledLoginCred);
        console.log(cred)
    }

    const handleMonthChange = (date) => {
        if (requestAbortController.current) {
            // make sure that you are aborting useless requests
            // because it is possible to switch between months pretty quickly
            requestAbortController.current.abort();
        }

        setIsLoading(true);
        setHighlightedDays([]);
        fetchHighlightedDays(date);
    };

    const fetchHighlightedDays = (date) => {
        const controller = new AbortController();
        fakeFetch(date, {
            signal: controller.signal,
        })
            .then(({ daysToHighlight }) => {
                setHighlightedDays(daysToHighlight);
                setIsLoading(false);
            })
            .catch((error) => {
                // ignore the error if it's caused by `controller.abort`
                if (error.name !== 'AbortError') {
                    throw error;
                }
            });

        requestAbortController.current = controller;
    };

    const genderHandleChange = (event) => {
        setSelectedGender(event.target.value);
    };
    const genderControlProps = (item) => ({
        checked: selectedGender === item,
        onChange: genderHandleChange,
        value: item,
        name: 'color-radio-button-demo',
        inputProps: { 'aria-label': item },
    });

    function validateOTP() {
        userOTP = document.getElementById('validate-otp').value
        console.log('User Entered OTP - ' + userOTP)
        validateOTPTime = (new Date().getMinutes() * 60) + new Date().getSeconds();
        otpExpire = validateOTPTime - otpSentTime
        console.log(otpSentTime)
        console.log(validateOTPTime)
        console.log(otpExpire)
        if (otpExpire < 120) {
            if (otp == userOTP) {
                console.log('OTP Verified')
                setTimeout(() => {
                    navigate('/home');
                }, 500);
            } else if (otp != userOTP) {
                console.log('Invalid OTP')
                alert("Invalid OTP please check again");
            }
        } else if (otpExpire > 120) {
            console.log('Invalid expired')
            alert("OTP Expired please try again!!!");
        }
    }

    function loginButton() {
        console.log(cred)
        axios.post(localIP, cred).then((response) => {
            const profile = response.data;
            console.log(profile)
            const token = profile.token;
            if (response.data != null) {
                otpTimer()
                otpSentTime = (new Date().getMinutes() * 60) + new Date().getSeconds();
                localStorage.clear();
                localStorage.setItem('user-token', token);
                otp = profile.otp;
                console.log('Sent OTP from DB')
                return;
            } if (response.ok === 'false') {
                alert("Something went wrong")
            }
        }).catch((error) => {
            return;
        });
    }

    function otpTimer() {
        let circularProgress = document.querySelector('.circular-progress'),
            progressValue = document.querySelector('.progress-value')
        let progressStartValue = 120, progressEndValue = 0, speed = 1000
        let progress = setInterval(() => {
            progressStartValue--
            progressValue.textContent = `${progressStartValue} sec left`
            circularProgress.style.background = `conic-gradient(#7d2ae8 ${progressStartValue * 3}deg, #ededed 0deg)`
            if (progressStartValue === progressEndValue) {
                clearInterval(progress);
            }
        }, speed);
    }

    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'white',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
    }));

    return (
        <React.Fragment>
            <Box className="loginBox" sx={{ flexGrow: 1 }}>
                <Grid className="loginGrid" container spacing={3} style={{ marginLeft: '1%', marginTop: '1%' }} sx={{ width: '100%' }}>
                    <Grid container direction="row" justifyContent="center" alignItems="center">
                        <Grid item sx={{ width: '100%', border: 2, marginRight: '1%' }}>
                            <Item>
                                <label style={{ fontSize: '200%', backgroundColor: 'white' }}>Orphanage Management System</label>
                            </Item>
                        </Grid>
                    </Grid>


                    <Grid container direction="row" justifyContent="center" alignItems="center" marginTop={"1%"}>
                        <div style={{ width: '49%', height:'100%' }}>
                            <Grid item sx={{ border: 1 }}>
                                <div>
                                    <label style={{ fontSize: '100%', backgroundColor: 'white' }}>Login</label>
                                </div>
                                <Box
                                    component="form"
                                    noValidate
                                    autoComplete="on"
                                    style={{ width: '100%' }}>
                                    <div>
                                        <TextField
                                            required
                                            type='email'
                                            className="form-control"
                                            id="login-email"
                                            label="Enter Email"
                                            placeholder='Registered Email'
                                            onChange={loginHandleChange}
                                            helperText="Please enter registered Email ID"
                                            style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                            inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                            InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                            FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }} />
                                    </div>
                                    <br />
                                    <div>
                                        <TextField
                                            required
                                            type='password'
                                            className="form-control"
                                            id="login-password"
                                            label="Enter Password"
                                            placeholder='Registered Password'
                                            onChange={loginHandleChange}
                                            helperText="Please enter registered password"
                                            style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                            inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                            InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                            FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }} />
                                    </div>
                                </Box>
                                <br />
                                <div>
                                    <Popup onOpen={loginButton} open={open} trigger={<button>LOGIN</button>} modal nested>
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
                                                        <input type={'text'} id={'validate-otp'} name="otp" required />
                                                        <br />
                                                        <div className="circular-container" id="circular-container">
                                                            <div className="circular-progress">
                                                                <span className="progress-value">120 sec left</span>
                                                            </div>
                                                        </div>
                                                        <button className="btn btn-success" id="login-btn" onClick={validateOTP}>Validate OTP</button>
                                                    </div>
                                                    <br />
                                                </div>
                                            </div>
                                        </div>
                                    </Popup>
                                </div>

                                <div>
                                    <img src={kids_jumping} alt="kids jumping" style={{ height: '25%', width: '25%' }}></img>
                                </div>

                            </Grid>
                        </div>
                        {/* ========================================================================SignUp======================================================================== */}
                        <div style={{ overflow: 'auto', width: '49%', marginLeft: '1%', textAlign: '-webkit-center', backgroundImage: `url(${form_background})`, backgroundSize:'100%', backgroundRepeat: 'no-repeat' }}>
                            <Grid item sx={{ width: '50%', marginTop: '20%', marginBottom: '2%', textAlign: '-webkit-center' }}>
                                <Grid style={{ width: '100%' }}>
                                    <div>
                                        <label style={{ fontSize: '200%', backgroundColor: 'white' }}>SignUp</label>
                                    </div>
                                    <Box
                                        component="form"
                                        noValidate
                                        autoComplete="on"
                                        style={{ width: '100%' }}>

                                        <div>
                                            <Select
                                                variant="filled"
                                                id="whoAmI"
                                                defaultValue={"Select Sponsor/Admin"}
                                                label='Sponsor/Admin'
                                                onChange={(e) => {
                                                    signUp['whoAmI'] = e.target.value;
                                                    magic();
                                                }}
                                                style={{ width: '100%', alignItems: 'center' }}>
                                                <MenuItem id="whoAmI" value='Select Sponsor/Admin'>Select Sponsor/Admin</MenuItem>
                                                <MenuItem value="Sponsor">Sponsor</MenuItem>
                                                <MenuItem value="Admin">Admin</MenuItem>
                                            </Select>
                                        </div>
                                        <br />
                                        <div>
                                            <Select
                                                variant="filled"
                                                id="adminRole"
                                                defaultValue={"Select Volunteer/Guardian"}
                                                label='Volunteer/Guardian'
                                                onChange={(e) => {
                                                    signUp['adminRole'] = e.target.value;
                                                    magic();
                                                }}
                                                style={{ width: '100%', alignItems: 'center', visibility: 'hidden' }}>
                                                <MenuItem id="whoAmI" value='Select Volunteer/Guardian'>Select Volunteer/Guardian</MenuItem>
                                                <MenuItem value="Volunteer">Volunteer</MenuItem>
                                                <MenuItem value="Guardian">Guardian</MenuItem>
                                            </Select>
                                        </div>
                                        <br />
                                        <div>
                                            <TextField
                                                required
                                                type='text'
                                                id="name"
                                                label="Enter Full Name"
                                                placeholder='Full Name'
                                                onChange={signUpHandleChange}
                                                helperText="Please enter Full Name"
                                                style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                                inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                                InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                                FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }} />
                                        </div>
                                        <br />
                                        <div>
                                            <TextField
                                                required
                                                error
                                                type='date'
                                                id="dob"
                                                label="Date Of Birth"
                                                placeholder='Date of Birth'
                                                onChange={signUpHandleChange}
                                                helperText="Should be older than 18 Years"
                                                style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                                inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                                InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                                FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }}>

                                                <LocalizationProvider dateAdapter={AdapterDayjs}>
                                                    <DateCalendar
                                                        defaultValue={initialValue}
                                                        loading={isLoading}
                                                        onMonthChange={handleMonthChange}
                                                        renderLoading={() => <DayCalendarSkeleton />} />
                                                </LocalizationProvider>
                                            </TextField>
                                        </div>
                                        <br />
                                        <div>
                                            <FormControl sx={{ border: 1, borderColor: 'red' }} style={{ alignItems: 'center', marginBottom: '20px' }}>
                                                <InputLabel>Gender</InputLabel>
                                                <RadioGroup
                                                    label="Gender"
                                                    row
                                                    onChange={signUpHandleChange}
                                                    name="radio-buttons-group">
                                                    <FormControlLabel value="Female" control={<Radio id='gender' {...genderControlProps('Female')} sx={{ color: pink[800], '&.Mui-checked': { color: pink[600], }, }} />} label="Female" />
                                                    <FormControlLabel value="Male" control={<Radio id='gender' {...genderControlProps('Male')} color="success" />} label="Male" />
                                                    <FormControlLabel value="Other" control={<Radio id='gender' {...genderControlProps('Other')} color="secondary" />} label="Other" />
                                                </RadioGroup>
                                            </FormControl>
                                        </div>
                                        <br />
                                        <div>
                                            <TextField
                                                required
                                                type='tel'
                                                id="mobile"
                                                label="Enter mobile"
                                                placeholder='Registered Mobile Number'
                                                onChange={signUpHandleChange}
                                                helperText="Please enter proper mobile number"
                                                style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                                inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                                InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                                FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }} />
                                        </div>
                                        <br />
                                        <div>
                                            <TextField
                                                required
                                                type='email'
                                                id="email"
                                                label="Email"
                                                placeholder='Email'
                                                onChange={signUpHandleChange}
                                                helperText="Please enter your Email ID"
                                                style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                                inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                                InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                                FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }} />
                                        </div>
                                        <br />
                                        <div>
                                            <TextField
                                                required
                                                type='password'
                                                id="password"
                                                label="Set Password"
                                                placeholder='Set Password'
                                                onChange={signUpHandleChange}
                                                helperText="Please set Password for your account"
                                                style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                                inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                                InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                                FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }} />
                                        </div>
                                        <br />
                                        <div>
                                            <TextField
                                                required
                                                type='password'
                                                id="confirmPassword"
                                                label="Confirm Password"
                                                placeholder='Confirm Password'
                                                onChange={signUpHandleChange}
                                                helperText="Please confirm Password for your account"
                                                style={{ textAlignLast: 'center', width: '100%', height: '100%' }}
                                                inputProps={{ style: { fontSize: '100%', width: '100%' } }} // font size of input text
                                                InputLabelProps={{ style: { fontSize: '100%', color: 'white', backgroundColor: 'black' } }}
                                                FormHelperTextProps={{ style: { fontSize: '50%', color: 'red', backgroundColor: 'black' } }} />
                                        </div>
                                        <br />
                                        <button>SIGN UP</button>

                                    </Box>
                                </Grid>
                            </Grid>
                        </div>
                    </Grid>

                </Grid>
            </Box >
        </React.Fragment >
    );
}

export default Login;