package com.arc.util;

import com.arc.models.TaxpayerMainView;
import com.arc.models.PumsMainView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableColumns {

	public TableColumns() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// make sure to map columns to POJO names
		public static TableColumn<TaxpayerMainView, Double> getCalcAmtCol() {
			TableColumn<TaxpayerMainView, Double> calcAmtCol = new TableColumn("CALC_AMOUNT");
			calcAmtCol.setCellValueFactory(new PropertyValueFactory("calcAmount"));
			calcAmtCol.setStyle("-fx-alignment: CENTER-RIGHT;");
			calcAmtCol.setCellFactory(tc -> new CurrencyCell<>());
			return calcAmtCol;
		}

		public static TableColumn<TaxpayerMainView, Double> getTaxAmtCol() {
			TableColumn<TaxpayerMainView, Double> taxAmtCol = new TableColumn("TAX_AMOUNT");
			taxAmtCol.setCellValueFactory(new PropertyValueFactory("taxAmount"));
			taxAmtCol.setStyle("-fx-alignment: CENTER-RIGHT;");
			taxAmtCol.setCellFactory(tc -> new CurrencyCell<>());
			return taxAmtCol;
		}

		public static TableColumn<TaxpayerMainView, Double> getTaxBaseCol() {
			TableColumn<TaxpayerMainView, Double> taxBaseCol = new TableColumn("TAX_BASE");
			taxBaseCol.setCellValueFactory(new PropertyValueFactory("taxBase"));
			taxBaseCol.setStyle("-fx-alignment: CENTER-RIGHT;");
			taxBaseCol.setCellFactory(tc -> new CurrencyCell<>());
			return taxBaseCol;
		}

		public static TableColumn<TaxpayerMainView, String> getLnameCol() {
			TableColumn<TaxpayerMainView, String> lnameCol = new TableColumn("LAST_NAME");
			lnameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
			return lnameCol;
		}

		public static TableColumn<TaxpayerMainView, String> getFnameCol() {
			TableColumn<TaxpayerMainView, String> fnameCol = new TableColumn("FIRST_NAME");
			fnameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
			return fnameCol;
		}

		public static TableColumn<TaxpayerMainView, String> getSSNCol() {
			TableColumn<TaxpayerMainView, String> ssnCol = new TableColumn("SSN");
			ssnCol.setCellValueFactory(new PropertyValueFactory("ssn"));

			return ssnCol;
		}

		public static TableColumn<TaxpayerMainView, Integer> getIdCol() {
			TableColumn<TaxpayerMainView, Integer> idCol = new TableColumn("CUSTOMER_ID");
			idCol.setCellValueFactory(new PropertyValueFactory("customerId"));
			return idCol;
		}
		
		public static TableColumn<PumsMainView, Integer> getPumsIdCol() {
			TableColumn<PumsMainView, Integer> idCol = new TableColumn("CUSTOMER_ID");
			idCol.setCellValueFactory(new PropertyValueFactory("custid"));
			return idCol;
		}
		
		public static TableColumn<PumsMainView, Integer> getAGEPCol() {
			TableColumn<PumsMainView, Integer> agepCol = new TableColumn("AGEP");
			agepCol.setCellValueFactory(new PropertyValueFactory("agep"));
			return agepCol;
		}
		
		public static TableColumn<PumsMainView, String> getSerialNoCol() {
			TableColumn<PumsMainView, String> serialnoCol = new TableColumn("SERIALNO");
			serialnoCol.setCellValueFactory(new PropertyValueFactory("serialno"));
			return serialnoCol;
		}
		
		public static TableColumn<PumsMainView, String> getWAGPCol() {
			TableColumn<PumsMainView, String> wagpCol = new TableColumn("WAGP");
			wagpCol.setCellValueFactory(new PropertyValueFactory("wagp"));
			return wagpCol;
		}
		



}
