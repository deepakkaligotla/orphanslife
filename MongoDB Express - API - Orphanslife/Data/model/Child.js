const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var Child = new Schema({
    child_id:{
        type: Number
    },
    child_name:{
        type: String
    },
    child_dob:{
        type: String
    },
    child_gender:{
        type: String
    },
    admitted_date:{
        type: String
    },
    leave_date:{
        type: String
    },
    mother_name:{
        type: String
    },
    father_name:{
        type: String
    },
    child_mobile:{
        type: String
    },
    child_image:{
        type: String
    },
    status_id:{
        type: Number
    },
    admin_id:{
        type: Number
    },
    child_created_at:{
        type: String
    },
    child_updated_at:{
        type: String
    }
}, { collection: 'child' })
  
Child.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Child', Child)