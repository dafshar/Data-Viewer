package com.arc.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.arc.models.TaxpayerMainView;
import com.arc.models.PumsMainView;
import com.arc.util.CurrencyCell;
import com.arc.util.TableColumns;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class BaseAnchorController {

	@FXML
	private TextField searchFld;

	public BaseAnchorController() {
		super();
	}

	public BaseAnchorController(TaxpayerMainView model) {
		this.model = model;

	}

	@FXML
	private Button connectBtn;

	@FXML
	private Button clearBtn;

	@FXML
	private Button searchBtn;

	@FXML
	private Button exitBtn;

	// create pointer to HBOXes
	@FXML
	private HBox taxpayer_main;
	@FXML
	private HBox pums_main;

	private static TaxpayerMainView model;
	private static String dbURL = "jdbc:derby:C:\\fx\\pumProject\\derby\\myPUMS";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
	private static PreparedStatement stmt2 = null;
	private static TableView<TaxpayerMainView> tp_table = new TableView<TaxpayerMainView>();
	private static TableView<PumsMainView> pums_table = new TableView<PumsMainView>();
	private static TableColumns tableColumns = new TableColumns();
	private static int CustIdKey;

	@FXML
	private void initialize() {
		System.out.println("Initializing BaseAnchorController...");
		long startTime = System.nanoTime();
		createConnection();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
		System.out.println("get the connection took: " + duration);

	}

	@FXML
	void performConnect(ActionEvent event) {
		// System.out.println("connect button pushed");
		// this.searchFld.setText("connect!");
		long startTime = System.nanoTime();
		taxpayerView();
		long endTime = System.nanoTime();

		long duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
		System.out.println("taxpayer view took: " + duration);
	}

	@FXML
	void performExit(ActionEvent event) {
		try {
			conn.close();
		} catch (SQLException except) {
			except.printStackTrace();
		}
		Platform.exit();
	}

	// add the table views
	void taxpayerView() {

		layoutForm();

	}

	private void layoutForm() {
		long startTime;
		// createConnection();
		long endTime;
		long duration; // divide by 1000000 to get milliseconds.

		try {
			String sql = "select * from taxpayer_main_view";
			stmt = conn.prepareStatement(sql);
			startTime = System.nanoTime();
			ResultSet results = stmt.executeQuery();
			// ResultSet results = stmt.executeQuery();
			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("get result set took: " + duration);

			// create the data table
			startTime = System.nanoTime();
			tp_table.getItems().addAll(getTaxpayerList(results));
			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("get list view took: " + duration);

			// now add the columns to the table
			startTime = System.nanoTime();
			tp_table.getColumns().addAll(tableColumns.getIdCol(), tableColumns.getSSNCol(), tableColumns.getFnameCol(),
					tableColumns.getLnameCol(), tableColumns.getTaxBaseCol(), tableColumns.getTaxAmtCol(),
					tableColumns.getCalcAmtCol());

			// set tp_table properties
			tp_table.setPrefHeight(170);
			tp_table.setPrefWidth(1194);
			tp_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					// this.searchFld.setText(newSelection.getSsn());
					this.CustIdKey = newSelection.getCustomerId();
					// now we have the customer id key; we can now display the child table/view
					pumsLayoutForm();
				}
			});

			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("get cols and props set took: " + duration);

			startTime = System.nanoTime();
			// now show the table in the right hbox
			taxpayer_main.getChildren().add(tp_table);
			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("render took: " + duration);

			// now close
			startTime = System.nanoTime();
			results.close();
			// stmt.close();
			// conn.close();
			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("close took: " + duration);

		} catch (SQLException except) {
			except.printStackTrace();
		}
	}

	private void pumsLayoutForm() {
		long startTime;
		// createConnection();
		long endTime;
		long duration; // divide by 1000000 to get milliseconds.
		try {
			// first check if table is already in the view
			
			if (pums_main.getChildren().size() > 0) {
				pums_table.getItems().removeAll();
				pums_table.getItems().clear();
				pums_main.getChildren().remove(pums_table);
				pums_table = new TableView<PumsMainView>();
			}
			String sql = "select * from pums_main_view where custid = " + this.CustIdKey;

			stmt2 = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet results = stmt2.executeQuery();
			// results.setFetchSize(100);

			// get row count
			results.last();
			int k = results.getRow();
			results.beforeFirst();
			
			// create the data table
			startTime = System.nanoTime();
			pums_table.getItems().addAll(getPumsList(results, k));
			endTime = System.nanoTime();
			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			// System.out.println("get pums list took: " + duration);

			// now add the columns to the table
			pums_table.getColumns().addAll(tableColumns.getPumsIdCol(), tableColumns.getSerialNoCol(),
					tableColumns.getAGEPCol(), tableColumns.getWAGPCol());

			// set tp_table properties
			pums_table.setPrefHeight(170);
			pums_table.setPrefWidth(578);

			// now show the table in the right hbox
			pums_main.getChildren().add(pums_table);

			// now close
			results.close();
			stmt2.close();
			// conn.close();

		} catch (SQLException except) {
			except.printStackTrace();
		}
	}

	private static ObservableList<TaxpayerMainView> getTaxpayerList(ResultSet results1) {
		// System.out.println("Into taxpayer list generation...");
		ArrayList<TaxpayerMainView> tp1list = new ArrayList<TaxpayerMainView>();

		// get first 50 taxpayer records
		try {
			for (int j = 1; j <= 100; j++) {
				if (results1.next()) {
					int theCustId = results1.getInt(1);
					String theSSN = results1.getString(2);
					String theFname = results1.getString(3);
					String theLname = results1.getString(4);
					Double theTaxBase = results1.getDouble(5);
					Double theTaxAmt = results1.getDouble(6);
					Double theCaclAmt = results1.getDouble(7);
					TaxpayerMainView t1 = new TaxpayerMainView(theCaclAmt, theCustId, theFname, theLname, theSSN,
							theTaxAmt, theTaxBase);
					tp1list.add(t1);
					// System.out.println("Row number: " + j + " " + t1.getSsn());
				}
			}
		} catch (SQLException except) {
			except.printStackTrace();
		}

		return FXCollections.<TaxpayerMainView>observableArrayList(tp1list);
	}

	private static ObservableList<PumsMainView> getPumsList(ResultSet results1, int k) {
		long startTime;
		// createConnection();
		long endTime;
		long duration; // divide by 1000000 to get milliseconds.
		// System.out.println("Into taxpayer list generation...");
		ArrayList<PumsMainView> pumslist = new ArrayList<PumsMainView>();

		try {
			// lets just get a count of the results before looping through next

			for (int i = 1; i <= k; i++) {
				if (results1.next()) {
					int theCustId = results1.getInt(1);
					String theSerialNo = results1.getString(2);
					int theAgep = results1.getInt(3);
					String theWagp = results1.getString(4);
					PumsMainView p1 = new PumsMainView(theAgep, theCustId, theSerialNo, theWagp);

					pumslist.add(p1);
					// System.out.println("Row number: " + j + " " + t1.getSsn());
				}
			}
			
		} catch (

		SQLException except) {
			except.printStackTrace();
		}

		return FXCollections.<PumsMainView>observableArrayList(pumslist);
	}

	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			// get a connection
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

}
