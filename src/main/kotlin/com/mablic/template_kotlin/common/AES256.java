package com.mablic.template_kotlin.common;

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
import java.security.spec.AlgorithmParameterSpec;
import org.apache.commons.codec.binary.Base64;

public class AES256 {
	
	private static byte[] ivBytes = "7779328267167869".getBytes(StandardCharsets.UTF_8);

	public static String AES_Encode(String str, String key)	{
		try{
			byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
			return  new String(Base64.encodeBase64(cipher.doFinal(textBytes)), StandardCharsets.UTF_8);
		}catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String AES_Decode(String str, String key)	{
		try {
			byte[] textBytes = Base64.decodeBase64(str);
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
			return new String(cipher.doFinal(textBytes), StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String AES_EncodeURLsafe(String str, String key)	{
		try{
			byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
			return Base64.encodeBase64URLSafeString(cipher.doFinal(textBytes));
		}catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			return null;
		}
	}
}