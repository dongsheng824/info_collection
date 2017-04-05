package com.guods.toutiao.model;

import java.util.List;

public class UgcData {

	private Long read_count;
	private List<String> ugc_images;
	private UgcUser ugc_user;
	private Integer digg_count;
	private String content;
	private Integer comment_count;

	public Long getRead_count() {
		return read_count;
	}

	public void setRead_count(Long read_count) {
		this.read_count = read_count;
	}

	public List<String> getUgc_images() {
		return ugc_images;
	}

	public void setUgc_images(List<String> ugc_images) {
		this.ugc_images = ugc_images;
	}

	public UgcUser getUgc_user() {
		return ugc_user;
	}

	public void setUgc_user(UgcUser ugc_user) {
		this.ugc_user = ugc_user;
	}

	public Integer getDigg_count() {
		return digg_count;
	}

	public void setDigg_count(Integer digg_count) {
		this.digg_count = digg_count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

}
