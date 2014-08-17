package dk.madfro.kaffir;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import dk.madfro.kaffir.model.User;
import dk.madfro.kaffir.model.ShoppingListFacade;

@Path("login")
public class LoginService {
	public enum LoginStatus {
		Ok, UserNotFound
	}
	
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
    public LoginResponse login(LoginAttempt login) {
    	System.out.println("User (" + login.email + ") logged in.");
    	ShoppingListFacade application = ShoppingListFacade.instance();
    	LoginResponse loginResponse = new LoginResponse();
    	if (application.userExists(login.email)) {
    		loginResponse.user = application.getUser(login.email);
    		loginResponse.status = LoginStatus.Ok;
    	} else {
    		loginResponse.status = LoginStatus.UserNotFound;
    	}
    	return loginResponse;
    }
	
	@SuppressWarnings("unused")
	private static class LoginAttempt {
		public String email;
		public String password;
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