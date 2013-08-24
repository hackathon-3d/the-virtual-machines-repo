package net.c0ffee.tailgatr.data;

import java.util.Date;

public class Tailgate {
	private long _id;
	private String title;
	private Date date;
	private String description;
	public Tailgate(long _id, String title, Date date, String description) {
		super();
		this._id = _id;
		this.title = title;
		this.date = date;
		this.description = description;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return title;
	}
}
