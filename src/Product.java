public class Product<T> {

	private int pId;
	private String pname;
	private int quantity;
	private String category;
	private String supplier;
	private int price;

	public Product(int pId, String pname, int quantity, String category, String supplier, int price) {
		this.pId = pId;
		this.pname = pname;
		this.quantity = quantity;
		this.category = category;
		this.supplier = supplier;
		this.price = price;
	}

	

	public int getPId() {
		return pId;
	}

	public String getPname() {
		return pname;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getCategory() {
		return category;
	}

	public String getSupplier() {
		return supplier;
	}

	public int getPrice() {
		return price;
	}

}

