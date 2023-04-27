const express = require('express')
const db = require('./db')
const utils = require('./utils')
const auth = require('./Auth/auth.js')
const { admin, editor, viewer } = require("./Auth/roles.js");

const router = express.Router()

router.get('/locations/', [auth], (request, response) => {
    const statement = `SELECT * FROM location`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
});

router.post('/findByIdLocation/:id', [auth], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select * from location where id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/findByPincodeLocation/:pincode', [auth], (request, response) => {
    const pincode = request.params.pincode
    console.log(pincode);
    const statement = `select * from location where pincode=${request.params.pincode};`
    db.pool.query(statement, [pincode], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/newLocation/', [auth], (request, response) => {
    const {id,pincode,area,city,district,state} = request.body
    console.log(request.body);
    const statement = `insert into location values(?,?,?,?,?,?)`
    db.pool.query(statement, [id,pincode,area,city,district,state], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router