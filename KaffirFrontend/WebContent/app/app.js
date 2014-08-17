angular.module('kaffir', [
	'login',
	'shoppinglist',
	'shoppinglist-overview',
	'ngRoute'
]).

config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/', {templateUrl: 'app/login/login.html', controller: 'LoginController'}).
        when('/shoppinglist/', {templateUrl: 'app/shoppinglist/shoppinglist.html', controller: 'ShoppingListController'}).
        when('/shoppinglist-overview/', {templateUrl: 'app/shoppinglist/overview.html', controller: 'ShoppingListOverviewController'}).
        when('/createuser/', {templateUrl: 'app/login/createuser.html', controller: 'CreateUserController'}).
        otherwise({redirectTo: '/'});
}]).

factory("TypedItemNames", function() {
    var obj = {
        itemNames: null,

        add: function(name) {
            if (!this.itemNames) {
                this._load();
            }
            var existingName = this.itemNames[name];
            if (existingName) {
                existingName.count++;
            } else {
                this.itemNames[name] = {name: name, count: 1};
                this._save();
            }
        },

        search: function(partialName) {
            if (partialName.length === 0) {
                return;
            }
            if (!this.itemNames) {
                this._load();
            }
            partialName = partialName.toLowerCase();
            var hits = [];
            for (var name in this.itemNames) {
                if (this.itemNames.hasOwnProperty(name)) {
                    if (name.toLowerCase().indexOf(partialName) > -1) {
                        hits.push(name);
                    }
                    if (hits.length === 5) {
                        break;
                    }
                }
            }
            //return hits;
            return []; // TODO Hvad skal der vises af suggestions?
        },

        _load: function() {
            var items = localStorage.getItem("typedNames");
            console.log(items);
            if (items) {
                this.itemNames = JSON.parse(items);
            } else {
                this.itemNames = {};
            }
        },

        _save: function() {
            localStorage.setItem("typedNames", JSON.stringify(this.itemNames));
        }
    };
    return obj;
}).

controller("CreateUserController", ['$http', '$location', 'UserService', function($http, $location, UserService) {
    this.error = null;
    this.email = "";
    this.username = "";

    this.create = function() {
        var self = this;
        $http.get("data/createuser-fail-response.json").success(function(data) {
            UserService.email = self.email;
            UserService.username = self.username;
            if (data.status === "ok") {
                $location.path("shoppinglist");
            } else if (data.status = "user-exists") {
                self.error = "user exists!";
            }
        });
    };
}]);