package com.arc.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.arc.models.TaxpayerMainView;

import com.arc.models.EventView;
import com.arc.models.InputEventView;
import com.arc.models.PumsMainView;
import com.arc.util.CurrencyCell;
import com.arc.util.TableColumns;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InputEventScreenController {

	@FXML
	private TextField searchFld;

	public InputEventScreenController() {
		super();
	}

	public InputEventScreenController(InputEventView model) {
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
	
	@FXML
	private HBox event_main;
	
	@FXML
	private HBox input_event_main;
	
	@FXML
	private TabPane tp;
	
	@FXML
	private TabPane tp2;
	
	@FXML
	private ChoiceBox mftCodeBox;
	
	@FXML
	private TitledPane pane_tree;
	
	@FXML 
	MenuBar menuBar;
	
	@FXML
	private TextField tc_code_search;
	
	
	Image nodeIcon = new Image (getClass().getResourceAsStream("/img/node_icon.png"));
	Image taxpayerIcon = new Image (getClass().getResourceAsStream("/img/taxpayer.png"));
	TreeView<String> treeView = new TreeView();
	


	private static InputEventView model;
	private static String dbURL = "jdbc:derby:C:\\Users\\dafshar\\Documents\\IRS\\DataViewerDB";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
	private static PreparedStatement stmt2 = null;
	private static TableView<TaxpayerMainView> tp_table = new TableView<TaxpayerMainView>();
	private static TableView<PumsMainView> pums_table = new TableView<PumsMainView>();
	private static TableView<EventView> event_table = new TableView<EventView>();
	private static TableView<InputEventView> input_event_table = new TableView<InputEventView>();
	private static TableColumns tableColumns = new TableColumns();
	private static int CustIdKey;
	private static String pinKey;
	private static Date transDateKey;
	private static String transCodeKey;
	ObservableList<String> mftCodeList = FXCollections.observableArrayList("MFT30","MFT55");
	public int inputEventIndex;

	@FXML
	private void initialize() {
		System.out.println("Initializing InputEventScreenController...");
		long startTime = System.nanoTime();
		createConnection();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
		System.out.println("get the connection took: " + duration);
		connect();
	}

	@FXML
	void performConnect(ActionEvent event) {
		// System.out.println("connect button pushed");
		// this.searchFld.setText("connect!");
		long startTime = System.nanoTime();
		inputEventView();
		long endTime = System.nanoTime();

		long duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
		System.out.println("Event view took: " + duration);
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

	
	
	private static ObservableList<EventView> getEventList(ResultSet results1) {

		ArrayList<EventView> tp1list = new ArrayList<EventView>();

		try {
			for (int j = 1; j <= 100; j++) {
				if (results1.next()) {
					String pin = results1.getString(1);
					Date transDate = results1.getDate(2);
					String transCode = results1.getString(3);
					String sysId = results1.getString(4);
					String partitionSysId = results1.getString(5);
					String cycleId = results1.getString(6);
					int unpostableCd = results1.getInt(7);
					int validityCd = results1.getInt(8);
					String inputTransText = results1.getString(9);
					
					//Check if you need this controller
					//EventView t1 = new EventView(pin, transDate, transCode, sysId, partitionSysId, cycleId, unpostableCd, validityCd, inputTransText);
					//tp1list.add(t1);
					// System.out.println("Row number: " + j + " " + t1.getSsn());
				}
			}
		} catch (SQLException except) {
			except.printStackTrace();
		}

		return FXCollections.<EventView>observableArrayList(tp1list);
	}
	
	private static ObservableList<InputEventView> getInputEventList(ResultSet results1) {

		ArrayList<InputEventView> tp1list = new ArrayList<InputEventView>();

		try {
			for (int j = 1; j <= 100; j++) {
				if (results1.next()) {
					String sysId = results1.getString(1);
					String inputTransText = results1.getString(2);
					InputEventView t1 = new InputEventView(sysId, sysId, inputTransText);
					tp1list.add(t1);
					// System.out.println("Row number: " + j + " " + t1.getSsn());
				}
			}
		} catch (SQLException except) {
			except.printStackTrace();
		}

		return FXCollections.<InputEventView>observableArrayList(tp1list);
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
	
	public void createTree() {
	    TreeItem<String> root = new TreeItem<>("Taxpayer", new ImageView(taxpayerIcon));
	    
	    TreeItem<String> firstNode = new TreeItem<>("Year", new ImageView(nodeIcon));
	    TreeItem<String> secondNode = new TreeItem<>("MFT Code", new ImageView(nodeIcon));
	    TreeItem<String> thirdNode = new TreeItem<>("Tax Module", new ImageView(nodeIcon));

	    
	    root.getChildren().add(firstNode);
	    firstNode.getChildren().add(secondNode);
	    secondNode.getChildren().add(thirdNode);
	    
	    
	    treeView.setRoot(root);

	}
	
	public void createTreePane()
    {
		createTree();
	    
		pane_tree.setContent(treeView);
    }
	
 
	
	public void createTab() {

		Tab tb1 = new Tab("2019");
		Tab tb2 = new Tab("2018");
		Tab tb3 = new Tab("2017");
		Tab tb4 = new Tab("2016");
		
		tp.getTabs().addAll(tb1, tb2, tb3, tb4);


		


	}
	
	public void createMFTBox() {
		
		mftCodeBox.setItems(mftCodeList);
	}
	
	@FXML
	
	private void eventMenuAction (ActionEvent event) throws IOException {
		
		Parent eventScreen = FXMLLoader.load(getClass().getResource("/com/arc/views/AccountScreen.fxml"));
		
		Scene eventScene = new Scene(eventScreen);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(eventScene);
		stage.show();
		
  
	
	}
	
	void inputEventView() {

		inputEventLayoutForm();

	}
	

	
	private void inputEventLayoutForm() {
		long startTime;
		// createConnection();
		long endTime;
		long duration; // divide by 1000000 to get milliseconds.
		//int index = returnEventIndex();
		

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();

	        fxmlLoader.setLocation(getClass().getResource("/com/arc/views/EventScreen.fxml"));
	        
	        Parent root = (Parent) fxmlLoader.load();
	        
	        EventScreenController eventController = fxmlLoader.getController();;
	        int indexValue = eventController.getEventIndex();
	        System.out.println("Testing input event from eventlayoutForm" + eventController.getEventIndex());
	        
			String sql = "select ACCOUNT_TRANSACTION_SYS_ID, INPUT_TRANSACTION_TXT from input_event_view where rownum = " + (indexValue + 1) ;
			stmt = conn.prepareStatement(sql);
			startTime = System.nanoTime();
			ResultSet results = stmt.executeQuery();
			// ResultSet results = stmt.executeQuery();
			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("get result set took: " + duration);

			// create the data table
			startTime = System.nanoTime();
			input_event_table.getItems().addAll(getInputEventList(results));
			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("get list view took: " + duration);

			// now add the columns to the table
			startTime = System.nanoTime();
			
			input_event_table.getColumns().addAll(tableColumns.getInputEventSysIdCol(), tableColumns.getInputEventInputTransTextCol());
			


			input_event_table.setPrefHeight(170);
			input_event_table.setPrefWidth(1400);


			endTime = System.nanoTime();

			duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
			System.out.println("get cols and props set took: " + duration);

			startTime = System.nanoTime();
			// now show the table in the right hbox
			input_event_main.getChildren().add(input_event_table);
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
		
		catch (IOException except) {
			except.printStackTrace();
		}
		
		System.out.println("We are after inputeventlayout");
	}
	


	void connect() {
		// System.out.println("connect button pushed");
		// this.searchFld.setText("connect!");
		long startTime = System.nanoTime();

		System.out.println("get the input index" + getInputEventIndex());
		inputEventView();
		long endTime = System.nanoTime();

		long duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
		System.out.println("Event view took: " + duration);
		
	}
	
	public int getInputEventIndex() {
		return this.inputEventIndex;
	}
	
	public void setInputEventIndex(int inputEventIndex) {
		this.inputEventIndex = inputEventIndex;
	}
	



	

}
