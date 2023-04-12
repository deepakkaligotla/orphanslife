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
    const [orphanages, setorphanages] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setorphanages(result.data);
            }
        };
        helper.open("GET","http://localhost:4000/orphanages");
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
                    <StyledTableCell align="center">TYPE</StyledTableCell>
                    <StyledTableCell align="center">ADDRESS</StyledTableCell>
                    <StyledTableCell align="center">LOCATION ID</StyledTableCell>
                    <StyledTableCell align="center">CONTACT PERSON</StyledTableCell>
                    <StyledTableCell align="center">MOBILE</StyledTableCell>
                    <StyledTableCell align="center">PHONE</StyledTableCell>
                    <StyledTableCell align="center">EMAIL</StyledTableCell>
                    <StyledTableCell align="center">IN HOME</StyledTableCell>
                    <StyledTableCell align="center">ADOPTABLE</StyledTableCell>
                    <StyledTableCell align="center">BOYS</StyledTableCell>
                    <StyledTableCell align="center">GIRLS</StyledTableCell>
                    <StyledTableCell align="center">ORPHANAGE IMAGE</StyledTableCell>
                    <StyledTableCell align="center">Created At</StyledTableCell>
                    <StyledTableCell align="center">Updated At</StyledTableCell>
                    <StyledTableCell align="center">ACTION</StyledTableCell>
                  </TableRow>
                </TableHead>
                  <TableBody>
                    {orphanages.map((orphanage) => (
                    <StyledTableRow key={orphanage.id}>
                    <StyledTableCell component="th" scope="orphanage">{orphanage.id}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.type}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.address}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.location_id}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.contact_person}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.mobile}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.phone}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.email}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.in_home}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.adoptable}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.boys}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.girls}</StyledTableCell>
                    <StyledTableCell align="center"><img className='potrait' alt={orphanage.type} src={orphanage.orphanage_image}></img></StyledTableCell>
                    <StyledTableCell align="center">{orphanage.created_at}</StyledTableCell>
                    <StyledTableCell align="center">{orphanage.updated_at}</StyledTableCell>
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
        <a href="addSponsor" className="btn btn-success">New Orphanage? 🏡🏠 Add it</a>
        </div>
        </div>
    );
}

export default Donations;