package com.Property.Utility;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.StringWriter;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

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

	public String getRandomNumber(int length)
	{
		String KeyString="0123456789";
		StringBuffer stringBuffer=new StringBuffer();
		int len=KeyString.length();
		SecureRandom secureRandom=new SecureRandom();
		for(int i=0;i<length;i++)
		{
			stringBuffer.append(KeyString.charAt(secureRandom.nextInt(len-1)));
		}
		return stringBuffer.toString();
	}


	public Pair<RSAPrivateKey,RSAPublicKey> generateRSAKey()
	{
		try
		{
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			KeyPairGenerator keyPairGenerator=KeyPairGenerator
					.getInstance("RSA","BC");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair=keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey=(RSAPublicKey)keyPair.getPublic();
			RSAPrivateKey privateKey=(RSAPrivateKey)keyPair.getPrivate();


			Pair<RSAPrivateKey,RSAPublicKey> resultPair=new Pair<>(privateKey,publicKey);
			return resultPair;
		}
		catch(NoSuchAlgorithmException|NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String RSAEncrypt(PublicKey publicKey,String input)
	{
		try
		{
//			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding","BC");
//					("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE,publicKey);
			return encodeBase64String(cipher.doFinal(input.getBytes()));
//			return new String(cipher.doFinal(input.getBytes()));
		}
		catch(NoSuchAlgorithmException|NoSuchPaddingException|InvalidKeyException|IllegalBlockSizeException|BadPaddingException|NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String RSADecrypt(PrivateKey privateKey,String input)
	{
		try
		{
			byte[] encryptedText=Base64.decodeBase64(input);
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding","BC");
//					("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE,privateKey);
			return new String(cipher.doFinal(encryptedText));
		}
		catch(NoSuchAlgorithmException|NoSuchPaddingException|InvalidKeyException|IllegalBlockSizeException|BadPaddingException|NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public PublicKey getPublicKeyFromString(String input)
	{
		byte[] keyBytes=Base64.decodeBase64(input);
		X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
		try
		{
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
			PublicKey publicKey=keyFactory.generatePublic(keySpec);
			return publicKey;
		}
		catch(NoSuchAlgorithmException|InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public PrivateKey getPrivateKeyFromString(String input)
	{
		try
		{
			byte[] keyBytes=Base64.decodeBase64(input);
			PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
			PrivateKey privateKey=keyFactory.generatePrivate(keySpec);
			return privateKey;
		}
		catch(NoSuchAlgorithmException|InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getPKCS1PublicKey(RSAPublicKey publicKey)
	{
		try
		{
			SubjectPublicKeyInfo pkInfo=SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
			ASN1Primitive primitive=pkInfo.parsePublicKey();
			byte[] publ=primitive.getEncoded();
//			return Base64.encodeBase64String(publ);
			PemObject pemObject=new PemObject("RSA PUBLIC KEY",publ);
			StringWriter stringWriter=new StringWriter();
			PemWriter pemWriter=new PemWriter(stringWriter);
			pemWriter.writeObject(pemObject);
			pemWriter.close();
			String pemString=stringWriter.toString();
			return pemString;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public String getHashedPassword(String password,String username,String salt)
	{
		String alg="SHA-512";
		Object salt_real=username+salt;
		int iter=5;
		Object result=new SimpleHash(alg,password,salt_real,iter);
		return result.toString();
	}


}
