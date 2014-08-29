angular.module('login', [
    'auth',
	'ngCookies'
]).

controller('LoginController', ['$http', '$location', '$cookieStore', 'authorization' ,'kaffirAPI', function ($http, $location, $cookieStore, authorization, kaffirAPI) {
	this.error = null;
	this.email = '';
    
    this.login = function () {
    	var self = this;
    	
    	var credentials = {
    		email: this.email,
    		password: ''
    	};
        	
    	var storeTokenAndChangePage = function(data) {
    		if (data.status === "Ok") {
    			kaffirAPI.init(data.token);
    			$location.path('shoppinglist-overview');
    		} else {
    			self.error = true;
    		}
    	};
    	
    	var showError = function(data) {
    		alert("Error logging in, server might be inaccessible.");
    	};
    	
    	authorization.login(credentials).success(storeTokenAndChangePage).error(showError);
    };
}]);