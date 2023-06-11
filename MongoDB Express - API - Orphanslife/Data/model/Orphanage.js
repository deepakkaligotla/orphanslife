const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var Orphanage = new Schema({
    orphanage_id:{
        type: Number
    },
    type:{
        type: String
    },
    orphanage_address:{
        type: String
    },
    orphanage_location_id:{
        type: Number
    },
    contact_person:{
        type: String
    },
    orphanage_mobile1:{
        type: String
    },
    orphanage_mobile2:{
        type: String
    },
    orphanage_email:{
        type: String
    },
    in_home:{
        type: Number
    },
    adoptable:{
        type: Number
    },
    boys:{
        type: Number
    },
    girls:{
        type: Number
    },
    orphanage_image:{
        type: Number
    },
    orphanage_created_at:{
        type: String
    },
    orphanage_updated_at:{
        type: String
    }
}, { collection: 'orphanage' })
  
Orphanage.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Orphanage', Orphanage)