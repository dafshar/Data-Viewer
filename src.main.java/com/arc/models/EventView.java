package com.arc.models;

import java.io.Serializable;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class EventView implements Serializable {
	private static final long serialVersionUID = 1L;

	private StringProperty pin;
	private int validityCd;
	private Date transDate;
	private String transCode;
	private String transSubTypCd;
	private String cycleId;
	private int unpostableCd;
	private String sysId;
	private String partitionSysId;
	private StringProperty compInd;

	public EventView() {
	}

	public EventView(String pin, int validityCd, Date transDate, String transCode, String transSubTypCd, String cycleId, 
			int unpostableCd, String sysId, String partitionSysId, String compInd) {
		super();
		this.pin =  new SimpleStringProperty(pin);
		this.transDate = transDate;
		this.validityCd = validityCd;
		this.transCode = transCode;
		this.transSubTypCd = transSubTypCd;
		this.cycleId = cycleId;
		this.unpostableCd = unpostableCd;
		this.sysId = sysId;
		this.partitionSysId = partitionSysId;
		this.compInd = new SimpleStringProperty(compInd);
		
	}

	public String getPIN() {
		return pin.get();
	}

	public void setPIN(String ssn) {
		pin.set(ssn);
	}
	
	public StringProperty pinProperty() {
        return pin;
    }
	
	public Date getTransDate() {
		return this.transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	
	public String getTransCode() {
		return this.transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	
	public String getTransSubTypCd() {
		return this.transSubTypCd;
	}

	public void setTransSubTypCd(String transCode) {
		this.transSubTypCd = transSubTypCd;
	}
	
	public String getSysId() {
		return this.sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	
	public String getPartitionSysId() {
		return this.partitionSysId;
	}

	public void setPartitionSysId(String partitionSysId) {
		this.partitionSysId = partitionSysId;
	}
	
	public String getCycleId() {
		return this.cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	
	public int getUnpostableCd() {
		return this.unpostableCd;
	}

	public void setUnpostableCd(int unpostableCd) {
		this.unpostableCd = unpostableCd;
	}
	
	public int getValidityCd() {
		return this.validityCd;
	}

	public void setValidityCd(int validityCd) {
		this.validityCd = validityCd;
	}
	
	public String getCompInd() {
		return compInd.get();
	}

	public void setCompInd(String tempCompInd) {
		compInd.set(tempCompInd);
	}
	
	public StringProperty compIndProperty() {
        return compInd;
    }
	




}