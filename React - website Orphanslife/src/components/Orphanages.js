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

    @media (min-width: 100%) {
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

function Donations()
{
  const loginAPI = 'http://localhost:4000/orphanages';
  const [orphanages, setorphanages] =  useState([]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [selected, setSelected] = React.useState([]);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };
  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };
  function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
      return -1;
    }
    if (b[orderBy] > a[orderBy]) {
      return 1;
    }
    return 0;
  }
  function getComparator(order, orderBy) {
    return order === 'desc'
      ? (a, b) => descendingComparator(a, b, orderBy)
      : (a, b) => -descendingComparator(a, b, orderBy);
  }

  function stableSort(array, comparator) {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
      const order = comparator(a[0], b[0]);
      if (order !== 0) {
        return order;
      }
      return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
  }

  const isSelected = (name) => selected.indexOf(name) !== -1;

  useEffect(()=>{
    axios.get(loginAPI, { headers: {"x-auth-token" : `${localStorage.getItem('user-token')}`}}).then((response) => {
      if(Array.isArray(response.data.data)) {
        if(response.data.data[0]!=null) {
          setorphanages(response.data.data);
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
              <table className="table table-hover table-dark">
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
                  {(rowsPerPage > 0
                  ? orphanages.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) : orphanages).map((orphanage) => 
                    (<StyledTableRow key={orphanage.id}>
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
          <tfoot>
            <tr>
              <CustomTablePagination
                rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                colSpan={3}
                count={orphanages.length}
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
        <a href="addSponsor" className="btn btn-success">New Orphanage? 🏡🏠 Add it</a>
      </div>
      </React.Fragment>
  );
}

export default Donations;