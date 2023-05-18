const jwt = require("jsonwebtoken");
const express = require("express");
const cryptoJs = require('crypto-js');
const mailer = require('../mailer.js')
const router = express.Router();
const users = [{ email:'', password:'', roles:'' }];
let authUser=users;
let expireTime;
var otp;
const Sponsor = require("../model/Sponsor.js");
const Admin = require("../model/Admin.js");


router.post("/", async (req, res) => {
    const encryptedPassword = String(cryptoJs.MD5(req.body.password))
    try {
        const sponsor = await Sponsor.findOne({ sponsor_email: req.body.email});
        if (sponsor) {
          const encryptedPassword = String(cryptoJs.MD5(req.body.password))
                const result = encryptedPassword === sponsor.sponsor_password;
                if (result) {
                console.log('inside Sponsor login')
                otp = Math.floor(100000 + Math.random() * 900000)
                expireTime = "2 days"
                authUser = users.find(u => u.email=sponsor.sponsor_email)
                authUser = users.find(u => u.password=sponsor.sponsor_password)
                authUser = users.find(u => u.roles='viewer')
                const body = 
                    `<html>
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
                    
                          Hi ${sponsor.sponsor_name},
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
                    </html>`
                mailer.sendEmail(sponsor.sponsor_email, `Orphanslife Account - ${otp} is your verification code for secure access`, body, () => {
                })
            response(sponsor)
                }  else {
                  console.log('checking admin')
                  checkingAdmin()
        }
        } else {
            console.log('checking admin')
            checkingAdmin()
        }
      } catch (error) {
        console.log("no sponsor account found")
        const admin = await Admin.findOne({ admin_email: req.body.email });
            if(admin) {
                const encryptedPassword = String(cryptoJs.MD5(req.body.password))
                const result = encryptedPassword === admin.admin_password;
                if (result) {
                    console.log('inside Admin login')
                    otp = Math.floor(100000 + Math.random() * 900000)
                    expireTime = "1d"
                    const body = 
                        `<html>
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
                              
                                    Hi ${admin.admin_name},
                                    <br/>
                                    <br/>
                                    Greetings!
                              
                                    <br/>
                                    <br/>
                              
                                    You are just a step away from accessing your Orphanslife account
                              
                                    <br/>
                                    <br/>
                              
                                    Once you have verified the code, you'll be logged into app.
                              
                                    <br/>
                                    <br/>
                              
                                    <mark> Your OTP: ${otp}</mark><br/>
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
                              </html>`
                          authUser = users.find(u => u.email=admin.admin_email)
                          authUser = users.find(u => u.password=admin.admin_password)
                          if(admin.role_id===1) {
                              authUser = users.find(u => u.roles='editor')
                          } else if(admin.role_id===2) {
                              authUser = users.find(u => u.roles='editor')
                          } else if(admin.role_id===3) {
                              authUser = users.find(u => u.roles='admin')
                          }
                          mailer.sendEmail(admin.admin_email, `Orphanslife Account - ${otp} is your verification code for secure access`, body, ()=>{
      
                          })
                      response(admin)
                }  else {
                      res.send({
                          ok: false,
                          otp: null,
                          token: null
                      });
                }
            } else {
                res.status(400).json({
                    ok: false,
                    otp: null,
                    token: null
                });
            }
      }

      //null values checking
    function response(loggedInUser) {
        if(authUser.email!=null && authUser.password!=null && authUser.roles!=null) {
            const token = jwt.sign({
                email: authUser.email,
                roles: authUser.roles
            }, "jwtPrivateKey", { expiresIn: expireTime });
    
            res.status(200).json({
                ok: true,
                otp: otp,
                token: token,
                data: loggedInUser,
                roles: authUser.roles
            });
        }
    }
})

module.exports = router;