const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var Role = new Schema({
    role_id:{
        type: Number
    },
    role:{
        type: String
    },
    role_created_at:{
        type: String
    },
    role_updated_at:{
        type: String
    }
}, { collection: 'role' })

Role.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Role', Role)