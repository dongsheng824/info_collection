# info_collection

1、采集58同城中的客户信息
2、采集今日头条中评论量大于设定数量的文章
3、采集赶集网的客户信息

需求：1、采集58同城上商家的名称、联系方式。58同城在浏览器打开列表是看不到联系电话的，需要下载app扫一下二维码才会显示联系号码。
	但是这个功能是在前端实现的，客户的联系方式实际上已经发送到html页面，查看html页面的源码就能看到联系方式，这个应该是58同城的一个bug吧。
	2、采集今日头条评论量较高的页面及URL
	3、采集赶集网的客户信息


使用框架：poi-ooxml，采集结果存储到excel
	jsoup，访问url，解析html页面元素
	jackson，解析json串。原来用的是fastjson，但是使用的时候发现fastjson不能识别json串转义后转成String的字段，最后改用jackson。
	如：{"log_extra": "{\"rit\": 1, \"convert_id\": 0, \"req_id\": \"201704060907361720170010047435D6\"}"} 不能识别

类结构：
	com.guods.contact.MyHttpClient：jsoup访问url，返回document
	com.guods.contact.PageParser：解析document。58同城的商家信息都在返回的html中，用jsoup解析html中的元素即可获得商家信息和联系方式。
		今日头条返回的是json串，用jackson解析json串获得信息。
	com.guods.contact.Excel：把符合条件的信息记录到excel
	com.guods.contact.AsCpGenerator：58同城发送url请求的时候要送as和cp两个参数，在页面js查看生成这两个参数的算法，包装成这个类，用于生成as和cp。
	实际上请求参数中as和cp并没有用到，只要修改请求参数max_behot_time就可以获取到数据了，这个应该是头条服务器端的一个bug吧，并没有真正校验as和cp。
	com.guods.view.MainView：一个简单的swing界面。由于第一次用swing，界面比较寒碜，只要功能出来就行。
	com.guods.toutiao.model：今日头条json中需要解析的各种类，根据json串来写即可
