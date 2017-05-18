package com.guods.toutiao.model;

import java.util.List;

public class DataUnit {

	private String log_extra;
	private String image_url;
	private Integer read_count;
	private Integer ban_comment;
	private Boolean single_mode;
	private String abstract0;
	private List<Image> image_list;
	private Boolean has_video;
	private Integer article_type;
	private String tag;
	private String display_info;
	private Integer has_m3u8_video;
	private String label;
	private Integer user_verified;
	private Integer aggr_type;
	private Long expire_seconds;
	private Integer cell_type;
	private Integer article_sub_type;
	private String tag_url;
	private Integer bury_count;
	private String title;
	private Integer ignore_web_transform;
	private Integer source_icon_style;
	private Integer tip;
	private Integer hot;
	private String share_url;
	private Integer has_mp4_video;
	private String source;
	private Integer comment_count;
	private String article_url;
	private List<FilterWords> filter_words;
	private Boolean has_gallery;
	private Long publish_time;
	private Long ad_id;
	private List<Action> action_list;
	private Boolean has_image;
	private Integer cell_layout_style;
	private Long tag_id;
	private String source_url;
	private Integer video_style;
	private String verified_content;
	private Boolean is_feed_ad;
	private List<Image> large_image_list;
	private Long item_id;
	private Integer natant_level;
	private String article_genre;
	private Integer level;
	private Integer cell_flag;
	private String source_open_url;
	private String display_url;
	private Integer digg_count;
	private Long behot_time;
	private String article_alt_url;
	private Long cursor;
	private String url;
	private Integer preload_web;
	private String ad_label;
	private Integer user_repin;
	private Integer label_style;
	private Integer item_version;
	private String group_id;
	private Object middle_image;
////////////////////////////////////////////////////////////////////////////////////////
	private String chinese_tag;
	private String media_avatar_url;
    private Boolean middle_mode;
    private Boolean more_mode;
    private Integer group_source;
    private Integer comments_count;
    private String media_url;
	
    private Integer repin_count;
    private Integer gallary_image_count;
    private String video_duration_str;
    private String video_id;
    private Long video_play_count;
    private Integer like_count;
    private String ugc_data;
    
    private String content;
    private String datetime;
    private Long display_dt;
    private Long group_flags;
    private Boolean is_stick;
    private String media_name;
    private String keywords;
    private Boolean large_mode;
    private String large_image_url;
    private String action_extra;
    private MediaInfo media_info;
    private String source_avatar;
    private AdData ad_data;
    private String type;
    private Boolean honey;
    private String city;
    private String source_desc;
    private String info_desc;
    private String source_desc_open_url;
    private VideoDetailInfo video_detail_info;
    private Integer video_duration;
    private List<String> ad_track_url_list;
    private List<String> track_url_list;
    private Long media_creator_id;
    private Integer show_play_effective_count;
    private String display_title;
    private Long create_time;
    private Integer favorite_count;
    private Integer gallery_pic_count;
    private Long id;
    private String item_source_url;
    private Long display_time;
    private Long go_detail_count;
    private String item_seo_url;
    private String seo_url;
    private Object image_detail;
    private Object highlight;
    private Object summary;
    private List<Query> queries;
	private Object play_effective_count;
	private String qid;
	private String ala_src;
	private List<String> tokens;
	private Object display;
	private String track_url;
	private String ad_track_url;
    
	public String getAd_track_url() {
		return ad_track_url;
	}

	public void setAd_track_url(String ad_track_url) {
		this.ad_track_url = ad_track_url;
	}

	public String getTrack_url() {
		return track_url;
	}

	public void setTrack_url(String track_url) {
		this.track_url = track_url;
	}

	public Object getDisplay() {
		return display;
	}

	public void setDisplay(Object display) {
		this.display = display;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public String getAla_src() {
		return ala_src;
	}

	public void setAla_src(String ala_src) {
		this.ala_src = ala_src;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public List<Query> getQueries() {
		return queries;
	}

	public void setQueries(List<Query> queries) {
		this.queries = queries;
	}

	public Object getPlay_effective_count() {
		return play_effective_count;
	}

	public void setPlay_effective_count(Object play_effective_count) {
		this.play_effective_count = play_effective_count;
	}

	public Object getSummary() {
		return summary;
	}

	public void setSummary(Object summary) {
		this.summary = summary;
	}

	public Object getHighlight() {
		return highlight;
	}

	public void setHighlight(Object highlight) {
		this.highlight = highlight;
	}

	public Object getImage_detail() {
		return image_detail;
	}

	public void setImage_detail(Object image_detail) {
		this.image_detail = image_detail;
	}

	public String getSeo_url() {
		return seo_url;
	}

	public void setSeo_url(String seo_url) {
		this.seo_url = seo_url;
	}

	public String getItem_seo_url() {
		return item_seo_url;
	}

	public void setItem_seo_url(String item_seo_url) {
		this.item_seo_url = item_seo_url;
	}

	public Long getGo_detail_count() {
		return go_detail_count;
	}

	public void setGo_detail_count(Long go_detail_count) {
		this.go_detail_count = go_detail_count;
	}

	public Long getDisplay_time() {
		return display_time;
	}

	public void setDisplay_time(Long display_time) {
		this.display_time = display_time;
	}

	public String getItem_source_url() {
		return item_source_url;
	}

	public void setItem_source_url(String item_source_url) {
		this.item_source_url = item_source_url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGallery_pic_count() {
		return gallery_pic_count;
	}

	public void setGallery_pic_count(Integer gallery_pic_count) {
		this.gallery_pic_count = gallery_pic_count;
	}

	public Integer getFavorite_count() {
		return favorite_count;
	}

	public void setFavorite_count(Integer favorite_count) {
		this.favorite_count = favorite_count;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	public String getDisplay_title() {
		return display_title;
	}

	public void setDisplay_title(String display_title) {
		this.display_title = display_title;
	}

	public Integer getShow_play_effective_count() {
		return show_play_effective_count;
	}

	public void setShow_play_effective_count(Integer show_play_effective_count) {
		this.show_play_effective_count = show_play_effective_count;
	}

	public Long getMedia_creator_id() {
		return media_creator_id;
	}

	public void setMedia_creator_id(Long media_creator_id) {
		this.media_creator_id = media_creator_id;
	}

	public List<String> getTrack_url_list() {
		return track_url_list;
	}

	public void setTrack_url_list(List<String> track_url_list) {
		this.track_url_list = track_url_list;
	}

	public List<String> getAd_track_url_list() {
		return ad_track_url_list;
	}

	public void setAd_track_url_list(List<String> ad_track_url_list) {
		this.ad_track_url_list = ad_track_url_list;
	}

	public Integer getVideo_duration() {
		return video_duration;
	}

	public void setVideo_duration(Integer video_duration) {
		this.video_duration = video_duration;
	}

	public VideoDetailInfo getVideo_detail_info() {
		return video_detail_info;
	}

	public void setVideo_detail_info(VideoDetailInfo video_detail_info) {
		this.video_detail_info = video_detail_info;
	}

	public String getSource_desc_open_url() {
		return source_desc_open_url;
	}

	public void setSource_desc_open_url(String source_desc_open_url) {
		this.source_desc_open_url = source_desc_open_url;
	}

	public String getInfo_desc() {
		return info_desc;
	}

	public void setInfo_desc(String info_desc) {
		this.info_desc = info_desc;
	}

	public String getSource_desc() {
		return source_desc;
	}

	public void setSource_desc(String source_desc) {
		this.source_desc = source_desc;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getHoney() {
		return honey;
	}

	public void setHoney(Boolean honey) {
		this.honey = honey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AdData getAd_data() {
		return ad_data;
	}

	public void setAd_data(AdData ad_data) {
		this.ad_data = ad_data;
	}

	public String getSource_avatar() {
		return source_avatar;
	}

	public void setSource_avatar(String source_avatar) {
		this.source_avatar = source_avatar;
	}

	public MediaInfo getMedia_info() {
		return media_info;
	}

	public void setMedia_info(MediaInfo media_info) {
		this.media_info = media_info;
	}

	public String getAction_extra() {
		return action_extra;
	}

	public void setAction_extra(String action_extra) {
		this.action_extra = action_extra;
	}

	public String getLarge_image_url() {
		return large_image_url;
	}

	public void setLarge_image_url(String large_image_url) {
		this.large_image_url = large_image_url;
	}

	public Boolean getLarge_mode() {
		return large_mode;
	}

	public void setLarge_mode(Boolean large_mode) {
		this.large_mode = large_mode;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLog_extra() {
		return log_extra;
	}

	public void setLog_extra(String log_extra) {
		this.log_extra = log_extra;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Integer getRead_count() {
		return read_count;
	}

	public void setRead_count(Integer read_count) {
		this.read_count = read_count;
	}

	public Integer getBan_comment() {
		return ban_comment;
	}

	public void setBan_comment(Integer ban_comment) {
		this.ban_comment = ban_comment;
	}

	public Boolean getSingle_mode() {
		return single_mode;
	}

	public void setSingle_mode(Boolean single_mode) {
		this.single_mode = single_mode;
	}

	public String getAbstract0() {
		return abstract0;
	}

	public void setAbstract0(String abstract0) {
		this.abstract0 = abstract0;
	}

	public List<Image> getImage_list() {
		return image_list;
	}

	public void setImage_list(List<Image> image_list) {
		this.image_list = image_list;
	}

	public Boolean getHas_video() {
		return has_video;
	}

	public void setHas_video(Boolean has_video) {
		this.has_video = has_video;
	}

	public Integer getArticle_type() {
		return article_type;
	}

	public void setArticle_type(Integer article_type) {
		this.article_type = article_type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDisplay_info() {
		return display_info;
	}

	public void setDisplay_info(String display_info) {
		this.display_info = display_info;
	}

	public Integer getHas_m3u8_video() {
		return has_m3u8_video;
	}

	public void setHas_m3u8_video(Integer has_m3u8_video) {
		this.has_m3u8_video = has_m3u8_video;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getUser_verified() {
		return user_verified;
	}

	public void setUser_verified(Integer user_verified) {
		this.user_verified = user_verified;
	}

	public Integer getAggr_type() {
		return aggr_type;
	}

	public void setAggr_type(Integer aggr_type) {
		this.aggr_type = aggr_type;
	}

	public Long getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(Long expire_seconds) {
		this.expire_seconds = expire_seconds;
	}

	public Integer getCell_type() {
		return cell_type;
	}

	public void setCell_type(Integer cell_type) {
		this.cell_type = cell_type;
	}

	public Integer getArticle_sub_type() {
		return article_sub_type;
	}

	public void setArticle_sub_type(Integer article_sub_type) {
		this.article_sub_type = article_sub_type;
	}

	public String getTag_url() {
		return tag_url;
	}

	public void setTag_url(String tag_url) {
		this.tag_url = tag_url;
	}

	public Integer getBury_count() {
		return bury_count;
	}

	public void setBury_count(Integer bury_count) {
		this.bury_count = bury_count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIgnore_web_transform() {
		return ignore_web_transform;
	}

	public void setIgnore_web_transform(Integer ignore_web_transform) {
		this.ignore_web_transform = ignore_web_transform;
	}

	public Integer getSource_icon_style() {
		return source_icon_style;
	}

	public void setSource_icon_style(Integer source_icon_style) {
		this.source_icon_style = source_icon_style;
	}

	public Integer getTip() {
		return tip;
	}

	public void setTip(Integer tip) {
		this.tip = tip;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public Integer getHas_mp4_video() {
		return has_mp4_video;
	}

	public void setHas_mp4_video(Integer has_mp4_video) {
		this.has_mp4_video = has_mp4_video;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public String getArticle_url() {
		return article_url;
	}

	public void setArticle_url(String article_url) {
		this.article_url = article_url;
	}

	public List<FilterWords> getFilter_words() {
		return filter_words;
	}

	public void setFilter_words(List<FilterWords> filter_words) {
		this.filter_words = filter_words;
	}

	public Boolean getHas_gallery() {
		return has_gallery;
	}

	public void setHas_gallery(Boolean has_gallery) {
		this.has_gallery = has_gallery;
	}

	public Long getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(Long publish_time) {
		this.publish_time = publish_time;
	}

	public Long getAd_id() {
		return ad_id;
	}

	public void setAd_id(Long ad_id) {
		this.ad_id = ad_id;
	}

	public List<Action> getAction_list() {
		return action_list;
	}

	public void setAction_list(List<Action> action_list) {
		this.action_list = action_list;
	}

	public Boolean getHas_image() {
		return has_image;
	}

	public void setHas_image(Boolean has_image) {
		this.has_image = has_image;
	}

	public Integer getCell_layout_style() {
		return cell_layout_style;
	}

	public void setCell_layout_style(Integer cell_layout_style) {
		this.cell_layout_style = cell_layout_style;
	}

	public Long getTag_id() {
		return tag_id;
	}

	public void setTag_id(Long tag_id) {
		this.tag_id = tag_id;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public Integer getVideo_style() {
		return video_style;
	}

	public void setVideo_style(Integer video_style) {
		this.video_style = video_style;
	}

	public String getVerified_content() {
		return verified_content;
	}

	public void setVerified_content(String verified_content) {
		this.verified_content = verified_content;
	}

	public Boolean getIs_feed_ad() {
		return is_feed_ad;
	}

	public void setIs_feed_ad(Boolean is_feed_ad) {
		this.is_feed_ad = is_feed_ad;
	}

	public List<Image> getLarge_image_list() {
		return large_image_list;
	}

	public void setLarge_image_list(List<Image> large_image_list) {
		this.large_image_list = large_image_list;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public Integer getNatant_level() {
		return natant_level;
	}

	public void setNatant_level(Integer natant_level) {
		this.natant_level = natant_level;
	}

	public String getArticle_genre() {
		return article_genre;
	}

	public void setArticle_genre(String article_genre) {
		this.article_genre = article_genre;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getCell_flag() {
		return cell_flag;
	}

	public void setCell_flag(Integer cell_flag) {
		this.cell_flag = cell_flag;
	}

	public String getSource_open_url() {
		return source_open_url;
	}

	public void setSource_open_url(String source_open_url) {
		this.source_open_url = source_open_url;
	}

	public String getDisplay_url() {
		return display_url;
	}

	public void setDisplay_url(String display_url) {
		this.display_url = display_url;
	}

	public Integer getDigg_count() {
		return digg_count;
	}

	public void setDigg_count(Integer digg_count) {
		this.digg_count = digg_count;
	}

	public Long getBehot_time() {
		return behot_time;
	}

	public void setBehot_time(Long behot_time) {
		this.behot_time = behot_time;
	}

	public String getArticle_alt_url() {
		return article_alt_url;
	}

	public void setArticle_alt_url(String article_alt_url) {
		this.article_alt_url = article_alt_url;
	}

	public Long getCursor() {
		return cursor;
	}

	public void setCursor(Long cursor) {
		this.cursor = cursor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPreload_web() {
		return preload_web;
	}

	public void setPreload_web(Integer preload_web) {
		this.preload_web = preload_web;
	}

	public String getAd_label() {
		return ad_label;
	}

	public void setAd_label(String ad_label) {
		this.ad_label = ad_label;
	}

	public Integer getUser_repin() {
		return user_repin;
	}

	public void setUser_repin(Integer user_repin) {
		this.user_repin = user_repin;
	}

	public Integer getLabel_style() {
		return label_style;
	}

	public void setLabel_style(Integer label_style) {
		this.label_style = label_style;
	}

	public Integer getItem_version() {
		return item_version;
	}

	public void setItem_version(Integer item_version) {
		this.item_version = item_version;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public Object getMiddle_image() {
		return middle_image;
	}

	public void setMiddle_image(Object middle_image) {
		this.middle_image = middle_image;
	}

	public String getChinese_tag() {
		return chinese_tag;
	}

	public void setChinese_tag(String chinese_tag) {
		this.chinese_tag = chinese_tag;
	}

	public String getMedia_avatar_url() {
		return media_avatar_url;
	}

	public void setMedia_avatar_url(String media_avatar_url) {
		this.media_avatar_url = media_avatar_url;
	}

	public Boolean getMiddle_mode() {
		return middle_mode;
	}

	public void setMiddle_mode(Boolean middle_mode) {
		this.middle_mode = middle_mode;
	}

	public Boolean getMore_mode() {
		return more_mode;
	}

	public void setMore_mode(Boolean more_mode) {
		this.more_mode = more_mode;
	}

	public Integer getGroup_source() {
		return group_source;
	}

	public void setGroup_source(Integer group_source) {
		this.group_source = group_source;
	}

	public Integer getComments_count() {
		return comments_count;
	}

	public void setComments_count(Integer comments_count) {
		this.comments_count = comments_count;
	}

	public String getMedia_url() {
		return media_url;
	}

	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}

	public Integer getRepin_count() {
		return repin_count;
	}

	public void setRepin_count(Integer repin_count) {
		this.repin_count = repin_count;
	}

	public Integer getGallary_image_count() {
		return gallary_image_count;
	}

	public void setGallary_image_count(Integer gallary_image_count) {
		this.gallary_image_count = gallary_image_count;
	}

	public String getVideo_duration_str() {
		return video_duration_str;
	}

	public void setVideo_duration_str(String video_duration_str) {
		this.video_duration_str = video_duration_str;
	}

	public String getVideo_id() {
		return video_id;
	}

	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}

	public Long getVideo_play_count() {
		return video_play_count;
	}

	public void setVideo_play_count(Long video_play_count) {
		this.video_play_count = video_play_count;
	}

	public Integer getLike_count() {
		return like_count;
	}

	public void setLike_count(Integer like_count) {
		this.like_count = like_count;
	}

	public String getUgc_data() {
		return ugc_data;
	}

	public void setUgc_data(String ugc_data) {
		this.ugc_data = ugc_data;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Long getDisplay_dt() {
		return display_dt;
	}

	public void setDisplay_dt(Long display_dt) {
		this.display_dt = display_dt;
	}

	public Long getGroup_flags() {
		return group_flags;
	}

	public void setGroup_flags(Long group_flags) {
		this.group_flags = group_flags;
	}

	public Boolean getIs_stick() {
		return is_stick;
	}

	public void setIs_stick(Boolean is_stick) {
		this.is_stick = is_stick;
	}

	public String getMedia_name() {
		return media_name;
	}

	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}
	
}
