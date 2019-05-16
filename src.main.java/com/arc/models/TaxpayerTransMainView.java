package com.arc.models;

import java.io.Serializable;
import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;


public class TaxpayerTransMainView implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final IntegerProperty trans_cd;
	private final IntegerProperty trans_sub_cd;
	private final StringProperty  locator_no;
	private final ObjectProperty<Date>  end_dt;
	private final StringProperty  info_text;
	private final StringProperty  posted_cyc_id;
	private final ObjectProperty<Date>  eff_end_dt;
	private final StringProperty  ssn;
	private final StringProperty account_transaction_sys_id;
	
	public TaxpayerTransMainView(int trans_cd, int trans_sub_cd, String locator_no, Date end_dt, String info_text, 
			String posted_cyc_id, Date eff_end_dt, String ssn, String account_transaction_sys_id) {
		this.locator_no = new SimpleStringProperty(locator_no);
        this.ssn = new SimpleStringProperty(ssn);
        
        this.trans_sub_cd = new SimpleIntegerProperty(trans_sub_cd);
        this.end_dt = new SimpleObjectProperty<Date>(end_dt);
        this.posted_cyc_id = new SimpleStringProperty(posted_cyc_id);
        this.trans_cd = new SimpleIntegerProperty(trans_cd);
        this.info_text = new SimpleStringProperty(info_text);
        this.eff_end_dt = new SimpleObjectProperty<Date>(eff_end_dt);
        this.account_transaction_sys_id = new SimpleStringProperty(account_transaction_sys_id);
	}
	
	
	public int gettrans_cd() {
		return trans_cd.get();
	}

	public void settrans_cd(int transCd) {
		trans_cd.set(transCd);
	}
	
	public IntegerProperty trans_cdProperty() {
        return trans_cd;
    }
	
	public int gettrans_sub_cd() {
		return trans_sub_cd.get();
	}

	public void settrans_sub_cd(int transSubCd) {
		trans_sub_cd.set(transSubCd);
	}
	
	public IntegerProperty trans_sub_cdProperty() {
        return trans_sub_cd;
    }
	
	public String getlocator_no() {
		return locator_no.get();
	}

	public void setlocator_no(String locatorNo) {
		locator_no.set(locatorNo);
	}
	
	 public StringProperty locator_noProperty() {
	        return locator_no;
	    }
	
	public Date getendDt() {
		return end_dt.get();
	}

	public void setendDt_no(Date endDt) {
		end_dt.set(endDt);
	}
	
	public ObjectProperty<Date> end_dtProperty() {
        return end_dt;
    }
	
	public String getinfoText() {
		return info_text.get();
	}

	public void setinfoText(String infoText) {
		info_text.set(infoText);
	}
	
	public StringProperty info_textProperty() {
        return info_text;
    }
	
	public String getpostedCycId() {
		return posted_cyc_id.get();
	}

	public void setpostedCycId(String postedCycId) {
		posted_cyc_id.set(postedCycId);
	}
	
	public StringProperty posted_cyc_idProperty() {
        return posted_cyc_id;
    }
	
	public Date geteffEndDt() {
		return eff_end_dt.get();
	}

	public void seteffEndDt(Date effEndDt) {
		eff_end_dt.set(effEndDt);
	}
	
	public ObjectProperty<Date> eff_end_dtProperty() {
        return eff_end_dt;
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
	
	public String getAccountTransSysId() {
		return account_transaction_sys_id.get();
	}

	public void setAccountTransSysId(String accountTransSysId) {
		account_transaction_sys_id.set(accountTransSysId);
	}
	
	public StringProperty accountTransSysIdProperty() {
        return account_transaction_sys_id;
    }
	
	
	
	@Override
	   public String toString() {
	        return ("trans_cd:"+this.trans_cd 
	                    );
	   }


}