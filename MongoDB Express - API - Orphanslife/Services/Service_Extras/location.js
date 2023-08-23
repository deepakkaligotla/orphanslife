const express = require('express')
const utils = require('../../Functions/utils')
const auth = require('../../API_Auth/auth')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const router = express.Router()
const Location = require('../../Data/model/Location.js')

router.get('/locations', [auth], async (req, res) => {
    try {
      const data = await Location.find();
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

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