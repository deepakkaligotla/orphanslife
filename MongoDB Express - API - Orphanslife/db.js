const mongo = require("mongodb");
const uri = process.env.ATLAS_URI || "";
let db

const conn = mongo.MongoClient.connect(uri, function(err, db){
    if(db)  db = conn.db("sample_training");
    if (err) throw err;
    console.log("Database created!");
})

module.exports = {db};
