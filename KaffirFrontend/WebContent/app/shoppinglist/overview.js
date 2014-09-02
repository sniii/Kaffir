angular.module('shoppinglist-overview', [
	'model.ShoppingList',
	'model.User'
]).

controller('ShoppingListOverviewController', ['$http', '$location', 'ShoppingListModel', 'User', function($http, $location, ShoppingListModel, User) {
    this.lists = [];
    this.sharedLists = [];

    this.loadLists = function() {
        var self = this;
        $http.get('/KaffirPseudoBackend/shoppinglist/lists').success(function(lists) {
            self.lists = lists.shoppingLists;
            self.sharedLists = lists.sharedShoppingLists;
        });
    };

    this.select = function(shoppinglist) {
    	$location.path('shoppinglist/' + shoppinglist.id);
    };
    
    this.share = function(shoppinglist, $event) {
    	var user = prompt("Enter email", "");
    	var data = {targetUserID: user, listID: shoppinglist.id};
    	$http.post('/KaffirPseudoBackend/shoppinglist/list/share', data).success(function(response) {
    		if (response.status === "Ok") {
    			alert("Yay! List shared with " + data.targetUserID);
    		} else {
    			alert("Couldn't share list, user " + data.targetUserID + " doesn't exist.");	
    		}
    	}).error(function() {
    		alert("Yikes...err'ed!");
    	});
    	this._preventEventBubbling($event);
    };
    
    this.unshare = function(shoppinglist, $event) {
    	var data = {listID: shoppinglist.id};
    	$http.post('/KaffirPseudoBackend/shoppinglist/list/unshare', data).success(function(response) {
    		alert("List not shared anymore.");
    		shoppinglist.shared = false;
    	}).error(function() {
    		alert("Yikes...err'ed!");
    	});
    	this._preventEventBubbling($event);
    };
    
    this._preventEventBubbling = function($event) {
    	$event.preventDefault();
    	$event.stopPropagation();
    	$event.cancelBubble = true;
        $event.returnValue = false; 
    };
}]);