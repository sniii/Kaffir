angular.module('auth', []).

service('Authorization', ['$http', function ($http) {
  var url = '/KaffirPseudoBackend/login';
  
  this.login = function(credentials) {
	  return $http.post(url, credentials);
  };
}]);