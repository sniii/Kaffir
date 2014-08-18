package dk.madfro.kaffir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class ShoppingList implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement(name="items")
	private List<Item> items = new ArrayList<Item>();
	private String id;
	private String username;
	private String userID;
	
	public ShoppingList() {
	}
	
	public ShoppingList(String username, String userID) {
		this.username = username;
		this.userID = userID;
		this.id = UUID.randomUUID().toString();
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public List<Item> getItems() {
		return items;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}	
}