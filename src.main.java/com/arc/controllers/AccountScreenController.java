package com.arc.controllers;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.arc.models.TaxpayerTransMainView;
import com.arc.models.EventView;
import com.arc.models.InputEventView;
import com.arc.models.PumsMainView;
import com.arc.models.TaxModuleMainView;

import com.arc.util.Context;
import com.arc.util.CurrencyCell;
import com.arc.util.TableColumns;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.arc.util.SqlUtil;
import org.controlsfx.control.textfield.TextFields;


public class AccountScreenController {
	
	public AccountScreenController() {
		super();
	}

	public AccountScreenController(TaxpayerTransMainView model) {
		this.model = model;

	}
	
	public AccountScreenController(TaxModuleMainView tm_model) {
		this.tm_model = tm_model;

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
	private Button process_events_btn;
	
	@FXML
	private TextField validity_cd_txtfield;
	
	@FXML
	private TextField tp_name_txtfield;
	
	@FXML
	private TextField tp_address_txtfield;
	
	@FXML
	private TextField tp_bal_amt_txtfield;
	
	@FXML
	private TextField tp_birth_txtfield;
	
	@FXML
	private TextField tm_mft_code_txtfield;
	
	@FXML
	private TextField tm_balance_amt_txtfield;
	
	@FXML 
	private TextField textfield_tin;
	
	@FXML
	private HBox taxpayer_main;
	
	@FXML
	private HBox taxmodule_main;
	
	@FXML
	ComboBox<String> combobox_taxperiod;
	
	@FXML
	ComboBox<String> combobox_mftcode;
	
	@FXML
	TableView<TaxpayerTransMainView> tp_table;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, Number> trans_cdCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, Number> trans_sub_cdCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, String> locator_noCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, Date> end_dtCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, String> info_textCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, String> posted_cyc_idCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, Date> eff_end_dtCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, Number> expiration_cdCol;
	
	@FXML
	private TableColumn<TaxpayerTransMainView, Number> mft_cdCol;
	 
	@FXML
	private TableColumn<TaxpayerTransMainView, String> ssnCol;
	
	@FXML
	TableView<TaxModuleMainView> tm_table;
	
	@FXML
	private TableColumn<TaxModuleMainView, Number> tm_mft_cdCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, Number> tm_trans_cdCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, Number> tm_trans_sub_cdCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, String> tm_locator_noCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, Date> tm_end_dtCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, String> tm_info_textCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, String> tm_posted_cyc_idCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, Date> tm_eff_end_dtCol;
	
	@FXML
	private TableColumn<TaxModuleMainView, Number> tm_expiration_cdCol;
	
	@FXML
	Label tmPostTransLabel;
	
	@FXML
	Label tpPostTransLabel;

	private static TaxpayerTransMainView model;
	private static TaxModuleMainView tm_model;
	private static String dbURL = "jdbc:derby:C:\\Users\\dafshar\\Documents\\IRS\\DataViewerDB";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
	ObservableList mftCodeList = FXCollections.observableArrayList();
	ObservableList taxPeriodList = FXCollections.observableArrayList();
	private static TableColumns tableColumns = new TableColumns();
	private static String SSN;
	private static SqlUtil sqlUtil = new SqlUtil();
	private static PreparedStatement stmt2 = null;
	final ObservableList<TaxpayerTransMainView> taxPayerData = FXCollections.observableArrayList();
	final ObservableList<TaxModuleMainView> taxModuleData = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		System.out.println("Initializing AccountScreenController...");

		long startTime = System.nanoTime();
		createConnection();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; 
		System.out.println("get the connection took: " + duration);
		
		tp_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tm_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Label tpPlaceHolder = new Label("No results displayed");
		Label tmPlaceHolder = new Label("No results displayed");
		tmPostTransLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		tpPostTransLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		tp_table.setPlaceholder(tpPlaceHolder);
		tm_table.setPlaceholder(tmPlaceHolder);
		
		
		combobox_mftcode.setDisable(true);
		combobox_taxperiod.setDisable(true);
		
		stmt =sqlUtil.getPreparedStatement(conn, "getAllSSN");	
		
		
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
			
		tp_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                  if(event.getClickCount() == 2){
                  try {
                               String parameter = tp_table.getSelectionModel().getSelectedItem().getAccountTransSysId();                             

                          FXMLLoader fxmlLoader = new FXMLLoader();

                          fxmlLoader.setLocation(getClass().getResource("/com/arc/views/EventScreen.fxml"));
                          
                          Parent root = (Parent) fxmlLoader.load();
                          
                          EventScreenController eventController = fxmlLoader.getController();;
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root));
                          //Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                       
                          stage.setTitle("Event Screen");
                          //stage.setScene(scene);
                          stage.show();
                          
                          System.out.println("Parameter passed to Event Screen:" + parameter);
                          
                          eventController.eventLayoutForm(parameter);
       
                      } catch (IOException e) {
                          Logger logger = Logger.getLogger(getClass().getName());
                          logger.log(Level.SEVERE, "Failed to create new Window.", e);
                      }
                  }
                  else {
                         System.out.println("Not double clicked");
                  }
                  
     }});
		
		tm_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                  if(event.getClickCount() == 2){
                  try {
                               String parameter = tm_table.getSelectionModel().getSelectedItem().getAccountTransSysId();                             

                          FXMLLoader fxmlLoader = new FXMLLoader();

                          fxmlLoader.setLocation(getClass().getResource("/com/arc/views/EventScreen.fxml"));
                          
                          Parent root = (Parent) fxmlLoader.load();
                          
                          EventScreenController eventController = fxmlLoader.getController();;
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root));
                          //Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                       
                          stage.setTitle("Event Screen");
                          //stage.setScene(scene);
                          stage.show();
                          
                          System.out.println("Parameter passed to Event Screen:" + parameter);
                          
                          eventController.eventLayoutForm(parameter);
       
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
	
	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
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
	
	combobox_mftcode.setDisable(false);
	combobox_taxperiod.setDisable(false);
	
	
	populateTaxpayerHeader(textfield_tin.getText());
	populateTaxpayerBalance(textfield_tin.getText());
	populateMFTComboBox(textfield_tin.getText());
	
	taxpayerPostedTrans(textfield_tin.getText());
	
	taxModulePostedTrans(textfield_tin.getText(), null, null);
	

	long startTime = System.nanoTime();
	long endTime = System.nanoTime();
	long duration = (endTime - startTime) / 1000000; 
	System.out.println("taxpayer view took: " + duration);
}

	public void populateMFTComboBox(String SSN) {
		
		combobox_mftcode.getItems().clear();
		
		try {
			
			stmt =sqlUtil.getPreparedStatement(conn, "getMFTCode");
			stmt.setString(1, SSN);
			ResultSet results = stmt.executeQuery();
			
			while (results.next()) {
				mftCodeList.add(results.getString("mft_cd"));

			}
			
			combobox_mftcode.setItems(mftCodeList);
	
			results.close();
			stmt.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	
	}
	
	@FXML
	void performClear(ActionEvent event) {
		
		combobox_mftcode.setValue(null);
		combobox_taxperiod.setValue(null);
		combobox_mftcode.setDisable(true);
		combobox_taxperiod.setDisable(true);
		textfield_tin.clear();
		tp_name_txtfield.clear();
		tp_birth_txtfield.clear();
		tp_address_txtfield.clear();
		tm_mft_code_txtfield.clear();
		tm_balance_amt_txtfield.clear();
		tp_table.getItems().clear();
		tm_table.getItems().clear();
	}
	
	@FXML
	void performProcessEvents(ActionEvent event) {
		
		try {                          

       FXMLLoader fxmlLoader = new FXMLLoader();

       fxmlLoader.setLocation(getClass().getResource("/com/arc/views/EventScreen.fxml"));
       
       Parent root = (Parent) fxmlLoader.load();
       
       EventScreenController eventController = fxmlLoader.getController();;
       Stage stage = new Stage();
       stage.setScene(new Scene(root));
       //Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    
       stage.setTitle("Event Screen");
       //stage.setScene(scene);
       stage.show();
       
       System.out.println("Parameter passed to Event Screen:" + textfield_tin.getText());
       
       eventController.unprocessedEventLayoutForm(textfield_tin.getText());
       eventController.setTextFieldTin(textfield_tin.getText());

   } catch (IOException e) {
       Logger logger = Logger.getLogger(getClass().getName());
       logger.log(Level.SEVERE, "Failed to create new Window.", e);
   }
}
		
		
	
	

public void populateTaxPeriodComboBox(String SSN, String mftCode) {
	
	combobox_taxperiod.getItems().clear();

		try {
			
			stmt =sqlUtil.getPreparedStatement(conn, "getTaxPeriod");
			stmt.setString(1, SSN);
			stmt.setString(2, mftCode);
			ResultSet results = stmt.executeQuery();
			
			while (results.next()) {
				taxPeriodList.add(results.getString("posted_cyc_id"));

			}
			
			combobox_taxperiod.setItems(taxPeriodList);

			results.close();
			stmt.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	
	}

public void mftComboChanged(ActionEvent event) {
	
	tm_table.getItems().removeAll();
	tm_table.getItems().clear();
	
	populateTaxPeriodComboBox(textfield_tin.getText(), combobox_mftcode.getValue());
	populateTaxModuleHeaderByMft(textfield_tin.getText(), combobox_mftcode.getValue());
	taxModulePostedTrans(textfield_tin.getText(), combobox_mftcode.getValue(), null);

    System.out.println("mftComboChanged " + combobox_mftcode.getValue());
	
}

public void taxPeriodComboChanged(ActionEvent event) {
	
	tm_table.getItems().removeAll();
	tm_table.getItems().clear();
	
	populateTaxModuleHeaderByYear(textfield_tin.getText(), combobox_mftcode.getValue(), combobox_taxperiod.getValue());
	taxModulePostedTrans(textfield_tin.getText(), combobox_mftcode.getValue(), combobox_taxperiod.getValue());
	
	
    System.out.println("Tin:" + textfield_tin.getText() + "taxPeriodCombo " + combobox_taxperiod.getValue() + "MFTCombo " + combobox_mftcode.getValue());
	
}


public ObservableList<TaxpayerTransMainView> getTaxpayerData() {
    return taxPayerData;
}

public ObservableList<TaxModuleMainView> getTaxModuleData() {
    return taxModuleData;
}

public void taxpayerPostedTrans(String SSN) {
	
	tp_table.getItems().clear();
	
	 try {
		 stmt =sqlUtil.getPreparedStatement(conn, "getTaxpayerAccountTrans");
		stmt.setString(1, SSN);
		 ResultSet results = stmt.executeQuery();

		 
		 while (results.next()) {
			 taxPayerData.add(new TaxpayerTransMainView ( 
					 results.getInt("trans_cd"),
					 results.getInt("trans_sub_cd"),
					 results.getString("locator_no"),
					 results.getDate("end_dt"),
					 results.getString("info_text"),
					 results.getString("posted_cyc_id"),
					 results.getDate("eff_end_dt"),
					 results.getString("ssn"),
					 results.getString("account_transaction_sys_id")
					 ));
		 }
		 results.close();
		 
	 } catch (Exception except) {
			except.printStackTrace();
		}
	
	trans_cdCol.setCellValueFactory(cellData -> cellData.getValue().trans_cdProperty());
	trans_sub_cdCol.setCellValueFactory(cellData -> cellData.getValue().trans_sub_cdProperty());
	locator_noCol.setCellValueFactory(cellData -> cellData.getValue().locator_noProperty());
	end_dtCol.setCellValueFactory(cellData -> cellData.getValue().end_dtProperty());
	info_textCol.setCellValueFactory(cellData -> cellData.getValue().info_textProperty());
	posted_cyc_idCol.setCellValueFactory(cellData -> cellData.getValue().posted_cyc_idProperty());
	eff_end_dtCol.setCellValueFactory(cellData -> cellData.getValue().eff_end_dtProperty());

    tp_table.setItems(getTaxpayerData());
    
    

}

public void taxpayerPostedTransEvent(String accountTransSysId) {
	
	tp_table.getItems().clear();
	
	 try {
		 stmt =sqlUtil.getPreparedStatement(conn, "getAllTaxPayerTrans");
		stmt.setString(1, accountTransSysId);
		 ResultSet results = stmt.executeQuery();

		 
		 while (results.next()) {
			 taxPayerData.add(new TaxpayerTransMainView ( 
					 results.getInt("trans_cd"),
					 results.getInt("trans_sub_cd"),
					 results.getString("locator_no"),
					 results.getDate("end_dt"),
					 results.getString("info_text"),
					 results.getString("posted_cyc_id"),
					 results.getDate("eff_end_dt"),
					 results.getString("ssn"),
					 results.getString("account_transaction_sys_id")
					 ));
		 }
		 results.close();
		 
	 } catch (Exception except) {
			except.printStackTrace();
		}
	
	trans_cdCol.setCellValueFactory(cellData -> cellData.getValue().trans_cdProperty());
	trans_sub_cdCol.setCellValueFactory(cellData -> cellData.getValue().trans_sub_cdProperty());
	locator_noCol.setCellValueFactory(cellData -> cellData.getValue().locator_noProperty());
	end_dtCol.setCellValueFactory(cellData -> cellData.getValue().end_dtProperty());
	info_textCol.setCellValueFactory(cellData -> cellData.getValue().info_textProperty());
	posted_cyc_idCol.setCellValueFactory(cellData -> cellData.getValue().posted_cyc_idProperty());
	eff_end_dtCol.setCellValueFactory(cellData -> cellData.getValue().eff_end_dtProperty());
	
    tp_table.setItems(getTaxpayerData());
    
    

}

public void taxModulePostedTrans(String SSN, String mftCd, String taxYear) {
	
	tm_table.getItems().clear();
	
	if(mftCd == null)
	 try {
		  
		 stmt =sqlUtil.getPreparedStatement(conn, "getTaxModules");
		stmt.setString(1, SSN);
		 ResultSet results = stmt.executeQuery();
		 
		 while (results.next()) {
			 taxModuleData.add(new TaxModuleMainView ( 
					 results.getInt("mft_cd"),
					 results.getInt("trans_cd"),
					 results.getInt("trans_sub_cd"),
					 results.getString("locator_no"),
					 results.getDate("end_dt"),
					 results.getString("info_text"),
					 results.getString("posted_cyc_id"),
					 results.getDate("eff_end_dt"),
					 results.getInt("expiration_cd"),
					 results.getString("ssn"),
					 results.getString("account_transaction_sys_id")
					 ));
		 }
		 results.close();
		 
	 } catch (Exception except) {
			except.printStackTrace();
	 }
	else if (mftCd != null && taxYear == null) {
		try {
			  
			 stmt =sqlUtil.getPreparedStatement(conn, "getTaxModulesMftCd");
			 stmt.setString(1, SSN);
			 stmt.setString(2, mftCd);
			 ResultSet results = stmt.executeQuery();
			 
			 while (results.next()) {
				 taxModuleData.add(new TaxModuleMainView ( 
						 results.getInt("mft_cd"),
						 results.getInt("trans_cd"),
						 results.getInt("trans_sub_cd"),
						 results.getString("locator_no"),
						 results.getDate("end_dt"),
						 results.getString("info_text"),
						 results.getString("posted_cyc_id"),
						 results.getDate("eff_end_dt"),
						 results.getInt("expiration_cd"),
						 results.getString("ssn"),
						 results.getString("account_transaction_sys_id")
						 ));
			 }
			 results.close();
			 
		 } catch (Exception except) {
				except.printStackTrace();
		 }
		
	}
	
	else if (mftCd != null && taxYear != null) {
		try {
			  
			 stmt =sqlUtil.getPreparedStatement(conn, "getTaxModulesTaxYear");
			 stmt.setString(1, SSN);
			 stmt.setString(2, mftCd);
			 stmt.setString(3, taxYear);
			 ResultSet results = stmt.executeQuery();
			 
			 while (results.next()) {
				 taxModuleData.add(new TaxModuleMainView ( 
						 results.getInt("mft_cd"),
						 results.getInt("trans_cd"),
						 results.getInt("trans_sub_cd"),
						 results.getString("locator_no"),
						 results.getDate("end_dt"),
						 results.getString("info_text"),
						 results.getString("posted_cyc_id"),
						 results.getDate("eff_end_dt"),
						 results.getInt("expiration_cd"),
						 results.getString("ssn"),
						 results.getString("account_transaction_sys_id")
						 ));
			 }
			 results.close();
			 
		 } catch (Exception except) {
				except.printStackTrace();
		 }
	}
		
	
	tm_mft_cdCol.setCellValueFactory(cellData -> cellData.getValue().mft_cdProperty());
	tm_trans_cdCol.setCellValueFactory(cellData -> cellData.getValue().trans_cdProperty());
	tm_trans_sub_cdCol.setCellValueFactory(cellData -> cellData.getValue().trans_sub_cdProperty());
	tm_locator_noCol.setCellValueFactory(cellData -> cellData.getValue().locator_noProperty());
	tm_end_dtCol.setCellValueFactory(cellData -> cellData.getValue().end_dtProperty());
	tm_info_textCol.setCellValueFactory(cellData -> cellData.getValue().info_textProperty());
	tm_posted_cyc_idCol.setCellValueFactory(cellData -> cellData.getValue().posted_cyc_idProperty());
	tm_eff_end_dtCol.setCellValueFactory(cellData -> cellData.getValue().eff_end_dtProperty());
	tm_expiration_cdCol.setCellValueFactory(cellData -> cellData.getValue().expiration_cdProperty());


   tm_table.setItems(getTaxModuleData());
   
  
}


public void populateTaxpayerHeader(String SSN){
	
	tp_name_txtfield.clear();
	tp_birth_txtfield.clear();
	tp_address_txtfield.clear();
	
	try {
		
		stmt =sqlUtil.getPreparedStatement(conn, "getTaxPayerHeader");
		stmt.setString(1, SSN);
		ResultSet results = stmt.executeQuery();
		
		while (results.next()) {

			String taxpayerName = results.getString("NAME");
			String taxpayerBirthDt = results.getString("BIRTH_DT");
			String taxpayerAddress = results.getString("Address");
			
			tp_name_txtfield.setText(taxpayerName);
			tp_birth_txtfield.setText(taxpayerBirthDt);
			tp_address_txtfield.setText(taxpayerAddress);
		}

		results.close();
		stmt.close();
		
	} catch (SQLException e) {

		e.printStackTrace();
	}
}

public void populateTaxpayerBalance(String SSN){
	
	tp_bal_amt_txtfield.clear();
	
	try {
		
		stmt =sqlUtil.getPreparedStatement(conn, "getTaxPayerBalance");
		stmt.setString(1, SSN);
		ResultSet results = stmt.executeQuery();
		
		while (results.next()) {

			String taxpayerBalAmt = results.getString("Balance");
			
			tp_bal_amt_txtfield.setText(taxpayerBalAmt);

		}

		results.close();
		stmt.close();
		
	} catch (SQLException e) {

		e.printStackTrace();
	}
}

public void populateTaxModuleHeaderByMft(String SSN, String mftCd){
	
	tm_mft_code_txtfield.clear();
	tm_balance_amt_txtfield.clear();
	
	try {
		
		stmt =sqlUtil.getPreparedStatement(conn, "getTaxModuleHeaderByMft");
		stmt.setString(1, SSN);
		stmt.setString(2, mftCd);
		
		ResultSet results = stmt.executeQuery();
		
		while (results.next()) {

			String mftCode = results.getString("mft_cd");
			String balanceAmt = results.getString("balance_amt");

			
			tm_mft_code_txtfield.setText(mftCode);
			tm_balance_amt_txtfield.setText(balanceAmt);
		}

		results.close();
		stmt.close();
		
	} catch (SQLException e) {

		e.printStackTrace();
	}
}

public void populateTaxModuleHeaderByYear(String SSN, String mftCd, String taxPeriod){
	
	tm_mft_code_txtfield.clear();
	tm_balance_amt_txtfield.clear();
	
	try {
		
		stmt =sqlUtil.getPreparedStatement(conn, "getTaxModuleHeaderByYear");
		stmt.setString(1, SSN);
		stmt.setString(2, mftCd);
		stmt.setString(3, taxPeriod);
		
		ResultSet results = stmt.executeQuery();
		
		while (results.next()) {

			String mftCode = results.getString("mft_cd");
			String balanceAmt = results.getString("balance_amt");

			
			tm_mft_code_txtfield.setText(mftCode);
			tm_balance_amt_txtfield.setText(balanceAmt);
		}

		results.close();
		stmt.close();
		
	} catch (SQLException e) {

		e.printStackTrace();
	}
}

	public void filterTaxModuleData () {
		
		 FilteredList<TaxModuleMainView> filteredData = new FilteredList<>(getTaxModuleData(), p -> true);
		 
		 System.out.println("Is getTaxModuleData empty?" + filteredData.size());

		 combobox_mftcode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	            filteredData.setPredicate(taxmodule -> {
	                if (newValue == null || newValue.isEmpty()) {
	                    return true;
	                }
	                
	                String valueFilter = newValue;
	                String taxMftCd = String.valueOf(taxmodule.getmft_cd());
	                
	                if (taxMftCd.contains(valueFilter)) {
	                    return true; 
	                } 
	                return false; 
	            });
	        });

	        SortedList<TaxModuleMainView> sortedData = new SortedList<>(filteredData);
	        sortedData.comparatorProperty().bind(tm_table.comparatorProperty());
	        tm_table.setItems(sortedData);
	}
	
	public void setTextFieldTin (String tin) {
		this.textfield_tin.setText(tin);
	}
	

}
