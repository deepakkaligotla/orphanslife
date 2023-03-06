const express = require('express')
const db = require('./db')
const utils = require('./utils')

const router = express.Router()

router.post('/locations', (request, response) => {
    const {page, size} = request.body;
    const statement = `call LocationPaging(?,?);`
    db.pool.query(statement, [page, size], (error, result) => {
        response.send(utils.createResult(error, result))
    })
});

router.post('/location/:pincode', (request, response) => {
    const statement = `SELECT * FROM location where pincode=${request.params.pincode}`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
});

router.get('/findByIdLocation/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select * from location where id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router