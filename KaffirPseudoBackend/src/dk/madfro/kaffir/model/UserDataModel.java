package dk.madfro.kaffir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
	private Set<ShoppingList> sharedShoppingLists = new HashSet<ShoppingList>();
	
	public void addShoppingList(ShoppingList list) {
		shoppingLists.add(list);
	}
	
	public void addSharedShoppingList(ShoppingList list) {
		sharedShoppingLists.add(list);
	}
	
	public List<ShoppingList> getShoppingLists() {
		return shoppingLists;
	}
	
	public Set<ShoppingList> getSharedShoppingLists() {
		return sharedShoppingLists;
	}
	
	public ShoppingList getShoppingListByID(String id) {
		for (ShoppingList list : shoppingLists) {
			if (list.getId().equalsIgnoreCase(id)) {
				return list;
			}
		}
		return null;
	}
	
	public ShoppingList getSharedShoppingListByID(String id) {
		for (ShoppingList list : sharedShoppingLists) {
			if (list.getId().equalsIgnoreCase(id)) {
				return list;
			}
		}
		return null;
	}
}