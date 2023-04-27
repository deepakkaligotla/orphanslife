import React from 'react';
import { render } from 'react-dom';
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
import {Box} from '@mui/material'
import { tablesMenuItems } from "../Layouts/navbar/NavItems.js";
import '../Asset/Css/Body.css';

function TablesMenu() {

    return (
      <React.Fragment>
        <Box>
        <ul 
          className="submenu"
        >
          {tablesMenuItems.map((table) => {
            return (
              <li key={table.id} value={table.id}>
                <button id='tablesKey' value={table.id} onClick={OpenTable}>
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
    console.log(event.target.value)
    switch(event.target.value) {
        case "1":
            render(<Sponsors/>, document.getElementById("tablesContentDiv"))
            break;
        case "2":
            render(<Childs/>, document.getElementById("tablesContentDiv"))
            break;
        case "3":
            render(<Admins/>, document.getElementById("tablesContentDiv"))
            break;
        case "4":
            render(<Orphanages/>, document.getElementById("tablesContentDiv"))
            break;
        case "5":
            render(<Locations/>, document.getElementById("tablesContentDiv"))
            break;
        case "6":
            render(<OrphanageActivities/>, document.getElementById("tablesContentDiv"))
            break;
        case "7":
            render(<AdoptRequests/>, document.getElementById("tablesContentDiv"))
            break;
        case "8":
            render(<AdoptStatus/>, document.getElementById("tablesContentDiv"))
            break;
        case "9":
            render(<Donations/>, document.getElementById("tablesContentDiv"))
            break;
        case "10":
            render(<Roles/>, document.getElementById("tablesContentDiv"))
            break;
      }
      
}

function Tables() {

    return (
        <React.Fragment>
            <div className="tableDiv">
                <div className="tablesMenuDiv">
                    <TablesMenu/>
                </div>
                <div id="tablesContentDiv" className="tablesContentDiv" >
                </div>
            </div>
        </React.Fragment>
    )

}

export default Tables;