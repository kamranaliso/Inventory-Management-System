import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BackEnd1 {

	public static void main(String[] args) throws Exception {
		BackEnd1 n = new BackEnd1();
		n.createTables();
	}
	
	public Connection getConnection() throws Exception {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Database.db");
		} catch (Exception e) {
		}
		return c;
	}
	
	public void createTables() throws Exception {
		Connection c = getConnection();

		String sql = "CREATE TABLE IF NOT EXISTS Products (\n" + "	pId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
				+ "	pname	TEXT,\n" + " quantity	INTEGER,\n" + "	category	TEXT,\n" + "	supplier	TEXT,\n"
				+ "	price	INTEGER\n" + ");";
		PreparedStatement post = c.prepareStatement(sql);
		post.executeUpdate();

		sql = "CREATE TABLE IF NOT EXISTS Supplier (\n" + "	sId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
				+ "	sname	TEXT,\n" + " phone	INTEGER,\n" + "	address	TEXT,\n" + "	company	TEXT\n" + ");";
		post = c.prepareStatement(sql);
		post.executeUpdate();

		sql = "CREATE TABLE IF NOT EXISTS category (\n" + "	cId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
				+ "	cname	TEXT\n" + ");";
		post = c.prepareStatement(sql);
		post.executeUpdate();

		c.close();

	}

	public ArrayList<ArrayList<String>> selectall() {
		try {
			Connection c = getConnection();
			PreparedStatement post = c.prepareStatement("SELECT * FROM Products");
			ResultSet result = post.executeQuery();

			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();

			while (result.next()) {
				ArrayList<String> list = new ArrayList<String>();

				list.add(result.getString("pId"));
				list.add(result.getString("pname"));
				list.add(result.getString("quantity"));
				list.add(result.getString("category"));
				list.add(result.getString("supplier"));
				list.add(result.getString("price"));
				array.add(list);
			}
			c.close();
			return array;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public ArrayList<ArrayList<String>> suppSelectall() {
		try {
			Connection c = getConnection();
			PreparedStatement post = c.prepareStatement("SELECT * FROM Supplier");
			ResultSet result = post.executeQuery();

			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();

			while (result.next()) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(result.getString("sId"));
				list.add(result.getString("sname"));
				list.add(result.getString("phone"));
				list.add(result.getString("address"));
				list.add(result.getString("company"));
				array.add(list);
			}
			c.close();
			return array;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public ArrayList<ArrayList<String>> catSelectall() {
		try {
			Connection c = getConnection();
			PreparedStatement post = c.prepareStatement("SELECT * FROM category");
			ResultSet result = post.executeQuery();

			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();

			while (result.next()) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(result.getString("cId"));
				list.add(result.getString("cname"));
				array.add(list);
			}
			c.close();
			return array;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public void productInsert(String pname, String qty, String cat, String supp, String price) throws Exception {

		try {

			Connection c = getConnection();
			PreparedStatement post = c
					.prepareStatement("INSERT INTO Products (pname,quantity,category,supplier,price) VALUES ('" + pname
							+ "','" + qty + "','" + cat + "','" + supp + "','" + price + "')");
			post.executeUpdate();

			c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insert(String sname, String phone, String address, String company) throws Exception {

		try {

			Connection c = getConnection();
			PreparedStatement post = c.prepareStatement("INSERT INTO Supplier (sname,phone,address,company) VALUES ('"
					+ sname + "','" + phone + "','" + address + "','" + company + "')");
			post.executeUpdate();

			c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void catInsert(String cname) throws Exception {

		try {

			Connection c = getConnection();
			PreparedStatement post = c.prepareStatement("INSERT INTO category (cname) VALUES ('" + cname + "')");
			post.executeUpdate();

			c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Update(String id, String cartqty, String stockqty) {

		try {
			Connection c = getConnection();

			int newqty = Integer.parseInt(stockqty) - Integer.parseInt(cartqty);
			PreparedStatement post = c
					.prepareStatement("UPDATE Products Set quantity='" + newqty + "' WHERE pId = " + id);
			post.executeUpdate();
			c.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void Delete(String id) {

		try {
			Connection c = getConnection();

			PreparedStatement post = c.prepareStatement("DELETE FROM Products WHERE pId=" + id);
			post.executeUpdate();
			c.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void SuppDelete(String id) {

		try {
			Connection c = getConnection();

			PreparedStatement post = c.prepareStatement("DELETE FROM Supplier WHERE sId=" + id);
			post.executeUpdate();
			c.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void catDelete(String id) {

		try {
			Connection c = getConnection();

			PreparedStatement post = c.prepareStatement("DELETE FROM category WHERE cId=" + id);
			post.executeUpdate();
			c.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public ArrayList<String> SelectOne(String id) {
		try {
			Connection c = getConnection();

			PreparedStatement post = c.prepareStatement("SELECT * FROM Products WHERE pId=" + id);
			ResultSet result = post.executeQuery();

			ArrayList<String> list = new ArrayList<String>();
			list.add(result.getString("pId"));
			list.add(result.getString("pname"));
			list.add(result.getString("quantity"));
			list.add(result.getString("category"));
			list.add(result.getString("supplier"));
			list.add(result.getString("price"));
			System.out.println(list);
			c.close();

			return list;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	boolean checkInt(String text) {
		int num;
		try {
			num = Integer.parseInt(text);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}

	}

	int numOfSupp() {

		Iterator it = suppSelectall().iterator();
		int size = 0;
		while (it.hasNext()) {
			ArrayList<String> arr = (ArrayList<String>) it.next();
			System.err.println(arr.get(1));
//			size++;
		}

		return size;

	}

}
