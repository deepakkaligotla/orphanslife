const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var AdoptiveStatus = new Schema({
    adoptive_status_id:{
        type: Number
    },
    status:{
        type: String
    }
}, { collection: 'adoptive_status' })

AdoptiveStatus.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('adoptive_status', AdoptiveStatus)