import React from 'react';
import axios from "axios";
import { useEffect, useState } from "react";
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

const Sponsors = () => {
  const loginAPI = 'http://192.168.0.14:4000/sponsors';
    const [sponsors, setSponsors] =  useState([]);

    useEffect(()=>{
      axios.get(loginAPI, { headers: {"x-auth-token" : `${localStorage.getItem('user-token')}`}}).then((response) => {
        if(Array.isArray(response.data.data)) {
          if(response.data.data[0]!=null) {
            setSponsors(response.data.data);
            return;
          }
        }
    }).catch((error) => {
        return;
    });
}, []);

    return (
        <React.Fragment>
        <div className="table-wrap">
                <div className="table-responsive">

                <TableContainer component={Paper}>
              <table class="table table-hover table-dark">
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
                    <StyledTableCell component="th" scope="sponsor">{sponsor.sponsor_id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_name}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_dob}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_gender}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_govt_id_type}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_govt_id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_mobile}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_email}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_password}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.marital_status}</StyledTableCell>
                    <StyledTableCell align="center"><img className='potrait' alt={sponsor.sponsor_name} src={sponsor.user_image}></img></StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_address}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_city}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_name}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_dob}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_govt_id_type}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_govt_id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.spouce_mobile}</StyledTableCell>
                    <StyledTableCell align="center"><img className='potrait' alt={sponsor.spouce_name} src={sponsor.spouce_image}></img></StyledTableCell>
                    <StyledTableCell align="center">{sponsor.donation_id}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_created_at}</StyledTableCell>
                    <StyledTableCell align="center">{sponsor.sponsor_updated_at}</StyledTableCell>
                    <StyledTableCell align="center">
                        <a href="/editSponsor" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                        <a href="/deleteSponsor" className="btn btn-primary">DEL</a>
                    </StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </table>
            </TableContainer>
        </div>
        <div>
        <a href="addSponsor" className="btn btn-success">Like to be a Sponsor?🥺</a>
        </div>
        </div>
        </React.Fragment>
    );
}

export default Sponsors;