const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()

const router = express.Router()

router.get('/activities', (request, response) => {
    const statement = `SELECT * FROM orphanage_activities JOIN orphanage ON orphanage_activities.orphanage_id=orphanage.id`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/newOrphanageActivity', upload.none(), (request, response) => {
    console.log(request.body)
    const {details, image_1, image_2, image_3, image_4, image_5} = request.body
    const statement = `insert into orphanage_activities(orphanage_id, details, image_1, image_2, image_3, image_4, image_5) values(${request.body.orphanage.id},?,?,?,?,?,?)`
    db.pool.query(statement, [details, image_1, image_2, image_3, image_4, image_5], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdOrphanageActivities/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `SELECT * FROM orphanage_activities JOIN orphanage ON orphanage_activities.orphanage_id=orphanage.id where orphanage_activities.event_id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router
