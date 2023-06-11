const { Double } = require('mongodb');
const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var OrphanageDetails = new Schema({
    event_id:{
        type: Number
    },
    orphanage_id:{
        type: Number
    },
    details:{
        type: String
    },
    image_1:{
        type: String
    },
    image_2:{
        type: String
    },
    image_3:{
        type: String
    },
    image_4:{
        type: String
    },
    image_5:{
        type: String
    },
}, { collection: 'orphanage_activities' })
  
OrphanageDetails.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('OrphanageDetails', OrphanageDetails)