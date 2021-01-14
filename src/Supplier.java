public class Supplier {
	private int sId;
	private String sname;
	private int phone;
	private String address;
	private String company;

	public Supplier(int sId,String sname, int phone, String address, String company) {
		this.sId = sId;
		this.sname = sname;
		this.phone = phone;
		this.address = address;
		this.company = company;
	}
	
	public int getSId() {
		return sId;
	}


	public String getSname() {
		return sname;
	}
	

	public int getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}
	
	public String getCompany() {
		return company;
	}
}
