const express = require('express')
const utils = require('../../Functions/utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('../../Functions/mailer')
const upload = multer()
const auth = require('../../API_Auth/auth.js')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const router = express.Router()
const Child = require('../../Data/model/Child.js')


router.get('/childs', [auth], async (req, res) => {
    try {
      const data = await Child.find();
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.post('/newchild', [auth], async (req, res) => {
    const newChild = new Child(req.body)
    try {
        const dataToSave = await newChild.save();
        res.status(200).json(dataToSave)
    }
    catch (error) {
        res.status(400).json({ message: error.message })
    }
})

router.get('/findByIdChild/:id', [auth], async (req, res) => {
    try {
      const data = await Child.findOne({child_id: req.params.id});
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.delete('/deletechild/:id', [auth, admin], async (req, res) => {
    try {
      const data = await Child.deleteOne({child_id: req.params.id});
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
  })

module.exports = router