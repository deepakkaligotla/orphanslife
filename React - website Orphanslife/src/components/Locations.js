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
  { id: 'location_id', label: 'LOCATION ID', maxWidth: 60, numeric: true, disablePadding: false },
  { id: 'pincode', label: 'PINCODE', minWidth: 80, numeric: true, disablePadding: false },
  { id: 'area', label: 'AREA', minWidth: 170, numeric: false, disablePadding: false },
  { id: 'city', label: 'CITY', minWidth: 170, numeric: false, disablePadding: false },
  { id: 'district', label: 'DISTRICT', minWidth: 170, numeric: false, disablePadding: false },
  { id: 'state', label: 'STATE', minWidth: 170, numeric: false, disablePadding: false },
  { id: 'action', label: 'ACTION', minWidth: 150 },
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


  function searchLocationTable(e) {
    const results = locations.filter(location => {
      if (e.target.value === "") return locations
      if (location.location_id.toString().includes(e.target.value)) return location
      if (location.pincode.toString().includes(e.target.value)) return location
      if (location.area.toLowerCase().toString().includes(e.target.value.toString().toLowerCase())) return location
      if (location.city.toLowerCase().toString().includes(e.target.value.toString().toLowerCase())) return location
      if (location.district.toLowerCase().toString().includes(e.target.value.toString().toLowerCase())) return location
      if (location.state.toLowerCase().toString().includes(e.target.value.toString().toLowerCase())) return location
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
        <StyledTableCell align='center' style={{ fontSize: '20px' }}>Locations Table
          <input type='search' placeholder='Search in Location!' style={{ color: 'black', textAlign: 'center', width: '180px', fontSize: '15px', marginLeft: '10px' }} onChange={searchLocationTable} />
        </StyledTableCell>
        <div className="table-responsive">
          <Box sx={{ width: '100%' }}>
            <Paper sx={{ width: '80%', overflow: 'hidden', border: 1 }}>
              <LocationsTableToolbar numSelected={selected.length} />
              <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table" sx={{ minWidth: 750 }}
                  aria-labelledby="tableTitle">
                  <LocationsTableHead
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
                            <StyledTableCell sx={{ border: 1 }} align="center" component="th" scope="location">{row.location_id}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.pincode}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.area}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.city}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.district}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">{row.state}</StyledTableCell>
                            <StyledTableCell sx={{ border: 1 }} align="center">
                              <a href="/editLocation" className="btn btn-primary" style={{ marginRight: '10px' }}><span role="img" aria-label="Love">✏️</span></a>
                              <a href="/deleteLocation" className="btn btn-primary">DEL</a>
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
              <br/>
            </Paper>
          </Box>
        </div>
      </div>
      <br />
      <div>
        <a href="addAdmin" className='btn btn-success'>Location not found here 😱, ADD IT</a>
      </div>
    </React.Fragment>
  );
}

export default Locations;