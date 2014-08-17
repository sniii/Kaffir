angular.module('model.ShoppingList', []).

service('ShoppingListModel', function() {
	this.lists = [];
	this.activeList = {
		id: '',
		items: []
	}
});