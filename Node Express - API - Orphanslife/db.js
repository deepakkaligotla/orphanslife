const mysql = require('mysql')

const pool = mysql.createPool({
    connectionLimit: 20,
    user: "admin",
    password: "$ujata214326",
    host: "orphanslife.caslfryoyuxg.ap-northeast-1.rds.amazonaws.com",
    port: 3306,
    database: "orphanslife"
})

module.exports = { pool }
