angular.module('createuser', [
	'model.User'                         
]).

controller("CreateUserController", ['$http', '$location', 'User', function($http, $location, User) {
    this.error = null;
    this.email = "";
    this.username = "";

    this.create = function() {
    	var self = this;
        $http.post('/KaffirPseudoBackend/createuser', {email: this.email, username: this.username}).success(function(data) {
            if (data.status === "Ok") {
                User.email = data.user.email;
                User.username = data.user.username;
                $location.path("shoppinglist-overview");
            } else if (data.status === "UserAlreadyExists") {
                self.error = "User exists!";
            }
        });
    };
}]);