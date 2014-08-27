package dk.madfro.kaffir;

import java.util.HashMap;
import java.util.Map;

import dk.madfro.kaffir.model.Token;
import dk.madfro.kaffir.model.User;

public class UserSession {
	private static final Map<Token, User> session = new HashMap<Token, User>();
	
	private static ThreadLocal<User> currentUser = new ThreadLocal<User>();
	
	public static Token storeAuthenticatedUser(User user) {
		Token token = Token.create();
		session.put(token, user);
		return token;
	}
	
	public static User retrieveUser(Token token) {
		return session.get(token);
	}
	
	public static User getCurrentUser() {
		return currentUser.get();
	}
	
	public static void setCurrentUser(Token token) {
		User user = session.get(token);
		if (user != null) {
			currentUser.set(user);
		}
	}
}