package com.guods.toutiao.model;

import java.util.List;

public class ResponseObj {

	private Boolean has_more;
	private String message;
	private List<DataUnit> data;
	private Next next;
	private Integer return_count;
	private String page_id;
	private Integer count;
	private Object _ck;
	private Object html;
	private String action_label;
	private Integer no_outsite_res;
	private Integer cur_tab;
	private Object tab;
	private Integer offset;
	private String action_label_web;
	private Integer show_tabs;
	private String action_label_pgc;

	public String getAction_label_pgc() {
		return action_label_pgc;
	}

	public void setAction_label_pgc(String action_label_pgc) {
		this.action_label_pgc = action_label_pgc;
	}

	public String getAction_label_web() {
		return action_label_web;
	}

	public void setAction_label_web(String action_label_web) {
		this.action_label_web = action_label_web;
	}

	public Integer getShow_tabs() {
		return show_tabs;
	}

	public void setShow_tabs(Integer show_tabs) {
		this.show_tabs = show_tabs;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Object getTab() {
		return tab;
	}

	public void setTab(Object tab) {
		this.tab = tab;
	}

	public Integer getCur_tab() {
		return cur_tab;
	}

	public void setCur_tab(Integer cur_tab) {
		this.cur_tab = cur_tab;
	}

	public Integer getNo_outsite_res() {
		return no_outsite_res;
	}

	public void setNo_outsite_res(Integer no_outsite_res) {
		this.no_outsite_res = no_outsite_res;
	}

	public String getAction_label() {
		return action_label;
	}

	public void setAction_label(String action_label) {
		this.action_label = action_label;
	}

	public Boolean getHas_more() {
		return has_more;
	}

	public void setHas_more(Boolean has_more) {
		this.has_more = has_more;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DataUnit> getData() {
		return data;
	}

	public void setData(List<DataUnit> data) {
		this.data = data;
	}

	public Next getNext() {
		return next;
	}

	public void setNext(Next next) {
		this.next = next;
	}

	public Integer getReturn_count() {
		return return_count;
	}

	public void setReturn_count(Integer return_count) {
		this.return_count = return_count;
	}

	public String getPage_id() {
		return page_id;
	}

	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}

	public Object get_ck() {
		return _ck;
	}

	public void set_ck(Object _ck) {
		this._ck = _ck;
	}

	public Object getHtml() {
		return html;
	}

	public void setHtml(Object html) {
		this.html = html;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
