import React from 'react';
import axios from "axios";
import PropTypes from 'prop-types';
import { alpha } from '@mui/material/styles';
import { useEffect, useState } from "react";
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TableSortLabel, Toolbar, Typography, Paper, Checkbox, tableCellClasses, IconButton, Tooltip } from '@mui/material'
import { styled } from '@mui/material/styles';
import TablePaginationUnstyled, {
  tablePaginationUnstyledClasses as classes,
} from '@mui/base/TablePaginationUnstyled';
import { blue, grey } from '@mui/material/colors';
import DeleteIcon from '@mui/icons-material/Delete';
import FilterListIcon from '@mui/icons-material/FilterList';
import { visuallyHidden } from '@mui/utils';
import '../Asset/Css/Body.css';

const columns = [
  { id: 'donation_id', label: 'DONATION ID', maxWidth: 40, numeric: true, disablePadding: false },
  { id: 'amount', label: 'AMOUNT', minWidth: 80, numeric: true, disablePadding: false },
  { id: 'payment_status', label: 'PAYMENT STATUS', minWidth: 170, numeric: false, disablePadding: false },
  { id: 'sponsor_id', label: 'SPONSOR ID', minWidth: 170, numeric: true, disablePadding: false },
  { id: 'created_at', label: 'CREATED AT', minWidth: 170, numeric: false, disablePadding: false },
  { id: 'updated_at', label: 'UPDATED AT', minWidth: 170, numeric: false, disablePadding: false },
  { id: 'action', label: 'ACTION', minWidth: 200 },
];

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

const DEFAULT_ORDER = 'asc';
const DEFAULT_ORDER_BY = 'donation_id';
const DEFAULT_ROWS_PER_PAGE = 5;

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: '#404040',
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

function DonationsTableHead(props) {
  const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort } =
    props;
  const createSortHandler = (newOrderBy) => (event) => {
    onRequestSort(event, newOrderBy);
  };

  return (
    <TableHead>
      <TableRow>
        <StyledTableCell padding="checkbox">
          <Checkbox
            indeterminate={numSelected > 0 && numSelected < rowCount}
            checked={rowCount > 0 && numSelected === rowCount}
            onChange={onSelectAllClick}
            style={{color:'white'}}
            inputProps={{
              'aria-label': 'select all desserts',
            }}
          />
        </StyledTableCell>
        {columns.map((headCell) => (
          <StyledTableCell
            key={headCell.id}
            align='center'
            sortDirection={orderBy === headCell.id ? order : false}
            width={headCell.minWidth}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <Box component="span" >
                  {order === 'desc' ? ' - desc' : '- asc'}
                </Box>
              ) : null}
            </TableSortLabel>
          </StyledTableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

DonationsTableHead.propTypes = {
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

function DonationsTableToolbar(props) {
  const { numSelected } = props;

  return (
    <Toolbar
      sx={{
        pl: { sm: 2 },
        pr: { xs: 1, sm: 1 },
        ...(numSelected > 0 && {
          bgcolor: (theme) =>
            alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
        }),
      }}
    >
      {numSelected > 0 ? (
        <Typography
          sx={{ flex: '1 1 100%' }}
          color="red"
          fontSize="15px"
          variant="subtitle1"
          component="div"
        >
          {numSelected} selected
        </Typography>
      ) : (
        <Typography
          sx={{ flex: '1 1 100%' }}
          variant="h6"
          fontSize="15px"
          id="tableTitle"
          component="div"
        >
          Selected
        </Typography>
      )}

      {numSelected > 0 ? (
        <Tooltip title="Delete">
          <IconButton>
            <DeleteIcon/>
          </IconButton>
        </Tooltip>
      ) : (
        <Tooltip title="Filter list">
          <IconButton>
            <FilterListIcon />
          </IconButton>
        </Tooltip>
      )}
    </Toolbar>
  );
}

DonationsTableToolbar.propTypes = {
  numSelected: PropTypes.number.isRequired,
};

function Donations(props) {
  const [order, setOrder] = React.useState(DEFAULT_ORDER);
  const [orderBy, setOrderBy] = React.useState(DEFAULT_ORDER_BY);
  const [selected, setSelected] = React.useState([]);
  const [visibleRows, setVisibleRows] = React.useState(null);
  const [donations, setDonations] = useState([{ donation_id: '', amount: '', payment_status: '', sponsor_id: '', created_at: '', updated_at: '' }]);
  const [state, setState] = useState({
    list: [{ donation_id: '', amount: '', payment_status: '', sponsor_id: '', created_at: '', updated_at: '' }],
  })
  const loginAPI = "http://localhost:4000/donations/";
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [dense, setDense] = React.useState(false);
  const [paddingHeight, setPaddingHeight] = React.useState(0);

  const handleRequestSort = React.useCallback(
    (event, newOrderBy) => {
      const isAsc = orderBy === newOrderBy && order === 'asc';
      const toggledOrder = isAsc ? 'desc' : 'asc';
      setOrder(toggledOrder);
      setOrderBy(newOrderBy);

      const sortedRows = stableSort(state.list, getComparator(toggledOrder, newOrderBy));
      const updatedRows = sortedRows.slice(
        page * rowsPerPage,
        page * rowsPerPage + rowsPerPage,
      );

      setVisibleRows(updatedRows);
    },
    [state.list, order, orderBy, page, rowsPerPage],
  );

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelected = state.list.map((n) => n.donation_id);
      setSelected(newSelected);
      return;
    }
    setSelected([]);
  };

  const handleClick = (event, name) => {
    const selectedIndex = selected.indexOf(name);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, name);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1),
      );
    }

    setSelected(newSelected);
  };

  const handleChangePage = React.useCallback(
    (event, newPage) => {
      setPage(newPage);

      const sortedRows = stableSort(state.list, getComparator(order, orderBy));
      const updatedRows = sortedRows.slice(
        newPage * rowsPerPage,
        newPage * rowsPerPage + rowsPerPage,
      );

      setVisibleRows(updatedRows);

      // Avoid a layout jump when reaching the last page with empty rows.
      const numEmptyRows =
        newPage > 0 ? Math.max(0, (1 + newPage) * rowsPerPage - state.list.length) : 0;

      const newPaddingHeight = (dense ? 33 : 53) * numEmptyRows;
      setPaddingHeight(newPaddingHeight);
    },
    [state.list, order, orderBy, dense, rowsPerPage],
  );

  const handleChangeRowsPerPage = React.useCallback(
    (event) => {
      const updatedRowsPerPage = parseInt(event.target.value, 5);
      setRowsPerPage(updatedRowsPerPage);

      setPage(0);

      const sortedRows = stableSort(state.list, getComparator(order, orderBy));
      const updatedRows = sortedRows.slice(
        0 * updatedRowsPerPage,
        0 * updatedRowsPerPage + updatedRowsPerPage,
      );

      setVisibleRows(updatedRows);

      // There is no layout jump to handle on the first page.
      setPaddingHeight(0);
    },
    [state.list, order, orderBy],
  );

  React.useEffect(() => {
    let rowsOnMount = stableSort(
      state.list,
      getComparator(DEFAULT_ORDER, DEFAULT_ORDER_BY),
    );

    rowsOnMount = rowsOnMount.slice(
      0 * DEFAULT_ROWS_PER_PAGE,
      0 * DEFAULT_ROWS_PER_PAGE + DEFAULT_ROWS_PER_PAGE,
    );

    setVisibleRows(rowsOnMount);
  }, [state.list]);

  useEffect(() => {
    axios.get(loginAPI, { headers: { "x-auth-token": `${localStorage.getItem('user-token')}` } }).then((response) => {
      if (Array.isArray(response.data.data)) {
        if (response.data.data[0] != null) {
          setDonations(response.data.data);
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


  function searchDonationTable(e) {
    const results = donations.filter(donation => {
      if (e.target.value === "") return donations
      if (donation.donation_id.toString().includes(e.target.value)) return donation
      if (donation.amount.toString().includes(e.target.value)) return donation
      if (donation.payment_status.toLowerCase().toString().includes(e.target.value.toString().toLowerCase())) return donation
      if (donation.sponsor_id.toString().includes(e.target.value.toString())) return donation
      if (donation.created_at.toLowerCase().toString().includes(e.target.value.toString().toLowerCase())) return donation
      if(donation.updated_at!=null) {
        if (donation.updated_at.toLowerCase().toString().includes(e.target.value.toString().toLowerCase())) return donation
      }
      return null
    })
    setState({
      query: e.target.value,
      list: results
    })
  }

  const isSelected = (id) => selected.indexOf(id) !== -1;

  return (
    <React.Fragment>
      <div className="table-wrap">
        <StyledTableCell align='center' style={{ fontSize: '20px' }}>Donations Table
          <input type='search' placeholder='Search in Donation!' style={{ color: 'black', textAlign: 'center', width: '180px', fontSize: '15px', marginLeft: '10px' }} onChange={searchDonationTable} />
        </StyledTableCell>
        <div className="table-responsive">
          <Box sx={{ width: '100%' }}>
            <Paper sx={{ width: '80%', overflow: 'hidden', border: 1 }}>
              <DonationsTableToolbar numSelected={selected.length} />
              <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table" sx={{ minWidth: 750 }}
                  aria-labelledby="tableTitle">
                  <DonationsTableHead
                    numSelected={selected.length}
                    order={order}
                    orderBy={orderBy}
                    onSelectAllClick={handleSelectAllClick}
                    onRequestSort={handleRequestSort}
                    rowCount={state.list.length}
                  />
                  <TableBody>
                    {visibleRows
                      ? visibleRows.map((row, index) => {
                        const isItemSelected = isSelected(row.donation_id);
                        const labelId = `Donations-table-checkbox-${index}`;
                        return (
                          <StyledTableRow hover
                            onClick={(event) => handleClick(event, row.donation_id)}
                            role="checkbox"
                            aria-checked={isItemSelected}
                            tabIndex={-1}
                            key={row.location_id}
                            selected={isItemSelected}
                            sx={{ cursor: 'pointer' }}>
                            <StyledTableCell padding="checkbox">
                              <Checkbox
                                checked={isItemSelected}
                                inputProps={{
                                  'aria-labelledby': labelId,
                                }}
                              />
                            </StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center" component="th" scope="donation">{row.donation_id}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.amount}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.payment_status}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.sponsor_id}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.created_at}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.updated_at}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">
                              <a href="/editDonation" className="btn btn-primary" style={{ marginRight: '10px' }}><span role="img" aria-label="Love">✏️</span></a>
                              <a href="/deleteDonation" className="btn btn-primary">DEL</a>
                            </StyledTableCell>
                          </StyledTableRow>
                        );
                      }) : null}
                    {paddingHeight > 0 && (
                      <TableRow
                        style={{
                          height: paddingHeight,
                        }}
                      >
                        <TableCell colSpan={6} />
                      </TableRow>
                    )}
                  </TableBody>
                </Table>
              </TableContainer>
              <br />
              <CustomTablePagination
                rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
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
              <br/>
            </Paper>
          </Box>
        </div>
      </div>
      <br />
      <div>
        <a href="addAdmin" className='btn btn-success'>New Donation 🤑, ADD IT</a>
      </div>
    </React.Fragment>
  );
}

export default Donations;