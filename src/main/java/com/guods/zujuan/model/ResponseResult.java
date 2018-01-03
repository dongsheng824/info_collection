package com.guods.zujuan.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseResult {

	@JsonIgnore
	private List<Object> data;
	private String pager;
	private int total;
	private List<Long> ids;

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getPager() {
		return pager;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public List<Long> getIds() {
		return ids;
	}

}
