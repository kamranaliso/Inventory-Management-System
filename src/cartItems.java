public class cartItems {
		private String pname;
		private int quantity;
		private int price;
		private int lineTotal;
	
		public cartItems(String pname, int quantity, int price, int lineTotal) {
			this.pname = pname;
			this.quantity = quantity;
			this.price = price;
			this.lineTotal = lineTotal;
		}

		public String getPname() {
			return pname;
		}
		
	
		public int getQuantity() {
			return quantity;
		}
	
		public int getPrice() {
			return price;
		}
		
		public int getLineTotal() {
			return lineTotal;
		}
}
