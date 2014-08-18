package dk.madfro.kaffir.app;

import java.util.List;

import dk.madfro.kaffir.model.ShoppingList;
import dk.madfro.kaffir.model.User;

public interface ShoppingListAPI {
	public List<ShoppingList> getShoppingLists(String userID);
	public ShoppingList getShoppingListByID(String userID, String listID);
	public boolean userExists(String userID);
	public User getUser(String userID);
	public User createUser(String userID, String username) throws UsernameAlreadyExistsException;
}