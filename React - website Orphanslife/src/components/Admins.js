import React from 'react';
import { useEffect, useState } from "react";
import {TableCell,tableCellClasses,TableContainer,Paper,Table,TableHead,TableRow,TableBody} from '@mui/material'
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

function Admins()
{
    const [admins, setadmins] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setadmins(result.data);
            }
        };
        helper.open("GET","http://localhost:4000/admins");
        helper.send();
    }, []);

    return (
        <div className="table-wrap">
          <div className="table-responsive">
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 750 }} aria-label="customized table">
                <TableHead>
                  <TableRow>
                    <StyledTableCell align="center">ID</StyledTableCell>
                    <StyledTableCell align="center">Name</StyledTableCell>
                    <StyledTableCell align="center">DOB</StyledTableCell>
                    <StyledTableCell align="center">Gender</StyledTableCell>
                    <StyledTableCell align="center">Govt ID Type</StyledTableCell>
                    <StyledTableCell align="center">Govt ID</StyledTableCell>
                    <StyledTableCell align="center">Mobile</StyledTableCell>
                    <StyledTableCell align="center">Email</StyledTableCell>
                    <StyledTableCell align="center">Password</StyledTableCell>
                    <StyledTableCell align="center">Address</StyledTableCell>
                    <StyledTableCell align="center">Location ID</StyledTableCell>
                    <StyledTableCell align="center">Role ID</StyledTableCell>
                    <StyledTableCell align="center">Orphange ID</StyledTableCell>
                    <StyledTableCell align="center">Image</StyledTableCell>
                    <StyledTableCell align="center">Created At</StyledTableCell>
                    <StyledTableCell align="center">Updated At</StyledTableCell>
                    <StyledTableCell align="center">Actions</StyledTableCell>

                  </TableRow>
                </TableHead>
            <TableBody>
                {admins.map((admin)=>(
                    <StyledTableRow key={admin.id}>
                        <StyledTableCell component="th" scope="role">{admin.admin_id}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_name}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_dob}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_gender}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_govt_id_type}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_govt_id}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_mobile}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_email}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_password}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_address}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_location_id}</StyledTableCell>
                        <StyledTableCell align="center">{admin.role_id}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_orphanage_id}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_image}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_created_at}</StyledTableCell>
                        <StyledTableCell align="center">{admin.admin_updated_at}</StyledTableCell>
                        <StyledTableCell align="center">
                            <a href="/editAdmin" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                            <a href="/deleteAdmin" className="btn btn-primary">DEL</a>
                        </StyledTableCell>
                    </StyledTableRow>
                ))}
            </TableBody>
        </Table>
        </TableContainer>
        </div>
        <div>
        <a href="addAdmin" className='btn btn-success'>Add new Admin</a>
        </div>
        </div>
    );
}

export default Admins;