angular.module('createuser', [
	'auth',
	'ngCookies'
]).

controller("CreateUserController", ['$http', '$location', '$cookieStore', 'authorization', 'kaffirAPI', function($http, $location, $cookieStore, authorization, kaffirAPI) {
    this.error = null;
    this.email = "";
    this.username = "";

    this.create = function() {
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
    	
    	authorization.createUser(credentials).success(storeTokenAndChangePage).error(showError);
    };
}]);