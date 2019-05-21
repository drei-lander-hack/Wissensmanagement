(function () {
  'use strict'

  var body = document.querySelector('body')

  var form = document.querySelector('#search')
  var result = document.querySelector('#result-list')

  form.addEventListener('submit', function (event) {
    event.preventDefault()

    var headers = {Authorization: 'Bearer ' + window.getToken()}
    var search = form.elements['search'].value
<<<<<<< HEAD
    fetch('/search?value=' + encodeURIComponent(search), {headers})
=======
    fetch('https://tedevi.azurewebsites.net/api/users?value=' + encodeURIComponent(search), {headers})
>>>>>>> 7665426143659599bfa3e33de94b9f27cfe4fa1d
      .then(function (result) {
        if (!result.ok) {
          return Promise.reject(result.statusText)
        } else {
          return result.json()
        }
      })
      .then(function (list) {
        result.innerHTML = list.map(function (entry) {
          const skills = entry.skills
            .map(e => '<span class="tag">#' + e + '</span>')
            .join(' ')

          return '<li class="result">' +
            '<img src="' + entry.image + '" class="result__img">' +

            '<div class="result__inner">' +
            '<div>' +
            '<span class="result__name">' + entry.name + '</span>' +
            '<img class="result__company" src="img/' + entry.company.toLowerCase() + '.png">' +
            '<span class="result__projects">' + entry.projects.join(', ') + '</span>' +
            '<span class="result__skills">' + skills + '</span>' +
            '</div>' +
            '<div class="matching">' +
            '<a href="#" class="result__send-query dialog-trigger" data-name="'+ entry.name +'">Anfrage senden <i class="fas fa-paper-plane"></i></a>' +
            '<span class="result__rank">' + entry.rank.toFixed() + '% Matching</span>' +
            '</div>' +
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
