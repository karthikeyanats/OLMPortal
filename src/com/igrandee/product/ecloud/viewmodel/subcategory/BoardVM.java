package com.igrandee.product.ecloud.viewmodel.subcategory;

import java.io.Serializable;

public class BoardVM implements Serializable {
	/**
	 * @author venkatraman_v
	 */
	private static final long serialVersionUID = 1L;
	int boardid;
	String boardname;
	String status;
	char boardstatus;
	
	public int getBoardid() {
		return boardid;
	}
	public void setBoardid(int boardid) {
		this.boardid = boardid;
	}
	public String getBoardname() {
		return boardname;
	}
	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}
	public char getBoardstatus() {
		return boardstatus;
	}
	public void setBoardstatus(char boardstatus) {
		this.boardstatus = boardstatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
