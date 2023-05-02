import React, { Component } from 'react';
import { styled } from '@mui/material/styles';
import { Box, Paper, Grid, TextField, Radio, RadioGroup, FormControlLabel, FormControl, MenuItem, Select, InputLabel, InputBase, Typography, ButtonBase } from '@mui/material';
import { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider, DateCalendar, DayCalendarSkeleton } from '@mui/x-date-pickers';
import { pink } from '@mui/material/colors';
import OtpInput from 'react-otp-input';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import image from '../Asset/images/image.jpeg';

function getRandomNumber(min, max) {
  return Math.round(Math.random() * (max - min) + min);
}

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'white',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
}));

const ImageButton = styled(ButtonBase)(({ theme }) => ({
  position: 'relative',
  height: 200,
  [theme.breakpoints.down('sm')]: {
    width: '100% !important', // Overrides inline-style
    height: 100,
  },
  '&:hover, &.Mui-focusVisible': {
    zIndex: 1,
    '& .MuiImageBackdrop-root': {
      opacity: 0.15,
    },
    '& .MuiImageMarked-root': {
      opacity: 0,
    },
    '& .MuiTypography-root': {
      border: '4px solid currentColor',
    },
  },
}));

const ImageSrc = styled('span')({
  position: 'absolute',
  left: 0,
  right: 0,
  top: 0,
  bottom: 0,
  backgroundSize: 'cover',
  backgroundPosition: 'center 40%',
});

const Image = styled('span')(({ theme }) => ({
  position: 'absolute',
  left: 0,
  right: 0,
  top: 0,
  bottom: 0,
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  color: theme.palette.common.white,
}));

const ImageBackdrop = styled('span')(({ theme }) => ({
  position: 'absolute',
  left: 0,
  right: 0,
  top: 0,
  bottom: 0,
  backgroundColor: theme.palette.common.black,
  opacity: 0.4,
  transition: theme.transitions.create('opacity'),
}));

const ImageMarked = styled('span')(({ theme }) => ({
  height: 3,
  width: 18,
  backgroundColor: theme.palette.common.white,
  position: 'absolute',
  bottom: -2,
  left: 'calc(50% - 9px)',
  transition: theme.transitions.create('opacity'),
}));

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
const maritalStatus = [
  'Single', 'Married', 'Divorced', 'Break_UP', 'Widowed'
];

export default function AddSponsor() {
  const [sponsor, setSponsor] = useState({
    sponsor_id: "",
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
    sponsor_updated_at: ""
  });
  const [message, setmessage] = useState("");
  const [shouldCleanTextBoxes, setShouldCleanTextBoxes] = useState(false);
  const [selectedGender, setSelectedGender] = React.useState('Male');
  const [selectedGovtIdType, setSelectedGovtIdType] = React.useState('');
  const [numInputs, setNumInputs] = React.useState(16);

  const sponsorHandleChange = (args) => {
    console.log("args.target.id = " + args.target.id)
    var filledSponsorData = { ...sponsor };
    if (args.target.id === "sponsor_name") {
      filledSponsorData['sponsor_name'] = args.target.value;
    } if (args.target.id === "sponsor_dob") {
      filledSponsorData['sponsor_dob'] = args.target.value;
    }
    if (args.target.id === "sponsor_gender") {
      filledSponsorData['sponsor_gender'] = args.target.value;
    } if (args.target.id === "sponsor_govt_id_type") {
      console.log(selectedGovtIdType)
      if (selectedGovtIdType === "Aadhaar") {
        setNumInputs(12)
      } if (selectedGovtIdType === "Driving_Licence") {
        setNumInputs(16)
      } if (selectedGovtIdType === "PAN" || args.target.value === "Voter_ID") {
        setNumInputs(10)
      } if (selectedGovtIdType === "Passport") {
        setNumInputs(8)
      }
      filledSponsorData['sponsor_govt_id_type'] = args.target.value;
    } if (args.target.id === "sponsor_govt_id") {
      console.log("inside govt id " + args.target.value)
      filledSponsorData['sponsor_govt_id'] = args.target.value;
    } if (args.target.id === "sponsor_mobile") {
      filledSponsorData['sponsor_mobile'] = args.target.value;
    } if (args.target.id === "sponsor_email") {
      filledSponsorData['sponsor_email'] = args.target.value;
    } if (args.target.id === "sponsor_password") {
      filledSponsorData['sponsor_password'] = args.target.value;
    } if (args.target.id === "marital_status") {
      filledSponsorData['marital_status'] = args.target.value;
    } if (args.target.id === "sponsor_image") {
      filledSponsorData['sponsor_image'] = args.target.value;
    } if (args.target.id === "sponsor_address") {
      filledSponsorData['sponsor_address'] = args.target.value;
    } if (args.target.id === "sponsor_location_id") {
      filledSponsorData['sponsor_location_id'] = args.target.value;
    } if (args.target.id === "spouce_name") {
      filledSponsorData['spouce_name'] = args.target.value;
    } if (args.target.id === "spouce_dob") {
      filledSponsorData['spouce_dob'] = args.target.value;
    } if (args.target.id === "spouce_govt_id_type") {
      filledSponsorData['spouce_govt_id_type'] = args.target.value;
    } if (args.target.id === "spouce_govt_id") {
      filledSponsorData['spouce_govt_id'] = args.target.value;
    } if (args.target.id === "spouce_mobile") {
      filledSponsorData['spouce_mobile'] = args.target.value;
    } if (args.target.id === "spouce_image") {
      filledSponsorData['spouce_image'] = args.target.value;
    } if (args.target.id === "donation_id") {
      filledSponsorData['donation_id'] = args.target.value;
    } if (args.target.id === "sponsor_created_at") {
      filledSponsorData['sponsor_created_at'] = args.target.value;
    } if (args.target.id === "sponsor_updated_at") {
      filledSponsorData['sponsor_updated_at'] = args.target.value;
    }
    setSponsor(filledSponsorData);
    console.log(sponsor)
  }

  useEffect(() => {
    if (message !== "") {
      setTimeout(() => {
        setmessage("");
      }, 2000);
    }
  }, [message]);

  useEffect(() => {
    if (shouldCleanTextBoxes === true) {
      setSponsor({ sponsor_id: "", sponsor_name: "", sponsor_dob: "", sponsor_gender: "", sponsor_govt_id_type: "", sponsor_govt_id: "", sponsor_mobile: "", sponsor_email: "", sponsor_password: "", marital_status: "", sponsor_image: "", sponsor_address: "", sponsor_location_id: "", spouce_name: "", spouce_dob: "", spouce_govt_id_type: "", spouce_govt_id: "", spouce_mobile: "", spouce_image: "", donation_id: "", sponsor_created_at: "", sponsor_updated_at: "" });
    }
  }, [shouldCleanTextBoxes])

  const addRecord = () => {
    var helper = new XMLHttpRequest();
    helper.onreadystatechange = () => {
      if (helper.readyState === 4 && helper.status === 200) {
        var result = JSON.parse(helper.responseText);
        if (result.affectedRows !== undefined) {
          if (result.affectedRows > 0) {
            setmessage("Record Added Successfully!");
            setShouldCleanTextBoxes(true);
          }
          else {
            setmessage("We could not add the record.!")
            setShouldCleanTextBoxes(false);
          }
        }
        else {
          setmessage("Something went wrong! Try Again!");
          setShouldCleanTextBoxes(false);
        }
      }
    };
    helper.open("POST", "http://http://localhost:4000/newsponsor");
    helper.setRequestHeader("Content-Type", "application/json")
    helper.setRequestHeader("x-auth-token", localStorage.getItem("user-token"))
    helper.send(JSON.stringify(sponsor));
  }

  const clearRecord = () => {
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
      <Box className="appGridBox" sx={{ flexGrow: 1, width: '-webkit-fill-available' }}>
        <Grid className="appGridContainer" container spacing={3} style={{ marginLeft: '4%', marginTop: '1%' }} sx={{ width: '-webkit-fill-available' }}>
          <Item sx={{ width: '100%' }} style={{ marginRight: '1%' }}>
            <Box
              component="form"
              noValidate
              autoComplete="on"
            >
              <label>NEW SPONSOR</label>
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
                  style={{ textAlignLast: 'center', width:'80%', height:'100%'}}
                  inputProps={{ style: { fontSize: '150%', width:'100%' } }} // font size of input text
                  InputLabelProps={{ style: { fontSize: '150%', color:'white', backgroundColor:'black', borderRadius:'25%' } }}
                  FormHelperTextProps={{style: { fontSize: '70%', color: 'red', backgroundColor:'black', borderRadius:'50%' }}}  />

                <br />

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
                  style={{ textAlignLast: 'center' }}>

                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateCalendar
                      defaultValue={initialValue}
                      loading={isLoading}
                      onMonthChange={handleMonthChange}
                      renderLoading={() => <DayCalendarSkeleton />} />
                  </LocalizationProvider>
                </TextField>

                <br />

                <FormControl sx={{ border: 1, borderColor: 'red' }} style={{ width: '60ch', alignItems: 'center', marginBottom: '20px' }}>
                  <InputLabel>Gender</InputLabel>
                  <RadioGroup
                    label="Gender"
                    row
                    onChange={sponsorHandleChange}
                    name="radio-buttons-group">
                    <FormControlLabel value="Female" control={<Radio id='sponsor_gender' {...genderControlProps('Female')} sx={{ color: pink[800], '&.Mui-checked': { color: pink[600], }, }} />} label="Female" />
                    <FormControlLabel value="Male" control={<Radio id='sponsor_gender' {...genderControlProps('Male')} color="success" />} label="Male" />
                    <FormControlLabel value="Other" control={<Radio id='sponsor_gender' {...genderControlProps('Other')} color="secondary" />} label="Other" />
                  </RadioGroup>
                </FormControl>

                <br />

                <FormControl sx={{ m: 1 }} variant="standard" style={{ width: '60ch', alignItems: 'center', marginBottom: '20px' }}>
                  <InputLabel id="demo-customized-select-label">Govt ID Type</InputLabel>
                  <Select
                    labelId="demo-customized-select-label"
                    id="sponsor_govt_id_type"
                    defaultValue={"Select Govt ID Type"}
                    onChange={sponsorHandleChange}
                    style={{ width: '60ch', alignItems: 'center' }}
                  >
                    <MenuItem value="Select Govt ID Type"><em>Select Govt ID Type</em></MenuItem>
                    <MenuItem value={"Aadhaar"}>Aadhaar</MenuItem>
                    <MenuItem value={"Driving_Licence"}>Driving_Licence</MenuItem>
                    <MenuItem value={"PAN"}>PAN</MenuItem>
                    <MenuItem value={"Passport"}>Passport</MenuItem>
                    <MenuItem value={"Voter_ID"}>Voter_ID</MenuItem>
                  </Select>
                </FormControl>

                <br />
                <FormControl sx={{ m: 1 }} variant="standard" style={{ width: '60ch', alignItems: 'center', marginBottom: '20px' }}>
                  <Box sx={{ border: 1 }} style={{ width: '60ch' }}>
                    <Box style={{ marginTop: '10px', marginLeft: '3px', marginBottom: '10px' }}>
                      <OtpInput
                        style={{ justifyContent: 'center', alignItems: 'center' }}
                        onChange={sponsorHandleChange}
                        id="sponsor_govt_id"
                        numInputs={numInputs}
                        renderSeparator={<span> - </span>}
                        renderInput={(props) =>
                          <input {...props} style={{ width: '3ch', textAlign: 'center', marginTop: '10px' }} />}
                      />
                    </Box>
                  </Box>
                </FormControl>

                <br />

                <TextField
                  required
                  error
                  type='tel'
                  id="sponsor_mobile"
                  label="Mobile Number"
                  placeholder='+91 XXXXX XXXXX'
                  onChange={sponsorHandleChange}
                  helperText="Please enter a valid mobile number"
                  style={{ textAlignLast: 'center' }} />
                <br />
                <TextField
                  required
                  error
                  type='email'
                  id="sponsor_email"
                  label="Email ID"
                  placeholder='xxxxxxx.xxxxxxxxx@xxxxx.com'
                  onChange={sponsorHandleChange}
                  helperText="Please enter a valid Email address"
                  style={{ textAlignLast: 'center' }} />
                <br />
                <TextField
                  required
                  error
                  type='password'
                  id="sponsor_password"
                  label="Password"
                  placeholder='$@AbCd123_'
                  onChange={sponsorHandleChange}
                  helperText="Should contain a Special Character, One Uppercase Letter and a Numeric number atleast, length 7-18"
                  style={{ textAlignLast: 'center' }} />
                <br />
                <FormControl sx={{ m: 1 }} variant="standard" style={{ width: '60ch', alignItems: 'center', marginBottom: '20px' }}>
                  <InputLabel id="demo-simple-select-label">Marital Status</InputLabel>
                  <Select
                    labelId="demo-simple-select-label"
                    id="marital_status"
                    label="Marital Status"
                    defaultValue={'Select Marital Status'}
                    onChange={sponsorHandleChange}
                    style={{ width: '60ch', alignItems: 'center' }}
                  >
                    <MenuItem value={'Select Marital Status'}>{'Select Marital Status'}</MenuItem>
                    <MenuItem value={maritalStatus[0]}>{maritalStatus[0]}</MenuItem>
                    <MenuItem value={maritalStatus[1]}>{maritalStatus[1]}</MenuItem>
                    <MenuItem value={maritalStatus[2]}>{maritalStatus[2]}</MenuItem>
                    <MenuItem value={maritalStatus[3]}>{maritalStatus[3]}</MenuItem>
                    <MenuItem value={maritalStatus[4]}>{maritalStatus[4]}</MenuItem>
                  </Select>
                </FormControl>
                <br />
                <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
                  <ImageButton
                    focusRipple
                    key={image.title}
                    style={{
                      width: image.width,
                    }}
                  >
                    <ImageBackdrop className="MuiImageBackdrop-root" />
                    <Image>
                      <Typography
                        component="span"
                        variant="subtitle1"
                        color="inherit"
                        sx={{
                          position: 'relative',
                          p: 4,
                          pt: 2,
                          pb: (theme) => `calc(${theme.spacing(1)} + 6px)`,
                        }}
                      >
                        {image.title}
                        <ImageMarked className="MuiImageMarked-root" />
                      </Typography>
                    </Image>
                  </ImageButton>
                </Box>
                <br />
              </div>
            </Box>
          </Item>
        </Grid>
      </Box>
    </React.Fragment>
  )
}