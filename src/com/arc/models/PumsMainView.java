package com.arc.models;

import java.io.Serializable;

/**
 * The persistent class for the PUMS_MAIN_VIEW database table.
 * 
 */

public class PumsMainView implements Serializable {

	public PumsMainView(int agep, int custid, String serialno, String wagp) {
		super();
		this.agep = agep;
		this.custid = custid;
		this.serialno = serialno;
		this.wagp = wagp;
	}

	private static final long serialVersionUID = 1L;

	private int agep;

	private int custid;

	private String serialno;

	private String wagp;

	public PumsMainView() {
	}

	public int getAgep() {
		return this.agep;
	}

	public void setAgep(int agep) {
		this.agep = agep;
	}

	public int getCustid() {
		return this.custid;
	}

	public void setCustid(int custid) {
		this.custid = custid;
	}

	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getWagp() {
		return this.wagp;
	}

	public void setWagp(String wagp) {
		this.wagp = wagp;
	}

}