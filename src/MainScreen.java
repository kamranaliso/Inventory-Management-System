import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class MainScreen extends Application {

	int idToDlt;
	TableView tab;
	HBox mainRoot;
	VBox ContentBox;
	VBox SupplierBox;
	HBox SalesBox;
	VBox CatBox;
	int totalAmount = 0;
	int cartsize = 0;

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		VBox SideNavBox = new VBox();
		SideNavBox.setMinWidth(270);
		SideNavBox.setAlignment(Pos.TOP_LEFT);
		SideNavBox.getStyleClass().add("SideNavBox");

//		Logo
		Label Logo = new Label("", getImage("logo.png"));
		Logo.setAlignment(Pos.TOP_CENTER);
		Logo.getStyleClass().add("Logo");
//		Nav Buttons
		Button productsbtn = new Button("   Products", getImage("products.png"));
		productsbtn.getStyleClass().add("navBtn");
		Button salesbtn = new Button("   Sales", getImage("sales.png"));
		salesbtn.getStyleClass().add("navBtn");
		Button catbtn = new Button("   Categories", getImage("cat.png"));
		catbtn.getStyleClass().add("navBtn");
		Button suppliersbtn = new Button("   Suppleirs", getImage("supplier.png"));
		suppliersbtn.getStyleClass().add("navBtn");

		SideNavBox.getChildren().addAll(Logo, productsbtn, salesbtn, suppliersbtn, catbtn);

		SalesBox();
		SupplierBox();
		CatBox();

		productsbtn.setOnAction(e -> {
			try {
				mainRoot.getChildren().removeAll(SupplierBox);
				mainRoot.getChildren().removeAll(CatBox);
				mainRoot.getChildren().removeAll(SalesBox);
				mainRoot.getChildren().addAll(ContentBox);

			} catch (Exception e1) {
			}
		});
		salesbtn.setOnAction(e -> {

			try {
				mainRoot.getChildren().removeAll(SupplierBox);
				mainRoot.getChildren().removeAll(ContentBox);
				mainRoot.getChildren().removeAll(CatBox);
				mainRoot.getChildren().addAll(SalesBox);

			} catch (Exception e1) {
			}

		});

		suppliersbtn.setOnAction(e -> {

			try {
				mainRoot.getChildren().removeAll(ContentBox);
				mainRoot.getChildren().removeAll(SalesBox);
				mainRoot.getChildren().removeAll(CatBox);
				mainRoot.getChildren().addAll(SupplierBox);
			} catch (Exception e1) {
			}

		});

		catbtn.setOnAction(e -> {

			try {
				mainRoot.getChildren().removeAll(ContentBox);
				mainRoot.getChildren().removeAll(SalesBox);
				mainRoot.getChildren().removeAll(SupplierBox);
				mainRoot.getChildren().addAll(CatBox);
			} catch (Exception e1) {
			}

		});

		mainRoot = new HBox(SideNavBox, ProductBox());
		mainRoot.getStyleClass().add("root");
		Scene scene = new Scene(mainRoot, 1250, 768);
		scene.getStylesheets().add("style.css");
		stage.setScene(scene);
		stage.show();
	}

	VBox addProductFormBox(Stage s) {

		Label Heading = new Label("Add Product");
		Heading.getStyleClass().add("heading");

		Label pnameL = new Label("Product Name");
		TextField pnameTf = new TextField();
		Label qtyL = new Label("Product Quantity");
		TextField qtyTf = new TextField();

		Label catL = new Label("Product Category");
		Label suppL = new Label("Supplier Name");

		String[] array1 = new String[getCats().size()];
		for (int i = 0; i < array1.length; i++) {
			array1[i] = getCats().get(i);
		}
		ObservableList<String> oCatList = FXCollections.observableArrayList(array1);
		ComboBox catTf = new ComboBox(oCatList);
		catTf.setPrefWidth(300);

		String[] array2 = new String[getSupp().size()];
		for (int i = 0; i < array2.length; i++) {
			array2[i] = getSupp().get(i);
		}
		ObservableList<String> oSuppList = FXCollections.observableArrayList(array2);
		ComboBox suppTf = new ComboBox(oSuppList);
		suppTf.setPrefWidth(300);

		Label priceL = new Label("Product Price (Per Unit/kg)");
		TextField priceTf = new TextField();
		Button addproductbtn = new Button(" Add Product ");
		addproductbtn.getStyleClass().add("addproductbtn");
		addproductbtn.setPrefWidth(400);
		Label x = new Label(" ");

		VBox root = new VBox(10, Heading, pnameL, pnameTf, qtyL, qtyTf, catL, catTf, suppL, suppTf, priceL, priceTf, x,
				addproductbtn);
		root.setPadding(new Insets(40));
		addproductbtn.setOnAction(e -> {
			BackEnd1 b = new BackEnd1();

			try {
				if ((b.checkInt(qtyTf.getText()) && (b.checkInt(priceTf.getText())))) {
					mainRoot.getChildren().remove(ContentBox);
					b.productInsert(pnameTf.getText(), qtyTf.getText(), (catTf.getValue()).toString(),
							(suppTf.getValue()).toString(), priceTf.getText());
					ProductBox();
					mainRoot.getChildren().add(ContentBox);
					s.close();
				} else {
					x.setText("Enter Valid details!");
					x.getStyleClass().add("errortxt");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		return root;

	}

	VBox addSupplierFormBox(Stage s) {

		Label Heading = new Label("Add Supplier");
		Heading.getStyleClass().add("heading");

		Label snameL = new Label("Supplier Name");
		TextField snameTf = new TextField();
		Label phoneL = new Label("Phone");
		TextField phoneTf = new TextField();
		Label addL = new Label("Address");
		TextField addTf = new TextField();
		Label compL = new Label("Company Name");
		TextField compTf = new TextField();
		Button addsuppbtn = new Button(" Add Supplier ");
		addsuppbtn.getStyleClass().add("addproductbtn");
		addsuppbtn.setPrefWidth(400);
		Label x = new Label(" ");

		VBox root = new VBox(10, Heading, snameL, snameTf, phoneL, phoneTf, addL, addTf, compL, compTf, x, addsuppbtn);
		root.setPadding(new Insets(40));

		addsuppbtn.setOnAction(e -> {
			BackEnd1 b = new BackEnd1();
			try {
				if ((b.checkInt(phoneTf.getText()))) {
					mainRoot.getChildren().remove(SupplierBox);
					b.insert(snameTf.getText(), phoneTf.getText(), addTf.getText(), compTf.getText());
					SupplierBox();
					mainRoot.getChildren().add(SupplierBox);
					s.close();
				} else {
					x.setText("Enter Valid details!");
					x.getStyleClass().add("errortxt");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		return root;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	VBox ProductBox() throws FileNotFoundException {
		ContentBox = new VBox();
		ContentBox.setPadding(new Insets(40));
//			Products Panel
		HBox controlbox = new HBox();

//			 search Box
		Label productHeading = new Label("Products");
		productHeading.getStyleClass().add("headingBlack");

		Button addproductbtn = new Button("+ Add Product ");
		addproductbtn.getStyleClass().add("addproductbtn");

		Button dltproductbtn = new Button("Delete Product ");
		dltproductbtn.getStyleClass().add("dltproductbtn");

		HBox btnBox = new HBox(10, dltproductbtn, addproductbtn);
		HBox.setHgrow(btnBox, Priority.ALWAYS);
		btnBox.setAlignment(Pos.CENTER_RIGHT);

		controlbox.getChildren().addAll(productHeading, btnBox);

		VBox productsListBox = new VBox(10);

		Label temp = new Label("  ");

		tab = new TableView();
		tab.isEditable();

		tab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tab.setPrefHeight(700);

		TableColumn pId = new TableColumn("Product ID");
		TableColumn pname = new TableColumn("Product Name");
		TableColumn quantity = new TableColumn("Quantity");
		TableColumn category = new TableColumn("Category");
		TableColumn supplier = new TableColumn("Supplier");
		TableColumn price = new TableColumn("Price");

		tab.getColumns().addAll(pId, pname, quantity, category, supplier, price);

		pId.setCellValueFactory(new PropertyValueFactory<>("pId"));
		pname.setCellValueFactory(new PropertyValueFactory<>("pname"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));

		pname.setPrefWidth(300);
		quantity.setPrefWidth(143);
		category.setPrefWidth(180);
		supplier.setPrefWidth(180);
		price.setPrefWidth(180);

		BackEnd1 be = new BackEnd1();

		Iterator it = be.selectall().iterator();

		while (it.hasNext()) {

			ArrayList<String> arr = (ArrayList<String>) it.next();
			int idInt = Integer.parseInt(arr.get(0));
			String pnameStr = arr.get(1);
			int qtyInt = Integer.parseInt(arr.get(2));
			String catStr = arr.get(3);
			String suppStr = arr.get(4);
			int priceInt = Integer.parseInt(arr.get(5));

			tab.getItems().add(new Product(idInt, pnameStr, qtyInt, catStr, suppStr, priceInt));

		}

		productsListBox.getChildren().addAll(temp, tab);

//		Selected Product ID

		tab.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue != null) {
				idToDlt = ((Product) newValue).getPId();
			}
		});

//		Deleting a Product
		dltproductbtn.setOnAction(e -> {
			be.Delete(Integer.toString(idToDlt));
			tab.getItems().removeAll(tab.getSelectionModel().getSelectedItems());
		});

		addproductbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Stage stage = new Stage();
				Scene scene = new Scene(addProductFormBox(stage), 350, 580);
				scene.getStylesheets().add("style.css");
				stage.setTitle("Add Product");
				stage.setScene(scene);
				stage.show();

			}
		});

		ContentBox.getChildren().addAll(controlbox, productsListBox);
		HBox.setHgrow(ContentBox, Priority.ALWAYS);

		return ContentBox;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	HBox SalesBox() throws FileNotFoundException {
		VBox SalesBoxLeft = new VBox();
		SalesBoxLeft.setPadding(new Insets(0, 40, 0, 0));
//			Products Panel
		HBox controlbox = new HBox();

//			 search Box
		Label searchicon = new Label("", getImage("search.png"));
		TextField searchF = new TextField();
		searchF.setPrefWidth(300);
		searchF.setPromptText("Search Product ID");
		Button searchbtn = new Button(" Search ");
		searchbtn.getStyleClass().add("searchbtn");

		HBox searchBox = new HBox(10, searchicon, searchF, searchbtn);
		searchBox.getStyleClass().add("searchBox");
		searchBox.setAlignment(Pos.CENTER_LEFT);

		controlbox.getChildren().addAll(searchBox);
		Label x = new Label("  ");
		Label x1 = new Label("  ");

		VBox ProductDetailsBox = new VBox();
		ProductDetailsBox.getStyleClass().add("whitebgBox");
		ProductDetailsBox.setPrefHeight(1000);

		Label heading = new Label("Product Details");
		heading.getStyleClass().add("heading");

		Label nameOfProduct = new Label("Product Name");
		nameOfProduct.getStyleClass().add("subheading");

		Label errorL = new Label("");

		Label priceOfProduct = new Label("Price");
		priceOfProduct.getStyleClass().add("subheading");

		Label qtyOfProductL = new Label("Quantity");
		qtyOfProductL.getStyleClass().add("subheading");
		TextField qtyOfProductTf = new TextField();
		qtyOfProductTf.setPromptText("Quantity");
		qtyOfProductTf.getStyleClass().add("text-field1");

		HBox qtyBox = new HBox(10, qtyOfProductTf);
		qtyBox.setAlignment(Pos.CENTER_LEFT);

		Label x3 = new Label(" ");

		Button cartbtn = new Button(" Add to Cart ");
		cartbtn.getStyleClass().add("addproductbtn");
		cartbtn.setPrefWidth(470);
		VBox cartbtnBox = new VBox(nameOfProduct, priceOfProduct, qtyBox, x3, cartbtn);
		VBox.setVgrow(cartbtnBox, Priority.ALWAYS);
		cartbtnBox.setAlignment(Pos.BOTTOM_LEFT);

		ProductDetailsBox.getChildren().addAll(heading, errorL, cartbtnBox);

		Label tAmountL = new Label("Total Amount: -");
		tAmountL.getStyleClass().add("heading");

		Label changeCalcl = new Label("Calculate Change");
		changeCalcl.getStyleClass().add("subheading");
		TextField changeCalcTf = new TextField();
		changeCalcTf.setMinWidth(300);
		changeCalcTf.getStyleClass().add("text-field1");

		Button changebtn = new Button("Calculate");
		changebtn.getStyleClass().add("addproductbtn");

		Label changeReturnL = new Label("Change to Return: ");

		changebtn.setOnAction(e -> {
			BackEnd1 b = new BackEnd1();
			if (b.checkInt(changeCalcTf.getText())) {
				int chReturn = Integer.parseInt(changeCalcTf.getText()) - totalAmount;
				changeReturnL.setText("Change to Return: " + Integer.toString(chReturn));
				changeReturnL.getStyleClass().remove("errortxt");
			} else {
				changeReturnL.setText("Enter amount in numbers.");
				changeReturnL.getStyleClass().add("errortxt");
			}

		});

		HBox ChangeBox = new HBox(10, changeCalcTf, changebtn);
		ChangeBox.setAlignment(Pos.CENTER_LEFT);
		VBox.setVgrow(ChangeBox, Priority.ALWAYS);

		/////////////////////

		TableView cartTable = new TableView();
		cartTable.isEditable();

		cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		cartTable.setPrefHeight(700);
//			cartTable.setPrefWidth(500);

		TableColumn pname = new TableColumn("Product Name");
		TableColumn quantity = new TableColumn("Quantity");
		TableColumn price = new TableColumn("Price");
		TableColumn lineTotal = new TableColumn("Line Total");

		cartTable.getColumns().addAll(pname, quantity, price, lineTotal);

		pname.setCellValueFactory(new PropertyValueFactory<>("pname"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		lineTotal.setCellValueFactory(new PropertyValueFactory<>("lineTotal"));

		pname.setPrefWidth(300);
		quantity.setPrefWidth(143);
		price.setPrefWidth(180);

		String[] ids = new String[10];
		String[] cartqty = new String[10];
		String[] stockqty = new String[10];

		BackEnd1 be = new BackEnd1();
		cartbtn.setOnAction(e -> {

			String idToSearch = searchF.getText();
			ArrayList<String> arr = be.SelectOne(idToSearch);
			String n = arr.get(1);
			int q = Integer.parseInt(qtyOfProductTf.getText());
			int p = Integer.parseInt(arr.get(5));
			cartTable.getItems().add(new cartItems(n, q, p, p * q));
			totalAmount += p * q;

			ids[cartsize] = searchF.getText();
			cartqty[cartsize] = qtyOfProductTf.getText();
			stockqty[cartsize] = arr.get(2);

			cartsize = cartsize + 1;

			qtyOfProductTf.clear();
			searchF.clear();

			tAmountL.setText("Total Amount: " + Integer.toString(totalAmount));

		});

		/////////////////////

		VBox changeVBox = new VBox(10, tAmountL, changeCalcl, changeReturnL, ChangeBox);
		changeVBox.getStyleClass().add("whitebgBox");
		changeCalcTf.setMaxWidth(350);

		searchbtn.setOnAction(e -> {
			try {
				String idToSearch = searchF.getText();
				if (be.checkInt(idToSearch)) {
					errorL.setText("");
					ArrayList<String> arr = be.SelectOne(idToSearch);
					String n = arr.get(1);
					String p = arr.get(5);

					nameOfProduct.setText(n);
					priceOfProduct.setText("Price: " + p);
				} else {
					errorL.setText("You entered an invalid ID.");
					errorL.getStyleClass().add("errortxt");
				}
			} catch (Exception e1) {
				errorL.setText("No Product available with ID " + searchF.getText());
				errorL.getStyleClass().add("errortxt");
			}
		});

		SalesBoxLeft.getChildren().addAll(controlbox, x, ProductDetailsBox, x1, changeVBox);
		SalesBoxLeft.setMinWidth(465);

		Button finSalebtn = new Button("Finish Sale");
		finSalebtn.getStyleClass().add("addproductbtn");
		finSalebtn.setPrefWidth(500);

		Button clrbtn = new Button("Clear All");
		clrbtn.getStyleClass().add("dltproductbtn");
		clrbtn.setPrefWidth(500);

		HBox hbox = new HBox(10, finSalebtn, clrbtn);

		finSalebtn.setOnAction(e -> {
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] != null) {
					be.Update(ids[i].toString(), cartqty[i].toString(), stockqty[i].toString());
				}
			}
			tab.getItems().remove(tab.getItems());
			qtyOfProductTf.clear();
			searchF.clear();

			mainRoot.getChildren().remove(ContentBox);
			try {
				ProductBox();
			} catch (FileNotFoundException e1) {
			}
			mainRoot.getChildren().add(ContentBox);
			mainRoot.getChildren().remove(ContentBox);

		});

		clrbtn.setOnAction(e -> {
			tab.getItems().remove(tab.getItems());
			qtyOfProductTf.clear();
			searchF.clear();
			changeCalcTf.clear();
		});

		VBox tbaleBox = new VBox(10, cartTable, hbox);
		SalesBox = new HBox(SalesBoxLeft, tbaleBox);
		SalesBox.setPadding(new Insets(40));
		return SalesBox;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	VBox SupplierBox() throws FileNotFoundException {
		SupplierBox = new VBox();
		SupplierBox.setPadding(new Insets(40));
//			Products Panel
		HBox controlbox = new HBox();

//			 search Box
		Label suppHeading = new Label("Suppliers");
		suppHeading.getStyleClass().add("headingBlack");

		Button addproductbtn = new Button("+ Add Product ");
		addproductbtn.getStyleClass().add("addproductbtn");

		Button dltproductbtn = new Button("Delete Product ");
		dltproductbtn.getStyleClass().add("dltproductbtn");

		HBox btnBox = new HBox(10, dltproductbtn, addproductbtn);
		HBox.setHgrow(btnBox, Priority.ALWAYS);
		btnBox.setAlignment(Pos.CENTER_RIGHT);

		controlbox.getChildren().addAll(suppHeading, btnBox);

		VBox productsListBox = new VBox(10);

		Label temp = new Label("  ");

		tab = new TableView();
		tab.isEditable();

		tab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tab.setPrefHeight(700);

		TableColumn sid = new TableColumn("Supplier ID");
		TableColumn sname = new TableColumn("Supplier Name");
		TableColumn phone = new TableColumn("Phone");
		TableColumn address = new TableColumn("Address");
		TableColumn company = new TableColumn("Company");

		tab.getColumns().addAll(sid, sname, phone, address, company);

		sid.setCellValueFactory(new PropertyValueFactory<>("sId"));
		sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		company.setCellValueFactory(new PropertyValueFactory<>("company"));

		displaySuppliers();

		productsListBox.getChildren().addAll(temp, tab);

//		Selected Product ID

		tab.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue != null) {
				idToDlt = ((Supplier) newValue).getSId();
			}
		});
//		Deleting a Product
		dltproductbtn.setOnAction(e -> {
			BackEnd1 be = new BackEnd1();
			be.SuppDelete(Integer.toString(idToDlt));
			tab.getItems().removeAll(tab.getSelectionModel().getSelectedItems());
		});

		addproductbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Stage stage = new Stage();
				Scene scene = new Scene(addSupplierFormBox(stage), 350, 500);
				scene.getStylesheets().add("style.css");
				stage.setTitle("Add Supplier");
				stage.setScene(scene);
				stage.show();

			}
		});

		SupplierBox.getChildren().addAll(controlbox, productsListBox);
		HBox.setHgrow(SupplierBox, Priority.ALWAYS);

		return SupplierBox;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	VBox CatBox() throws FileNotFoundException {
		CatBox = new VBox();
		CatBox.setPadding(new Insets(40));
//			Products Panel
		HBox controlbox = new HBox();

//			 search Box
		Label productHeading = new Label("Categories");
		productHeading.getStyleClass().add("headingBlack");

		TextField cattf = new TextField();
		cattf.getStyleClass().add("cattf");
		cattf.setPromptText("Enter category");
		Button addproductbtn = new Button("+ Add Category ");
		addproductbtn.getStyleClass().add("addproductbtn");

		Button dltproductbtn = new Button("Delete Category");
		dltproductbtn.getStyleClass().add("dltproductbtn");

		HBox btnBox = new HBox(10, cattf, addproductbtn, dltproductbtn);
		HBox.setHgrow(btnBox, Priority.ALWAYS);
		btnBox.setAlignment(Pos.CENTER_RIGHT);

		controlbox.getChildren().addAll(productHeading, btnBox);

		VBox productsListBox = new VBox(10);

		Label temp = new Label("  ");

		tab = new TableView();
		tab.isEditable();

		tab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tab.setPrefHeight(700);

		TableColumn cId = new TableColumn("Product ID");
		TableColumn cname = new TableColumn("Product Name");

		tab.getColumns().addAll(cId, cname);

		cId.setCellValueFactory(new PropertyValueFactory<>("cId"));
		cname.setCellValueFactory(new PropertyValueFactory<>("cname"));

		BackEnd1 be = new BackEnd1();

		Iterator it = be.catSelectall().iterator();

		while (it.hasNext()) {

			ArrayList<String> arr = (ArrayList<String>) it.next();
			int idInt = Integer.parseInt(arr.get(0));
			String pnameStr = arr.get(1);

			tab.getItems().add(new Category(idInt, pnameStr));

		}

		productsListBox.getChildren().addAll(temp, tab);

//		Selected Product ID

		tab.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue != null) {
				idToDlt = ((Category) newValue).getCId();
			}
		});

//		Deleting a Product
		dltproductbtn.setOnAction(e -> {
			tab.getItems().removeAll(tab.getSelectionModel().getSelectedItems());
			be.catDelete(Integer.toString(idToDlt));

		});

		addproductbtn.setOnAction(e -> {

			try {
				mainRoot.getChildren().remove(CatBox);
				be.catInsert(cattf.getText());
				CatBox();
				mainRoot.getChildren().add(CatBox);
			} catch (Exception e1) {
			}
		});

		CatBox.getChildren().addAll(controlbox, productsListBox);
		HBox.setHgrow(CatBox, Priority.ALWAYS);

		return CatBox;
	}

	ImageView getImage(String address) throws FileNotFoundException {

		FileInputStream input = new FileInputStream(address);
		Image image = new Image(input);
		ImageView imageView = new ImageView(image);
		return imageView;
	}

	void displaySuppliers() {

		BackEnd1 be = new BackEnd1();

		Iterator it = be.suppSelectall().iterator();

		while (it.hasNext()) {

			ArrayList<String> arr = (ArrayList<String>) it.next();
			int idS = Integer.parseInt(arr.get(0));
			String snameS = arr.get(1);
			int phoneS = Integer.parseInt(arr.get(2));
			String addS = arr.get(3);
			String compS = arr.get(4);

			tab.getItems().add(new Supplier(idS, snameS, phoneS, addS, compS));

		}
	}

	ArrayList<String> getSupp() {

		BackEnd1 be = new BackEnd1();

		Iterator it = be.suppSelectall().iterator();
		ArrayList<String> namesList = new ArrayList<>();
		while (it.hasNext()) {
			ArrayList<String> arr = (ArrayList<String>) it.next();
			namesList.add(arr.get(1));
		}
		return namesList;
	}

	ArrayList<String> getCats() {

		BackEnd1 be = new BackEnd1();

		Iterator it = be.catSelectall().iterator();
		ArrayList<String> namesList = new ArrayList<>();
		while (it.hasNext()) {
			ArrayList<String> arr = (ArrayList<String>) it.next();
			namesList.add(arr.get(1));
		}
		return namesList;
	}
}
