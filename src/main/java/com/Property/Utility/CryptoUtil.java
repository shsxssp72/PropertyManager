package com.Property.Utility;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;

import java.security.Key;
import java.security.SecureRandom;

public class CryptoUtil
{
	public String Base64Encoder(String input)
	{
		byte[] encodeBytes = Base64.encodeBase64(input.getBytes());
		return new String(encodeBytes);
	}

	public String Base64Decoder(String input)
	{
		byte[] decodeBytes = Base64.decodeBase64(input.getBytes());
		return new String((decodeBytes));
	}

	public String Sha2Encoder(String input, String salt)
	{
		return Sha2Crypt.sha512Crypt((input+salt).getBytes());
	}

	public Key generateAESKey()
	{
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(256);
		Key key = aesCipherService.generateNewKey();
		return key;
	}

	public String AESEncrypt(String input, Key key)
	{
		if (key == null)
			return null;
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(256);
		return aesCipherService.encrypt(input.getBytes(), key.getEncoded()).toBase64();
	}

	public String AESDecrypt(String input, Key key)
	{
		if (key == null)
			return null;
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(256);
		return new String(aesCipherService.decrypt(Base64.decodeBase64(input),key.getEncoded()).getBytes());
	}
	public String getRandomString(int length) {
		String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer stringBuffer = new StringBuffer();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return stringBuffer.toString();
	}
}
