const express = require('express')
const utils = require('../../Functions/utils')
const router = express.Router()
const auth = require('../../API_Auth/auth.js')
const { admin, editor, viewer } = require("../../API_Auth/roles.js");
const Donation = require('../../Data/model/Donation.js')
const Sponsor = require('../../Data/model/Sponsor.js')
const mailer = require('../../Functions/mailer')
const { jsPDFInvoiceTemplate, OutputType } = require('../../Functions/DonationCertificate')

router.get('/donations', [auth], async (req, res) => {
    try {
      const data = await Donation.find();
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.get('/monthwise_donations', [auth], async (req, res) => {
    try {
        const data = await Donation.find(
            {
                payment_status : 'SUCCESS' 
            }	,{
                "DATE_FORMAT": 1,
                "sum": 1
            }
        ).sort(
            {
                "Year" : 1,
                "Month" : 1
            }
        );
        res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.post('/newdonation', [auth], async (req, res) => {
    const newDonation = new Donation(req.body)
    try {
        const dataToSave = await newDonation.save();
        const donatedSponsor = await Sponsor.findOne({sponsor_id: newDonation.sponsor_id});
        generateDonationCertificate()
        const body = `
        <html>
        <style>
          mark {
            background-color: yellow;
            color: black;
          }
  
          .container {
            height: 200px;
            position: relative;
            border: 3px solid green;
          }
          
          .vertical-center {
            margin: 0;
            position: absolute;
            top: 50%;
            -ms-transform: translateY(-50%);
            transform: translateY(-50%);
          }
        </style>
          <body>
            <br/>
            <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/welcome.png" alt="Welcome to Orphanslife App" style="width:700px;height:150px;">
            <br/>
            <br/>
            <br/>
  
            Hi ${donatedSponsor.sponsor_name},
            <br/>
            <br/>
            Thank you for the Donation you have made, we have received the amount successfully.
  
            <br/>
            <br/>
  
            <mark>Amount: ${dataToSave.amount}</mark>
  
            <br/>
            <br/>
            
            GOD BLESS YOU!.<br/>
            <img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/party_popper.gif" alt="Success" style="width:48px;height:48px;">
            <br/>
            <br/>
            <div className="container">
              <div className="vertical-center">
                <a href='http://localhost/auth/login'" type="button" style="background-color: blue;color: white;border: 1px solid #e4e4e4;padding: 8px;border-radius: 3px;cursor: pointer;">Click here to Login</a>
              </div>
            </div>
            <br/>
            <br/>
            
            blah blah blah
  
            <br/>
            <br/>
            
            © 2 0 2 3   O r p h a n s l i f e
  
            <br/>
            <a href="http://localhost">localhost</a>
            <br/>
  
            Kasarsai Rd, Phase 2, Hinjewadi Rajiv Gandhi Infotech Park,<br/>
            Hinjawadi,<br/>
            Pimpri-Chinchwad,<br/>
            Maharashtra 411057<br/><br/>
            <a href="/Unsubscribe">Unsubscribe</a> •  <a href="/browser">View in browser</a> •  <a href="/terms">Terms of Use</a> •  <a href="/privacy">Privacy PolicyPage</a>
  
            <br/>
            Thank you.
          </body>
        </html>
      `
      
      mailer.sendEmailWithAttachments(donatedSponsor.sponsor_email, `Thank you for Donating ${dataToSave.donation_id} - Certificate Attached`, body, ["Donation Certificate.pdf","/Users/deepak.kaligotla/Documents/HMBP/awesome_project/orphanslife/server/Donation Certificate.pdf"], (result) => {
        if(result===null) {
          res.status(200).json(dataToSave)
        } else res.status(400).json({ message: error.message })
      })
    }
    catch (error) {
        res.status(400).json({ message: error.message })
    }
})

function generateDonationCertificate() {
    var props = {
        outputType: OutputType.Save,
        returnJsPDFDocObject: true,
        fileName: "Donation Certificate",
        orientationLandscape: true,
        compress: true,
        logo: {
            src: "",
            type: 'PNG',
            width: 53.33,
            height: 26.66,
            margin: {
                top: 0,
                left: 0
            }
        },
        stamp: {
            inAllPages: true,
            src: "",
            type: 'JPG',
            width: 20,
            height: 20,
            margin: {
                top: 0,
                left: 0
            }
        },
        business: {
            name: "Orphanslife",
            address: "Orphanage Management System",
            phone: "(+91) 93816 40235",
            email: "admin@orphanslife.in",
            email_1: "deepak.kaligotla@gmail.com",
            website: "www.orphanslife.in",
            otherInfo: "PAN - DTWPK2732P",
        },
        contact: {
            label: "Donation Certificate issued for:",
            name: "Deepak Uma Sai Kaligotla",
            address: "Bangalore",
            phone: "(+91) 938 164 0235",
            email: "deepak.kaligotla@gmail.com",
            otherInfo: "PAN - DTWPK2732P",
        },
        invoice: {
            label: "Donation ID #: ",
            num: 45931,
            invDate: ""+new Date(),
            invGenDate: ""+new Date(),
            headerBorder: false,
            tableBodyBorder: false,
            header: [
              {
                title: "#", 
                style: { 
                  width: 10 
                } 
              }, 
              { 
                title: "Title",
                style: {
                  width: 30
                } 
              }, 
              { 
                title: "Description",
                style: {
                  width: 80
                } 
              }, 
              { title: "Amount"},
            ],
            table: Array.from(Array(2), (item, index)=>([
                index + 1,
                "Donation",
                "Orphanage and cause",
                20000.50,
            ])),
            additionalRows: [{
                col1: 'Total:',
                col2: 'Rs. XXXXX.XX',
                col3: 'ALL',
                style: {
                    fontSize: 14
                }
            },
            {
                col1: 'VAT:',
                col2: '20',
                col3: '%',
                style: {
                    fontSize: 10
                }
            },
            {
                col1: 'SubTotal:',
                col2: 'Rs. XX,XXX.XX',
                col3: 'ALL',
                style: {
                    fontSize: 10
                }
            }],
            invDescLabel: "Donation Note",
            invDesc: "This Ceritifcate is valid for Income Tax Exemption\nThank you so much for donating these Orphanages, GOD BLESS YOU! Our children are so happy to meet you in real you can contact any nearest Orphanage volunteer or Guardian and request for visit at your convience",
        },
        footer: {
            text: "The invoice is created on a computer and is valid without the signature and stamp.",
        },
        pageEnable: true,
        pageLabel: "Page ",
    };
    jsPDFInvoiceTemplate(props)
}

router.get('/findByIdDonation/:id', [auth], async (req, res) => {
    try {
        const data = await Donation.findOne({ req_no: req.params.id });
        res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.get('/success_payments', [auth], async (req, res) => {
    try {
        const data = await Donation.find({payment_status: "SUCCESS"}).count();
        res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.get('/failed_payments', [auth], async (req, res) => {
    try {
        const data = await Donation.find({payment_status: "FAILED"}).count();
        res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

router.delete('/deletedonation/:donation_id', [auth, admin], async (req, res) => {
    try {
      const data = await Donation.deleteOne({donation_id: req.params.donation_id});
      res.status(200).json(utils.createSuccessResult(data))
    }
    catch (error) {
        res.status(400).json(utils.createErrorResult(error))
    }
})

module.exports = router