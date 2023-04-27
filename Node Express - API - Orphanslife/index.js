const http = require("http");
const app = require("./app");
const server = http.createServer(app);
const config = require('config')
const port = config.get("portno")
const express = require('express')

const saveUserMachineDetails = require('./UserMachine.js')
app.use(saveUserMachineDetails)

server.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
