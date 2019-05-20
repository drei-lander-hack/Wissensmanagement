(function () {
  'use strict'

  var form = document.querySelector('#search')
  var result = document.querySelector('#result-list')
  var headers = {}

  form.addEventListener('submit', function (event) {
    event.preventDefault()

    var search = form.elements['search'].value
    fetch('/search?value=' + encodeURIComponent(search), {headers})
      .then(function (result) {
        if (!result.ok) {
          return Promise.reject(result.statusText)
        } else {
          return result.json()
        }
      })
      .then(function (list) {
        result.innerHTML = list.map(function (entry) {
          return '<li class="result">' +
            '<img src="' + entry.picture + '" class="result__img">' +
            '<div class="result__inner">' +
            '<span class="result__name">' + entry.name + '</span>' +
            '<a href="#" class="result__send-query">Anfrage senden <i class="fas fa-paper-plane"></i></a>' +
            '</div>' +
            '</li>'
        }).join('\n')
      })
      .catch(function (error) {
        alert(error)
      })
  })
})()
