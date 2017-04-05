package com.guods.toutiao.model;

public class VideoDetailInfo {

	 private Long group_flags;
     private String video_id;
     private Integer direct_play;
     private Image detail_video_large_image;
     private Integer show_pgc_subscribe;
     private Long video_watch_count;
	public Long getGroup_flags() {
		return group_flags;
	}
	public void setGroup_flags(Long group_flags) {
		this.group_flags = group_flags;
	}
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public Integer getDirect_play() {
		return direct_play;
	}
	public void setDirect_play(Integer direct_play) {
		this.direct_play = direct_play;
	}
	public Image getDetail_video_large_image() {
		return detail_video_large_image;
	}
	public void setDetail_video_large_image(Image detail_video_large_image) {
		this.detail_video_large_image = detail_video_large_image;
	}
	public Integer getShow_pgc_subscribe() {
		return show_pgc_subscribe;
	}
	public void setShow_pgc_subscribe(Integer show_pgc_subscribe) {
		this.show_pgc_subscribe = show_pgc_subscribe;
	}
	public Long getVideo_watch_count() {
		return video_watch_count;
	}
	public void setVideo_watch_count(Long video_watch_count) {
		this.video_watch_count = video_watch_count;
	}
     
}
