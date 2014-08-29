package dk.madfro.kaffir;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import dk.madfro.kaffir.app.ShoppingListFacade;
import dk.madfro.kaffir.filter.CleanupFilter;
import dk.madfro.kaffir.filter.UserFilter;

@ApplicationPath("/*")
@WebListener
public class ShoppingListJAXRSApplication extends ResourceConfig implements ServletContextListener {

	public ShoppingListJAXRSApplication() {
		packages("dk.madfro.kaffir");
		//register(new LoggingFilter(Logger.getLogger(ShoppingListJAXRSApplication.class.getName()), true));
		register(UserFilter.class);
		register(CleanupFilter.class);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Saving database..");
		((ShoppingListFacade)ShoppingListFacade.instance()).save();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}
	
}