package com.arc.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;



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

public class EventScreenController {
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@FXML
	private TextField searchFld;

	public EventScreenController() {
		super();
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
	
	@FXML
	private TextField tin_search;
	
	@FXML
	private TextField date_search;
	
	@FXML
	private TextField validity_search;
	
	@FXML
	private Button process_event_Btn;
	
	private static EventView model;
	private static InputEventView inputModel;

	//private static String dbURL = "jdbc:derby:C:\\Users\\dafshar\\Documents\\IRS\\DataViewerDB";
	private static String dbURL = "jdbc:derby:C:\\Users\\dafshar\\Documents\\IRS\\TransactionHandlerDB";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
	private static PreparedStatement stmt2 = null;

	private static TableView<PumsMainView> pums_table = new TableView<PumsMainView>();
	private static TableView<EventView> event_table = new TableView<EventView>();
	private static TableView<InputEventView> input_event_table = new TableView<InputEventView>();
	private static TableColumns tableColumns = new TableColumns();
	private static int CustIdKey;
	private static String eventSysIdKey;
	private static String pinKey;
	private static Date transDateKey;
	private static String transCodeKey;
	ObservableList<String> mftCodeList = FXCollections.observableArrayList("MFT30","MFT55");
	public static int eventIndex;

	@FXML
	private void initialize() {
		System.out.println("Initializing EVentScreenController...");
		long startTime = System.nanoTime();
		createConnection();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
		System.out.println("get the connection took: " + duration);
		
		validity_search.setText("0");
		event_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		/*
		event_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                  if(event.getClickCount() == 2){
                  try {
                               String transSysId = event_table.getSelectionModel().getSelectedItem().getSysId();
                               String PIN = event_table.getSelectionModel().getSelectedItem().getPIN();

                          FXMLLoader fxmlLoader = new FXMLLoader();

                          fxmlLoader.setLocation(getClass().getResource("/com/arc/views/AccountScreen.fxml"));
                          
                          Parent root = (Parent) fxmlLoader.load();
                          
                          AccountScreenController accountController = fxmlLoader.getController();
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root));
                          //Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                       
                          stage.setTitle("Event Screen");
                          //stage.setScene(scene);
                          stage.show();
                          
                          System.out.println("Parameter passed to Event Screen:" + transSysId);
                          
                          accountController.populateTaxpayerHeader(PIN);
                          accountController.populateTaxpayerBalance(PIN);
                          accountController.taxpayerPostedTransEvent(transSysId);
       
                      } catch (IOException e) {
                          Logger logger = Logger.getLogger(getClass().getName());
                          logger.log(Level.SEVERE, "Failed to create new Window.", e);
                      }
                  }
                  else {
                         System.out.println("Not double clicked");
                  }
                  
     }});
     */
		
		event_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                  if(event.getClickCount() == 2){
                  try {
                               String parameter = event_table.getSelectionModel().getSelectedItem().getPartitionSysId();

                          FXMLLoader fxmlLoader = new FXMLLoader();
                          System.out.println("Our Paramenter is" + parameter);

                          fxmlLoader.setLocation(getClass().getResource("/com/arc/views/TransactionDetailScreen.fxml"));
                          
                          Parent root = (Parent) fxmlLoader.load();
                          
                          TransactionDetailController detailController = fxmlLoader.getController();;
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root,600,400));
                          //Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                       
                          stage.setTitle("Input Event Details");
                          //stage.setScene(scene);
                          stage.show();
                          
                      } catch (IOException e) {
                          Logger logger = Logger.getLogger(getClass().getName());
                          logger.log(Level.SEVERE, "Failed to create new Window.", e);
                      }
                  }
                  else {
                         System.out.println("Event not double clicked");
                  }
                  
     }});

	}
	
	@FXML
	void performClear(ActionEvent event) {
		date_search.setText("");
		tc_code_search.setText("");
		tin_search.setText("");
		performSearch(event);
	}
	

	@FXML
	void performConnect(ActionEvent event) {
		// System.out.println("connect button pushed");
		// this.searchFld.setText("connect!");
		long startTime = System.nanoTime();
		eventView();
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


	
	private static ObservableList<EventView> getEventList(ResultSet results1) {

		ArrayList<EventView> tp1list = new ArrayList<EventView>();

		try {
			for (int j = 1; j <= 100; j++) {
				if (results1.next()) {
					String pin = results1.getString("pin");
					int validityCd = results1.getInt(2);
					Date transDate = results1.getDate(3);
					String transCode = results1.getString(4);
					String transSubTypCode = results1.getString(5);
					String cycleId = results1.getString(6);
					int unpostableCd = results1.getInt(7);
					String sysId = results1.getString(8);
					String partitionSysId = results1.getString(9);
					String compInd = results1.getString("status");
					EventView t1 = new EventView(pin, validityCd, transDate, transCode, transSubTypCode, cycleId, unpostableCd, sysId, partitionSysId, compInd);
					tp1list.add(t1);
					

					// System.out.println("Row number: " + j + " " + t1.getSsn());
				}
			}
		} catch (SQLException except) {
			except.printStackTrace();
		}

		return FXCollections.<EventView>observableArrayList(tp1list);
	}

	
	
	private static ObservableList<InputEventView> getInputEventList(ResultSet results1) {
		long startTime;

		long endTime;
		long duration; 

		ArrayList<InputEventView> inputEventList = new ArrayList<InputEventView>();

		try {

			while (results1.next()) {
					String sysId = results1.getString(1);
					String partitionSysId = results1.getString(2);
					String inputTransText = results1.getString(3);
					InputEventView p1 = new InputEventView(partitionSysId, sysId, inputTransText);

					inputEventList.add(p1);

				}
			
		} catch (

		SQLException except) {
			except.printStackTrace();
		}

		return FXCollections.<InputEventView>observableArrayList(inputEventList);
	}

	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
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
	
	void eventView() {

		eventLayoutForm(null, null, null);

	}
	

	private void eventLayoutForm(String searchTIN, Date searchTransactionDate, Integer searchTransactionCode) {
		
		if(event_main.getChildren() !=null && event_main.getChildren().size() > 0) {
			event_table.getItems().removeAll();
			event_table.getItems().clear();
			event_main.getChildren().remove(event_table);
			event_table = new TableView<EventView>();
		}
		
		event_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				
				inputLayoutForm(newSelection.getSysId());
			}
		});
		
		try {
			boolean priorFilter = false;
			
			StringBuilder sql = new StringBuilder("Select bet.pin, bet.validity_cd, bet.account_transaction_dt,");
			sql.append("bet.account_transaction_cd, bet.account_transaction_trans_subtyp_cd, bet.posted_cyc_id, bet.unpostable_cd, ");
			sql.append("bet.account_transaction_sys_id, bet.partition_sys_id, bet.status ");
			sql.append("from account_transaction bet ");
			
			if (searchTIN !=null) {
				priorFilter = true;
				sql.append(" where bet.pin like '" + searchTIN + "%' ");
			}
			
			if (searchTransactionDate != null) {
				if (!priorFilter) {
					priorFilter = true;
					sql.append(" WHERE ");
				} else {
					sql.append(" AND ");
				}
				sql.append(" bet.account_transaction_dt = '" + dateFormat.format(searchTransactionDate) + "' ");
			
		}
			
			if (searchTransactionCode != null) {
				if(!priorFilter) {
					priorFilter = true;
					sql.append(" WHERE ");
				} else {
					sql.append(" AND ");
				}
				sql.append(" bet.account_transaction_cd = " + searchTransactionCode);
			}
				sql.append(" order by bet.account_transaction_dt asc");
			
			
			System.out.println("Executing sql: " + sql.toString());
			stmt = conn.prepareStatement(sql.toString());
			ResultSet results = stmt.executeQuery();
			
			event_table.getItems().addAll(getEventList(results));
			
			event_table.getColumns().addAll(TableColumns.getEventPin(), TableColumns.getEventTransDateCol(), 
					TableColumns.getEventTransCodeCol(), TableColumns.getEventCycleIdCol(), 
					TableColumns.getEventunpostableCdCol(), TableColumns.getEventSysIdCol(), TableColumns.getEventPartitionSysIdCol(), TableColumns.getEventCompInd());
			
			event_table.setPrefHeight(170);
			event_table.setPrefWidth(1280);

			event_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			event_main.getChildren().add(event_table);
			
			results.close();

		} catch (SQLException except) {
			except.printStackTrace();
		}
		
}
	
public void unprocessedEventLayoutForm(String searchTIN) {
		
		if(event_main.getChildren() !=null && event_main.getChildren().size() > 0) {
			event_table.getItems().removeAll();
			event_table.getItems().clear();
			event_main.getChildren().remove(event_table);
			event_table = new TableView<EventView>();
		}
		
		event_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				
				inputLayoutForm(newSelection.getSysId());
			}
		});
		
		try {
			boolean priorFilter = false;
			
			StringBuilder sql = new StringBuilder("Select bet.pin, bet.validity_cd, bet.account_transaction_dt,");
			sql.append("bet.account_transaction_cd, bet.account_transaction_trans_subtyp_cd, bet.posted_cyc_id, bet.unpostable_cd, ");
			sql.append("bet.account_transaction_sys_id, bet.partition_sys_id, bet.status ");
			sql.append("from account_transaction bet ");
				sql.append(" where bet.pin like '" + searchTIN + "'");
				sql.append(" and bet.status = 'N' ");
				sql.append(" order by bet.account_transaction_dt asc");
			
			
			System.out.println("Executing sql: " + sql.toString());
			stmt = conn.prepareStatement(sql.toString());
			ResultSet results = stmt.executeQuery();
			
			event_table.getItems().addAll(getEventList(results));
			
			event_table.getColumns().addAll(TableColumns.getEventPin(), TableColumns.getEventTransDateCol(), 
					TableColumns.getEventTransCodeCol(), TableColumns.getEventCycleIdCol(), 
					TableColumns.getEventunpostableCdCol(), TableColumns.getEventSysIdCol(), TableColumns.getEventPartitionSysIdCol(), TableColumns.getEventCompInd());
			
			event_table.setPrefHeight(170);
			event_table.setPrefWidth(1280);

			event_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			event_main.getChildren().add(event_table);
			
			results.close();
		} catch (SQLException except) {
			except.printStackTrace();
		}
		
}
	
	
	
	private void inputLayoutForm(String businessEventSysId) {
		long startTime;
		long endTime;
		long duration; 
		
		try {
			

				input_event_table.getItems().removeAll();
				input_event_table.getItems().clear();
				input_event_main.getChildren().remove(input_event_table);
				input_event_table = new TableView<InputEventView>();
			

			StringBuilder sql = new StringBuilder("select rei.account_transaction_sys_id, rei.partition_sys_id, ");
			sql.append("rei.input_transaction_txt from input_transaction rei where account_transaction_sys_id = ?");

			stmt2 = conn.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			stmt2.setString(1, businessEventSysId);

			ResultSet results = stmt2.executeQuery();

			input_event_table.getItems().addAll(getInputEventList(results));

			input_event_table.getColumns().addAll(TableColumns.getInputEventSysIdCol(),
					TableColumns.getInputEventPartitionSysIdCol(),TableColumns.getInputEventInputTransTextCol());


			input_event_table.setPrefHeight(170);
			input_event_table.setPrefWidth(2500);
			input_event_table.getColumns().get(0).setMaxWidth(300);
			input_event_table.getColumns().get(1).setMaxWidth(300);
			input_event_table.getColumns().get(2).setMaxWidth(850);

			input_event_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			input_event_main.getChildren().add(input_event_table);
			
			results.close();
			stmt2.close();

		} catch (SQLException except) {
			except.printStackTrace();
		}
	}
	
	public void eventLayoutForm(String searchAcctTransSysId) {
		
 
			event_table.getItems().removeAll();
			event_table.getItems().clear();
			event_main.getChildren().remove(event_table);
			event_table = new TableView<EventView>();
		
		
		event_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				
				inputLayoutForm(newSelection.getSysId());
			}
		});
		
		try {
			boolean priorFilter = false;
			
			StringBuilder sql = new StringBuilder("Select bet.pin, bet.validity_cd, bet.account_transaction_dt,");
			sql.append("bet.account_transaction_cd, bet.account_transaction_trans_subtyp_cd, bet.posted_cyc_id, bet.unpostable_cd, ");
			sql.append("bet.account_transaction_sys_id, bet.partition_sys_id, bet.status ");
			sql.append("from account_transaction bet ");
			
				sql.append(" where bet.account_transaction_sys_id = '"+ searchAcctTransSysId + "'");
			
			System.out.println("Executing sql: " + sql.toString());
			stmt = conn.prepareStatement(sql.toString());
			ResultSet results = stmt.executeQuery();
			
			event_table.getItems().addAll(getEventList(results));
			
			event_table.getColumns().addAll(TableColumns.getEventPin(), TableColumns.getEventTransDateCol(), 
					TableColumns.getEventTransCodeCol(), TableColumns.getEventCycleIdCol(), 
					TableColumns.getEventunpostableCdCol(), TableColumns.getEventSysIdCol(), TableColumns.getEventPartitionSysIdCol(), TableColumns.getEventCompInd());
			
			event_table.setPrefHeight(170);
			event_table.setPrefWidth(1280);

			event_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			event_main.getChildren().add(event_table);
			
			results.close();
		} catch (SQLException except) {
			except.printStackTrace();
		}
		
}

	
	@FXML
	private void performSearch(ActionEvent event) {
		Date targetTransactionDate = null;
		Integer targetTCCode = null;
		try {
			System.out.println("Searching on date: " + date_search.getText().trim());
			targetTransactionDate = dateFormat.parse(date_search.getText().trim());
		} catch (Exception e) {
			targetTransactionDate = null;
		}
		try {
			targetTCCode = Integer.valueOf(tc_code_search.getText().trim());
		} catch (Exception e) {
			targetTCCode = null;
		}
		eventLayoutForm(tin_search.getText().trim().length() == 0 ? null : tin_search.getText().trim(), targetTransactionDate, targetTCCode);
		
	}
	
	public int getEventIndex() {
		return this.eventIndex;
	}
	
	public void setEventIndex(int eventIndex) {
		this.eventIndex = eventIndex;
	}
	
	public void setTextFieldTin (String tin) {
		this.tin_search.setText(tin);
	}
	
	@FXML
	void performProcessEvents(ActionEvent event) {

		
		try {
			
			Runtime runTime = Runtime.getRuntime();
			Process process = runTime.exec("cmd /c jobrun.bat", null, new File("C:\\Users\\dafshar\\Documents\\IRS\\TransactionProcessor_commandlinerunner"));
			
			System.exit(1);
			InputStream inputStream = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			InputStream errorStream = process.getErrorStream();
			InputStreamReader esr = new InputStreamReader(errorStream);

			int n1;
			char[] c1 = new char[1024];
			StringBuffer standardOutput = new StringBuffer();
			while ((n1 = isr.read(c1)) > 0) {
				standardOutput.append(c1, 0, n1);
			}
			System.out.println("Standard Output: " + standardOutput.toString());

			int n2;
			char[] c2 = new char[1024];
			StringBuffer standardError = new StringBuffer();
			while ((n2 = esr.read(c2)) > 0) {
				standardError.append(c2, 0, n2);
			}
			System.out.println("Standard Error: " + standardError.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	

}
