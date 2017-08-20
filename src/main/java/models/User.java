package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	private int uid;
		
	public void setUid(int uid) {
		this.uid = uid;
	}

	private String uname;
	private String password;

	public int getUid() {
		return uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String login) {
		this.uname = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
