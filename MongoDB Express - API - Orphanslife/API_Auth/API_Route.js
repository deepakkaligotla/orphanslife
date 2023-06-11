const express = require("express");
const auth = require("./routes/auth.js");
const cors = require('cors')
const morgan = require('morgan')
var bodyParser = require('body-parser')

const messagesRouter = require("./routes/messages");

const api_route_app = express();

api_route_app.use(bodyParser.json({ extended: false }));
api_route_app.use(bodyParser.urlencoded({ extended: false }))
api_route_app.use(bodyParser.json())
api_route_app.use(cors())
api_route_app.use(express.json({ limit: '20mb' }))
api_route_app.use(morgan('combined'))
api_route_app.use(express.static('images'))

api_route_app.use((request, response, next)=>{
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");
    next();
});

api_route_app.use(express.json({ limit: "50mb" }));
api_route_app.use(auth)
api_route_app.use(messagesRouter)

module.exports = api_route_app