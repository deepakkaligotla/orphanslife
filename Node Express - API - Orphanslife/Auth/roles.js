function admin(req, res, next) {
    console.log("Called admin role")
    if (!req.user.roles.includes("admin")) return res.status(403).send({
        ok: false,
        error: "Access denied."
    });

    next();
}

function editor(req, res, next) {
    console.log("Called editor role")
    console.log(req.user)
    if (!req.user.roles.includes("editor")) return res.status(403).send({
        ok: false,
        error: "Access denied."
    });

    next();
}

function viewer(req, res, next) {
    console.log("Called viewer role")
    if (!req.user.roles.includes("viewer")) return res.status(403).send({
        ok: false,
        error: "Access denied."
    });

    next();
}

module.exports = { admin, editor, viewer };