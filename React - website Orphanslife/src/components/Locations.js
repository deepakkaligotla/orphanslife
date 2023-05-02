import React, {useRef} from 'react';
import axios from "axios";
import PropTypes from 'prop-types';
import { alpha } from '@mui/material/styles';
import { useEffect, useState } from "react";
import { Box, TextField, Grid, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TableSortLabel, Toolbar, Typography, Paper, Checkbox, tableCellClasses, IconButton, Tooltip, TableFooter } from '@mui/material'
import { styled } from '@mui/material/styles';
import TablePaginationUnstyled, {
  tablePaginationUnstyledClasses as classes,
} from '@mui/base/TablePaginationUnstyled';
import { blue, grey } from '@mui/material/colors';
import DeleteIcon from '@mui/icons-material/Delete';
import FilterListIcon from '@mui/icons-material/FilterList';
import '../Asset/Css/Body.css';

const columns = [
  { id: 'location_id', label: 'LOCATION ID', numeric: true, disablePadding: false },
  { id: 'pincode', label: 'PINCODE', numeric: true, disablePadding: false },
  { id: 'area', label: 'AREA', numeric: false, disablePadding: false },
  { id: 'city', label: 'CITY', numeric: false, disablePadding: false },
  { id: 'district', label: 'DISTRICT', numeric: false, disablePadding: false },
  { id: 'state', label: 'STATE', numeric: false, disablePadding: false },
  { id: 'action', label: 'ACTION' },
];

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'white',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
}));

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
const DEFAULT_ORDER_BY = 'location_id';
const DEFAULT_ROWS_PER_PAGE = 100;

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: "#404040",
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
    flex-direction: row;
    justify-content: center;
    gap: 1%;
    margin-top: 1%;
    width: -webkit-fill-available;
    background-color: ${theme.palette.mode === 'dark' ? 'white' : 'black'};

    @media (min-width: 2%) {
      flex-direction: row;
      align-items: center;
    }
  }

  & .${classes.selectLabel} {
    margin: 0;
    font-size: 150%;
    color: white;
    margin-top: 1%;
    width: 10%;
  }

  & .${classes.select}{
    padding: 1%;
    border: 1% solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    border-radius: 10%;
    background-color: transparent;
    font-size: 150%;
    color: white;
    color: ${theme.palette.mode === 'dark' ? grey[300] : 'white'};

    &:hover {
      background-color: ${theme.palette.mode === 'dark' ? 'red' : '#99ff99'};
      color: ${theme.palette.mode === 'dark' ? grey[300] : 'red'};
    }

    &:focus {
      outline: 1% solid ${theme.palette.mode === 'dark' ? blue[400] : blue[200]};
    }
  }

  & .${classes.displayedRows} {
    margin: 0;
    width: 10%;
    font-size: 150%;
    color: white;
    margin-top: 1%;

    @media (min-width: -webkit-fill-available) {
      margin-left: auto;
    }
  }

  & .${classes.actions} {
    padding: 1%;
    border: 1% solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    border-radius: 20%;
    text-align: center;
    font-size: 200%;
    width: 20%;
  }

  & .${classes.actions} > button {
    margin: 0 1%;
    border: transparent;
    border-radius: 20%;
    background-color: transparent;
    color: ${theme.palette.mode === 'dark' ? grey[300] : 'green'};

    &:hover {
      background-color: ${theme.palette.mode === 'dark' ? grey[800] : 'yellow'};
    }

    &:focus {
      outline: 1% solid ${theme.palette.mode === 'dark' ? blue[400] : 'red'};
    }
  }
  `,
);

function LocationsTableHead(props) {
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
            style={{ color: 'white' }}
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
            style={{ fontSize: '100%' }}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <Box component="span">
                  {order === 'desc' ? ' - desc' : ' - asc'}
                </Box>
              ) : null}
            </TableSortLabel>
          </StyledTableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

LocationsTableHead.propTypes = {
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

function LocationsTableToolbar(props) {
  const { numSelected } = props;

  return (<Item>
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
          fontSize="100%"
          variant="subtitle1"
          component="div"
        >
          {numSelected} selected
        </Typography>
      ) : (
        <Typography
          sx={{ flex: '1 1 100%' }}
          variant="h6"
          fontSize="100%"
          id="tableTitle"
          component="div"
        >
          Selected
        </Typography>
      )}

      {numSelected > 0 ? (
        <Tooltip title="Delete">
          <IconButton>
            <DeleteIcon />
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
  </Item>
  );
}

LocationsTableToolbar.propTypes = {
  numSelected: PropTypes.number.isRequired,
};

function Locations(props) {
  const [order, setOrder] = React.useState(DEFAULT_ORDER);
  const [orderBy, setOrderBy] = React.useState(DEFAULT_ORDER_BY);
  const [selected, setSelected] = React.useState([]);
  const [visibleRows, setVisibleRows] = React.useState(null);
  const [locations, setlocations] = useState([{ location_id: '', pincode: '', area: '', city: '', district: '', state: '' }]);
  const [state, setState] = useState({
    list: [{ location_id: '', pincode: '', area: '', city: '', district: '', state: '' }],
  })
  const loginAPI = "http://localhost:4000/locations/";
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(100);
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
      const newSelected = state.list.map((n) => n.location_id);
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
      const updatedRowsPerPage = parseInt(event.target.value, 10);
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
          setlocations(response.data.data);
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

  function SearchLocationTable(event) {
    const results = locations.filter(location => {
      if (event.target.value === "") return locations
      if (location.location_id.toString().includes(event.target.value)) return location
      if (location.pincode.toString().includes(event.target.value)) return location
      if (location.area.toLowerCase().toString().includes(event.target.value.toString().toLowerCase())) return location
      if (location.city.toLowerCase().toString().includes(event.target.value.toString().toLowerCase())) return location
      if (location.district.toLowerCase().toString().includes(event.target.value.toString().toLowerCase())) return location
      if (location.state.toLowerCase().toString().includes(event.target.value.toString().toLowerCase())) return location
      return null
    })

    setState({
      list: results
    })
  }

  const isSelected = (id) => selected.indexOf(id) !== -1;

  return (
    <React.Fragment>
      <Box className="appGridBox" sx={{ flexGrow: 1, width: '-webkit-fill-available' }}>
        <Grid className="appGridContainer" container spacing={3} style={{ marginLeft: '2%', marginTop: '1%' }} sx={{ width: '-webkit-fill-available' }}>
          <Item sx={{ width: '-webkit-fill-available' }} style={{ marginRight: '1%' }}>
            <label>Search in Locations Table</label>
            <br/>
            <TextField
              type='search'
              label="SEARCH IN LOCATIONS TABLE"
              placeholder='SEARCH IN LOCATIONS TABLE!'
              onChange={SearchLocationTable}
              helperText="Search Pincode, Area, City, District, State in Location Table"
              style={{ textAlignLast: 'center', fontSize: '180%', backgroundColor: 'grey', width: '50%', marginLeft: '1%' }} />

            <div className="table-wrap">
              <div className="table-responsive">
                <Box sx={{ width: '-webkit-fill-available', marginLeft: '8%' }}>
                  <Paper sx={{ width: '-webkit-fill-available', overflow: 'hidden', border: 1 }}>
                    <LocationsTableToolbar numSelected={selected.length} />
                    <TableContainer sx={{ height: '450px' }}>
                      <Table stickyHeader aria-label="sticky table"
                        aria-labelledby="tableTitle">
                        <LocationsTableHead
                          numSelected={selected.length}
                          order={order}
                          orderBy={orderBy}
                          onSelectAllClick={handleSelectAllClick}
                          onRequestSort={handleRequestSort}
                          rowCount={state.list.length}
                        />
                        <TableBody style={{ width: '-webkit-fill-available' }}>
                          {visibleRows
                            ? visibleRows.map((row, index) => {
                              const isItemSelected = isSelected(row.location_id);
                              const labelId = `Locations-table-checkbox-${index}`;
                              return (
                                <StyledTableRow hover
                                  onClick={(event) => handleClick(event, row.location_id)}
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
                                  <StyledTableCell sx={{ border: 1 }} align="center" style={{ fontSize: '100%' }} component="th" scope="location">{row.location_id}</StyledTableCell>
                                  <StyledTableCell sx={{ border: 1 }} align="center" style={{ fontSize: '100%' }}>{row.pincode}</StyledTableCell>
                                  <StyledTableCell sx={{ border: 1 }} align="center" style={{ fontSize: '100%' }}>{row.area}</StyledTableCell>
                                  <StyledTableCell sx={{ border: 1 }} align="center" style={{ fontSize: '100%' }}>{row.city}</StyledTableCell>
                                  <StyledTableCell sx={{ border: 1 }} align="center" style={{ fontSize: '100%' }}>{row.district}</StyledTableCell>
                                  <StyledTableCell sx={{ border: 1 }} align="center" style={{ fontSize: '100%' }}>{row.state}</StyledTableCell>
                                  <StyledTableCell sx={{ border: 1 }} align="center" style={{ fontSize: '100%' }}>
                                    <a href="/editLocation" className="btn btn-primary" style={{ marginRight: '2%', width: '10%', height: '10%' }}><span role="img" aria-label="Love">✏️</span></a>
                                    <a href="/deleteLocation" className="btn btn-primary" style={{ width: '10%', height: '10%' }}>🗑️</a>
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
                    <TableContainer>
                      <Table>
                        <TableFooter>
                          <TableRow>
                            <CustomTablePagination
                              rowsPerPageOptions={[100, 500, 1000, { label: 'All', value: -1 }]}
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
                          </TableRow>
                          <TableRow>
                            <StyledTableCell>
                              <a style={{ marginTop: '2%' }} href="addAdmin" className='btn btn-success'>Location not found here 😱, ADD IT</a>
                            </StyledTableCell>
                          </TableRow>
                        </TableFooter>
                      </Table>
                    </TableContainer>
                    <br />
                  </Paper>
                </Box>
              </div>
            </div>
          </Item>
        </Grid>
      </Box>
    </React.Fragment>
  );
}

export default Locations;