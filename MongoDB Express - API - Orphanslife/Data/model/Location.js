const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var Location = new Schema({
    location_id:{
        type: Number
    },
    pincode:{
        type: Number
    },
    area:{
        type: String
    },
    city:{
        type: String
    },
    district:{
        type: String
    },
    status:{
        type: String
    }
}, { collection: 'location' })

Location.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Location', Location)