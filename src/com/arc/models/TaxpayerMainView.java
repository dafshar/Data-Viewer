package com.arc.models;

import java.io.Serializable;


/**
 * The persistent class for the TAXPAYER_MAIN_VIEW database table.
 * 
 */

public class TaxpayerMainView implements Serializable {
	private static final long serialVersionUID = 1L;

	private double calcAmount;
	private int customerId;
	private String firstName;
	private String lastName;
	private String ssn;
	private double taxAmount;
	private double taxBase;

	public TaxpayerMainView() {
	}

	public TaxpayerMainView(double calcAmount, int customerId, String firstName, String lastName, String ssn,
			double taxAmount, double taxBase) {
		super();
		this.calcAmount = calcAmount;
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.taxAmount = taxAmount;
		this.taxBase = taxBase;
	}

	public double getCalcAmount() {
		return this.calcAmount;
	}

	public void setCalcAmount(double calcAmount) {
		this.calcAmount = calcAmount;
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public double getTaxAmount() {
		return this.taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getTaxBase() {
		return this.taxBase;
	}

	public void setTaxBase(double taxBase) {
		this.taxBase = taxBase;
	}

}