package com.arc.models;

import java.io.Serializable;



public class InputEventView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String partition_sys_id;
	private String sysId;
	private String inputTransText;


	public InputEventView() {
	}

	public InputEventView(String partitionSysId, String sysId, String inputTransText) {
		super();
		this.partition_sys_id =  partitionSysId;
		this.sysId = sysId;
		this.inputTransText = inputTransText;
		
	}


	
	public String getSysId() {
		return this.sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	

	
	public String getInputTransText() {
		return this.inputTransText;
	}

	public void setInputTransText(String inputTransText) {
		this.inputTransText = inputTransText;
	}
	
	public String getPartitionSysId() {
		return this.partition_sys_id;
	}

	public void setPartitionSysId(String partitionSysId) {
		this.partition_sys_id = partitionSysId;
	}


}