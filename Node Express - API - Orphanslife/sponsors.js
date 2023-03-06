const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()

const router = express.Router()

router.post('/newsponsor', (request, response) => {
    const {name, dob, gender, govt_id_type, govt_id, mobile, email, password, marital_status, user_address, location_id } = request.body
    const statement = `insert into sponsor(name, dob, gender, govt_id_type, govt_id, mobile, email, password, marital_status, user_address, location_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)`
    db.pool.query(statement, [name, dob, gender, govt_id_type, govt_id, mobile, email, password, marital_status, user_address, location_id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.put('/updatesponsorbyid/:sid', upload.none(), (request, response) => {
    console.log(request.body)
    
        const {name, dob, gender, govt_id_type, govt_id, mobile, email, password, marital_status, user_address, spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile} = request.body
        const statement = `update sponsor set name=?, dob=?, gender=?, govt_id_type=?, govt_id=?, mobile=?, email=?, password=?, marital_status=?, user_address=?, spouce_name=?, spouce_dob=?, spouce_govt_id_type=?, spouce_govt_id=?, spouce_mobile=? where id="${request.params.sid}"`
        db.pool.query(statement, [name, dob, gender, govt_id_type, govt_id, mobile, email, password, marital_status, user_address, spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile], (error, result) => {
            response.send(utils.createResult(error, result))
        })
    })

router.get('/sponsors', (request, response) => {
    const statement = `SELECT sponsor.*, location.pincode, location.area, location.city, location.district, location.state FROM sponsor LEFT JOIN location ON sponsor.location_id = location.id;`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/sponsorlogin', (request, response) => {
    const {email, password} = request.body
    console.log(email+" "+password);
    const statement = `SELECT * FROM sponsor where email="${email}" and password="${password}"`
    db.pool.query(statement, [email,password], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deletesponsor/:id', (request, response) => {
    console.log(request.params.id)
    const statement = `Delete from sponsor where id="${request.params.id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.put('/updatesponsorpassword', (request, response) => {
    const {email,password} = request.body
    console.log(email+" "+password);
    const statement = `update sponsor set password="${password}" where email = "${email}"`
    db.pool.query(statement, [email,password], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/findByEmailSponsor', (request, response) => {
    const {email} = request.body
    console.log(email);
    const statement = `select * from sponsor where email="${email}"`
    db.pool.query(statement, [email], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdSponsor/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select sponsor.*, location.pincode, location.area, location.city, location.district, location.state FROM sponsor JOIN location ON sponsor.location_id = location.id where sponsor.id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/sponsorsdonated', (request, response) => {
    const statement = `SELECT * FROM sponsor where donation_id is not null;`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router
