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

function Childs()
{
    const [childs, setchilds] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setchilds(result.data);
            }
        };
        helper.open("GET","http://orphanslife.in:4000/childs");
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
                    <StyledTableCell align="center">NAME</StyledTableCell>
                    <StyledTableCell align="center">DOB</StyledTableCell>
                    <StyledTableCell align="center">GENDER</StyledTableCell>
                    <StyledTableCell align="center">ADMITTED DATE</StyledTableCell>
                    <StyledTableCell align="center">LEAVE DATE</StyledTableCell>
                    <StyledTableCell align="center">MOTHER NAME</StyledTableCell>
                    <StyledTableCell align="center">FATHER NAME</StyledTableCell>
                    <StyledTableCell align="center">MOBILE</StyledTableCell>
                    <StyledTableCell align="center">CHILD IMAGE</StyledTableCell>
                    <StyledTableCell align="center">STATUS ID</StyledTableCell>
                    <StyledTableCell align="center">ADMIN ID</StyledTableCell>
                    <StyledTableCell align="center">Created At</StyledTableCell>
                    <StyledTableCell align="center">Updated At</StyledTableCell>
                    <StyledTableCell align="center">ACTION</StyledTableCell>
                  </TableRow>
                </TableHead>
                  <TableBody>
                    {childs.map((child) => (
                    <StyledTableRow key={child.child_id}>
                    <StyledTableCell component="th" scope="child">{child.child_id}</StyledTableCell>
                    <StyledTableCell align="center">{child.child_name}</StyledTableCell>
                    <StyledTableCell align="center">{child.dob}</StyledTableCell>
                    <StyledTableCell align="center">{child.gender}</StyledTableCell>
                    <StyledTableCell align="center">{child.admitted_date}</StyledTableCell>
                    <StyledTableCell align="center">{child.leave_date}</StyledTableCell>
                    <StyledTableCell align="center">{child.mother_name}</StyledTableCell>
                    <StyledTableCell align="center">{child.father_name}</StyledTableCell>
                    <StyledTableCell align="center">{child.mobile}</StyledTableCell>
                    <StyledTableCell align="center"><img className='potrait' src={child.child_image}></img></StyledTableCell>
                    <StyledTableCell align="center">{child.status_id}</StyledTableCell>
                    <StyledTableCell align="center">{child.admin_id}</StyledTableCell>
                    <StyledTableCell align="center">{child.created_at}</StyledTableCell>
                    <StyledTableCell align="center">{child.updated_at}</StyledTableCell>
                    <StyledTableCell align="center">
                                    <a href="/editChild" class="btn btn-primary"><span role="img" aria-label="Love">??????</span></a>
                                    <a href="/deleteChild" class="btn btn-primary">DEL</a></StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </Table>
            </TableContainer>
        </div>
        <div>
        <a href="addChild" className='btn btn-success'>Bacho aajaao..????????????????????????????????????????</a>
        </div>
        </div>
    );
}

export default Childs;