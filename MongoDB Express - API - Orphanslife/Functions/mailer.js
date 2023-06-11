const nodemailer = require('nodemailer')

function sendEmailWithAttachments(email, subject, body, [filename, path], callback) {
  // connecting to the smtp server
  const transporter = nodemailer.createTransport({
    host: 'smtp.gmail.com',
    secure: true,
    port: 465,
    auth: {
      user: 'deepak.kaligotla@gmail.com',
      pass: 'mksietyoxirejlnq',
    },
  })

  const mailOptions = {
    from: 'donations@orphanslife.in',
    to: email,
    subject: subject,
    html: body,
    attachments: [{
      filename: filename,
      path: path
    }]
  }

  transporter.sendMail(mailOptions, function (error, info) {
    callback(error, info)
  })
}

function sendEmail(email, subject, body, callback) {
  // connecting to the smtp server
  const transporter = nodemailer.createTransport({
    host: 'smtp.gmail.com',
    secure: true,
    port: 465,
    auth: {
      user: 'deepak.kaligotla@gmail.com',
      pass: 'mksietyoxirejlnq',
    },
  })

  const mailOptions = {
    from: 'donations@orphanslife.in',
    to: email,
    subject: subject,
    html: body,
  }

  transporter.sendMail(mailOptions, function (error, info) {
    callback(error, info)
  })
}

module.exports = {
  sendEmail: sendEmail,
}
