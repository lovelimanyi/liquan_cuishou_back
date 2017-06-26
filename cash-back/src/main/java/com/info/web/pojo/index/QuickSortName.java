package com.info.web.pojo.index;

/**
 * 快速建单--通过模糊查询结果集
 * 
 * @author gaoyuhai 2016-6-13 上午08:55:46
 */
public class QuickSortName {

	private int id;
	private int borrowId;
	private String sortName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	@Override
	public String toString() {
		return "QuickSortName [borrowId=" + borrowId + ", id=" + id
				+ ", sortName=" + sortName + "]";
	}

}
