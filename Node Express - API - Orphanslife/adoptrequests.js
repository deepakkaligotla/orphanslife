const express = require('express')
const db = require('./db')
const utils = require('./utils')

const router = express.Router()

router.get('/adoptrequests', (request, response) => {
    const statement = `SELECT * FROM adopt_req`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdAdoptRequest/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select * from adopt_req where req_no=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.put('/updateadoptreqbyid/:req_no', (request, response) => {
    console.log(request.body)
        const {reason, req_stage, date_of_req, last_checked, req_comment, next_check, adopted} = request.body
        const statement = `update adopt_req set user_id=${request.body.sponsor.id}, admin_id=${request.body.admin.admin_id}, child_id=${request.body.child.child_id}, reason=?, req_stage=?, date_of_req=?, last_checked=?, req_comment=?, next_check=?, adopted=? where req_no="${request.params.req_no}"`
        db.pool.query(statement, [reason, req_stage, date_of_req, last_checked, req_comment, next_check, adopted], (error, result) => {
            response.send(utils.createResult(error, result))
        })
    })
    
router.post('/newadoptreq', (request, response) => {
    console.log(request.body)
    const {reason, req_stage, date_of_req, last_checked, req_comment, next_check, adopted} = request.body
    const statement = `insert into adopt_req(user_id, admin_id, child_id, reason, req_stage, date_of_req, last_checked, req_comment, next_check, adopted) values(${request.body.sponsor.id},${request.body.admin.admin_id},${request.body.child.child_id},?,?,?,?,?,?,?)`
    db.pool.query(statement, [reason, req_stage, date_of_req, last_checked, req_comment, next_check, adopted], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/new_adopt_reqs/', (request, response) => {
    const statement = `SELECT count(*) as new_req FROM orphanslife.adopt_req where req_stage = 'New Request';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/approved_adopt_reqs/', (request, response) => {
    const statement = `SELECT count(*) as approved FROM orphanslife.adopt_req where req_stage = 'approved';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deleteAdoptReqById/:req_no', (request, response) => {
    console.log(request.params.req_no);
    const statement = `Delete from adopt_req where req_no="${request.params.req_no}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/rejected_adopt_reqs/', (request, response) => {
    const statement = `SELECT count(*) as rejected FROM orphanslife.adopt_req where req_stage = 'rejected';`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router