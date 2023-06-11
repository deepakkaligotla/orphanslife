const express = require('express')
const utils = require('../../Functions/utils')
const router = express.Router()
const auth = require('../../API_Auth/auth')
const { admin, editor, viewer } = require("../../API_Auth/roles");
const Role = require('../../Data/model/Role.js')

router.get('/roles', [auth], async (req, res) => {
    try {
      const data = await Role.find();
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.get('/findByIdRole/:id', [auth], async (req, res) => {
    try {
      const data = await Role.findOne({admin_id: req.params.admin_id});
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

module.exports = router