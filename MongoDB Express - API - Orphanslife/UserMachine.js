const express = require('express')
const db = require('./db')
const utils = require('./utils')
const router = express.Router()
const auth = require('./Auth/auth.js')
const { admin, editor, viewer } = require("./Auth/roles.js");

router.get('/user_machines', (request, response) => {
    const statement = `SELECT * FROM user_machine`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router