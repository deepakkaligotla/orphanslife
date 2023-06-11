const express = require('express')
const utils = require('../../Functions/utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('../../Functions/mailer')
const upload = multer()
const auth = require('../../API_Auth/auth.js')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const router = express.Router()
const OrphanageDetails = require('../../Data/model/OrphanageDetails.js')

router.get('/activities', [auth], async (req, res) => {
    try {
      const data = await OrphanageDetails.find();
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.post('/newOrphanageActivity', [auth, editor], upload.none(), (request, response) => {
    console.log(request.body)
    const {details, image_1, image_2, image_3, image_4, image_5} = request.body
    const statement = `insert into orphanage_activities(orphanage_id, details, image_1, image_2, image_3, image_4, image_5) values(${request.body.orphanage.id},?,?,?,?,?,?)`
    db.pool.query(statement, [details, image_1, image_2, image_3, image_4, image_5], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdOrphanageActivities/:id', [auth], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `SELECT * FROM orphanage_activities JOIN orphanage ON orphanage_activities.orphanage_id=orphanage.id where orphanage_activities.event_id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router
