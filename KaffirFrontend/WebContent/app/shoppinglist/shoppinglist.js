angular.module('shoppinglist', [
    'auth'
]).

controller("ShoppingListController", function($http, $routeParams, $location, kaffirAPI) {
	this.shoppingList = null;
    this.items = [];
    this.newItemName = "";
    this.matchingNames = [];
    
    this.loadShoppingList = function() {
    	var self = this;
    	var id = $routeParams.id;
    	$http.get('/KaffirPseudoBackend/shoppinglist/list/' + id).success(function(list) {
    		self.shoppingList = list;
    		self.items = list.items;
    	});
    }

    this.addItem = function(event) {
      if (this._isEnter(event)) {
          this._addItemOnServer();
      } else {
          //this.matchingNames = TypedItemNames.search(this.newItemName);
      }
    };
    
    this._addItemOnServer = function() {
    	var self = this;
    	if (this.newItemName.length === 0) {
    		return;
    	}
        $http.post("/KaffirPseudoBackend/shoppinglist/list/additem", {
        	listID: self.shoppingList.id, itemName: self.newItemName
        }).success(function(data) {
        	self.items.push({name: self.newItemName, category: "?"});
            //TypedItemNames.add(self.newItemName);
            self.newItemName = "";
        });
    };

    this._isEnter = function(event) {
      return (event.which === 13);
    };

    this.removeItem = function(itemIndex) {
        var item = this.items[itemIndex]; //this.items.splice(item, 1);
        var self = this;
        $http.post("/KaffirPseudoBackend/shoppinglist/list/removeitem", {
        	listID: self.shoppingList.id, itemID: item.id
        }).success(function(data) {
        	self.items.splice(itemIndex, 1);
        });
    };
    
    this.logout = function() {
    	kaffirAPI.logout();
    	$location.path('/login');
    };
    
    this.loadShoppingList();
});