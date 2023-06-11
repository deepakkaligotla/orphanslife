const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');
var Admin = new Schema({
    admin_id:{
        type: Number
    },
    admin_name:{
        type: String
    },
    admin_dob:{
        type: String
    },
    admin_gender:{
        type: String
    },
    admin_govt_id_type:{
        type: String
    },
    admin_govt_id:{
        type: String
    },
    admin_mobile:{
        type: String
    },
    admin_email:{
        type: String
    },
    admin_password:{
        type: String
    },
    admin_address:{
        type: String
    },
    admin_location_id:{
        type: Number
    },
    role_id:{
        type: Number
    },
    admin_orphanage_id:{
        type: Number
    },
    admin_image:{
        type: String
    },
    admin_created_at:{
        type: String
    },
    admin_updated_at:{
        type: String
    }
}, { collection: 'admin' })
  
Admin.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Admin', Admin)