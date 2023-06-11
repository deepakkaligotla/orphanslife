const express = require('express')
const utils = require('../../Functions/utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('../../Functions/mailer')
const upload = multer()
const auth = require('../../API_Auth/auth.js')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const Admin = require('../../Data/model/Admin.js')

const router = express.Router()

router.get('/admins', [auth], async (req, res) => {
  try {
    const data = await Admin.find();
    res.status(200).json(utils.createSuccessResult(data))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

router.post('/adminlogin', async (req, res) => {
  try {
    const admin = await Admin.findOne({ admin_email: req.body.email });
    res.status(200).json(utils.createSuccessResult(admin))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

router.get('/findByIdAdmin/:admin_id', [auth], async (req, res) => {
  try {
    const data = await Admin.findOne({admin_id: req.params.admin_id});
    res.status(200).json(utils.createSuccessResult(data))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

router.put('/updateadminbyid/:aid', [auth], upload.none(), async (req, res) => {
  try {
    const updatedData = req.body;
    updatedData.admin_password = String(cryptoJs.MD5(updatedData.admin_password))

    const options = { new: true };

    const selectedAdmin = await Admin.findOne({admin_id: req.params.aid})
    const updatedAdmin = await Admin.findByIdAndUpdate(selectedAdmin._id, updatedData, options)

    res.status(200).send(updatedAdmin)
  }
  catch (error) {
    res.status(400).json({ message: error.message })
  }
})

router.post('/newadmin', [auth], async (req, res) => {
  const newAdmin = new Admin(req.body)
  newAdmin.admin_password = String(cryptoJs.MD5(newAdmin.admin_password))
  try {
      const dataToSave = await newAdmin.save();
      const body = `
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
          <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/welcome.png" alt="Welcome to Orphanslife App" style="width:700px;height:150px;">
          <br/>
          <br/>
          <br/>

          Hi ${newAdmin.admin_name},
          <br/>
          <br/>
          Welcome to Orphanslife. Your account is successfully created.

          <br/>
          <br/>

          <mark> Email: ${newAdmin.admin_email}</mark><br/>
          <mark>Password: ${req.body.admin_password}</mark>

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
    mailer.sendEmail(newAdmin.admin_email, 'Welcome to Orphanslife', body, (result) => {
      if(result===null) {
        res.status(200).json(dataToSave)
      } else res.status(400).json({ message: error.message })
    })
  }
  catch (error) {
      res.status(400).json({ message: error.message })
  }
})

router.put('/updateadminpassword', [auth], async (req, res) => {
  try {
    const updatedData = req.body;
    updatedData.admin_password = String(cryptoJs.MD5(updatedData.admin_password))

    const options = { new: true };

    const selectedAdmin = await Admin.findOne({admin_email: updatedData.admin_email})
    const updatedAdmin = await Admin.findByIdAndUpdate(selectedAdmin._id, updatedData, options)

    res.status(200).send(updatedAdmin)
  }
  catch (error) {
    res.status(400).json({ message: error.message })
  }
})

router.delete('/deleteadmin/:admin_id', [auth, admin], async (req, res) => {
  try {
    const data = await Admin.deleteOne({admin_id: req.params.admin_id});
    res.status(200).json(utils.createSuccessResult(data))
  }
  catch (error) {
      res.status(400).json(utils.createErrorResult(error))
  }
})

router.post('/findbyemailadmin', [auth], async (req, res) => {
  try {
    const selectedAdmin = await Admin.findOne({admin_email: req.body.admin_email})
    res.status(200).send(selectedAdmin)
  }
  catch (error) {
    res.status(400).json({ message: error.message })
  }
})

module.exports = router