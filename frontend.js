const express = require('express')
const logger = console

const app = express()

app.use('/', express.static('frontend'))

app.listen(8000, () => {
  logger.info('Running on port 8000')
})
