
package com.guods.util;

import com.guods.model.Entity58;

public class Translate {

	public static String[] toArray(Entity58 entity){
		String[] rowData = new String[18];
		rowData[0] = entity.getArticleTitle();
		rowData[1] = entity.getArticleType();
		rowData[2] = entity.getArticleUrl();
		rowData[3] = entity.getArticleContent();
		rowData[4] = entity.getArticleAddr();
		rowData[5] = entity.getArticleContact();
		rowData[6] = entity.getArticleDesc();
		rowData[7] = entity.getArticleCreateTime();
		rowData[8] = entity.getArticleReadCount();
		rowData[9] = entity.getArticleSeviceRegion();
		rowData[10] = entity.getCompanyName();
		rowData[11] = entity.getCompanyAddress();
		rowData[12] = entity.getCompanyContactName();
		rowData[13] = entity.getCompanyPhone();
		rowData[14] = entity.getCompanyIntro();
		rowData[15] = entity.getCompanyUrl();
		rowData[16] = entity.getEmail();
		rowData[17] = entity.getUserName();
		return rowData;
	}
}
