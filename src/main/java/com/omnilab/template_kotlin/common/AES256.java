package com.omnilab.template_kotlin.common;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AES256 {

	private static Logger logger = LoggerFactory.getLogger("AES256");
	public static String AES_Encode(String str, String key)	{
		try{
			SecureRandom random = new SecureRandom();
			byte[] bytesIV = new byte[16];
			random.nextBytes(bytesIV);
			IvParameterSpec iv = new IvParameterSpec(bytesIV);

			byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, newKey, iv);
			return Base64.encodeBase64URLSafeString(cipher.doFinal(textBytes));
		}catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			logger.error("AES_ENCODE", e);
			return null;
		}
	}

	public static String AES_Decode(String str, String key)	{
		try {
			SecureRandom random = new SecureRandom();
			byte[] bytesIV = new byte[16];
			random.nextBytes(bytesIV);
			IvParameterSpec iv = new IvParameterSpec(bytesIV);

			byte[] textBytes = Base64.decodeBase64(str);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, newKey, iv);
			return new String(cipher.doFinal(textBytes), StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			logger.error("AES_DECODE", e);
			return null;
		}
	}

}