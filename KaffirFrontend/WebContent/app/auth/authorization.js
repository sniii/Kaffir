angular.module('auth').

service('authorization', function ($http) {
  var urlLogin = '/KaffirPseudoBackend/login';
  var urlCreateUser = '/KaffirPseudoBackend/createuser';
  
  this.login = function(credentials) {
	  return $http.post(urlLogin, credentials);
  };
  
  this.createUser = function(credentials) {
	  return $http.post(urlCreateUser, credentials);
  };
});