package com.guods.toutiao.model;

public class MediaInfo {

	private String avatar_url;
	private Long media_id;
	private String name;
	private Boolean user_verified;
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public Long getMedia_id() {
		return media_id;
	}
	public void setMedia_id(Long media_id) {
		this.media_id = media_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getUser_verified() {
		return user_verified;
	}
	public void setUser_verified(Boolean user_verified) {
		this.user_verified = user_verified;
	}
	
}
