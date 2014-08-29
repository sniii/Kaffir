package dk.madfro.kaffir.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import dk.madfro.kaffir.app.ShoppingListFacade;
import dk.madfro.kaffir.model.Item;
import dk.madfro.kaffir.model.ShoppingList;
import dk.madfro.kaffir.model.User;
import dk.madfro.kaffir.util.UserSession;

@Path("shoppinglist")
public class ShoppingListService {
	
    @GET
    @Path("lists")
    @Produces("application/json")
    public List<ShoppingList> retrieveShoppingLists() {
    	return ShoppingListFacade.instance().getShoppingLists(UserSession.getUser());
    }
    
    @GET
    @Path("/list/{id}")
    @Produces("application/json")
    public ShoppingList retrieveShoppingList(@PathParam("id") String listID) {
    	return ShoppingListFacade.instance().getShoppingListByID(UserSession.getUser(), listID);
    }
    
    @POST
    @Path("/list/additem")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addItem(AddItemInput input) {
    	ShoppingList list = ShoppingListFacade.instance().getShoppingListByID(UserSession.getUser(), input.listID);
    	list.addItem(new Item(input.item, ""));
    }
    
    @POST
    @Path("/list/removeitem")
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeItem(RemoveItemInput input) {
    	ShoppingList list = ShoppingListFacade.instance().getShoppingListByID(UserSession.getUser(), input.listID);
    	list.removeItem(Item.createFromID(input.itemID));
    }
    
    private static class AddItemInput {
    	public String listID;
    	public String item;
    }
    
    private static class RemoveItemInput {
    	public String listID;
    	public String itemID;
    }
    
}