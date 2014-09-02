package dk.madfro.kaffir.service;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dk.madfro.kaffir.app.ShoppingListFacade;
import dk.madfro.kaffir.exception.UserNotExistingException;
import dk.madfro.kaffir.model.Item;
import dk.madfro.kaffir.model.ShoppingList;

@Path("shoppinglist")
public class ShoppingListService {
	
    @GET
    @Path("lists")
    @Produces("application/json")
    public ShoppingListsResponse retrieveShoppingLists() {
    	List<ShoppingList> myLists = ShoppingListFacade.instance().getShoppingLists();
    	Set<ShoppingList> sharedLists = ShoppingListFacade.instance().getSharedShoppingLists();
    	return new ShoppingListsResponse(myLists, sharedLists);
    }
    
    @GET
    @Path("/list/{id}")
    @Produces("application/json")
    public ShoppingList retrieveShoppingList(@PathParam("id") String listID) {
    	return ShoppingListFacade.instance().getShoppingListByID(listID);
    }
    
    @POST
    @Path("/list/share")
    @Consumes(MediaType.APPLICATION_JSON)
    public ShareListResponse shareShoppingList(ShareListInput input) {
    	ShareListResponse response = new ShareListResponse();
    	try {
			ShoppingListFacade.instance().shareShoppingList(input.targetUserID, input.listID);
			response.status = ShareStatus.Ok;
		} catch (UserNotExistingException e) {
			response.status = ShareStatus.UserNotFound; 
		}
    	return response;
    }
    
    @POST
    @Path("/list/additem")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addItem(AddItemInput input) {
    	ShoppingList list = ShoppingListFacade.instance().getShoppingListByID(input.listID);
    	list.addItem(new Item(input.itemName, ""));
    }
    
    @POST
    @Path("/list/removeitem")
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeItem(RemoveItemInput input) {
    	ShoppingList list = ShoppingListFacade.instance().getShoppingListByID(input.listID);
    	list.removeItem(Item.createFromID(input.itemID));
    }
    
	public enum ShareStatus {
		Ok, UserNotFound
	}
    
	@SuppressWarnings("unused")
    private static class ShareListResponse {
    	public ShareStatus status;
    }
    
    private static class ShareListInput {
    	public String targetUserID;
    	public String listID;
    }
    
    private static class AddItemInput {
    	public String listID;
    	public String itemName;
    }
    
    private static class RemoveItemInput {
    	public String listID;
    	public String itemID;
    }
    
    @SuppressWarnings("unused")
    private static class ShoppingListsResponse {
		public List<ShoppingList> shoppingLists;
    	public Set<ShoppingList> sharedShoppingLists;
    	public ShoppingListsResponse() {}
		public ShoppingListsResponse(List<ShoppingList> shoppingLists, Set<ShoppingList> sharedShoppingLists) {
			this.shoppingLists = shoppingLists;
			this.sharedShoppingLists = sharedShoppingLists;
		}
    }
    
}