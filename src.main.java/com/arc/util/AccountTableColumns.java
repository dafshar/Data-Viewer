package com.arc.util;

import java.util.Date;

import com.arc.models.TaxpayerMainView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccountTableColumns {
	
	public AccountTableColumns() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static TableColumn<TaxpayerMainView, Integer> getTPTransCdCol() {
		TableColumn<TaxpayerMainView, Integer> transCdCol = new TableColumn("Trans_cd");
		transCdCol.setCellValueFactory(new PropertyValueFactory("test"));
		return transCdCol;
	}
	
	public static TableColumn<TaxpayerMainView, Integer> getTPTransSubCdCol() {
		TableColumn<TaxpayerMainView, Integer> transSubCdCol = new TableColumn("trans_sub_cd");
		transSubCdCol.setCellValueFactory(new PropertyValueFactory("trans_sub_cd"));
		return transSubCdCol;
	}
	
	public static TableColumn<TaxpayerMainView, String> getTPLocatorNoCol() {
		TableColumn<TaxpayerMainView, String> locatorNoCol = new TableColumn("locator_no");
		locatorNoCol.setCellValueFactory(new PropertyValueFactory("locator_no"));
		return locatorNoCol;
	}
	
	public static TableColumn<TaxpayerMainView, Date> getTPEndDtCol() {
		TableColumn<TaxpayerMainView, Date> endDtCol = new TableColumn("end_dt");
		endDtCol.setCellValueFactory(new PropertyValueFactory("end_dt"));
		return endDtCol;
	}
	
	public static TableColumn<TaxpayerMainView, String> getTPInfoTextCol() {
		TableColumn<TaxpayerMainView, String> infoTextCol = new TableColumn("info_text");
		infoTextCol.setCellValueFactory(new PropertyValueFactory("info_text"));
		return infoTextCol;
	}
	
	public static TableColumn<TaxpayerMainView, String> getTPPostedCycIdCol() {
		TableColumn<TaxpayerMainView, String> postedCycIdCol = new TableColumn("posted_cyc_id");
		postedCycIdCol.setCellValueFactory(new PropertyValueFactory("posted_cyc_id"));
		return postedCycIdCol;
	}
	
	public static TableColumn<TaxpayerMainView, Date> getTPEffEndDtCol() {
		TableColumn<TaxpayerMainView, Date> effEndDtCol = new TableColumn("eff_end_dt");
		effEndDtCol.setCellValueFactory(new PropertyValueFactory("eff_end_dt"));
		return effEndDtCol;
	}
	
	public static TableColumn<TaxpayerMainView, Integer> getTPExpirationCdCol() {
		TableColumn<TaxpayerMainView, Integer> expirationCdCol = new TableColumn("expiration_cd");
		expirationCdCol.setCellValueFactory(new PropertyValueFactory("expiration_cd"));
		return expirationCdCol;
	}
	
	public static TableColumn<TaxpayerMainView, Integer> getTPMftCdCol() {
		TableColumn<TaxpayerMainView, Integer> mftCdCol = new TableColumn("mft_cd");
		mftCdCol.setCellValueFactory(new PropertyValueFactory("mft_cd"));
		return mftCdCol;
	}
	
	public static TableColumn<TaxpayerMainView, Integer> getTPSSNCol() {
		TableColumn<TaxpayerMainView, Integer> SSNCol = new TableColumn("ssn");
		SSNCol.setCellValueFactory(new PropertyValueFactory("ssn"));
		return SSNCol;
	}

}
