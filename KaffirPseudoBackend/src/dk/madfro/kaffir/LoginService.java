package dk.madfro.kaffir;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dk.madfro.kaffir.app.ShoppingListAPI;
import dk.madfro.kaffir.app.ShoppingListFacade;
import dk.madfro.kaffir.app.UsernameAlreadyExistsException;
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
    public LoginResponse login(UserData user) {
    	ShoppingListAPI application = ShoppingListFacade.instance();
    	LoginResponse response = new LoginResponse();
    	if (application.userExists(user.email)) {
    		response.user = application.getUser(user.email);
    		response.status = LoginStatus.Ok;
    		System.out.println("User (" + user.email + ") logged in.");
    	} else {
    		response.status = LoginStatus.UserNotFound;
    	}
    	return response;
    }
	
	@POST
	@Path("createuser")
	public LoginResponse createUser(UserData user) {
		ShoppingListAPI application = ShoppingListFacade.instance();
		LoginResponse response = new LoginResponse();
		try {
			response.user = application.createUser(user.email, user.username);
			response.status = LoginStatus.Ok;
		} catch (UsernameAlreadyExistsException e) {
			response.status = LoginStatus.UserAlreadyExists;
		}
		return response;
	}
	
	@SuppressWarnings("unused")
	private static class UserData {
		public String email;
		public String password;
		public String username;
	}
	
	@SuppressWarnings("unused")
	private static class LoginResponse {
		public LoginStatus status;
		public User user;
		
		public LoginResponse() {
		}

		public LoginResponse(LoginStatus status, User user) {
			this.status = status;
			this.user = user;
		}
	}
}