package dk.madfro.kaffir.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	private static final String databasePath = "c:\\kaffir-database\\kaffir.db";
	private Map<User, UserDataModel> database;
	
	private ShoppingListFacade() {
		load();
	}
	
	@SuppressWarnings("unchecked")
	private void load() {
		File file = new File(databasePath);
		if (file.exists()) {
			try (FileInputStream fileStream = new FileInputStream(file);
				 ObjectInputStream objectStream = new ObjectInputStream (fileStream)) {
				 database = (Map<User, UserDataModel>)objectStream.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			buildDummyData();
		}
	}
	
	public void save() {
		File file = new File(databasePath);
		try (FileOutputStream fileStream = new FileOutputStream(file);
			 ObjectOutputStream objectStream = new ObjectOutputStream (fileStream)) {
			objectStream.writeObject(database);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		user = new User("Mads", "mdiget@gmail.com");
		model = new UserDataModel();
		shoppinglist = new ShoppingList("Mads", "mdiget@gmail.com");
		shoppinglist.addItem(new Item("Tyggegummi", "Diverse"));
		shoppinglist.addItem(new Item("Pærer", "Frugt"));
		shoppinglist.addItem(new Item("Sko", "Tøj og sko"));
		shoppinglist.addItem(new Item("Bacon", "Pålæg"));
		model.addList(shoppinglist);
		database.put(user, model);
	}
}