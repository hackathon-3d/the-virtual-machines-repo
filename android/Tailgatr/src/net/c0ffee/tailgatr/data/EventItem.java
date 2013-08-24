package net.c0ffee.tailgatr.data;

public class EventItem {
	private long _id;
	private String title;
	private String description;
	private String type;
	private User provider; // person providing the object
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User getProvider() {
		return provider;
	}
	public void setProvider(User provider) {
		this.provider = provider;
	}
	public EventItem(long _id, String title, String description, String type,
			User provider) {
		super();
		this._id = _id;
		this.title = title;
		this.description = description;
		this.type = type;
		this.provider = provider;
	}
}