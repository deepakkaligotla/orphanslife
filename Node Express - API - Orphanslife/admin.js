const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()

const router = express.Router()

router.get('/admins', (request, response) => {
    const statement = `SELECT * FROM admin`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/adminlogin', (request, response) => {
    const {email, password} = request.body
    const encryptedPassword = String(cryptoJs.MD5(password))
    console.log(email+" "+password);
    const statement = `SELECT * FROM admin LEFT JOIN role ON admin.role_id = role.id where admin_email="${email}" and admin_password="${encryptedPassword}"`
    db.pool.query(statement, [email,password], (error, result) => {
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
              <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/welcome.png" alt="Welcome to HotelBooking App" style="width:700px;height:150px;">
              <br/>
              <br/>
              <br/>
    
              Hi ${result[0].admin_name},
              <br/>
              <br/>
              Greetings!
    
              <br/>
              <br/>
    
              You are just a step away from accessing your Orphanslife account
    
              <br/>
              <br/>
    
              Once you have verified the code, you'll be prompted to set a new password immediately. This is to ensure that only you have access to your account.
    
              <br/>
              <br/>
    
              <mark> Your OTP: ${otp}</mark><br/>
              <mark>Expires in: 2 minutes</mark>
    
              <br/>
              <br/>
              
              <div class="container">
                <div class="vertical-center">
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
            mailer.sendEmail(email, `Orphanslife Account - ${otp} is your verification code for secure access`, body, () => {
              response.send(utils.createSuccessResult(result, otp))
            })
          } else response.send(utils.createErrorResult(error))
        })
})

router.get('/findByIdAdmin/:admin_id', (request, response) => {
    const admin_id = request.params.admin_id
    console.log(admin_id);
    const statement = `select * from admin where admin.admin_id=${request.params.admin_id};`
    db.pool.query(statement, [admin_id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.put('/updateadminbyid/:aid', upload.none(), (request, response) => {
    console.log(request.body)
        const {admin_name, admin_dob, admin_gender, admin_govt_id_type, admin_govt_id, admin_mobile, admin_email, admin_password, admin_address, admin_location_id, role_id, admin_orphanage_id} = request.body
        const statement = `update admin set admin_name=?, admin_dob=?, admin_gender=?, admin_govt_id_type=?, admin_govt_id=?, admin_mobile=?, admin_email=?, admin_password=?, admin_address=?, admin_location_id=?, role_id=?, admin_orphanage_id=? where admin_id="${request.params.aid}"`
        db.pool.query(statement, [admin_name, admin_dob, admin_gender, admin_govt_id_type, admin_govt_id, admin_mobile, admin_email, admin_password, admin_address, admin_location_id, role_id, admin_orphanage_id], (error, result) => {
            response.send(utils.createResult(error, result))
        })
    })

router.post('/newadmin', (request, response) => {
    const {admin_name, admin_dob, admin_gender, admin_govt_id_type, admin_govt_id, admin_mobile, admin_email, admin_password, admin_address, admin_location_id, role_id, admin_orphanage_id} = request.body
    const encryptedPassword = String(cryptoJs.MD5(admin_password))
    const statement = `insert into admin(admin_name, admin_dob, admin_gender, admin_govt_id_type, admin_govt_id, admin_mobile, admin_email, admin_password, admin_address, admin_location_id, role_id, admin_orphanage_id) values(?,?,?,?,?,?,?,?,?,?,?,?)`
    db.pool.query(statement, [admin_name, admin_dob, admin_gender, admin_govt_id_type, admin_govt_id, admin_mobile, admin_email, encryptedPassword, admin_address, admin_location_id, role_id, admin_orphanage_id], (error, result) => {
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

          Hi ${admin_name},
          <br/>
          <br/>
          Welcome to Orphanslife. Your account is successfully created.

          <br/>
          <br/>

          <mark> Email: ${admin_email}</mark><br/>
          <mark>Password: ${admin_password}</mark>

          <br/>
          <br/>
          
          You're All Signed Up.<br/>
          <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/party_popper.gif" alt="Success" style="width:48px;height:48px;">
          <br/>
          <br/>
          <div class="container">
            <div class="vertical-center">
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
    mailer.sendEmail(admin_email, 'Welcome to Orphanslife', body, () => {
      response.send(utils.createResult(error, result))
    })
    })
})

router.put('/updateadminpassword', (request, response) => {
    const {email, password} = request.body
    const encryptedPassword = String(cryptoJs.MD5(password))
    const statement = `update admin set admin_password="${encryptedPassword}" where admin_email = "${email}"`
    db.pool.query(statement, [email, password], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deleteadmin/:admin_id', (request, response) => {
    console.log(request.params.admin_id);
    const statement = `Delete from admin where admin_id="${request.params.admin_id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/findbyemailadmin', (request, response) => {
    const {admin_email} = request.body
    console.log(admin_email);
    const statement = `select * from admin where admin_email="${admin_email}"`
    db.pool.query(statement, [admin_email], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router