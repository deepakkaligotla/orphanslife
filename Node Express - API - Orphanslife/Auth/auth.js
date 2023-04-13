// Import dependencies
const jwt = require("jsonwebtoken");

module.exports = (req, res, next) => {

    console.log('inside Auth/auth.js')
    const token = req.header("x-auth-token");
    if (!token) return res.status(401).send({
        ok: false,
        error: "Access denied. No token provided"
    });

    try {

    console.log('inside Auth/auth.js')
        const decoded = jwt.verify(token, "jwtPrivateKey");
        req.user = decoded;
    } catch (error) {
        return res.status(401).send({
            ok: false,
            error: "Token expired"
        });
    }

    next();
}