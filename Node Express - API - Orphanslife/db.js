const mysql = require('mysql')

const pool = mysql.createPool({
    connectionLimit: 20,
    user: "root",
    password: "$ujata214326",
    host: "localhost",
    port: 3306,
    database: "orphanslife"
})

module.exports = { pool }
