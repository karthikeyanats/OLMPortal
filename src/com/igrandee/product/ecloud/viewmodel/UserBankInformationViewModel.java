/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 * ecloudbaseutil 
 * com.igrandee.product.ecloud.viewmodel
 * UserBankInformationViewModel.java
 * Created Sep 25, 2014 4:15:36 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel;

import java.io.Serializable;

/**
 * @author balajichander_r
 * 
 */
public class UserBankInformationViewModel implements Serializable {

	/**
	 * AUTO GENERATED DEFAULT SERIAL VERSION ID STUB serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	String bankInfoId, bankInfoName, bankInfoBranchName, bankInfoAccountNumber,
			bankInfoIFSCCode, bankInfoBranchLocation, country;

	/**
	 * @return the bankInfoId
	 */
	public String getBankInfoId() {
		return bankInfoId;
	}

	/**
	 * @param bankInfoId
	 *            the bankInfoId to set
	 */
	public void setBankInfoId(String bankInfoId) {
		this.bankInfoId = bankInfoId;
	}

	/**
	 * @return the bankInfoName
	 */
	public String getBankInfoName() {
		return bankInfoName;
	}

	/**
	 * @param bankInfoName
	 *            the bankInfoName to set
	 */
	public void setBankInfoName(String bankInfoName) {
		this.bankInfoName = bankInfoName;
	}

	/**
	 * @return the bankInfoBranchName
	 */
	public String getBankInfoBranchName() {
		return bankInfoBranchName;
	}

	/**
	 * @param bankInfoBranchName
	 *            the bankInfoBranchName to set
	 */
	public void setBankInfoBranchName(String bankInfoBranchName) {
		this.bankInfoBranchName = bankInfoBranchName;
	}

	/**
	 * @return the bankInfoAccountNumber
	 */
	public String getBankInfoAccountNumber() {
		return bankInfoAccountNumber;
	}

	/**
	 * @param bankInfoAccountNumber
	 *            the bankInfoAccountNumber to set
	 */
	public void setBankInfoAccountNumber(String bankInfoAccountNumber) {
		this.bankInfoAccountNumber = bankInfoAccountNumber;
	}

	/**
	 * @return the bankInfoIFSCCode
	 */
	public String getBankInfoIFSCCode() {
		return bankInfoIFSCCode;
	}

	/**
	 * @param bankInfoIFSCCode
	 *            the bankInfoIFSCCode to set
	 */
	public void setBankInfoIFSCCode(String bankInfoIFSCCode) {
		this.bankInfoIFSCCode = bankInfoIFSCCode;
	}

	/**
	 * @return the bankInfoBranchLocation
	 */
	public String getBankInfoBranchLocation() {
		return bankInfoBranchLocation;
	}

	/**
	 * @param bankInfoBranchLocation
	 *            the bankInfoBranchLocation to set
	 */
	public void setBankInfoBranchLocation(String bankInfoBranchLocation) {
		this.bankInfoBranchLocation = bankInfoBranchLocation;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


}
