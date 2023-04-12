const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()

const router = express.Router()

router.get('/childs', (request, response) => {
    const statement = `SELECT * FROM child LEFT JOIN adoptive_status ON child.status_id = adoptive_status.adoptive_status_id LEFT JOIN admin ON child.admin_id = admin.admin_id`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/newchild', (request, response) => {
    const {name, dob, gender, admitted_date, leave_date, mother_name, father_name, mobile, child_image, status_id, admin_id} = request.body
    const statement = `insert into child(name, dob, gender, admitted_date, leave_date, mother_name, father_name, mobile, child_image, status_id, admin_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)`
    db.pool.query(statement, [name, dob, gender, admitted_date, leave_date, mother_name, father_name, mobile, child_image, status_id, admin_id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdChild/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `SELECT * FROM child LEFT JOIN adoptive_status ON child.status_id = adoptive_status.adoptive_status_id LEFT JOIN admin ON child.admin_id = admin.admin_id where child.child_id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deletechild/:id', (request, response) => {
    console.log(request.params.id);
    const statement = `Delete from child where id="${request.params.id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router