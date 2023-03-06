const express = require('express')
const db = require('./db')
const utils = require('./utils')

const router = express.Router()

router.get('/adoptrequests', (request, response) => {
    const statement = `SELECT * FROM adopt_req`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdAdoptRequest/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select * from adopt_req where req_no=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/new_adopt_reqs/', (request, response) => {
    const statement = `SELECT count(*) as new_req FROM orphanslife.adopt_req where req_stage = 'new';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/approved_adopt_reqs/', (request, response) => {
    const statement = `SELECT count(*) as approved FROM orphanslife.adopt_req where req_stage = 'approved';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deleteAdoptReqById/:req_no', (request, response) => {
    console.log(request.params.req_no);
    const statement = `Delete from adopt_req where req_no="${request.params.req_no}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/rejected_adopt_reqs/', (request, response) => {
    const statement = `SELECT count(*) as rejected FROM orphanslife.adopt_req where req_stage = 'rejected';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router