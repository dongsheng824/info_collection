package com.guods.zujuan;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guods.contact.JsoupHttpClient;
import com.guods.zujuan.model.ResponseResult;

public class JsonGet {

	private static JsoupHttpClient client = new JsoupHttpClient();
	
	public static List<Long> getQuestionList(String url, String cookies) throws JsonParseException, JsonMappingException, IOException {
		Document document = client.cookieGet(url, cookies);
		String jsonStr = document.body().text();
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseResult readValue = objectMapper.readValue(jsonStr, ResponseResult.class);
		return readValue.getIds();
	}
}
