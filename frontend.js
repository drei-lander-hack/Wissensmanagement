const express = require('express')
const logger = console
const SearhController = require('./SearchController')({logger})

const app = express()

app.use('/', express.static('frontend'))
app.get('/search', (req, res) => res.json(SearhController.search(req.query.value)))
app.post('/login', (req, res) => res.cookie('TOKEN', req.body.token, {}).redirect('/'))

app.listen(8000, () => {
  logger.info('Running on port 8000')
})
