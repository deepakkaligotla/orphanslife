const bcrypt = require("bcrypt");

(async () => {
    bcrypt.genSalt(saltRounds, function(err, salt) {
        bcrypt.hash(myPlaintextPassword, salt, function(err, hash) {
            return hash
        });
    });
})();