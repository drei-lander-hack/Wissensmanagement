(function () {
  'use strict'

  var body = document.querySelector('body')

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
            '<a href="#" class="result__send-query dialog-trigger">Anfrage senden <i class="fas fa-paper-plane"></i></a>' +
            '</div>' +
            '</li>'
        }).join('\n')

        addDialogTriggerEvents()
      })
      .catch(function (error) {
        alert(error)
      })
  })

  var addDialogTriggerEvents = function() {
    var classname = document.getElementsByClassName("dialog-trigger");

    for (var i = 0; i < classname.length; i++) {
        classname[i].addEventListener('click', myFunction, false);
    }

  }

  

  var myFunction = function(event) {
    event.preventDefault()  
    
    body.classList.toggle('dialog-active')
  };


  

})()
