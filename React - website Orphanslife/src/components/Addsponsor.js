import React, { Component } from 'react';
import { styled } from '@mui/material/styles';
import {Box, TextField, Radio, RadioGroup, FormControlLabel, FormControl, FormLabel, MenuItem, Select, InputLabel, InputBase} from '@mui/material';
import { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider, DateCalendar, DayCalendarSkeleton } from '@mui/x-date-pickers';
import { pink } from '@mui/material/colors';
import OtpInput from 'react-otp-input';
import OutlinedBox from '../Asset/Essentials/OutlinedBox.js'

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

const initialValue = dayjs(new Date());

export default function AddSponsor()
{
    const [sponsor, setSponsor] = useState({sponsor_id: "",
    sponsor_name: "",
    sponsor_dob: "",
    sponsor_gender: "",
    sponsor_govt_id_type: "",
    sponsor_govt_id: "",
    sponsor_mobile: "",
    sponsor_email: "",
    sponsor_password: "",
    marital_status: "",
    sponsor_image: "",
    sponsor_address: "",
    sponsor_location_id: "",
    spouce_name: "",
    spouce_dob: "",
    spouce_govt_id_type: "",
    spouce_govt_id: "",
    spouce_mobile: "",
    spouce_image: "",
    donation_id: "",
    sponsor_created_at: "",
    sponsor_updated_at: ""});
    const [message, setmessage] = useState("");
    const [shouldCleanTextBoxes, setShouldCleanTextBoxes] = useState(false);
    const [selectedGender, setSelectedGender] = React.useState('Male');
    const [selectedGovtIdType, setSelectedGovtIdType] = React.useState('');
    const [numInputs, setNumInputs] = React.useState('0');

    const sponsorHandleChange = (args)=>
    {
        console.log("args.target.id = "+args.target.id)
        var filledSponsorData = {...sponsor};
        if(args.target.id==="sponsor_name") {
            filledSponsorData['sponsor_name'] = args.target.value;
        } if(args.target.id==="sponsor_dob") {
            filledSponsorData['sponsor_dob'] = args.target.value;
        } 
        if(args.target.id==="sponsor_gender") {
            filledSponsorData['sponsor_gender'] = args.target.value;
        } if(args.target.id==="sponsor_govt_id_type") {
            console.log(selectedGovtIdType)
            if(selectedGovtIdType==="Aadhaar") {
                setNumInputs(12)
            } if(selectedGovtIdType==="Driving_Licence") {
                setNumInputs(16)
            } if(selectedGovtIdType==="PAN" || args.target.value==="Voter_ID") {
                setNumInputs(10)
            } if(selectedGovtIdType==="Passport") {
                setNumInputs(8)
            }
            filledSponsorData['sponsor_govt_id_type'] = args.target.value;
        } if(args.target.id==="sponsor_govt_id") {
            console.log("inside govt id "+args.target.value)
            filledSponsorData['sponsor_govt_id'] = args.target.value;
        } if(args.target.id==="sponsor_mobile") {
            filledSponsorData['sponsor_mobile'] = args.target.value;
        } if(args.target.id==="sponsor_email") {
            filledSponsorData['sponsor_email'] = args.target.value;
        } if(args.target.id==="sponsor_password") {
            filledSponsorData['sponsor_password'] = args.target.value;
        } if(args.target.id==="marital_status") {
            filledSponsorData['marital_status'] = args.target.value;
        } if(args.target.id==="sponsor_image") {
            filledSponsorData['sponsor_image'] = args.target.value;
        } if(args.target.id==="sponsor_address") {
            filledSponsorData['sponsor_address'] = args.target.value;
        } if(args.target.id==="sponsor_location_id") {
            filledSponsorData['sponsor_location_id'] = args.target.value;
        } if(args.target.id==="spouce_name") {
            filledSponsorData['spouce_name'] = args.target.value;
        } if(args.target.id==="spouce_dob") {
            filledSponsorData['spouce_dob'] = args.target.value;
        } if(args.target.id==="spouce_govt_id_type") {
            filledSponsorData['spouce_govt_id_type'] = args.target.value;
        } if(args.target.id==="spouce_govt_id") {
            filledSponsorData['spouce_govt_id'] = args.target.value;
        } if(args.target.id==="spouce_mobile") {
            filledSponsorData['spouce_mobile'] = args.target.value;
        } if(args.target.id==="spouce_image") {
            filledSponsorData['spouce_image'] = args.target.value;
        } if(args.target.id==="donation_id") {
            filledSponsorData['donation_id'] = args.target.value;
        } if(args.target.id==="sponsor_created_at") {
            filledSponsorData['sponsor_created_at'] = args.target.value;
        } if(args.target.id==="sponsor_updated_at") {
            filledSponsorData['sponsor_updated_at'] = args.target.value;
        }
        setSponsor(filledSponsorData);
        console.log(sponsor)
    }

    useEffect(()=>{
        if(message!=="")
        {
            setTimeout(() => 
            {
                setmessage("");
            }, 2000);
        }
    }, [message]);

    useEffect(()=>
    {
        if(shouldCleanTextBoxes === true)
        {
            setSponsor({sponsor_id: "", sponsor_name: "", sponsor_dob: "", sponsor_gender: "", sponsor_govt_id_type: "", sponsor_govt_id: "", sponsor_mobile: "", sponsor_email: "", sponsor_password: "", marital_status: "", sponsor_image: "", sponsor_address: "", sponsor_location_id: "", spouce_name: "", spouce_dob: "", spouce_govt_id_type: "", spouce_govt_id: "", spouce_mobile: "", spouce_image: "", donation_id: "", sponsor_created_at: "", sponsor_updated_at: ""});
        }
    }, [shouldCleanTextBoxes])

    const addRecord =()=>
    {
    var helper = new XMLHttpRequest();
    helper.onreadystatechange = ()=>{
        if(helper.readyState === 4 && helper.status === 200)
        {
            var result = JSON.parse(helper.responseText);
            if(result.affectedRows!==undefined)
            {
                if(result.affectedRows > 0)
                {
                   setmessage("Record Added Successfully!");
                   setShouldCleanTextBoxes(true);
                }
                else
                {
                   setmessage("We could not add the record.!")
                   setShouldCleanTextBoxes(false);
                }
            }
            else
            {
                setmessage("Something went wrong! Try Again!"); 
                setShouldCleanTextBoxes(false);  
            }
        }
    };
    helper.open("POST","http://http://localhost:4000/newsponsor");
    helper.setRequestHeader("Content-Type", "application/json")
    helper.setRequestHeader("x-auth-token",localStorage.getItem("user-token"))
    helper.send(JSON.stringify(sponsor));
    }

    const clearRecord =()=>
    {
        setSponsor({});
    }

    const requestAbortController = React.useRef(null);
    const [isLoading, setIsLoading] = React.useState(false);
    const [highlightedDays, setHighlightedDays] = React.useState([1, 2, 15]);

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

    React.useEffect(() => {
      fetchHighlightedDays(initialValue);
      // abort request on unmount
      return () => requestAbortController.current?.abort();
    }, []);

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

    const BootstrapInput = styled(InputBase)(({ theme }) => ({
        'label + &': {
          marginTop: theme.spacing(3),
        },
        '& .MuiInputBase-input': {
          borderRadius: 4,
          position: 'relative',
          backgroundColor: theme.palette.background.paper,
          border: '1px solid #ced4da',
          fontSize: 10,
          padding: '10px 26px 10px 12px',
          transition: theme.transitions.create(['border-color', 'box-shadow']),
          // Use the system font instead of the default Roboto font.
          fontFamily: [
            '-apple-system',
            'BlinkMacSystemFont',
            '"Segoe UI"',
            'Roboto',
            '"Helvetica Neue"',
            'Arial',
            'sans-serif',
            '"Apple Color Emoji"',
            '"Segoe UI Emoji"',
            '"Segoe UI Symbol"',
          ].join(','),
          '&:focus': {
            borderRadius: 4,
            borderColor: '#80bdff',
            boxShadow: '0 0 0 0.2rem rgba(0,123,255,.25)',
          },
        },
      }));
    
    return (
        <React.Fragment>
            <Box
              component="form"
              sx={{'& .MuiTextField-root': { m: 1, width: '60ch' },}}
              noValidate
              autoComplete="on"
            >
                <div>
                    <TextField
                        required
                        error
                        type='text'
                        id="sponsor_name"
                        label="Full Name"
                        placeholder='Full Name'
                        onChange={sponsorHandleChange}
                        helperText="Please enter First Name, Middle Name & Last Name"
                        style={{textAlignLast:'center'}}/>

                    <br/>

                    <TextField
                        required
                        error
                        type='date'
                        value={setSponsor.sponsor_dob}
                        id="sponsor_dob"
                        label="Date Of Birth"
                        placeholder='Date of Birth'
                        onChange={sponsorHandleChange}
                        helperText="Should be older than 18 Years"
                        style={{textAlignLast:'center'}}>

                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                          <DateCalendar
                            defaultValue={initialValue}
                            loading={isLoading}
                            onMonthChange={handleMonthChange}
                            renderLoading={() => <DayCalendarSkeleton />}/>
                        </LocalizationProvider>
                    </TextField>

                    <br/>

                    <FormControl sx={{border:1, borderColor:'red'}} style={{width:'60ch', alignItems:'center', marginBottom:'20px'}}>
                      <InputLabel>Gender</InputLabel>
                      <RadioGroup
                        label="Gender"
                        row
                        onChange={sponsorHandleChange} 
                        name="radio-buttons-group">
                        <FormControlLabel value="Female" control={<Radio id='sponsor_gender' {...genderControlProps('Female')} sx={{color: pink[800],'&.Mui-checked': {color: pink[600],},}}/>} label="Female" />
                        <FormControlLabel value="Male" control={<Radio id='sponsor_gender' {...genderControlProps('Male')} color="success"/>} label="Male" />
                        <FormControlLabel value="Other" control={<Radio id='sponsor_gender' {...genderControlProps('Other')} color="secondary"/>} label="Other" />
                      </RadioGroup>
                    </FormControl>

                    <br/>

                    <FormControl sx={{ m: 1 }} variant="standard">
                      <InputLabel id="demo-customized-select-label">Govt ID Type</InputLabel>
                      <Select
                        labelId="demo-customized-select-label"
                        id="sponsor_govt_id_type"
                        defaultValue={"Select Govt ID Type"}
                        onChange={sponsorHandleChange}
                        input={<BootstrapInput />}
                      >
                        <MenuItem value="Select Govt ID Type"><em>Select Govt ID Type</em></MenuItem>
                        <MenuItem value={"Aadhaar"}>Aadhaar</MenuItem>
                        <MenuItem value={"Driving_Licence"}>Driving_Licence</MenuItem>
                        <MenuItem value={"PAN"}>PAN</MenuItem>
                        <MenuItem value={"Passport"}>Passport</MenuItem>
                        <MenuItem value={"Voter_ID"}>Voter_ID</MenuItem>
                      </Select>
                    </FormControl>

                    <br/>

                    <OutlinedBox label='Govt ID'></OutlinedBox>
                    <Box sx={{border:1}} style={{width:'60ch'}}>
                        <Box style={{marginTop:'10pxpx', marginLeft:'3px', marginBottom:'10px'}}>
                            <OtpInput
                                style={{justifyContent: 'center', alignItems: 'center'}}
                                onChange={sponsorHandleChange}
                                id="sponsor_govt_id"
                                numInputs={numInputs}
                                renderSeparator={<span> - </span>}
                                renderInput={(props) => 
                                <input {...props} style={{width:'3ch', textAlign:'center', marginTop:'10px'}}/>}
                            />
                        </Box>
                    </Box>

                    <br/>

                    <TextField
                        required
                        error
                        type='tel'
                        id="sponsor_mobile"
                        label="Mobile Number"
                        placeholder='+91 XXXXX XXXXX'
                        onChange={sponsorHandleChange}
                        helperText="Please enter a valid mobile number"
                        style={{textAlignLast:'center'}}/>
        
                </div>
            </Box>
        </React.Fragment>
    )
}