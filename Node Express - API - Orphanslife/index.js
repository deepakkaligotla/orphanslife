const http = require("http");
const app = require("./app");
const server = http.createServer(app);
const config = require('config')
const port = config.get("portno")
const MessageRouter = require('./routes/messages')

app.use(MessageRouter)

server.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
