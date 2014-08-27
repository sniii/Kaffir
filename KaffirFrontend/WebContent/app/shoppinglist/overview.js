angular.module('shoppinglist-overview', [
	'model.ShoppingList',
	'model.User'
]).

controller('ShoppingListOverviewController', ['$http', '$location', 'ShoppingListModel', 'User', function($http, $location, ShoppingListModel, User) {
    this.lists = [];

    this.loadLists = function() {
        var self = this;
        $http.get('/KaffirPseudoBackend/shoppinglist/lists').success(function(data) {
            self.lists = ShoppingListModel.lists = data;
        });
    };

    this.select = function(shoppinglist) {
        $http.get('/KaffirPseudoBackend/shoppinglist/list/' + shoppinglist.id, {
        	params: { userID: User.email }
        }).success(function(data) {
            ShoppingListModel.activeList.id = data.id;
            ShoppingListModel.activeList.items = data.items;
            $location.path('shoppinglist');
        });
    };
}]);