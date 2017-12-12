package com.guods.contact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.HttpStatusException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ApachHttpClient {
	
	private CloseableHttpClient httpClient = HttpClients.createDefault();

	public void download(String url, String cookies, String file){
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(file)); 
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
			httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpGet.addHeader("Cookie", cookies);
			httpGet.addHeader("Cache-Control", "max-age=0");
			httpGet.addHeader("Upgrade-Insecure-Requests", "1");
			CloseableHttpResponse response = httpClient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			byte buff[] = new byte[4096];  
            int len = 0; 
            while ((len = inputStream.read(buff)) != -1) {  
                outputStream.write(buff, 0, len);  
            }  
            outputStream.flush();  
            outputStream.close(); 
		}catch (HttpStatusException e0){
			System.out.println(e0.getMessage());
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
		}
	}
}
