package com.arc.models;

import java.io.Serializable;
import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;


public class TaxpayerMainView implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final StringProperty ssn;
	private final StringProperty lastName;
	private final StringProperty firstName;
	private final StringProperty address;
	private final ObjectProperty<Date> DOB;
	private final DoubleProperty balanceAmt;
	

	
	public TaxpayerMainView(String ssn, String lastName, String firstName, String address, Date DOB, 
			Double balanceAmt) {
		this.ssn = new SimpleStringProperty(ssn);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.address = new SimpleStringProperty(address);
        this.DOB = new SimpleObjectProperty<Date>(DOB);
        this.balanceAmt = new SimpleDoubleProperty(balanceAmt);
	}
	
	public String getSSN() {
		return ssn.get();
	}

	public void setSSN(String social) {
		ssn.set(social);
	}
	
	public StringProperty ssnProperty() {
        return ssn;
    }
	
	public String getlastName() {
		return lastName.get();
	}

	public void setLastName(String lName) {
		lastName.set(lName);
	}
	
	public StringProperty lastNameProperty() {
        return lastName;
    }
	
	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String fName) {
		firstName.set(fName);
	}
	
	public StringProperty firstNameProperty() {
        return firstName;
    }
	
	public String getAddress() {
		return address.get();
	}

	public void setAddress(String addr) {
		address.set(addr);
	}
	
	public StringProperty addressProperty() {
        return address;
    }
	
	public Date getDOB() {
		return DOB.get();
	}

	public void setDOB(Date birthDate) {
		DOB.set(birthDate);
	}
	
	public ObjectProperty<Date> DOBProperty() {
        return DOB;
    }
	
	public Double getBalanceAmt() {
		return balanceAmt.get();
	}

	public void seBalanceAmt(Double balance) {
		balanceAmt.set(balance);
	}
	
	public DoubleProperty balanceAmtProperty() {
        return balanceAmt;
    }
	
	

	
	@Override
	   public String toString() {
	        return ("TaxpayerMainView: "+this.ssn 
	                    );
	   }


}