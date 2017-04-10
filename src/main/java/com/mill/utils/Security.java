package com.mill.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Security {
	
	public static String sha256(String plain) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(plain.getBytes());

		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
          sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString();
	}
	
	public static String generateApiKey()
	{
		return UUID.randomUUID().toString();
	}

}
