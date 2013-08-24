package net.c0ffee.tailgatr.data;

import java.io.Serializable;

public class Tailgate implements Serializable {
	private static final long serialVersionUID = -2932923298366486641L;
	
	private long _id;
	private String title;
	private String description;
	private boolean owner;
	public Tailgate(long _id, String title, String description,boolean owner) {
		super();
		this._id = _id;
		this.title = title;
		this.description = description;
		this.owner = owner;
	}
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
	public boolean isOwner() {
		return owner;
	}
	
	public void setOwner(boolean owner) {
		this.owner = owner;
	}
	public String toString() {
		return title;
	}
}
