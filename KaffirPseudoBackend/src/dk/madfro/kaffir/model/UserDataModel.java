package dk.madfro.kaffir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
	
	public void addList(ShoppingList list) {
		shoppingLists.add(list);
	}
	
	public List<ShoppingList> getLists() {
		return shoppingLists;
	}
}