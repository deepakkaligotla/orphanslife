const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()

const router = express.Router()

router.get('/childs', (request, response) => {
    const statement = `SELECT child.name as child_name, child.id as child_id, child.dob as child_dob, child.gender as child_gender, child.*, adoptive_status.id as status_id, adoptive_status.status as status, admin.name as admin_name, admin.* FROM child LEFT JOIN adoptive_status ON child.status_id = adoptive_status.id LEFT JOIN admin ON child.admin_id = admin.id`
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

router.get('/findByIdChild/:child_id', (request, response) => {
    const child_id = request.params.id
    console.log(child_id);
    const statement = `SELECT child.name as child_name, child.id as child_id, child.dob as child_dob, child.gender as child_gender, child.*, adoptive_status.id as status_id, adoptive_status.status as status, admin.name as admin_name, admin.* FROM child LEFT JOIN adoptive_status ON child.status_id = adoptive_status.id LEFT JOIN admin ON child.admin_id = admin.id where child.id=${request.params.child_id};`
    db.pool.query(statement, [child_id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router