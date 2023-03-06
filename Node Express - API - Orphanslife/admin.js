const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()

const router = express.Router()

router.get('/admins', (request, response) => {
    const statement = `SELECT * FROM admin`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/adminlogin', (request, response) => {
    const {email, password} = request.body
    console.log(email+" "+password);
    const statement = `SELECT * FROM admin LEFT JOIN role ON admin.role_id = role.id where email="${email}" and password="${password}"`
    db.pool.query(statement, [email,password], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdAdmin/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select * from admin where admin.id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/newadmin', (request, response) => {
    const {name, dob, gender, govt_id_type, govt_id, mobile, email, password, address, location_id, role_id, orphanage_id} = request.body
    const statement = `insert into admin(name, dob, gender, govt_id_type, govt_id, mobile, email, password, address, location_id, role_id, orphanage_id) values(?,?,?,?,?,?,?,?,?,?,?,?)`
    db.pool.query(statement, [name, dob, gender, govt_id_type, govt_id, mobile, email, password, address, location_id, role_id, orphanage_id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.put('/updateadminpassword', (request, response) => {
    const {email, password} = request.body
    const statement = `update admin set password="${password}" where email = "${email}"`
    db.pool.query(statement, [email, password], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deleteadmin/:id', (request, response) => {
    console.log(request.params.id);
    const statement = `Delete from admin where id="${request.params.id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/findbyemailadmin', (request, response) => {
    const {email} = request.body
    console.log(email);
    const statement = `select * from admin where email="${email}"`
    db.pool.query(statement, [email], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router