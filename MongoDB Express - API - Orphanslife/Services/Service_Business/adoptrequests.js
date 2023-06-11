const express = require('express')
const utils = require('../../Functions/utils')
const router = express.Router()
const auth = require('../../API_Auth/auth.js')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const AdoptReq = require('../../Data/model/AdoptReq.js');
const Sponsor = require('../../Data/model/Sponsor.js');
const Admin = require('../../Data/model/Admin.js');
const multer = require('multer')
const upload = multer()
const mailer = require('../../Functions/mailer');

router.get('/adoptrequests', [auth], async (req, res) => {
    try {
        const data = await AdoptReq.find();
        res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.get('/findByIdAdoptRequest/:id', [auth], async (req, res) => {
    try {
        const data = await AdoptReq.findOne({ req_no: req.params.id });
        res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.put('/updateadoptreqbyid/:req_no', [auth], async (req, res) => {
    try {
        const updatedData = req.body;
        const options = { new: true };

        const selectedAdoptReq = await AdoptReq.findOne({ req_no: req.params.req_no })
        const updatedAdoptReq = await AdoptReq.findByIdAndUpdate(selectedAdoptReq._id, updatedData, options)

        res.status(200).send(updatedAdoptReq)
    }
    catch (error) {
        res.status(400).json({ message: error.message })
    }
})

router.post('/newadoptreq', [auth], async (req, res) => {
    const newAdoptReq = new AdoptReq(req.body)
    try {
        const dataToSave = await newAdoptReq.save();
        const requestedUser = await Sponsor.findOne({sponsor_id : req.body.user_id})
        const assignedAdmin = await Admin.findOne({admin_id : req.body.admin_id})
        const savedAdoptReq = await AdoptReq.findOne({req_comment: dataToSave.req_comment})
        const sponsorBody = `<html>
      <style>
        mark {
          background-color: yellow;
          color: black;
        }

        .container {
          height: 200px;
          position: relative;
          border: 3px solid green;
        }
        
        .vertical-center {
          margin: 0;
          position: absolute;
          top: 50%;
          -ms-transform: translateY(-50%);
          transform: translateY(-50%);
        }
      </style>
        <body>
          <br/>
          <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/welcome.png" alt="Way to go - New Adopt Request" style="width:700px;height:150px;">
          <br/>
          <br/>
          <br/>

          Hi ${requestedUser.sponsor_name},
          <br/>
          <br/>
          Welcome to Orphanslife. Your account is successfully created.

          <br/>
          <br/>

          <mark> Request Number: ${savedAdoptReq.req_no}</mark><br/>
          <mark> Request Stage: ${savedAdoptReq.req_stage}</mark><br/>
          <mark> Last Checked Date: ${savedAdoptReq.last_checked}</mark><br/>
          <mark> Comment on Request: ${savedAdoptReq.req_comment}</mark><br/>
          <mark> Next Checked Date: ${savedAdoptReq.next_check}</mark><br/>

          <br/>
          <br/>
          
          You're All Signed Up.<br/>
          <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/party_popper.gif" alt="Success" style="width:48px;height:48px;">
          <br/>
          <br/>
          <div className="container">
            <div className="vertical-center">
              <a href='http://localhost/auth/login'" type="button" style="background-color: blue;color: white;border: 1px solid #e4e4e4;padding: 8px;border-radius: 3px;cursor: pointer;">Click here to Login</a>
            </div>
          </div>
          <br/>
          <br/>
          
          blah blah blah

          <br/>
          <br/>
          
          © 2 0 2 3   O r p h a n s l i f e

          <br/>
          <a href="http://localhost">localhost</a>
          <br/>

          Kasarsai Rd, Phase 2, Hinjewadi Rajiv Gandhi Infotech Park,<br/>
          Hinjawadi,<br/>
          Pimpri-Chinchwad,<br/>
          Maharashtra 411057<br/><br/>
          <a href="/Unsubscribe">Unsubscribe</a> •  <a href="/browser">View in browser</a> •  <a href="/terms">Terms of Use</a> •  <a href="/privacy">Privacy PolicyPage</a>

          <br/>
          Thank you.
        </body>
      </html>
    `
    const adminBody = `
      <html>
      <style>
        mark {
          background-color: yellow;
          color: black;
        }

        .container {
          height: 200px;
          position: relative;
          border: 3px solid green;
        }
        
        .vertical-center {
          margin: 0;
          position: absolute;
          top: 50%;
          -ms-transform: translateY(-50%);
          transform: translateY(-50%);
        }
      </style>
        <body>
          <br/>
          <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/welcome.png" alt="Way to go - New Adopt Request" style="width:700px;height:150px;">
          <br/>
          <br/>
          <br/>

          Hi ${assignedAdmin.admin_name},
          <br/>
          <br/>
          Welcome to Orphanslife. Your account is successfully created.

          <br/>
          <br/>

          <mark> Request Number: ${savedAdoptReq.req_no}</mark><br/>
          <mark> Request Stage: ${savedAdoptReq.req_stage}</mark><br/>
          <mark> Request Raised on: ${savedAdoptReq.date_of_req}</mark><br/>
          <mark> Next Checked Date: ${savedAdoptReq.next_check}</mark>

          <br/>
          <br/>
          
          You're All Signed Up.<br/>
          <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/party_popper.gif" alt="Success" style="width:48px;height:48px;">
          <br/>
          <br/>
          <div className="container">
            <div className="vertical-center">
              <a href='http://localhost/auth/login'" type="button" style="background-color: blue;color: white;border: 1px solid #e4e4e4;padding: 8px;border-radius: 3px;cursor: pointer;">Click here to Login</a>
            </div>
          </div>
          <br/>
          <br/>
          
          blah blah blah

          <br/>
          <br/>
          
          © 2 0 2 3   O r p h a n s l i f e

          <br/>
          <a href="http://localhost">localhost</a>
          <br/>

          Kasarsai Rd, Phase 2, Hinjewadi Rajiv Gandhi Infotech Park,<br/>
          Hinjawadi,<br/>
          Pimpri-Chinchwad,<br/>
          Maharashtra 411057<br/><br/>
          <a href="/Unsubscribe">Unsubscribe</a> •  <a href="/browser">View in browser</a> •  <a href="/terms">Terms of Use</a> •  <a href="/privacy">Privacy PolicyPage</a>

          <br/>
          Thank you.
        </body>
      </html>
    `
        mailer.sendEmail(requestedUser.sponsor_email, `Way to go - New Adopt Request -  ${savedAdoptReq.req_no}`, sponsorBody, (result) => {
            if (result === null) {
                sendAdmin()
            } else res.status(400).json({ message: error.message })
        })

        function sendAdmin() {
            mailer.sendEmail(assignedAdmin.admin_email, `New Adopt Request to check - ${savedAdoptReq.req_no}`, adminBody, (result) => {
                if (result === null) {
                    res.status(200).json(dataToSave)
                } else res.status(400).json({ message: error.message })
            })
        }
    }
    catch (error) {
        res.status(400).json({ message: error.message })
    }
})

router.get('/new_adopt_reqs', [auth], async (req, res) => {
  try {
      const data = await AdoptReq.find({req_stage: "New Request"}).count();
      res.status(200).json(utils.createSuccessResult(data))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

router.get('/approved_adopt_reqs', [auth], async (req, res) => {
  try {
      const data = await AdoptReq.find({req_stage: "Approved"}).count();
      res.status(200).json(utils.createSuccessResult(data))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

router.get('/rejected_adopt_reqs', [auth], async (req, res) => {
  try {
      const data = await AdoptReq.find({req_stage: "Rejected"}).count();
      res.status(200).json(utils.createSuccessResult(data))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

router.delete('/deleteAdoptReqById/:req_no', [auth, admin], async (req, res) => {
  try {
    const data = await AdoptReq.deleteOne({req_no: req.params.req_no});
    res.status(200).json(utils.createSuccessResult(data))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

module.exports = router