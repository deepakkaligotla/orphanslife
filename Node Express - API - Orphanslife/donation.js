const express = require('express')
const db = require('./db')
const utils = require('./utils')
const router = express.Router()
const auth = require('./Auth/auth.js')
const { admin, editor, viewer } = require("./Auth/roles.js");

router.get('/donations', [auth], (request, response) => {
    const statement = `SELECT * FROM donation`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/monthwise_donations', [auth], (request, response) => {
    const statement = `SELECT DATE_FORMAT(created_at, '%M') as Month, DATE_FORMAT(created_at, '%Y') as Year, sum(amount) as donations FROM orphanslife.donation where payment_status='success' GROUP BY MONTH(created_at) ORDER BY STR_TO_DATE(CONCAT('0001 ', Month, ' 01'), '%Y %M %d');`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/newdonation', [auth, editor], (request, response) => {
    const {amount, payment_status, user_id} = request.body
    console.log(amount+" "+payment_status+" "+user_id)
    const statement = `insert into donation(amount, payment_status, user_id) values(?,?,?)`
    db.pool.query(statement, [amount, payment_status, user_id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdDonation/:id', [auth, editor], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select * from donation where id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/success_payments/', [auth, editor], (request, response) => {
    const statement = `SELECT count(*) as success_payments FROM orphanslife.donation where payment_status = 'success';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/failed_payments/', [auth], (request, response) => {
    const statement = `SELECT count(*) as failed_payments FROM orphanslife.donation where payment_status = 'failed';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deletedonation/:donation_id',  [auth, admin], (request, response) => {
    console.log(request);
    const statement = `Delete from donation where donation_id="${request.params.donation_id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router