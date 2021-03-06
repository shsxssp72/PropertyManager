package com.Property;

import com.Property.Service.Impl.UserInfoServiceImpl;
import com.Property.Service.UserInfoService;
import com.Property.Utility.CryptoUtil;

import com.Property.Utility.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@SpringBootApplication
@EnableScheduling
public class TestApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(TestApplication.class,args);
	}
}
