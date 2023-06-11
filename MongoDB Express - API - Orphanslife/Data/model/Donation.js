const { Double } = require('mongodb');
const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var Donation = new Schema({
    donation_id:{
        type: Number
    },
    amount:{
        type: Number
    },
    payment_status:{
        type: String
    },
    sponsor_id:{
        type: Number
    },
    donation_created_at:{
        type: String
    },
    donation_updated_at:{
        type: String
    }
}, { collection: 'donation' })
  
Donation.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Donation', Donation)