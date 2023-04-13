const mysql = require('mysql')

const pool = mysql.createPool({
    connectionLimit: 20,
    user: "viewer",
    password: "$ponsor@143",
    host: "localhost",
    port: 3306,
    database: "orphanslife"
})

module.exports = { pool }
