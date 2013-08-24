package net.c0ffee.tailgatr.data;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -1688027765746856174L;
	
	private long _id;
	private String email;
	private String nickname;
	private String password;
	public User(long _id, String email, String nickname, String password) {
		super();
		this._id = _id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	
	public String toString() {
		return nickname;
	}
}
