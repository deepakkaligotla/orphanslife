const jwt = require("jsonwebtoken");
const express = require("express");
const cryptoJs = require('crypto-js');
const db = require('../db')

const router = express.Router();

const users = [{ email:'', password:'', roles:'' }];
let authUser=users;

router.post("/", async (req, res) => {
    console.log('inside Routes/auth.js')
    const {email, password} = req.body
    const encryptedPassword = String(cryptoJs.MD5(password))

    const statement = `SELECT * FROM sponsor where sponsor_email="${email}" and sponsor_password="${encryptedPassword}"`
    db.pool.query(statement, [email,encryptedPassword], (error, result) => {
        if(Array.isArray(result)) {
            if(result[0]!=null) {
                if(!result[0].role_id) {
                    authUser = users.find(u => u.email=result[0].sponsor_email)
                    authUser = users.find(u => u.password=result[0].sponsor_password)
                    authUser = users.find(u => u.roles='viewer')
                    response()
                }
            } else {
                checkingAdmin()
            }
        }
    });

    function checkingAdmin() {
        const statement = `SELECT * FROM admin where admin_email="${email}" and admin_password="${encryptedPassword}"`
        db.pool.query(statement, [email,encryptedPassword], (error, result) => {
            if(Array.isArray(result)) {
                if(result[0]!=null) {
                    authUser = users.find(u => u.email=result[0].admin_email)
                    authUser = users.find(u => u.password=result[0].admin_password)
                    if(result[0].role_id===1) {
                        authUser = users.find(u => u.roles='editor')
                    } else if(result[0].role_id===2) {
                        authUser = users.find(u => u.roles='editor')
                    } else if(result[0].role_id===3) {
                        authUser = users.find(u => u.roles='admin')
                    }
                    response()
                }  else {
                    res.send({
                        ok: false,
                        token: null
                    });
                }
            }
        });
    }

    //null values checking
    function response() {
        if(authUser.email!=null && authUser.password!=null && authUser.roles!=null) {
            const token = jwt.sign({
                email: authUser.email,
                role: authUser.role,
            }, "jwtPrivateKey", { expiresIn: "5m" });
    
            res.send({
                ok: true,
                token: token
            });
        }
    }
})

module.exports = router;