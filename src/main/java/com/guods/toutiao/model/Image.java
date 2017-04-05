package com.guods.toutiao.model;

import java.util.List;

public class Image {
	private String url;
	private Integer width;
	private List<Object> url_list;
	private String uri;
	private Integer height;
	private String pc_url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public List<Object> getUrl_list() {
		return url_list;
	}

	public void setUrl_list(List<Object> url_list) {
		this.url_list = url_list;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getPc_url() {
		return pc_url;
	}

	public void setPc_url(String pc_url) {
		this.pc_url = pc_url;
	}

}
