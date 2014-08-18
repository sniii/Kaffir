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
	private User user;
	
	public ShoppingList() {
	}
	
	public ShoppingList(User user) {
		this.id = UUID.randomUUID().toString();
		this.user = user;
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}