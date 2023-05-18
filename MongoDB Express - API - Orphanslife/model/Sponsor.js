const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var Sponsor = new Schema({
    sponsor_id:{
        type: Number
    },
    sponsor_name:{
        type: String
    },
    sponsor_dob:{
        type: String
    },
    sponsor_gender:{
        type: String
    },
    sponsor_govt_id_type:{
        type: String
    },
    sponsor_govt_id:{
        type: String
    },
    sponsor_mobile:{
        type: String
    },
    sponsor_email:{
        type: String
    },
    sponsor_password:{
        type: String
    },
    marital_status:{
        type: String
    },
    sponsor_image:{
        type: String
    },
    sponsor_address:{
        type: String
    },
    sponsor_location_id:{
        type: Number
    },
    spouce_name:{
        type: String
    },
    spouce_dob:{
        type: String
    },
    spouce_govt_id_type:{
        type: String
    },
    spouce_govt_id:{
        type: String
    },
    spouce_mobile:{
        type: String
    },
    spouce_image:{
        type: String
    },
    donation_id:{
        type: Number
    },
    sponsor_created_at:{
        type: String
    },
    sponsor_updated_at:{
        type: String
    }
}, { collection: 'sponsor' })

Sponsor.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Sponsor', Sponsor)