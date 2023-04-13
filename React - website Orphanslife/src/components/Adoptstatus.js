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

function AdoptStatus()
{
    const [adoptstatus, setadoptstatus] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setadoptstatus(result.data);
            }
        };
        helper.open("GET","http://localhost:4000/adoptstatus");
        helper.send();
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
                    <StyledTableCell align="center">STATUS</StyledTableCell>
                    <StyledTableCell align="center">ACTION</StyledTableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                    {adoptstatus.map((adoptstate) => (
                    <StyledTableRow key={adoptstate.id}>
                    <StyledTableCell component="th" scope="adoptstate">{adoptstate.id}</StyledTableCell>
                    <StyledTableCell align="center">{adoptstate.status}</StyledTableCell>
                    <StyledTableCell align="center">
                                    <a href="/editAdoptReq" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                                    <a href="/deleteAdoptReq" className="btn btn-primary">DEL</a>
                    </StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </table>
            </TableContainer>
        </div>
        <div>
        <a href="addAdoptStatus" className="btn btn-success">Add new Adopt Status</a>
        </div>
        </div>
        </React.Fragment>
    );
}

export default AdoptStatus;