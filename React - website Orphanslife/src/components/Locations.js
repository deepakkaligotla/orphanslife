import React from 'react';
import { useState } from "react";
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

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
                           debugger
                           setlocations(result.data);
                        }
                        else
                        {
                           setmessage("Pincode NOT FOUND.!")
                        }
                }
            };
            const url = "http://orphanslife.in:4000/location/"+pin.pincode
            console.log(url)
            helper.open("POST",url);
            helper.setRequestHeader("Content-Type", "application/json");
            helper.setRequestHeader("Access-Control-Allow-Origin", "*");
            helper.setRequestHeader("Access-Control-Allow-Methods", "*");
            helper.setRequestHeader("Access-Control-Allow-Headers", "*");
            helper.send();
        }

    return (
        <div className="table-wrap">
        <hr></hr>
        <div id="search">
            <input type="text" name="pincode" placeholder={pin.pincode} id="findpincode" onChange={handleChange}/><br></br><br></br>
            <button className='btn btn-success' id="findbutton" type="button" onClick={find}>Haat lagaya toh maar dunga 🔪🤺🥊</button>
            <h6 style={{color: "orange"}}>{message}</h6>
        </div><br></br>
                <div className="table-responsive">

                <TableContainer component={Paper}>
              <table sx={{ minWidth: 700 }} aria-label="customized table">
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
                        <a href="/editLocation" class="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                        <a href="/deleteLocation" class="btn btn-primary">DEL</a>
                    </StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </table>
            </TableContainer>
        </div>
        <div>
        <a href="locations" class="btn btn-info">Back</a> || <a href="addLocation" class="btn btn-success">Location not found here 😱, ADD IT</a> || <a href="locations" class="btn btn-info">Next</a>
        </div>
        </div>
    );
}

export default Locations;