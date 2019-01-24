package com.arc.application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import com.arc.controllers.*;


public class Main extends Application {
	
	private Stage primaryStage;
	private VBox rootLayout;
	

	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CADE 2 Database Explorer");

		initRootLayout(); // this sets the root layout
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/com/arc/views/BaseAnchor.fxml"));
			rootLayout = (VBox) loader.load();

			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();  
			
			// set controller for this FXML
			BaseAnchorController contoller = loader.getController();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
