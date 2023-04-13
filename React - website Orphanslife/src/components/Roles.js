import React from 'react';
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
        helper.open("GET","http://localhost:4000/roles");
        helper.send();
    }, []);

    return (
        <React.Fragment>
          <div className="table-wrap">
          <div className="table-responsive">
            <TableContainer component={Paper}>
              <Table class="table table-hover table-dark">
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
        </React.Fragment>
    );
}

export default Roles;