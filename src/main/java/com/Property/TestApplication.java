package com.Property;

import com.Property.Utility.CryptoUtil;

import com.Property.Utility.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@SpringBootApplication
public class TestApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(TestApplication.class,args);
	}
}
