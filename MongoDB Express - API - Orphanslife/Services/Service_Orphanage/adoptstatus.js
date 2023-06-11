const express = require('express')
const utils = require('../../Functions/utils')
const router = express.Router()
const auth = require('../../API_Auth/auth.js')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const AdoptiveStatus = require('../../Data/model/AdoptiveStatus.js')

router.get('/adoptstatus', [auth], async (req, res) => {
    try {
      const data = await AdoptiveStatus.find();
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.get('/findByIdAdoptStatus/:id', [auth], async (req, res) => {
    try {
      const data = await AdoptiveStatus.findOne({adoptive_status_id: req.params.id});
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
  })

module.exports = router