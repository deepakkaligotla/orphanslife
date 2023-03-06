import React from 'react';
import { useEffect, useState } from "react";
import {td, table} from './mainDiv.css'
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

const Sponsors = () => {
    const [sponsors, setsponsors] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setsponsors(result.data);
            }
        };
        helper.open("GET","http://orphanslife.in:4000/sponsors");
        helper.send();
    }, []);

    return (
        <React.Fragment>
        <div className="table-wrap">
                <div className="table-responsive">

                <TableContainer component={Paper}>
              <table sx={{ minWidth: 700 }} aria-label="customized table">
                <TableHead>
                  <TableRow>
                    <StyledTableCell align="center">ID</StyledTableCell>
                    <StyledTableCell align="center">NAME</StyledTableCell>
                    <StyledTableCell align="center">DOB</StyledTableCell>
                    <StyledTableCell align="center">GENDER</StyledTableCell>
                    <StyledTableCell align="center">GOVT ID TYPE</StyledTableCell>
                    <StyledTableCell align="center">GOVT ID</StyledTableCell>
                    <StyledTableCell align="center">MOBILE</StyledTableCell>
                    <StyledTableCell align="center">EMAIL</StyledTableCell>
                    <StyledTableCell align="center">PASSWORD</StyledTableCell>
                    <StyledTableCell align="center">MARITAL STATUS</StyledTableCell>
                    <StyledTableCell align="center">USER IMAGE</StyledTableCell>
                    <StyledTableCell align="center">USER ADDRESS</StyledTableCell>
                    <StyledTableCell align="center">LOCATION CITY</StyledTableCell>
                    <StyledTableCell align="center">SPOUCE NAME</StyledTableCell>
                    <StyledTableCell align="center">SPOUCE DOB</StyledTableCell>
                    <StyledTableCell align="center">SPOUCE GOVT ID TYPE</StyledTableCell>
                    <StyledTableCell align="center">SPOUCE GOVT ID</StyledTableCell>
                    <StyledTableCell align="center">SPOUCE MOBILE</StyledTableCell>
                    <StyledTableCell align="center">SPOUCE IMAGE</StyledTableCell>
                    <StyledTableCell align="center">DONATION ID</StyledTableCell>
                    <StyledTableCell align="center">Created At</StyledTableCell>
                    <StyledTableCell align="center">Updated At</StyledTableCell>
                    <StyledTableCell align="center">ACTION</StyledTableCell>
                  </TableRow>
                </TableHead>
                  <TableBody>
                    {sponsors.map((sponsor) => (
                    <StyledTableRow key={sponsor.id}>
                    <StyledTableCell component="th" scope="sponsor">{sponsor.id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.name}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.dob}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.gender}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.govt_id_type}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.govt_id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.mobile}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.email}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.password}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.marital_status}</StyledTableCell>
                    <StyledTableCell align="center"><img className='potrait' src={sponsor.user_image}></img></StyledTableCell>
                    <StyledTableCell align="center">{sponsor.user_address}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.city}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_name}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_dob}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_govt_id_type}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_govt_id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_mobile}</StyledTableCell>
                    <StyledTableCell align="center"><img className='potrait' src={sponsor.spouce_image}></img></StyledTableCell>
                    <StyledTableCell align="center">{sponsor.donation_id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.created_at}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.updated_at}</StyledTableCell>
                    <StyledTableCell align="center">
                        <a href="/editSponsor" class="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                        <a href="/deleteSponsor" class="btn btn-primary">DEL</a>
                    </StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </table>
            </TableContainer>
        </div>
        <div>
        <a href="addSponsor" class="btn btn-success">Like to be a Sponsor?🥺</a>
        </div>
        </div>
        </React.Fragment>
    );
}

export default Sponsors;