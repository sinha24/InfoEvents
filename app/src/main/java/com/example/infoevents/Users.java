package com.example.infoevents;

public class Users {

	String uname;
	String upass;
	String uemail;
	long uphone;
	
	
	public Users(String uname, String upass, String uemail, long uphone) {
		super();
		this.uname = uname;
		this.upass = upass;
		this.uemail = uemail;
		this.uphone = uphone;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpass() {
		return upass;
	}

	public void setUpass(String upass) {
		this.upass = upass;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public long getUphone() {
		return uphone;
	}

	public void setUphone(long uphone) {
		this.uphone = uphone;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return uname+":"+upass+":"+uemail+":"+uphone;
	}
}
