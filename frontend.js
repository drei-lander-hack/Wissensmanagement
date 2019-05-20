const fs = require('fs')
const express = require('express')
const https = require('https')
const logger = console
const SearhController = require('./SearchController')({logger})

const app = express()

const privateKey = fs.readFileSync('server.key')
const certificate = fs.readFileSync('server.crt')

app.use('/', express.static('frontend'))
app.get('/search', (req, res) => res.json(SearhController.search(req.query.value)))
app.post('/login', (req, res) => res.cookie('TOKEN', req.body.token, {}).redirect('/'))

https.createServer({
  key: privateKey,
  cert: certificate
}, app).listen(8000, () => {
  logger.info('Running on port 8000')
})
