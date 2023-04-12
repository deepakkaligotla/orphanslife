import React from 'react';
import { useEffect, useState } from "react";
import {TableCell,tableCellClasses,TableContainer,Paper,TableHead,TableRow,TableBody} from '@mui/material'
import { styled } from '@mui/material/styles';

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

function Donations()
{
    const [donations, setdonations] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setdonations(result.data);
            }
        };
        helper.open("GET","http://localhost:4000/donations");
        helper.send();
    }, []);
    
    return (
        <div className="table-wrap">
                <div className="table-responsive">

                <TableContainer component={Paper}>
              <table sx={{ minWidth: 700 }} aria-label="customized table">
                <TableHead>
                  <TableRow>
                    <StyledTableCell align="center">ID</StyledTableCell>
                    <StyledTableCell align="center">AMOUNT</StyledTableCell>
                    <StyledTableCell align="center">PAYMENT STATUS</StyledTableCell>
                    <StyledTableCell align="center">USER ID</StyledTableCell>
                    <StyledTableCell align="center">Created At</StyledTableCell>
                    <StyledTableCell align="center">Updated At</StyledTableCell>
                    <StyledTableCell align="center">ACTION</StyledTableCell>
                  </TableRow>
                </TableHead>
                  <TableBody>
                    {donations.map((donation) => (
                    <StyledTableRow key={donation.id}>
                    <StyledTableCell component="th" scope="donation">{donation.id}</StyledTableCell>
                    <StyledTableCell align="center">{donation.amount}</StyledTableCell>
                    <StyledTableCell align="center">{donation.payment_status}</StyledTableCell>
                    <StyledTableCell align="center">{donation.user_id}</StyledTableCell>
                    <StyledTableCell align="center">{donation.created_at}</StyledTableCell>
                    <StyledTableCell align="center">{donation.updated_at}</StyledTableCell>
                    <StyledTableCell align="center">
                        <a href="/editDonation" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                        <a href="/deleteDonation" className="btn btn-primary">DEL</a>
                    </StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </table>
            </TableContainer>
        </div>
        <div>
        <a href="addDonation" className="btn btn-success">Here to Donate..💝🎁</a>
        </div>
        </div>
    );
}

export default Donations;