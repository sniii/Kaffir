package dk.madfro.kaffir.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingListFacade {
	
	private static class InstanceHolder {
		static final ShoppingListFacade instance = new ShoppingListFacade();
	}
	
	public static ShoppingListFacade instance() {
		return InstanceHolder.instance;
	}
	
	////////////////////////////////////////////////////////////////////
	
	private Map<User, UserDataModel> database;
	
	private ShoppingListFacade() {
		load();
	}
	
	private void load() {
		buildDummyData();
	}
	
	public void save() {
	}
	
	public List<ShoppingList> getShoppingLists(String userID) {
		return getDataForUser(userID).getLists();
	}
	
	public ShoppingList getShoppingListByID(String userID, String listID) {
		UserDataModel model = getDataForUser(userID);
		for (ShoppingList list : model.getLists()) {
			if (list.getId().equalsIgnoreCase(listID)) {
				return list;
			}
		}
		return null;
	}
	
	public boolean userExists(String userID) {
		return database.containsKey(new User("", userID));
	}
	
	public User getUser(String userID) {
		for (User user : database.keySet()) {
			if (user.getEmail().equalsIgnoreCase(userID)) {
				return user;
			}
		}
		return null;
	}
	
	private UserDataModel getDataForUser(String userID) {
		return database.get(new User("", userID));
	}
	
	private void buildDummyData() {
		database = new HashMap<User, UserDataModel>();
		
		User user = new User("Morten", "mortenfb@gmail.com");
		UserDataModel model = new UserDataModel();
		ShoppingList shoppinglist = new ShoppingList("Morten", "mortenfb@gmail.com");
		shoppinglist.addItem(new Item("Bananer", "Frugt"));
		shoppinglist.addItem(new Item("Vandmelon", "Frugt"));
		shoppinglist.addItem(new Item("Adidas sneakers", "Tøj og sko"));
		model.addList(shoppinglist);
		shoppinglist = new ShoppingList("Morten", "mortenfb@gmail.com");
		shoppinglist.addItem(new Item("Item 1", "Lala"));
		shoppinglist.addItem(new Item("Item 2", "Lulu"));
		shoppinglist.addItem(new Item("Item 3", "Tøj og sko"));
		model.addList(shoppinglist);
		database.put(user, model);
		
		user = new User("Kimia", "kimiafrost@gmail.com");
		model = new UserDataModel();
		shoppinglist = new ShoppingList("Kimia", "kimiafrost@gmail.com");
		shoppinglist.addItem(new Item("Marlboro Light", "Cigaretter"));
		shoppinglist.addItem(new Item("Æbler", "Frugt"));
		model.addList(shoppinglist);
		database.put(user, model);
	}
}