package com.Property.Utility;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class CryptoUtil
{
	public String Base64Encoder(String input)
	{
		byte[] encodeBytes=Base64.encodeBase64(input.getBytes());
		return new String(encodeBytes);
	}

	public String Base64Decoder(String input)
	{
		byte[] decodeBytes=Base64.decodeBase64(input.getBytes());
		return new String((decodeBytes));
	}

	public String Sha2Encoder(String input,String salt)
	{
		return Sha2Crypt.sha512Crypt((input+salt).getBytes());
	}

	public Key generateAESKey()
	{
		AesCipherService aesCipherService=new AesCipherService();
		aesCipherService.setKeySize(256);
		Key key=aesCipherService.generateNewKey();
		return key;
	}

	public String AESEncrypt(String input,Key key)
	{
		if(key==null)
			return null;
		AesCipherService aesCipherService=new AesCipherService();
		aesCipherService.setKeySize(256);
		return aesCipherService.encrypt(input.getBytes(),key.getEncoded()).toBase64();
	}

	public String AESDecrypt(String input,Key key)
	{
		if(key==null)
			return null;
		AesCipherService aesCipherService=new AesCipherService();
		aesCipherService.setKeySize(256);
		return new String(aesCipherService.decrypt(Base64.decodeBase64(input),key.getEncoded()).getBytes());
	}

	public String getRandomString(int length)
	{
		String KeyString="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer stringBuffer=new StringBuffer();
		int len=KeyString.length();
		SecureRandom secureRandom=new SecureRandom();
		for(int i=0;i<length;i++)
		{
			stringBuffer.append(KeyString.charAt(secureRandom.nextInt(len-1)));
		}
		return stringBuffer.toString();
	}

	public static final String PUBLIC_ALGORITHM="RSA";
	public static final String PUBLIC_CIPHER_ALGORITHM="RSA/ECB/PKCS1Padding";
	public static final int KEY_LENGTH=2048;

	public Pair<RSAPrivateKey,RSAPublicKey> generateRSAKey()
	{
		try
		{
			KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair=keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey=(RSAPublicKey)keyPair.getPublic();
			RSAPrivateKey privateKey=(RSAPrivateKey)keyPair.getPrivate();
			Pair<RSAPrivateKey,RSAPublicKey> resultPair=new Pair<>(privateKey,publicKey);
			return resultPair;
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String RSAEncrypt(PublicKey publicKey,String input)
	{
		try
		{
			Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE,publicKey);
			return Base64.encodeBase64String(cipher.doFinal(input.getBytes()));
		}
		catch(NoSuchAlgorithmException|NoSuchPaddingException|InvalidKeyException|IllegalBlockSizeException|BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String RSADecrypt(PrivateKey privateKey,String input)
	{
		try{
			byte [] encryptedText=Base64.decodeBase64(input);
			Cipher cipher=Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE,privateKey);
			return new String(cipher.doFinal(encryptedText));
		}
		catch(NoSuchAlgorithmException|NoSuchPaddingException|InvalidKeyException|IllegalBlockSizeException|BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
