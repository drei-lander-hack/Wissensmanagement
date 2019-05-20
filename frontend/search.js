(function () {
  'use strict'

  var token = document.cookie.match(/\bTOKEN=([\w.-]*)/)
  if (!token || !token[1]) {
    location.href = ''
  }
  var headers = {
    authorization: 'Bearer ' + token[1]
  }

  var form = document.querySelector('#search')
  var result = document.querySelector('#result-list')

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
          return '<li>' +
            '<img src="' + entry.picture + '">' +
            '<span class="name">' + entry.name + '</span>' +
            '</li>'
        }).join('\n')
      })
      .catch(function (error) {
        alert(error)
      })
  })
})()
