package com.arc.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;

import com.arc.models.EventView;
import com.arc.models.TaxpayerMainView;
import com.arc.util.SqlUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TaxpayerScreenController {
	
	public TaxpayerScreenController() {
		super();
	}
	
	@FXML 
	MenuBar menuBar;
	
	@FXML
	Menu menu_view;
	
	@FXML
	private Button searchBtn;
	
	@FXML
	private Button clearBtn;
	
	@FXML 
	private TextField textfield_tin;
	
	@FXML
	ComboBox<String> event_comp_ind;
	
	@FXML
	ComboBox<String> tp_trans_cd;
	
	@FXML
	ComboBox<String> tm_trans_cd;
	
	@FXML
	ComboBox<String> event_trans_cd;
	
	
	private static String dbURL = "jdbc:derby:C:\\Users\\dafshar\\Documents\\IRS\\DataViewerDB";
	private static Connection conn = null;
	
	private static SqlUtil sqlUtil = new SqlUtil();
	
	@FXML
	Pagination tpPagination;
	//private final TableView <TaxpayerMainView> tpTable = createTable();
	private TableView <TaxpayerMainView> tpTable = new TableView<TaxpayerMainView> ();
	private List <TaxpayerMainView> data; 
	private final static int rowsPerPage = 14;
	private final static int datasize = 1000;
	ObservableList compIndCodeList = FXCollections.observableArrayList();
	ObservableList transCodeList = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		System.out.println("Initializing TaxpayerScreen Controller...");

		long startTime = System.nanoTime();
		createConnection();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; 
		System.out.println("get the connection took: " + duration);
		
		tpTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		PreparedStatement stmt =sqlUtil.getPreparedStatement(conn, "getAllSSN");	
		
		
		try {
			ResultSet ssnResults = stmt.executeQuery();
			
			ArrayList<String> allSSN = new ArrayList<String>();
			
			while(ssnResults.next()) {
				allSSN.add(ssnResults.getString("ssn"));
				}

			TextFields.bindAutoCompletion(textfield_tin, allSSN);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		popCompIndComboBox();
		popTransCdComboBox();
		
		

	}
	
	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}
	
	private TableView <TaxpayerMainView> createTable() {
		
		TableView <TaxpayerMainView> tpTable = new TableView<>();
		
		TableColumn<TaxpayerMainView, String> ssnCol = new TableColumn<> ("ssn");
		ssnCol.setCellValueFactory(param -> param.getValue().ssnProperty());
		
		TableColumn<TaxpayerMainView, String> lnameCol = new TableColumn<> ("lastName");
		lnameCol.setCellValueFactory(param -> param.getValue().lastNameProperty());
		
		TableColumn<TaxpayerMainView, String> fnameCol = new TableColumn<> ("firstName");
		fnameCol.setCellValueFactory(param -> param.getValue().firstNameProperty());
		
		TableColumn<TaxpayerMainView, String> addressCol = new TableColumn<> ("address");
		addressCol.setCellValueFactory(param -> param.getValue().addressProperty());
		
		TableColumn<TaxpayerMainView, Date> DOBCol = new TableColumn<> ("DOB");
		DOBCol.setCellValueFactory(param -> param.getValue().DOBProperty());
		
		TableColumn<TaxpayerMainView, Number> balanceCol = new TableColumn<> ("balanceAmt");
		balanceCol.setCellValueFactory(param -> param.getValue().balanceAmtProperty());
		
		tpTable.getColumns().addAll(ssnCol, lnameCol, fnameCol, addressCol, DOBCol, balanceCol);
		
		return tpTable;
	}
	
	private List <TaxpayerMainView> createData (String pin){
		
		createConnection();
		List <TaxpayerMainView> data = new ArrayList<> (datasize);
		
		try {
			System.out.println("Pin:" + pin.isEmpty());
			PreparedStatement stmt =sqlUtil.getPreparedStatement(conn, "getAllTaxPayers");
			if (pin.isEmpty() ) {
				stmt.setString(1, "%");
			}
			else {
				stmt.setString(1, pin);
			}
			
			ResultSet results = stmt.executeQuery();
			
			for (int i = 1; i <= datasize; i++) {
				if (results.next()) {
					String ssn = results.getString(1);
					String lname = results.getString(2);
					String fname = results.getString(3);
					String address = results.getString(4);
					Date DOB = results.getDate(5);
					Double balanceAmt = results.getDouble(6);

					TaxpayerMainView t1 = new TaxpayerMainView(ssn, lname, fname, address, DOB, balanceAmt);
					data.add(t1);

				}
			}
		} catch (SQLException except) {
			except.printStackTrace();
		}
		
		return data;
	}
	
private List <TaxpayerMainView> createData (String pin, String compInd){
		
		createConnection();
		List <TaxpayerMainView> data = new ArrayList<> (datasize);
		
		try {
			System.out.println("Pin:" + pin.isEmpty());
			PreparedStatement stmt =sqlUtil.getPreparedStatement(conn, "getTaxPayersByCompInd");
			if (pin.isEmpty() ) {
				stmt.setString(1, "%");
				stmt.setString(2, compInd);
			}
			else {
				stmt.setString(1, pin);
				stmt.setString(2, compInd);
			}
			
			ResultSet results = stmt.executeQuery();
			
			for (int i = 1; i <= datasize; i++) {
				if (results.next()) {
					String ssn = results.getString(1);
					String lname = results.getString(2);
					String fname = results.getString(3);
					String address = results.getString(4);
					Date DOB = results.getDate(5);
					Double balanceAmt = results.getDouble(6);

					TaxpayerMainView t1 = new TaxpayerMainView(ssn, lname, fname, address, DOB, balanceAmt);
					data.add(t1);

				}
			}
		} catch (SQLException except) {
			except.printStackTrace();
		}
		
		return data;
	}

private List <TaxpayerMainView> createData (String pin, String compInd, String transCd){
	
	createConnection();
	List <TaxpayerMainView> data = new ArrayList<> (datasize);
	
	try {
		System.out.println("Pin for getTaxPayersByTransCd:" + pin.isEmpty() + pin + compInd + transCd );
		PreparedStatement stmt =sqlUtil.getPreparedStatement(conn, "getTaxPayersByTransCd");
		
		if (pin.isEmpty()) {
		stmt.setString(1, "%");
		stmt.setString(2, compInd);
		stmt.setString(3, transCd);
		}
		else {
		stmt.setString(1, pin);
		stmt.setString(2, compInd);
		stmt.setString(3, transCd);
		}
		ResultSet results = stmt.executeQuery();
		
		for (int i = 1; i <= datasize; i++) {
			if (results.next()) {
				String ssn = results.getString(1);
				String lname = results.getString(2);
				String fname = results.getString(3);
				String address = results.getString(4);
				Date DOB = results.getDate(5);
				Double balanceAmt = results.getDouble(6);

				TaxpayerMainView t1 = new TaxpayerMainView(ssn, lname, fname, address, DOB, balanceAmt);
				data.add(t1);

			}
		}
	} catch (SQLException except) {
		except.printStackTrace();
	}
	
	return data;
}


	
	private Node createPage (int pageIndex) {
		int fromIndex = pageIndex * rowsPerPage;
		int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
		tpTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
		return tpTable;
	}
	
@FXML
	
	private void eventMenuAction (ActionEvent event) throws IOException {
		
		Parent eventScreen = FXMLLoader.load(getClass().getResource("/com/arc/views/EventScreen.fxml"));
		
		Scene eventScene = new Scene(eventScreen);
		
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(eventScene);
		stage.show();
		
  
	
	}

@FXML

private void explorerMenuAction (ActionEvent event) throws IOException {
	
	Parent explorerScreen = FXMLLoader.load(getClass().getResource("/com/arc/views/ExplorerScreen.fxml"));
	
	Scene explorerScene = new Scene(explorerScreen);
	
	Stage stage = (Stage) menuBar.getScene().getWindow();
	stage.setScene(explorerScene);
	stage.show();
	


}

@FXML
void performSearch(ActionEvent event) {

	long startTime = System.nanoTime();
	long endTime = System.nanoTime();
	long duration = (endTime - startTime) / 1000000; 
	System.out.println("taxpayer view took: " + duration);
	
	if (event_comp_ind.getSelectionModel().isEmpty() && event_trans_cd.getSelectionModel().isEmpty()) {
		System.out.println("Is event comp in empty? " + event_comp_ind.getSelectionModel().isEmpty());
	this.data = createData(textfield_tin.getText());
	}
	else if (event_trans_cd.getSelectionModel().isEmpty()){
		this.data = createData(textfield_tin.getText(), event_comp_ind.getValue());
	}
	else {
		this.data = createData(textfield_tin.getText(), event_comp_ind.getValue() , event_trans_cd.getValue());
	}
	
	tpTable = createTable();
	tpTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	tpPagination.setPageFactory(this::createPage);
	
	tpTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
              if(event.getClickCount() == 2){
              try {
                           String ssn = tpTable.getSelectionModel().getSelectedItem().getSSN();                             

                      FXMLLoader fxmlLoader = new FXMLLoader();

                      fxmlLoader.setLocation(getClass().getResource("/com/arc/views/AccountScreen.fxml"));
                      
                      Parent root = (Parent) fxmlLoader.load();
                      
                      AccountScreenController accountController = fxmlLoader.getController();;
                      Stage stage = new Stage();
                      stage.setScene(new Scene(root));
                      //Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                   
                      stage.setTitle("Account Screen");
                      //stage.setScene(scene);
                      stage.show();
                      
                      System.out.println("Parameter passed to Account Screen:" + ssn);
                      
                      accountController.setTextFieldTin(ssn);
                      accountController.combobox_mftcode.setDisable(false);
                      accountController.combobox_taxperiod.setDisable(false);
                      accountController.populateTaxpayerHeader(ssn);
                      accountController.populateTaxpayerBalance(ssn);
                      accountController.taxpayerPostedTrans(ssn);
                      accountController.taxModulePostedTrans(ssn, null, null);
                      accountController.populateMFTComboBox(ssn);
   
                  } catch (IOException e) {
                      Logger logger = Logger.getLogger(getClass().getName());
                      logger.log(Level.SEVERE, "Failed to create new Window.", e);
                  }
              }
              else {
                     System.out.println("Not double clicked");
              }
              
 }});
}

@FXML

private void accountMenuAction (ActionEvent event) throws IOException {
	
	Parent eventScreen = FXMLLoader.load(getClass().getResource("/com/arc/views/AccountScreen.fxml"));
	
	Scene eventScene = new Scene(eventScreen);
	
	Stage stage = (Stage) menuBar.getScene().getWindow();
	stage.setScene(eventScene);
	stage.show();
	


}

public void popCompIndComboBox() {
	
	event_comp_ind.getItems().clear();
	
	try {
		PreparedStatement stmt =sqlUtil.getPreparedStatement(conn, "getCompInd");
		
		ResultSet results = stmt.executeQuery();
		
		while (results.next()) {
			compIndCodeList.add(results.getString("status"));

		}
		
		event_comp_ind.setItems(compIndCodeList);

		results.close();
		stmt.close();
		
	} catch (SQLException e) {

		e.printStackTrace();
	}

}

public void popTransCdComboBox() {
	
	tp_trans_cd.getItems().clear();
	tm_trans_cd.getItems().clear();
	event_trans_cd.getItems().clear();
	
	try {
		PreparedStatement stmt =sqlUtil.getPreparedStatement(conn, "getTransCdCombo");
		
		ResultSet results = stmt.executeQuery();
		
		while (results.next()) {
			transCodeList.add(results.getString("account_transaction_cd"));

		}
		
		tp_trans_cd.setItems(transCodeList);
		tm_trans_cd.setItems(transCodeList);
		event_trans_cd.setItems(transCodeList);

		results.close();
		stmt.close();
		
	} catch (SQLException e) {

		e.printStackTrace();
	}

}

@FXML
public void compIndComboChanged(ActionEvent event) {


    System.out.println("mftComboChanged " + event_comp_ind.getValue());
	
}

@FXML
void performClear(ActionEvent event) {
	
	event_comp_ind.setValue(null);
	tp_trans_cd.setValue(null);
	tm_trans_cd.setValue(null);
	event_trans_cd.setValue(null);
	textfield_tin.clear();
	tpTable.getItems().clear();
}

}
