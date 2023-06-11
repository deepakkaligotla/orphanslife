const express = require('express')
const utils = require('../../Functions/utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('../../Functions/mailer')
const upload = multer()
const auth = require('../../API_Auth/auth.js')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const router = express.Router()
const Orphanage = require('../../Data/model/Orphanage.js')

router.get('/orphanages', [auth], async (req, res) => {
    try {
      const data = await Orphanage.find();
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
  })

router.get('/findByIdOrphanage/:id', [auth], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select orphanage.*, location.pincode, location.area, location.city, location.district, location.state FROM orphanage LEFT JOIN location ON orphanage.location_id = location.id where orphanage.id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router