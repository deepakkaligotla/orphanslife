const express = require('express')
const db = require('./Service_Extras.Index.js')
const utils = require('../../Functions/utils.js')
const router = express.Router()

router.get('/user_machines', (request, response) => {
    const statement = `SELECT * FROM user_machine`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router