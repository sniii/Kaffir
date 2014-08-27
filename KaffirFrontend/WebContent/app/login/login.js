angular.module('login', [
	'model.User', 
	'auth',
	'ngCookies'
]).

controller('LoginController', ['$http', '$location', '$cookieStore', 'User', 'Authorization', function ($http, $location, $cookieStore, User, Authorization) {
    this.email = '';
    
    this.login = function () {
    	var self = this;
    	
    	var credentials = {
    		email: this.email,
    		password: ''
    	};
        	
    	var storeTokenAndChangePage = function(data) {
    		var token = data.token;
    		$cookieStore.put('token', token);
    		self._initAPI(token);
    		$location.path('shoppinglist-overview');
    	};
    	
    	var showAuthorizationError = function(data) {
    		alert("Error logging in, invalid credentials!");
    	};
    	
    	Authorization.login(credentials).success(storeTokenAndChangePage).error(showAuthorizationError);
    	
    	/*$http.post('/KaffirPseudoBackend/login', loginCredentials).success(function(data) {
    		if (data.status === "Ok") {
    			User.email = data.user.email;
    			User.username = data.user.username;
    			// TODO password??
    			$location.path("shoppinglist-overview");
    		}
        });*/
    };
    

    
    this._initAPI = function(token) {
    	$http.defaults.headers.common['kaffir-token'] = token || $cookies.token;
    };
}]);