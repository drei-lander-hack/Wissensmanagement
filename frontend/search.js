(function () {
  'use strict'

  var form = document.querySelector('#search')
  var result = document.querySelector('#result-list')

  form.addEventListener('submit', function (event) {
    event.preventDefault()

    var search = form.elements['search'].value;
    result.innerHTML = '<li>Suchergebnis fuer "' + search + '"</li>'
  })
})()
