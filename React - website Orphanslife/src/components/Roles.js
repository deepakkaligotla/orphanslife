import React from 'react';
import { useEffect, useState } from "react";
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

function Roles()
{
    const [roles, setroles] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setroles(result.data);
            }
        };
        helper.open("GET","http://orphanslife.in:4000/roles");
        helper.send();
    }, []);

    return (
        <div className="table-wrap">
          <div className="table-responsive">
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 700 }} aria-label="customized table">
                <TableHead>
                  <TableRow>
                    <StyledTableCell align="center">ID</StyledTableCell>
                    <StyledTableCell align="center">Role</StyledTableCell>
                    <StyledTableCell align="center">Created At</StyledTableCell>
                    <StyledTableCell align="center">Updated At</StyledTableCell>
                  </TableRow>
                </TableHead>
                  <TableBody>
                    {roles.map((role) => (
                    <StyledTableRow key={role.id}>
                    <StyledTableCell component="th" scope="role">{role.id}</StyledTableCell>
                    <StyledTableCell align="center">{role.role}</StyledTableCell>
                    <StyledTableCell align="center">{role.created_at}</StyledTableCell>
                    <StyledTableCell align="center">{role.updated_at}</StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </Table>
            </TableContainer>
          </div>
        </div>
    );
}

export default Roles;