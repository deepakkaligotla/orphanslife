const express = require("express");
const cors = require('cors')
const morgan = require('morgan')
var bodyParser = require('body-parser')

const routerLocation = require('./location.js')

const extras_app = express();

extras_app.use(bodyParser.json({ extended: false }));
extras_app.use(bodyParser.urlencoded({ extended: false }))
extras_app.use(bodyParser.json())
extras_app.use(cors()) //for multiple ports CORS access response.setHeader("Access-Control-Allow-Origin","*")
extras_app.use(express.json({ limit: '20mb' }))
extras_app.use(morgan('combined'))
extras_app.use(express.static('images'))

extras_app.use((request, response, next)=>{
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");
    next();
});

extras_app.use(express.json({ limit: "50mb" }));
extras_app.use(express.json()) //to get JSON data from get to post (or) page 1 to another page
extras_app.use(express.static('uploads'))

extras_app.use(routerLocation)

extras_app.use("*", (req, res) => {
    res.status(404).json({
      success: "false",
      message: "Page not found",
      error: {
        statusCode: 404,
        message: "You reached a route that is not defined on this server",
      },
    });
  }); 
  
  module.exports = extras_app;

