package com.guods.contact;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.guods.toutiao.model.AsCp;

public class AsCpGenerator {

	public static AsCp generate(String t) {
		if (8 != t.length()) {
			return new AsCp("479BB4B7254C150", "7E0AC8874BB0985");
		}
		Long currentTimeMillis = Long.valueOf(t) / 1000;
		t = currentTimeMillis.toString();
		StringBuffer as = new StringBuffer("A1");
		StringBuffer cp = new StringBuffer(t.substring(0, 3));
		String md5Result = getMd5(t);
		for (int i = 0; i < 5; i++) {
			as.append(md5Result.charAt(i)).append(t.charAt(t.length() - 5 + i));
			cp.append(t.charAt(i + 3)).append(md5Result.charAt(md5Result.length() - 5 + i));
		}
		as.append(t.substring(t.length() - 3));
		cp.append("E1");
		return new AsCp(as.toString(), cp.toString());
	}

	private static String getMd5(String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
