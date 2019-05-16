package com.arc.util;

import com.arc.models.TaxpayerTransMainView;

import java.util.Date;

import com.arc.models.EventView;
import com.arc.models.InputEventView;
import com.arc.models.PumsMainView;
import com.arc.models.TaxModuleMainView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableColumns {

	public TableColumns() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static TableColumn<TaxpayerTransMainView, Integer> getTPTransCdCol() {
		TableColumn<TaxpayerTransMainView, Integer> transCdCol = new TableColumn("TRANS_CD");
		transCdCol.setCellValueFactory(new PropertyValueFactory("TRANS_CD"));
		return transCdCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, Integer> getTPTransSubCdCol() {
		TableColumn<TaxpayerTransMainView, Integer> transSubCdCol = new TableColumn("trans_sub_cd");
		transSubCdCol.setCellValueFactory(new PropertyValueFactory("transSubCd"));
		return transSubCdCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, String> getTPLocatorNoCol() {
		TableColumn<TaxpayerTransMainView, String> locatorNoCol = new TableColumn("locator_no");
		locatorNoCol.setCellValueFactory(new PropertyValueFactory("locatorNo"));
		return locatorNoCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, Date> getTPEndDtCol() {
		TableColumn<TaxpayerTransMainView, Date> endDtCol = new TableColumn("end_dt");
		endDtCol.setCellValueFactory(new PropertyValueFactory("endDt"));
		return endDtCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, String> getTPInfoTextCol() {
		TableColumn<TaxpayerTransMainView, String> infoTextCol = new TableColumn("info_text");
		infoTextCol.setCellValueFactory(new PropertyValueFactory("infoText"));
		return infoTextCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, String> getTPPostedCycIdCol() {
		TableColumn<TaxpayerTransMainView, String> postedCycIdCol = new TableColumn("posted_cyc_id");
		postedCycIdCol.setCellValueFactory(new PropertyValueFactory("postedCycId"));
		return postedCycIdCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, Date> getTPEffEndDtCol() {
		TableColumn<TaxpayerTransMainView, Date> effEndDtCol = new TableColumn("eff_end_dt");
		effEndDtCol.setCellValueFactory(new PropertyValueFactory("effEndDt"));
		return effEndDtCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, Integer> getTPExpirationCdCol() {
		TableColumn<TaxpayerTransMainView, Integer> expirationCdCol = new TableColumn("expiration_cd");
		expirationCdCol.setCellValueFactory(new PropertyValueFactory("expirationCd"));
		return expirationCdCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, Integer> getTPMftCdCol() {
		TableColumn<TaxpayerTransMainView, Integer> mftCdCol = new TableColumn("mft_cd");
		mftCdCol.setCellValueFactory(new PropertyValueFactory("mftCd"));
		return mftCdCol;
	}
	
	public static TableColumn<TaxpayerTransMainView, Integer> getTPSSNCol() {
		TableColumn<TaxpayerTransMainView, Integer> SSNCol = new TableColumn("SSN");
		SSNCol.setCellValueFactory(new PropertyValueFactory("SSN"));
		return SSNCol;
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
		
		public static TableColumn<EventView, String> getEventPin() {
			TableColumn<EventView, String> eventPin = new TableColumn("PIN");
			eventPin.setCellValueFactory(param -> param.getValue().pinProperty());
			return eventPin;
		}
		
		public static TableColumn<EventView, String> getEventCompInd() {
			TableColumn<EventView, String> compIndCol = new TableColumn("Completion_Ind");
			compIndCol.setCellValueFactory(param -> param.getValue().compIndProperty());
			return compIndCol;
		}
		
		public static TableColumn<EventView, Date> getEventTransDateCol() {
			TableColumn<EventView, Date> eventTransDateCol = new TableColumn("TRANS_DT");
			eventTransDateCol.setCellValueFactory(new PropertyValueFactory("transDate"));
			return eventTransDateCol;
		}
		
		public static TableColumn<EventView, String> getEventTransCodeCol() {
			TableColumn<EventView, String> eventTransCodeCol = new TableColumn("TRANS_CD");
			eventTransCodeCol.setCellValueFactory(new PropertyValueFactory("transCode"));
			return eventTransCodeCol;
		}
		
		public static TableColumn<EventView, String> getEventTransSubCodeCol() {
			TableColumn<EventView, String> eventTransSubCodeCol = new TableColumn("TRANS_SUBTYP_CD");
			eventTransSubCodeCol.setCellValueFactory(new PropertyValueFactory("transSubTypCd"));
			return eventTransSubCodeCol;
		}
		
		public static TableColumn<EventView, String> getEventSysIdCol() {
			TableColumn<EventView, String> eventSysIdCol = new TableColumn("TRANS_SYS_ID");
			eventSysIdCol.setCellValueFactory(new PropertyValueFactory("sysId"));
			return eventSysIdCol;
		}
		
		public static TableColumn<EventView, String> getEventPartitionSysIdCol() {
			TableColumn<EventView, String> eventPartitionSysIdCol = new TableColumn("PARTITION_SYS_ID");
			eventPartitionSysIdCol.setCellValueFactory(new PropertyValueFactory("partitionSysId"));
			return eventPartitionSysIdCol;
		}
		
		public static TableColumn<EventView, String> getEventCycleIdCol() {
			TableColumn<EventView, String> eventCycleIdCol = new TableColumn("POSTED_CYC_ID");
			eventCycleIdCol.setCellValueFactory(new PropertyValueFactory("cycleId"));
			return eventCycleIdCol;
		}

		public static TableColumn<EventView, Integer> getEventunpostableCdCol() {
			TableColumn<EventView, Integer> eventunpostableCdCol = new TableColumn("UNPOSTABLE_CD");
			eventunpostableCdCol.setCellValueFactory(new PropertyValueFactory("unpostableCd"));
			return eventunpostableCdCol;
		}
		
		public static TableColumn<EventView, Integer> getEventValidityCdCol() {
			TableColumn<EventView, Integer> eventValidityCdCol = new TableColumn("VALIDITY_CD");
			eventValidityCdCol.setCellValueFactory(new PropertyValueFactory("validityCd"));
			return eventValidityCdCol;
		}
		
		public static TableColumn<InputEventView, String> getInputEventSysIdCol() {
			TableColumn<InputEventView, String> inputEventSysIdCol = new TableColumn("ACCOUNT_TRANSACTION_SYS_ID");
			inputEventSysIdCol.setCellValueFactory(new PropertyValueFactory("sysId"));
			return inputEventSysIdCol;
		}
		
		public static TableColumn<InputEventView, String> getInputEventPartitionSysIdCol() {
			TableColumn<InputEventView, String> inputEventPartitionSysIdCol = new TableColumn("PARTITION_SYS_ID");
			inputEventPartitionSysIdCol.setCellValueFactory(new PropertyValueFactory("partitionSysId"));
			return inputEventPartitionSysIdCol;
		}
		
		public static TableColumn<InputEventView, String> getInputEventInputTransTextCol() {
			TableColumn<InputEventView, String> inputEventTransTextCol = new TableColumn("INPUT_TRANSACTION_TXT");
			inputEventTransTextCol.setCellValueFactory(new PropertyValueFactory("inputTransText"));
			return inputEventTransTextCol;
		}
		
		public static TableColumn<TaxModuleMainView, Integer> getTransCdCol() {
			TableColumn<TaxModuleMainView, Integer> transCdCol = new TableColumn("trans_cd");
			transCdCol.setCellValueFactory(new PropertyValueFactory("transCd"));
			return transCdCol;
		}
		
		public static TableColumn<TaxModuleMainView, Integer> getTransSubCdCol() {
			TableColumn<TaxModuleMainView, Integer> transSubCdCol = new TableColumn("trans_sub_cd");
			transSubCdCol.setCellValueFactory(new PropertyValueFactory("transSubCd"));
			return transSubCdCol;
		}
		
		public static TableColumn<TaxModuleMainView, String> getLocatorNoCol() {
			TableColumn<TaxModuleMainView, String> locatorNoCol = new TableColumn("locator_no");
			locatorNoCol.setCellValueFactory(new PropertyValueFactory("locatorNo"));
			return locatorNoCol;
		}
		
		public static TableColumn<TaxModuleMainView, Date> getEndDtCol() {
			TableColumn<TaxModuleMainView, Date> endDtCol = new TableColumn("end_dt");
			endDtCol.setCellValueFactory(new PropertyValueFactory("endDt"));
			return endDtCol;
		}
		
		public static TableColumn<TaxModuleMainView, String> getInfoTextCol() {
			TableColumn<TaxModuleMainView, String> infoTextCol = new TableColumn("info_text");
			infoTextCol.setCellValueFactory(new PropertyValueFactory("infoText"));
			return infoTextCol;
		}
		
		public static TableColumn<TaxModuleMainView, String> getPostedCycIdCol() {
			TableColumn<TaxModuleMainView, String> postedCycIdCol = new TableColumn("posted_cyc_id");
			postedCycIdCol.setCellValueFactory(new PropertyValueFactory("postedCycId"));
			return postedCycIdCol;
		}
		
		public static TableColumn<TaxModuleMainView, Date> getEffEndDtCol() {
			TableColumn<TaxModuleMainView, Date> effEndDtCol = new TableColumn("eff_end_dt");
			effEndDtCol.setCellValueFactory(new PropertyValueFactory("effEndDt"));
			return effEndDtCol;
		}
		
		public static TableColumn<TaxModuleMainView, Integer> getExpirationCdCol() {
			TableColumn<TaxModuleMainView, Integer> expirationCdCol = new TableColumn("expiration_cd");
			expirationCdCol.setCellValueFactory(new PropertyValueFactory("expirationCd"));
			return expirationCdCol;
		}

}
