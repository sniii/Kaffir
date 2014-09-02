angular.module('auth').

factory('kaffirAPI', function($http) {
  return {
      init: function(token) {
    	  if (token) {
    		  localStorage.setItem('token', token);
    	  }
          $http.defaults.headers.common['kaffir-token'] = token || localStorage.getItem("token");
      },
      logout: function() {
    	  $http.defaults.headers.common['kaffir-token'] = undefined;
    	  localStorage.removeItem("token");
      }
  };
});