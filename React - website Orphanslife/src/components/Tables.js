import React from 'react';
import { render } from 'react-dom';
import { Box, Grid, Paper } from '@mui/material'
import { styled } from '@mui/material/styles';
import Sponsors from './Sponsors.js'
import Childs from './Childs.js'
import Admins from './Admins.js'
import Orphanages from './Orphanages.js'
import Locations from './Locations.js'
import OrphanageActivities from './OrphanageActivities.js'
import AdoptRequests from './Adoptrequests.js'
import AdoptStatus from './Adoptstatus.js'
import Donations from './Donations.js'
import Roles from './Roles.js'
import { tablesMenuItems } from "../Layouts/navbar/NavItems.js";
import '../Asset/Css/Body.css';

function TablesMenu() {

    return (
        <React.Fragment>
            <Box className="tableBox" sx={{ flexGrow: 1 }}>
                <ul className="tableMenuItems">
                    {tablesMenuItems.map((table) => {
                        return (
                            <li key={table.id} value={table.id}>
                                <button className='tablesButton' id='tablesKey' value={table.id} onClick={OpenTable} style={{ color: 'red', width:'100%', fontSize:'100%' }}>
                                    {table.title}
                                </button>
                            </li>
                        );
                    })}
                </ul>
            </Box>
        </React.Fragment>
    );
}

function OpenTable(event) {
    switch (event.target.value) {
        case "1":
            render(<Sponsors />, document.getElementById("tablesContentDiv"))
            break;
        case "2":
            render(<Childs />, document.getElementById("tablesContentDiv"))
            break;
        case "3":
            render(<Admins />, document.getElementById("tablesContentDiv"))
            break;
        case "4":
            render(<Orphanages />, document.getElementById("tablesContentDiv"))
            break;
        case "5":
            render(<Locations />, document.getElementById("tablesContentDiv"))
            break;
        case "6":
            render(<OrphanageActivities />, document.getElementById("tablesContentDiv"))
            break;
        case "7":
            render(<AdoptRequests />, document.getElementById("tablesContentDiv"))
            break;
        case "8":
            render(<AdoptStatus />, document.getElementById("tablesContentDiv"))
            break;
        case "9":
            render(<Donations />, document.getElementById("tablesContentDiv"))
            break;
        case "10":
            render(<Roles />, document.getElementById("tablesContentDiv"))
            break;
    }

}

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'white',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
}));

function Tables() {
    let totalWidth = window.outerWidth;
    // let rightMargin = totalWidth-(leftMargin+100);
    // let leftMargin = totalWidth-(rightMargin+100);

    return (
        <React.Fragment>
            <Box className="tableBox" sx={{ flexGrow: 1 }}>
                <Grid className="tableContainer" container style={{ marginLeft: '1%', marginTop: '1%' }} sx={{ width: '99%' }}>
                    <Grid item sx={{ width: '8%' }} direction="row" justifyContent="center" alignItems="center">
                        <Item className="tablesMenuDiv" sx={{ width: 'inherit' }} style={{ marginRight: '3%' }} >
                            <div >
                                <TablesMenu />
                            </div>
                        </Item>
                    </Grid>

                    <Grid item sx={{ width: '-webkit-fill-available' }} direction="row" justifyContent="center" alignItems="center">
                        <Item sx={{ width: '-webkit-fill-available' }} style={{ marginRight: '3%' }} >
                            <div id="tablesContentDiv" className="tablesContentDiv" ></div>
                        </Item>
                    </Grid>
                </Grid>
            </Box>
        </React.Fragment>
    )

}

export default Tables;