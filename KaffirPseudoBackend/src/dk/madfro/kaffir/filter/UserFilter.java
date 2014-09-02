package dk.madfro.kaffir.filter;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import dk.madfro.kaffir.model.Token;
import dk.madfro.kaffir.security.UserSession;

public class UserFilter implements ContainerRequestFilter {
	
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		if (isLoginAttempt(context)) {
			return; // pass through to login routine
		}
		UserSession.authenticate(Token.createFrom(context.getHeaderString("kaffir-token")));
		if (!UserSession.isUserLoggedIn()) {
			sendUnauthorizedResponse();
		}
	}
	
	private boolean isLoginAttempt(ContainerRequestContext context) {
		String path = context.getUriInfo().getPath();
		if (path.endsWith("/")) {
			path = path.replace("/", "");
		}
		if (path.equalsIgnoreCase("login") || path.equalsIgnoreCase("createuser")) {
			return true; 
		}
		return false;
	}
	
	private void sendUnauthorizedResponse() {
		ResponseBuilder builder = null;
        String response = "Not authorized to access this service.";
        builder = Response.status(Response.Status.UNAUTHORIZED).entity(response);
        throw new WebApplicationException(builder.build());
	}
	
}