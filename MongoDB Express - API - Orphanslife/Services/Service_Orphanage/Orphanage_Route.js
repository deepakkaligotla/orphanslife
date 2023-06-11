const express = require("express");
const cors = require('cors')
const morgan = require('morgan')
var bodyParser = require('body-parser')

const routerAdoptStatus = require('./adoptstatus.js')
const routerChild = require('./child.js')
const routerOrphanage = require('./orphanage.js')
const routerOrphanageActivities = require('./orphanage_details.js')

const app = express();

app.use(bodyParser.json({ extended: false }));
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
app.use(cors()) //for multiple ports CORS access response.setHeader("Access-Control-Allow-Origin","*")
app.use(express.json({ limit: '20mb' }))
app.use(morgan('combined'))
app.use(express.static('images'))

app.use((request, response, next)=>{
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");
    next();
});

app.use(express.json({ limit: "50mb" }));
app.use(express.json()) //to get JSON data from get to post (or) page 1 to another page
app.use(express.static('uploads'))

app.use(routerAdoptStatus)
app.use(routerChild)
app.use(routerOrphanage)
app.use(routerOrphanageActivities)

// This should be the last route else any after it won't work
app.use("*", (req, res) => {
    res.status(404).json({
      success: "false",
      message: "Page not found",
      error: {
        statusCode: 404,
        message: "You reached a route that is not defined on this server",
      },
    });
  }); 
  
  module.exports = app;