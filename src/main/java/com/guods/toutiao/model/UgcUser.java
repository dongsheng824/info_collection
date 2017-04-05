package com.guods.toutiao.model;

public class UgcUser {
	private String open_url;
	private Long user_id;
	private String name;
	private String avatar_url;
	private Boolean is_following;
	private Boolean is_self;
	private Integer user_verified;
	private UserAuthInfo user_auth_info;

	public String getOpen_url() {
		return open_url;
	}

	public void setOpen_url(String open_url) {
		this.open_url = open_url;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public Boolean getIs_following() {
		return is_following;
	}

	public void setIs_following(Boolean is_following) {
		this.is_following = is_following;
	}

	public Boolean getIs_self() {
		return is_self;
	}

	public void setIs_self(Boolean is_self) {
		this.is_self = is_self;
	}

	public Integer getUser_verified() {
		return user_verified;
	}

	public void setUser_verified(Integer user_verified) {
		this.user_verified = user_verified;
	}

	public UserAuthInfo getUser_auth_info() {
		return user_auth_info;
	}

	public void setUser_auth_info(UserAuthInfo user_auth_info) {
		this.user_auth_info = user_auth_info;
	}

}
