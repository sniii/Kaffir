package dk.madfro.kaffir;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dk.madfro.kaffir.app.ShoppingListAPI;
import dk.madfro.kaffir.app.ShoppingListFacade;
import dk.madfro.kaffir.app.UsernameAlreadyExistsException;
import dk.madfro.kaffir.model.Token;
import dk.madfro.kaffir.model.User;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginService {
	public enum LoginStatus {
		Ok, UserNotFound, UserAlreadyExists
	}
	
	@POST
	@Path("login")
    public LoginResponse login(LoginCredentials credentials) {
    	ShoppingListAPI application = ShoppingListFacade.instance();
    	LoginResponse response = new LoginResponse();
    	if (application.userExists(credentials.email)) {
    		User user = application.getUser(credentials.email);
    		Token token = UserSession.storeAuthenticatedUser(user);
    		response = new LoginResponse(LoginStatus.Ok, user, token.getSecretKey());
    		System.out.println("User (" + credentials.email + ") logged in.");
    	} else {
    		response.status = LoginStatus.UserNotFound;
    	}
    	return response;
    }
	
	@POST
	@Path("createuser")
	public LoginResponse createUser(LoginCredentials user) {
		ShoppingListAPI application = ShoppingListFacade.instance();
		LoginResponse response = new LoginResponse();
		try {
			User newUser = application.createUser(user.email, user.username);
			Token token = UserSession.storeAuthenticatedUser(newUser);
			response = new LoginResponse(LoginStatus.Ok, newUser, token.getSecretKey());
		} catch (UsernameAlreadyExistsException e) {
			response.status = LoginStatus.UserAlreadyExists;
		}
		return response;
	}
	
	@SuppressWarnings("unused")
	private static class LoginCredentials {
		public String email;
		public String password;
		public String username;
	}
	
	@SuppressWarnings("unused")
	private static class LoginResponse {
		public LoginStatus status;
		public User user;
		public String token;
		
		public LoginResponse() {
		}

		public LoginResponse(LoginStatus status, User user, String token) {
			this.status = status;
			this.user = user;
			this.token = token;
		}
	}
}