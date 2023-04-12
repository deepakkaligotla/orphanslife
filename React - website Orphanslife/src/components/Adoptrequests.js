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

function AdoptRequests()
{
    const [adoptrequests, setadoptrequests] =  useState([]);

    useEffect(()=>{
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                setadoptrequests(result.data);
            }
        };
        helper.open("GET","http://localhost:4000/adoptrequests");
        helper.send();
    }, []);

    return (
        <div className="table-wrap">
                <div className="table-responsive">
                <TableContainer component={Paper}>
              <table sx={{ minWidth: 700 }} aria-label="customized table">
                <TableHead>
                  <TableRow>
                    <StyledTableCell align="center">REQUEST NO</StyledTableCell>
                    <StyledTableCell align="center">USER ID</StyledTableCell>
                    <StyledTableCell align="center">ADMIN ID</StyledTableCell>
                    <StyledTableCell align="center">CHILD ID</StyledTableCell>
                    <StyledTableCell align="center">REASON</StyledTableCell>
                    <StyledTableCell align="center">REQUEST STAGE</StyledTableCell>
                    <StyledTableCell align="center">DATE OF REQUEST</StyledTableCell>
                    <StyledTableCell align="center">LAST CHECKED</StyledTableCell>
                    <StyledTableCell align="center">REQUEST COMMENT</StyledTableCell>
                    <StyledTableCell align="center">NEXT CHECK</StyledTableCell>
                    <StyledTableCell align="center">ADOPT STATUS</StyledTableCell>
                    <StyledTableCell align="center">ACTION</StyledTableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                    {adoptrequests.map((adoptreq) => (
                    <StyledTableRow key={adoptreq.req_no}>
                    <StyledTableCell component="th" scope="role">{adoptreq.req_no}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.user_id}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.admin_id}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.child_id}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.reason}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.req_stage}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.date_of_req}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.last_checked}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.req_comment}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.next_check}</StyledTableCell>
                    <StyledTableCell align="center">{adoptreq.adopted}</StyledTableCell>
                    <StyledTableCell align="center">
                                    <a href="/editAdoptReq" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                                    <a href="/deleteAdoptReq" className="btn btn-primary">DEL</a></StyledTableCell>
                    </StyledTableRow>
                  ))}
                  </TableBody>
              </table>
            </TableContainer>
        </div>
        <div>
        <a href="addAdoptReq" className='btn btn-success'>Add new Adopt Request</a>
        </div>
        </div>
    );
}

export default AdoptRequests;