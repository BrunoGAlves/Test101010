package com.example.myapplication;

        import android.provider.BaseColumns;

         class Contact implements BaseColumns {
 	static final String TABLE_NAME = "contact";
 	static final String COLUMN_NAME_NAME = "name";
 	static final String COLUMN_NAME_EMAIL = "email";
 	static final String COLUMN_NAME_PHONE = "phone";

        	private String name;
	private String email;
	private String phone;

        	Contact(String name, String email, String phone) {
        		setName(name);
        		setEmail(email);
        		setPhone(phone);
        	}

        	public String getName() {
        		return name;
        	}

        	public void setName(String name) {
        		this.name = name;
        	}

        	public String getEmail() {
        		return email;
        	}

        	public void setEmail(String email) {
        		this.email = email;
        	}

        	public String getPhone() {
        		return phone;
        	}

        	public void setPhone(String phone) {
        		this.phone = phone;
        	}
}