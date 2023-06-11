const express = require("express");
const cors = require('cors')
const morgan = require('morgan')
var bodyParser = require('body-parser')


const routerAdmin = require('./admin.js')
const routerRole = require('./role.js')

const admin_app = express();

admin_app.use(bodyParser.json({ extended: false }));
admin_app.use(bodyParser.urlencoded({ extended: false }))
admin_app.use(bodyParser.json())
admin_app.use(cors()) //for multiple ports CORS access response.setHeader("Access-Control-Allow-Origin","*")
admin_app.use(express.json({ limit: '20mb' }))
admin_app.use(morgan('combined'))
admin_app.use(express.static('images'))

admin_app.use((request, response, next)=>{
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");
    next();
});

admin_app.use(express.json({ limit: "50mb" }));
admin_app.use(express.json()) //to get JSON data from get to post (or) page 1 to another page
admin_app.use(express.static('uploads'))

admin_app.use(routerRole)
admin_app.use(routerAdmin)

admin_app.use("*", (req, res) => {
    res.status(404).json({
      success: "false",
      message: "Page not found",
      error: {
        statusCode: 404,
        message: "You reached a route that is not defined on this server",
      },
    });
  }); 
  
  module.exports = admin_app;