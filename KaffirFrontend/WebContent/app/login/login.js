angular.module('login', [
	'model.User'                         
]).

controller('LoginController', ['$http', '$location', 'User', function ($http, $location, User) {
    this.email = '';
    this.login = function () {
    	var email = this.email;
    	var loginCredentials = {
    		email: email,
    		password: ''
    	}
    	$http.post('/KaffirBackend/login', loginCredentials).success(function(data) {
    		if (data.status === "Ok") {
    			User.email = data.user.email;
    			User.username = data.user.username;
    			// TODO password??
    			$location.path("shoppinglist-overview");
    		}
        });
    };
}]);