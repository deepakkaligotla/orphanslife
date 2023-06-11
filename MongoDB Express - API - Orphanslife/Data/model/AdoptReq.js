const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var AdoptReq = new Schema({
    req_no:{
        type: Number
    },
    user_id:{
        type: Number
    },
    admin_id:{
        type: Number
    },
    child_id:{
        type: Number
    },
    reason:{
        type: String
    },
    req_stage:{
        type: String
    },
    date_of_req:{
        type: String
    },
    last_checked:{
        type: String
    },
    req_comment:{
        type: String
    },
    next_check:{
        type: String
    },
    adopted:{
        type: String
    }
}, { collection: 'adopt_req' })

AdoptReq.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('adopt_req', AdoptReq)