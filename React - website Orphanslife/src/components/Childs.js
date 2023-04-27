import React from 'react';
import axios from "axios";
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

function Childs()
{
  const loginAPI = 'http://localhost:4000/childs';
  const [childs, setchilds] =  useState([]);
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
    axios.get(loginAPI, { headers: {"x-auth-token" : `${localStorage.getItem('user-token')}`}}).then((response) => {
      if(Array.isArray(response.data.data)) {
        if(response.data.data[0]!=null) {
          setchilds(response.data.data);
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
            <Table className="table table-hover table-dark">
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
                {(rowsPerPage > 0
                  ? childs.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) : childs).map((child) => (
                  <StyledTableRow key={child.child_id}>
                  <StyledTableCell component="th" scope="child">{child.child_id}</StyledTableCell>
                  <StyledTableCell align="center">{child.child_name}</StyledTableCell>
                  <StyledTableCell align="center">{child.child_dob}</StyledTableCell>
                  <StyledTableCell align="center">{child.child_gender}</StyledTableCell>
                  <StyledTableCell align="center">{child.admitted_date}</StyledTableCell>
                  <StyledTableCell align="center">{child.leave_date}</StyledTableCell>
                  <StyledTableCell align="center">{child.mother_name}</StyledTableCell>
                  <StyledTableCell align="center">{child.father_name}</StyledTableCell>
                  <StyledTableCell align="center">{child.child_mobile}</StyledTableCell>
                  <StyledTableCell align="center"><img className='potrait' alt={child.child_name} src={child.child_image}></img></StyledTableCell>
                  <StyledTableCell align="center">{child.status_id}</StyledTableCell>
                  <StyledTableCell align="center">{child.admin_id}</StyledTableCell>
                  <StyledTableCell align="center">{child.child_created_at}</StyledTableCell>
                  <StyledTableCell align="center">{child.child_updated_at}</StyledTableCell>
                  <StyledTableCell align="center">
                                  <a href="/editChild" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                                  <a href="/deleteChild" className="btn btn-primary">DEL</a></StyledTableCell>
                  </StyledTableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
        <tfoot>
          <tr>
          <CustomTablePagination
            rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
            colSpan={3}
            count={childs.length}
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
        <div>
          <a href="addChild" className='btn btn-success'>Bacho aajaao..👦🏻🧒🏼👧🏻👧🏽👶🏻</a>
        </div>
      </div>
    </React.Fragment>
  );
}

export default Childs;