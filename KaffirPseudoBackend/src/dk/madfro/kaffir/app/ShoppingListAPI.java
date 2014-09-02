package dk.madfro.kaffir.app;

import java.util.List;
import java.util.Set;

import dk.madfro.kaffir.exception.UserNotExistingException;
import dk.madfro.kaffir.exception.UsernameAlreadyExistsException;
import dk.madfro.kaffir.model.ShoppingList;
import dk.madfro.kaffir.model.User;

public interface ShoppingListAPI {
	public List<ShoppingList> getShoppingLists();
	public ShoppingList getShoppingListByID(String listID);
	
	public boolean userExists(String userID);
	public User getUser(String userID);
	public User createUser(String userID, String username) throws UsernameAlreadyExistsException;
	
	public Set<ShoppingList> getSharedShoppingLists();
	public void shareShoppingList(String userID, String listID) throws UserNotExistingException;
	public void unshareShoppingList(String listID);
}