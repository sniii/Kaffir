angular.module('createuser', [
	'model.User',
	'auth',
	'ngCookies'
]).

controller("CreateUserController", ['$http', '$location', '$cookieStore', 'Authorization', function($http, $location, $cookieStore, Authorization) {
    this.error = null;
    this.email = "";
    this.username = "";

    this.create = function() {
    	var self = this;
    	
    	var storeTokenAndChangePage = function(data) {
    		var token = data.token;
    		$cookieStore.put('token', token);
    		self._initAPI(token);
    		$location.path('shoppinglist-overview');
    	};
    	
    	var showAuthorizationError = function(data) {
    		self.error = "User exists!";
    	};
    	
        $http.post('/KaffirPseudoBackend/createuser', {email: this.email, username: this.username}).
        	success(storeTokenAndChangePage).
        	error(showAuthorizationError(data));
    };
    
    this._initAPI = function(token) {
    	$http.defaults.headers.common['kaffir-token'] = token || $cookies.token;
    };
}]);