package com.example.infoevents;

public class Events {
	String Imgnm;
	
	String Event_name;
	String E_info;
	String E_date;
	String Address;
	String Uemail;
	
	public Events(String imgnm, String event_name, String e_info, String e_date,
			String address,String uemail) {
		super();
		Imgnm=imgnm;
		Event_name = event_name;
		E_info = e_info;
		E_date = e_date;
		Address = address;
		Uemail=uemail;
	}
	
	
	

	public Events(String imgnm, String event_name, String e_info, String e_date,
			String address) {
		super();
		Imgnm=imgnm;
		Event_name = event_name;
		E_info = e_info;
		E_date = e_date;
		Address = address;
	}
	public String getImgnm() {
		return Imgnm;
	}

	public void setImgnm(String imgnm) {
		Imgnm = imgnm;
	}
	
	public String getUemail() {
		return Uemail;
	}

	public void setUemail(String uemail) {
		Uemail = uemail;
	}
	public String getEvent_name() {
		return Event_name;
	}
	public void setEvent_name(String event_name) {
		Event_name = event_name;
	}
	public String getE_info() {
		return E_info;
	}
	public void setE_info(String e_info) {
		E_info = e_info;
	}
	public String getE_date() {
		return E_date;
	}
	public void setE_date(String e_date) {
		E_date = e_date;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
}
