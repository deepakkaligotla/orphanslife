import React from 'react';
import axios from "axios";
import { useEffect, useState } from "react";
import {TableBody, TableCell,TableContainer, TableHead, TableRow, Paper, Checkbox, tableCellClasses} from '@mui/material'
import { styled } from '@mui/material/styles';
import TablePaginationUnstyled, {
  tablePaginationUnstyledClasses as classes,
} from '@mui/base/TablePaginationUnstyled';
import { blue, grey } from '@mui/material/colors';
import '../Asset/Css/Body.css';
import cryptoJs from 'crypto-js';

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

const Sponsors = () => {
  const loginAPI = 'http://localhost:4000/sponsors';
  const [sponsors, setSponsors] =  useState([{sponsor_id:'',sponsor_name:'',sponsor_dob:'',sponsor_gender:'',sponsor_govt_id_type:'',sponsor_govt_id:'',
    sponsor_mobile:'',sponsor_email:'',sponsor_password:'',marital_status:'',user_image:'',sponsor_address:'',
    sponsor_location_id:'',spouce_name:'',spouce_dob:'',spouce_govt_id_type:'',spouce_govt_id:'',spouce_mobile:'',
    spouce_image:'',donation_id:'',sponsor_created_at:'',sponsor_updated_at:''}]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [state, setState] = useState({
    list: [{sponsor_id:'',sponsor_name:'',sponsor_dob:'',sponsor_gender:'',sponsor_govt_id_type:'',sponsor_govt_id:'',
    sponsor_mobile:'',sponsor_email:'',sponsor_password:'',marital_status:'',user_image:'',sponsor_address:'',
    sponsor_location_id:'',spouce_name:'',spouce_dob:'',spouce_govt_id_type:'',spouce_govt_id:'',spouce_mobile:'',
    spouce_image:'',donation_id:'',sponsor_created_at:'',sponsor_updated_at:''}]
  })

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };
  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  function searchSponsorById(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_id!=null) {
        if (sponsor.sponsor_id.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByName(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_name!=null) {
        if (sponsor.sponsor_name.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByDob(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_dob!=null) {
        if (sponsor.sponsor_dob.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByGender(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_gender!=null) {
        if (sponsor.sponsor_gender.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByGovtIdType(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_govt_id_type!=null) {
        if (sponsor.sponsor_govt_id_type.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByGovtId(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_govt_id!=null) {
        if (sponsor.sponsor_govt_id.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByMobile(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_mobile!=null) {
        if (sponsor.sponsor_mobile.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByEmail(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_email!=null) {
        if (sponsor.sponsor_email.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByPassword(e) {
    const encryptedPassword = String(cryptoJs.MD5(e.target.value))
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_id!=null) {
        if (sponsor.sponsor_id.toString().includes(encryptedPassword)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByMaritalStatus(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.marital_status!=null) {
        if (sponsor.marital_status.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByUserImage(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.user_image!=null) {
        if (sponsor.user_image.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByAddress(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_address!=null) {
        if (sponsor.sponsor_address.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByLocationId(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_location_id!=null) {
        if (sponsor.sponsor_location_id.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorBySpouceName(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.spouce_name!=null) {
        if (sponsor.spouce_name.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorBySpouceDob(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.spouce_dob!=null) {
        if (sponsor.spouce_dob.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorBySpouceGovtIdType(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.spouce_govt_id_type!=null) {
        if (sponsor.spouce_govt_id_type.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorBySpouceGovtId(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.spouce_govt_id!=null) {
        if (sponsor.spouce_govt_id.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorBySpouceMobile(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.spouce_mobile!=null) {
        if (sponsor.spouce_mobile.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorBySpouceImage(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.spouce_image!=null) {
        if (sponsor.spouce_image.toLowerCase().toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByDonationId(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.donation_id!=null) {
        if (sponsor.donation_id.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByCreatedAt(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_created_at!=null) {
        if (sponsor.sponsor_created_at.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  function searchSponsorByUpdatedAt(e) {
    const results = sponsors.filter(sponsor => {
      if (e.target.value === "") return sponsors
      if(sponsor.sponsor_updated_at!=null) {
        if (sponsor.sponsor_updated_at.toString().includes(e.target.value)) return sponsor
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  useEffect(()=>{
    axios.get(loginAPI, { headers: {"x-auth-token" : `${localStorage.getItem('user-token')}`}}).then((response) => {
      if(Array.isArray(response.data.data)) {
        if(response.data.data[0]!=null) {
          setSponsors(response.data.data);
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

  return (
      <React.Fragment>
      <div className="table-wrap">
              <div className="table-responsive">
              <TableContainer component={Paper}>
            <table className="table table-hover table-dark">
              <TableHead>
                <TableRow>
                  <StyledTableCell align='center' style={{color:'white'}}>Select
                    <br/>
                    <Checkbox style={{color:'white'}}></Checkbox>
                  </StyledTableCell>
                  <StyledTableCell align="center">ID
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorById}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">NAME
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByName}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">DOB
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByDob}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">GENDER
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByGender}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">GOVT ID TYPE
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByGovtIdType}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">GOVT ID
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByGovtId}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">MOBILE
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByMobile}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">EMAIL
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByEmail}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">PASSWORD
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByPassword}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">MARITAL STATUS
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByMaritalStatus}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">USER IMAGE
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByUserImage}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">USER ADDRESS
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByAddress}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">LOCATION CITY
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByLocationId}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">SPOUCE NAME
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorBySpouceName}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">SPOUCE DOB
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorBySpouceDob}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">SPOUCE GOVT ID TYPE
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorBySpouceGovtIdType}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">SPOUCE GOVT ID
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorBySpouceGovtId}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">SPOUCE MOBILE
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorBySpouceMobile}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">SPOUCE IMAGE
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorBySpouceImage}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">DONATION ID
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByDonationId}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">Created At
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByCreatedAt}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">Updated At
                    <br/>
                    <input type='search' placeholder='Search here...!' style={{color: 'black', textAlign: 'center'}} onChange={searchSponsorByUpdatedAt}/>
                  </StyledTableCell>
                  <StyledTableCell align="center">ACTION</StyledTableCell>
                </TableRow>
              </TableHead>
                <TableBody>
                  {(rowsPerPage > 0
                  ? state.list.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) : state.list).map((sponsor) => (
                  <StyledTableRow key={sponsor.sponsor_id}>
                  <StyledTableCell align='center'><Checkbox></Checkbox></StyledTableCell>
                  <StyledTableCell align='center' component="th" scope="sponsor">{sponsor.sponsor_id}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_name}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_dob}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_gender}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_govt_id_type}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_govt_id}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_mobile}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_email}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_password}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.marital_status}</StyledTableCell>
                  <StyledTableCell align="center"><img className='potrait' alt={sponsor.user_image} src={sponsor.user_image}></img></StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_address}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_location_id}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.spouce_name}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.spouce_dob}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.spouce_govt_id_type}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.spouce_govt_id}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.spouce_mobile}</StyledTableCell>
                  <StyledTableCell align="center"><img className='potrait' alt={sponsor.spouce_image} src={sponsor.spouce_image}></img></StyledTableCell>
                  <StyledTableCell align="center">{sponsor.donation_id}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_created_at}</StyledTableCell>
                  <StyledTableCell align="center">{sponsor.sponsor_updated_at}</StyledTableCell>
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
        </tr>
      </tfoot>
      </div>
      <div>
        <a href="addSponsor" className="btn btn-success">Like to be a Sponsor?🥺</a>
      </div>
      </React.Fragment>
  );
}

export default Sponsors;