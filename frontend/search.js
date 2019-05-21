(function () {
  'use strict'

  var body = document.querySelector('body')

  var form = document.querySelector('#search')
  var result = document.querySelector('#result-list')

  form.addEventListener('submit', function (event) {
    event.preventDefault()

    var headers = {Authorization: 'Bearer ' + window.getToken()}
    var search = form.elements['search'].value
    fetch('http://localhost:4567/search?value=' + encodeURIComponent(search), {headers})
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
            '<a href="#" class="result__send-query dialog-trigger" data-name="'+ entry.name +'">Anfrage senden <i class="fas fa-paper-plane"></i></a>' +
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
        classname[i].addEventListener('click', openDialog, false);
    }

  }

  

  var openDialog = function(event) {
    event.preventDefault()  
    var searchValue = document.querySelector('.search__input').value;
    var contactNameField = document.querySelector('.dialog-contactname');
    var messageField = document.querySelector('.dialog-contactmsg');
    
    var contactName = event.target.getAttribute('data-name');
    
    contactNameField.value = contactName;
    messageField.value = "Hallo " + contactName + "\n" +
      "ich habe gesehen, du hast auch zum Thema " + searchValue + " gearbeitet.\nKönnen wir uns treffen?";

    body.classList.toggle('dialog-active')
  };


  var closeButton = document.querySelector('.dialog-close')

  closeButton.addEventListener('click', function (event) {
    event.preventDefault()

    body.classList.toggle('dialog-active')
  });


  var sendButton = document.querySelector('.dialog-send')

  sendButton.addEventListener('click', function (event) {
    event.preventDefault()

    body.classList.toggle('dialog-active')
    body.classList.toggle('show-message')

    setTimeout(function(){
      body.classList.toggle('show-message')
    },3000);
  });
  
  

})()
