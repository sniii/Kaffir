package dk.madfro.kaffir.util;

import java.util.HashMap;
import java.util.Map;

import dk.madfro.kaffir.model.Token;
import dk.madfro.kaffir.model.User;

public class UserSession {
	private static final Map<Token, User> users = new HashMap<Token, User>();
	private static ThreadLocal<User> currentUser = new ThreadLocal<User>();
	
	public static Token login(User user) {
		Token token = Token.create();
		users.put(token, user);
		return token;
	}
	
	public static User getUser() {
		return currentUser.get();
	}
	
	public static boolean isUserLoggedIn() {
		return getUser() != null;
	}
	
	public static void authenticate(Token token) {
		if (token == null) {
			return;
		}
		User user = users.get(token);
		if (user != null) {
			currentUser.set(user);
		}
	}
	
	public static void cleanup() {
		currentUser.remove();
	}
}