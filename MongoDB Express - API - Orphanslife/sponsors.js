const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()
const router = express.Router()
const auth = require('./Auth/auth.js')
const { admin, editor, viewer } = require("./Auth/roles.js");

router.post('/newsponsor', [auth, editor], (request, response) => {
    const {sponsor_name, sponsor_dob, sponsor_gender, sponsor_govt_id_type, sponsor_govt_id, sponsor_mobile, sponsor_email, sponsor_password, marital_status, sponsor_image, sponsor_address, sponsor_location_id, spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile, spouce_image, donation_id} = request.body
    const encryptedPassword = String(cryptoJs.MD5(sponsor_password))
    
    const statement = `insert into sponsor(sponsor_name, sponsor_dob, sponsor_gender, sponsor_govt_id_type, sponsor_govt_id, sponsor_mobile, sponsor_email, sponsor_password, marital_status, sponsor_image, sponsor_address, sponsor_location_id, spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile, spouce_image, donation_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)`
    db.pool.query(statement, [sponsor_name, sponsor_dob, sponsor_gender, sponsor_govt_id_type, sponsor_govt_id, sponsor_mobile, sponsor_email, encryptedPassword, marital_status, sponsor_image, sponsor_address, sponsor_location_id, spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile, spouce_image, donation_id], (error, result) => {

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

          Hi ${sponsor_name},
          <br/>
          <br/>
          Welcome to Orphanslife. Your account is successfully created.

          <br/>
          <br/>

          <mark> Email: ${sponsor_email}</mark><br/>
          <mark>Password: ${sponsor_password}</mark>

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
    if(!error) {
      mailer.sendEmail(sponsor_email, 'Welcome to Orphanslife', body, () => {
        response.send(utils.createSuccessResult(error, result))
      })
    } else response.send(utils.createErrorResult(error, result))
    })
})

router.put('/updatesponsorbyid/:sid', [auth, editor], upload.none(), (request, response) => {
    console.log(request.body)
    
        const {sponsor_name, sponsor_dob, sponsor_gender, sponsor_govt_id_type, sponsor_govt_id, sponsor_mobile, sponsor_email, sponsor_password, marital_status, sponsor_address, spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile} = request.body
        const statement = `update sponsor set sponsor_name=?, sponsor_dob=?, sponsor_gender=?, sponsor_govt_id_type=?, sponsor_govt_id=?, sponsor_mobile=?, sponsor_email=?, sponsor_password=?, marital_status=?, sponsor_address=?, spouce_name=?, spouce_dob=?, spouce_govt_id_type=?, spouce_govt_id=?, spouce_mobile=? where sponsor_id="${request.params.sid}"`
        db.pool.query(statement, [sponsor_name, sponsor_dob, sponsor_gender, sponsor_govt_id_type, sponsor_govt_id, sponsor_mobile, sponsor_email, sponsor_password, marital_status, sponsor_address, spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile], (error, result) => {
            response.send(utils.createResult(error, result))
        })
    })

router.get('/sponsors', [auth], (request, response) => {
    const statement = `SELECT * FROM sponsor`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deletesponsor/:id', [auth, admin], (request, response) => {
    console.log(request.params.id)
    const statement = `Delete from sponsor where sponsor_id="${request.params.id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/forgotpasswordsendotp', [auth, editor], (request, response) => {
  const {email} = request.body
  const statement = `select * from sponsor where sponsor_email = "${email}"`
  db.pool.query(statement, [email], (error, result) => {
    if(result[0]!=null) {
      var otp = Math.floor(100000 + Math.random() * 900000)
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

        Hi ${result[0].sponsor_name},
        <br/>
        <br/>
        Greetings!

        <br/>
        <br/>

        You are just a step away from resetting your Orphanslife account password

        <br/>
        <br/>

        Once you have verified the code, you'll be prompted to set a new password immediately. This is to ensure that only you have access to your account.

        <br/>
        <br/>

        <mark>Your OTP: ${otp}</mark><br/>
        <mark>Expires in: 2 minutes</mark>

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
      if(error==null) {
        mailer.sendEmail(email, 'Welcome to Orphanslife', body, () => {
          response.send(utils.createSuccessResult(result,otp))
        })
      } else response.send(utils.createErrorResult(error, result))
    } else response.send(utils.createErrorResult(error, result))
  })
})

router.put('/updatesponsorpassword', [auth, editor], (request, response) => {
    const {email,password} = request.body
    console.log(request.body)
    const encryptedPassword = String(cryptoJs.MD5(password))
    const statement = `update sponsor set sponsor_password="${encryptedPassword}" where sponsor_email="${email}"`
    db.pool.query(statement, [email,encryptedPassword], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/findByEmailSponsor', [auth], (request, response) => {
    const {sponsor_email} = request.body
    console.log(sponsor_email);
    const statement = `select * from sponsor where sponsor_email="${sponsor_email}"`
    db.pool.query(statement, [sponsor_email], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdSponsor/:id', [auth], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select * FROM sponsor JOIN location ON sponsor.sponsor_location_id = location.location_id where sponsor.sponsor_id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/sponsorsdonated', [auth], (request, response) => {
    const statement = `SELECT * FROM sponsor where donation_id is not null;`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router
