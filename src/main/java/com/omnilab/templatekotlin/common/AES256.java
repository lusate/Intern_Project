package com.omnilab.templatekotlin.common;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AES256 {

	private static final Logger logger = LoggerFactory.getLogger("AES256");
	private static final String KEY = "iKItNTxTSkxgKTteyHyqcPjXqaYHTEm5";
	private static final GCMParameterSpec IV;

	static {
		/* 단독 사용시
		 * SecureRandom random = new SecureRandom();
		 * byte[] bytesIV = new byte[16];
		 * random.nextBytes(bytesIV);
		 * iv = new GCMParameterSpec(128, bytesIV);
		*/
		/* 타 시스템과 공유시 동일 key/iv 값 필요
		 * iv = new GCMParameterSpec(128, "7779328267167869".getBytes(StandardCharsets.UTF_8));
		*/
		SecureRandom random = new SecureRandom();
		byte[] bytesIV = new byte[16];
		random.nextBytes(bytesIV);
		IV = new GCMParameterSpec(128, bytesIV);


	}

	public static String enCode(String str)	{
		try{
			byte[] keyData = KEY.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, IV);
			byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
			return Base64.encodeBase64URLSafeString(encrypted);
		}catch (Exception e){
			logger.error("AES256 enCode Error", e);
			return null;
		}

	}

	public static String deCode(String str)	{
		try{
			byte[] keyData = KEY.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
			c.init(Cipher.DECRYPT_MODE, secureKey, IV);
			byte[] byteStr = Base64.decodeBase64(str.getBytes());
			return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
		}catch (Exception e){
			logger.error("AES256 deCode Error", e);
			return null;
		}
	}

}