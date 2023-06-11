const express = require("express");
const cors = require('cors')
const morgan = require('morgan')
var bodyParser = require('body-parser')


const routerAdoptRequests = require('./adoptrequests.js')
const routerDonation = require('./donation.js')

const business_app = express();

business_app.use(bodyParser.json({ extended: false }));
business_app.use(bodyParser.urlencoded({ extended: false }))
business_app.use(bodyParser.json())
business_app.use(cors()) //for multiple ports CORS access response.setHeader("Access-Control-Allow-Origin","*")
business_app.use(express.json({ limit: '20mb' }))
business_app.use(morgan('combined'))
business_app.use(express.static('images'))

business_app.use((request, response, next)=>{
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");
    next();
});

business_app.use(express.json({ limit: "50mb" }));
business_app.use(express.json()) //to get JSON data from get to post (or) page 1 to another page
business_app.use(express.static('uploads'))

business_app.use(routerDonation)
business_app.use(routerAdoptRequests)

business_app.use("*", (req, res) => {
    res.status(404).json({
      success: "false",
      message: "Page not found",
      error: {
        statusCode: 404,
        message: "You reached a route that is not defined on this server",
      },
    });
  }); 
  
  module.exports = business_app;