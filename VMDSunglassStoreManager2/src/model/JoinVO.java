package model;

public class JoinVO {

	String id;
	String password;
	String name;
	String position;
	String PhoneNumber;

	public JoinVO() {
		super();
	}

	public JoinVO(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public JoinVO(String id, String password, String name, String position, String phoneNumber) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.position = position;
		this.PhoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

}
