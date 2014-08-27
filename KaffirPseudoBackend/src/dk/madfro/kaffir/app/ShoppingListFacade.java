package dk.madfro.kaffir.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.madfro.kaffir.model.Item;
import dk.madfro.kaffir.model.ShoppingList;
import dk.madfro.kaffir.model.User;
import dk.madfro.kaffir.model.UserDataModel;

public class ShoppingListFacade implements ShoppingListAPI {
	private static class InstanceHolder {
		static final ShoppingListFacade instance = new ShoppingListFacade();
	}
	
	public static ShoppingListAPI instance() {
		return InstanceHolder.instance;
	}
	
	////////////////////////////////////////////////////////////////////
	
	private static final String databasePath = "c:\\kaffir-database\\kaffir.db";
	private Map<User, UserDataModel> database;
	
	private ShoppingListFacade() {
		load();
	}
	
	@Override
	public List<ShoppingList> getShoppingLists(User user) {
		return database.get(user).getLists();
	}
	
	@Override
	public ShoppingList getShoppingListByID(User user, String listID) {
		UserDataModel model = database.get(user);
		for (ShoppingList list : model.getLists()) {
			if (list.getId().equalsIgnoreCase(listID)) {
				return list;
			}
		}
		return null;
	}
	
	@Override
	public boolean userExists(String userID) {
		return database.containsKey(new User("", userID));
	}
	
	@Override
	public User getUser(String userID) {
		for (User user : database.keySet()) {
			if (user.getEmail().equalsIgnoreCase(userID)) {
				return user;
			}
		}
		return null;
	}
	
	public User createUser(String userID, String username) throws UsernameAlreadyExistsException {
		if (userExists(userID)) {
			throw new UsernameAlreadyExistsException();
		}
		User user = new User(username, userID);
		UserDataModel model = new UserDataModel();
		model.addList(new ShoppingList(user));
		database.put(user, model);
		return user;
	}
	
	private UserDataModel getDataForUser(String userID) {
		return database.get(new User("", userID));
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
				e.printStackTrace();
			}
		} else {
			buildDummyData();
		}
		System.out.println("\n\nRegistered users\n");
		for (User user : database.keySet()) {
			System.out.println("- " + user.getEmail() + " (" + user.getUsername() + ")");
		}
		System.out.println();
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
	
	private void buildDummyData() {
		database = new HashMap<User, UserDataModel>();
		
		User user = new User("Morten", "mortenfb@gmail.com");
		UserDataModel model = new UserDataModel();
		ShoppingList shoppinglist = new ShoppingList(user);
		shoppinglist.addItem(new Item("Bananer", "Frugt"));
		shoppinglist.addItem(new Item("Vandmelon", "Frugt"));
		shoppinglist.addItem(new Item("Adidas sneakers", "Tøj og sko"));
		model.addList(shoppinglist);
		shoppinglist = new ShoppingList(user);
		shoppinglist.addItem(new Item("Item 1", "Lala"));
		shoppinglist.addItem(new Item("Item 2", "Lulu"));
		shoppinglist.addItem(new Item("Item 3", "Tøj og sko"));
		model.addList(shoppinglist);
		database.put(user, model);
		
		user = new User("Kimia", "kimiafrost@gmail.com");
		model = new UserDataModel();
		shoppinglist = new ShoppingList(user);
		shoppinglist.addItem(new Item("Marlboro Light", "Cigaretter"));
		shoppinglist.addItem(new Item("Æbler", "Frugt"));
		model.addList(shoppinglist);
		database.put(user, model);
		
		user = new User("Mads", "mdiget@gmail.com");
		model = new UserDataModel();
		shoppinglist = new ShoppingList(user);
		shoppinglist.addItem(new Item("Tyggegummi", "Diverse"));
		shoppinglist.addItem(new Item("Pærer", "Frugt"));
		shoppinglist.addItem(new Item("Sko", "Tøj og sko"));
		shoppinglist.addItem(new Item("Bacon", "Pålæg"));
		model.addList(shoppinglist);
		database.put(user, model);
	}
}