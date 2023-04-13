import React from 'react';
import { useState } from "react";
import { alpha } from '@mui/material/styles';
import {Box, Table, TableBody, TableCell,TableContainer, TableHead, TablePagination, TableRow, TableSortLabel, Toolbar, Typography, Paper, Checkbox, IconButton, Tooltip, FormControlLabel, Switch, tableCellClasses} from '@mui/material'
import { styled } from '@mui/material/styles';
import DeleteIcon from '@mui/icons-material';
import FilterListIcon from '@mui/icons-material/FilterList';
import { visuallyHidden } from '@mui/utils';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
}));

function Locations()
{
    const [locations, setlocations] =  useState([]);
    const [message, setmessage] = useState("");
    const [pin, setpin] = useState({pincode:"411057"});

    var copypincode = {...pin};

    const handleChange = (args)=>{
        copypincode[args.target.name] = args.target.value;
        console.log(copypincode)
        setpin(copypincode)
        }

    const find = () => {
            var helper = new XMLHttpRequest();
            helper.onreadystatechange = ()=>{
                if(helper.readyState === 4 && helper.status === 200)
                {
                    var result = JSON.parse(helper.responseText);
                        if(result.data.length > 0)
                        {
                           setmessage("Pincode found!");
                           setlocations(result.data);
                        }
                        else
                        {
                           setmessage("Pincode NOT FOUND.!")
                        }
                }
            };
            const url = "http://localhost:4000/location/"+pin.pincode
            console.log(url)
            helper.open("POST",url);
            helper.setRequestHeader("Content-Type", "application/json");
            helper.setRequestHeader("Access-Control-Allow-Origin", "*");
            helper.setRequestHeader("Access-Control-Allow-Methods", "*");
            helper.setRequestHeader("Access-Control-Allow-Headers", "*");
            helper.send();
        }

    return (
        <React.Fragment>
          <div className="table-wrap">
        <hr></hr>
        <div id="search">
            <input type="text" name="pincode" placeholder={pin.pincode} id="findpincode" onChange={handleChange}/><br></br><br></br>
            <button className='btn btn-success' id="findbutton" type="button" onClick={find}>Haat lagaya toh maar dunga 🔪🤺🥊</button>
            <h6 style={{color: "orange"}}>{message}</h6>
        </div><br></br>
                <div className="table-responsive">

                <TableContainer component={Paper}>
              <table class="table table-hover table-dark">
                <TableHead>
                  <TableRow>
                    <StyledTableCell align="center">ID</StyledTableCell>
                    <StyledTableCell align="center">PINCODE</StyledTableCell>
                    <StyledTableCell align="center">AREA</StyledTableCell>
                    <StyledTableCell align="center">CITY</StyledTableCell>
                    <StyledTableCell align="center">DISTRICT</StyledTableCell>
                    <StyledTableCell align="center">STATE</StyledTableCell>
                    <StyledTableCell align="center">ACTION</StyledTableCell>
                  </TableRow>
                </TableHead>
                  <TableBody>
                    {locations.map((location) => (
                    <StyledTableRow key={location.id}>
                    <StyledTableCell component="th" scope="location">{location.id}</StyledTableCell>
                    <StyledTableCell align="center">{location.pincode}</StyledTableCell>
                    <StyledTableCell align="center">{location.area}</StyledTableCell>
                    <StyledTableCell align="center">{location.city}</StyledTableCell>
                    <StyledTableCell align="center">{location.district}</StyledTableCell>
                    <StyledTableCell align="center">{location.state}</StyledTableCell>
                    <StyledTableCell align="center">
                        <a href="/editLocation" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                        <a href="/deleteLocation" className="btn btn-primary">DEL</a>
                    </StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </table>
            </TableContainer>
        </div>
        <div>
        <a href="locations" className="btn btn-info">Back</a> || <a href="addLocation" className="btn btn-success">Location not found here 😱, ADD IT</a> || <a href="locations" className="btn btn-info">Next</a>
        </div>
        </div>
        </React.Fragment>
    );
}

export default Locations;