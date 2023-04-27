import React from 'react';
import { useEffect, useState } from "react";
import { alpha } from '@mui/material/styles';
import {Box, Table, TableBody, TableCell,TableContainer, TableHead, TablePagination, TableRow, TableSortLabel, Toolbar, Typography, Paper, Checkbox, IconButton, Tooltip, FormControlLabel, Switch, tableCellClasses} from '@mui/material'
import { styled } from '@mui/material/styles';
import DeleteIcon from '@mui/icons-material';
import FilterListIcon from '@mui/icons-material/FilterList';
import { visuallyHidden } from '@mui/utils';
import TablePaginationUnstyled, {
  tablePaginationUnstyledClasses as classes,
} from '@mui/base/TablePaginationUnstyled';
import { blue, grey } from '@mui/material/colors';
import '../Asset/Css/Body.css';

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

  const CustomTablePagination = styled(TablePaginationUnstyled)(
    ({ theme }) => `
    & .${classes.spacer} {
      display: none;
    }
  
    & .${classes.toolbar}  {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      gap: 10px;
      background-color: ${theme.palette.mode === 'dark' ? grey[900] : '#fff'};
  
      @media (min-width: 768px) {
        flex-direction: row;
        align-items: center;
      }
    }
  
    & .${classes.selectLabel} {
      margin: 0;
    }
  
    & .${classes.select}{
      padding: 2px;
      border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
      border-radius: 50px;
      background-color: transparent;
      color: ${theme.palette.mode === 'dark' ? grey[300] : grey[900]};
  
      &:hover {
        background-color: ${theme.palette.mode === 'dark' ? grey[800] : grey[50]};
      }
  
      &:focus {
        outline: 1px solid ${theme.palette.mode === 'dark' ? blue[400] : blue[200]};
      }
    }
  
    & .${classes.displayedRows} {
      margin: 0;
  
      @media (min-width: 768px) {
        margin-left: auto;
      }
    }
  
    & .${classes.actions} {
      padding: 2px;
      border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
      border-radius: 50px;
      text-align: center;
    }
  
    & .${classes.actions} > button {
      margin: 0 8px;
      border: transparent;
      border-radius: 2px;
      background-color: transparent;
      color: ${theme.palette.mode === 'dark' ? grey[300] : grey[900]};
  
      &:hover {
        background-color: ${theme.palette.mode === 'dark' ? grey[800] : grey[50]};
      }
  
      &:focus {
        outline: 1px solid ${theme.palette.mode === 'dark' ? blue[400] : blue[200]};
      }
    }
    `,
  );

function AdoptRequests()
{
    const [adoptrequests, setadoptrequests] =  useState([]);
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
  
    const handleChangePage = (event, newPage) => {
      setPage(newPage);
    };
  
    const handleChangeRowsPerPage = (event) => {
      setRowsPerPage(parseInt(event.target.value, 10));
      setPage(0);
    };

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
        helper.setRequestHeader("x-auth-token",localStorage.getItem("user-token"))
        helper.send();
    }, []);

    return (
        <React.Fragment>
          <div className="table-wrap">
                <div className="table-responsive">
                <TableContainer component={Paper}>
              <table className="table table-hover table-dark">
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
                  {(rowsPerPage > 0
                    ? adoptrequests.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) : adoptrequests).map((adoptreq) => (
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
        <tfoot>
          <tr>
            <CustomTablePagination
              rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
              colSpan={3}
              count={adoptrequests.length}
              rowsPerPage={rowsPerPage}
              page={page}
              slotProps={{
                select: {
                  'aria-label': 'rows per page',
                },
                actions: {
                  showFirstButton: true,
                  showLastButton: true,
                },
              }}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
          </tr>
        </tfoot>
        </div>
        <div>
          <a href="addAdoptReq" className='btn btn-success'>Add new Adopt Request</a>
        </div>
        </React.Fragment>
    );
}

export default AdoptRequests;