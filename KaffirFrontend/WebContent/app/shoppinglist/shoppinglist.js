angular.module('shoppinglist', [
	'model.ShoppingList',
	'model.User'
]).

controller("ShoppingListController", ['$http', 'ShoppingListModel', 'User', function($http, ShoppingListModel, User) {
	
    this.items = ShoppingListModel.activeList.items;
    this.newItemName = "";
    this.matchingNames = [];

    this.addItem = function(event) {
      if (this._isEnter(event)) {
          this._addItemOnServer();
      } else {
          //this.matchingNames = TypedItemNames.search(this.newItemName);
      }
    };
    
    this._addItemOnServer = function() {
    	var self = this;
        $http.post("/KaffirPseudoBackend/shoppinglist/list/additem", {
        	listID: ShoppingListModel.activeList.id, item: self.newItemName
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
        	listID: ShoppingListModel.activeList.id, itemID: item.id
        }).success(function(data) {
        	self.items.splice(itemIndex, 1);
        });
    };
}]);