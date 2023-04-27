import React from 'react';
import axios from "axios";
import { useEffect, useState } from "react";
import {Table, TableBody, TableCell,TableContainer, TableHead, TableRow, Paper, Checkbox, tableCellClasses} from '@mui/material'
import { styled } from '@mui/material/styles';
import TablePaginationUnstyled, {
  tablePaginationUnstyledClasses as classes,
} from '@mui/base/TablePaginationUnstyled';
import { blue, grey } from '@mui/material/colors';
import '../Asset/Css/Body.css';

const columns = [
  { id: 'admin_id', label: 'ADMIN ID', maxWidth: 60 },
  { id: 'admin_name', label: 'ADMIN NAME', maxWidth: 60 },
  { id: 'admin_dob', label: 'ADMIN DOB', maxWidth: 60 },
  { id: 'admin_gender', label: 'ADMIN GENDER', maxWidth: 60 },
  { id: 'admin_govt_id_type', label: 'ADMIN GOVT ID TYPE', maxWidth: 60 },
  { id: 'admin_govt_id', label: 'ADMIN GOVT ID', maxWidth: 60 },
  { id: 'admin_mobile', label: 'ADMIN MOBILE', maxWidth: 60 },
  { id: 'admin_email', label: 'ADMIN EMAIL', maxWidth: 60 },
  { id: 'admin_password', label: 'ADMIN PASSWORD', maxWidth: 60 },
  { id: 'admin_address', label: 'ADMIN ADDRESS', maxWidth: 60 },
  { id: 'admin_location_id', label: 'ADMIN LOCATION ID', maxWidth: 60 },
  { id: 'role_id', label: 'ROLE ID', maxWidth: 60 },
  { id: 'admin_orphanage_id', label: 'ADMIN ORPHANAGE ID ', maxWidth: 60 },
  { id: 'admin_image', label: 'ADMIN IMAGE', maxWidth: 60 },
  { id: 'admin_created_at', label: 'CREATED AT', maxWidth: 60 },
  { id: 'admin_updated_at', label: 'UPDATED AT', maxWidth: 60 },
  { id: 'action', label: 'ACTION', minWidth: 150},
];

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
    fontSize: 14,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  '&:last-child td, &:last-child th': {
    border: 0,
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

function Admins()
{
  const loginAPI = 'http://localhost:4000/admins';
  const [admins, setadmins] =  useState([{admin_id:'',admin_name:'',admin_dob:'',admin_gender:'',admin_govt_id_type:'',admin_govt_id:'',admin_mobile:'',
                                          admin_email:'',admin_password:'',admin_address:'',admin_location_id:'',role_id:'',admin_orphanage_id:'',admin_image:'',
                                        admin_created_at:'', admin_updated_at:''}]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [state, setState] = useState({
    list: [{admin_id:'',admin_name:'',admin_dob:'',admin_gender:'',admin_govt_id_type:'',admin_govt_id:'',admin_mobile:'',
            admin_email:'',admin_password:'',admin_address:'',admin_location_id:'',role_id:'',admin_orphanage_id:'',admin_image:'',
            admin_created_at:'', admin_updated_at:''}]
  })

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
              setadmins(response.data.data);
              setState({
                list: response.data.data
              })
              return;
            }
          }
      }).catch((error) => {
          return;
      });
  }, []);

  function searchAdminTable(e) {
    const results = admins.filter(admin => {
      if (e.target.value === "") return admins
      if (admin.admin_id.toString().includes(e.target.value)) return admin
      if (admin.admin_name.toString().toLowerCase().includes(e.target.value.toString().toLowerCase())) return admin
      if (admin.admin_dob.toString().includes(e.target.value)) return admin
      if (admin.admin_gender.toString().toLowerCase().includes(e.target.value.toString().toLowerCase())) return admin
      if (admin.admin_govt_id_type.toString().toLowerCase().includes(e.target.value.toString().toLowerCase())) return admin
      if (admin.admin_govt_id.toString().toLowerCase().includes(e.target.value.toString().toLowerCase())) return admin
      if (admin.admin_mobile.toString().includes(e.target.value)) return admin
      if (admin.admin_email.toString().toLowerCase().includes(e.target.value.toString().toLowerCase())) return admin
      if(admin.admin_address!=null) {
        if (admin.admin_address.toString().toLowerCase().includes(e.target.value.toString().toLowerCase())) return admin
      }
      if(admin.admin_location_id!=null) {
        if (admin.admin_location_id.toString().includes(e.target.value)) return admin
      }
      if (admin.role_id.toString().includes(e.target.value)) return admin
      if(admin.admin_orphanage_id!=null) {
        if (admin.admin_orphanage_id.toString().includes(e.target.value)) return admin
      }
      if(admin.admin_image!=null) {
        if (admin.admin_image.toString().includes(e.target.value)) return admin
      }
      if (admin.admin_created_at.toString().includes(e.target.value)) return admin
      if(admin.admin_updated_at!=null) {
        if (admin.admin_updated_at.toString().includes(e.target.value)) return admin
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  return (
      <React.Fragment>
        <div className="table-wrap">
        <hr></hr>
        <StyledTableCell align='center' style={{fontSize:'20px'}}>Admins Table
          <input type='search' placeholder='Search in Admins!' style={{color: 'black', textAlign: 'center', width:'180px', fontSize:'15px', marginLeft:'10px'}} onChange={searchAdminTable}/>
        </StyledTableCell>
          <div className="table-responsive">
          <Paper sx={{ width: '80%', overflow: 'hidden', border: 1}}>
            <TableContainer sx={{ maxHeight: 440}}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                  <StyledTableCell align='center' sx={{ border: 1 }}><Checkbox style={{color:'white'}}></Checkbox></StyledTableCell>
                  {columns.map((column) => (
                <StyledTableCell
                  sx={{ border: 1 }}
                  key={column.id}
                  align='center'
                  style={{ minWidth: column.minWidth, maxWidth: column.maxWidth }}
                >
                  {column.label}
                </StyledTableCell>
              ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {(rowsPerPage > 0
                      ? state.list.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) : state.list).map((admin)=>(
                      <StyledTableRow key={admin.admin_id}>
                        <StyledTableCell sx={{ border: 1 }} align='center'><Checkbox></Checkbox></StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center" component="th" scope="role">{admin.admin_id}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_name}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_dob}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_gender}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_govt_id_type}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_govt_id}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_mobile}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_email}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_password}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_address}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_location_id}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.role_id}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_orphanage_id}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center"><img className='potrait' alt={admin.admin_name} src={admin.admin_image}></img></StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_created_at}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">{admin.admin_updated_at}</StyledTableCell>
                        <StyledTableCell sx={{ border: 1 }} align="center">
                            <a href="/editAdmin" className="btn btn-primary"><span role="img" aria-label="Love">✏️</span></a>
                            <a href="/deleteAdmin" className="btn btn-primary">DEL</a>
                        </StyledTableCell>
                      </StyledTableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <br/>
            <CustomTablePagination
              rowsPerPageOptions={[5, 25, 100, { label: 'All', value: -1 }]}
              count={state.list.length}
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
            </Paper>
          </div>
        </div>
      <div>
        <a href="addAdmin" className='btn btn-success'>Add new Admin</a>
      </div>
      </React.Fragment>
  );
}

export default Admins;