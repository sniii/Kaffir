package dk.madfro.kaffir;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import dk.madfro.kaffir.model.Token;

public class UserFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		String token = context.getHeaderString("kaffir-token");
		if (token != null) {
			UserSession.setCurrentUser(Token.createFrom(token));
		}
	}

}