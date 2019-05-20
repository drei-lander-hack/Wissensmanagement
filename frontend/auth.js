(function () {
  var cachedToken

  window.config = {
    clientId: '3e916f2d-3563-4f27-942a-54b826be3232',
    cacheLocation: 'localStorage'
  }

  var authContext = new AuthenticationContext(config)

  if (authContext.isCallback(window.location.hash)) {
    authContext.handleWindowCallback();
  }

  var user = authContext.getCachedUser()
  if (user) {
    authContext.acquireToken('api://devsbb-hackathon-backend', function (errorDesc, token, error) {
      if (error) {
        if (config.popUp) {
          // If using popup flows
          authContext.acquireTokenPopup(webApiConfig.resourceId, null, null,  function (errorDesc, token, error) {});
        } else {
          // In this case the callback passed in the Authentication request constructor will be called.
          authContext.acquireTokenRedirect(webApiConfig.resourceId, null, null);
        }
      } else {
        cachedToken = token
      }
    })
  } else {
    // Initiate login
    authContext.login()
  }

  window.getToken = function () {
    return cachedToken
  }
})()
