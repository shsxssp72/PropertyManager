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
//	public static void main(String[] args)
//	{
//		CryptoUtil cryptoUtil=new CryptoUtil();
//		Pair<RSAPrivateKey,RSAPublicKey> keyPair=cryptoUtil.generateRSAKey();
//		System.out.println((org.apache.commons.codec.binary.Base64
//				.encodeBase64String(keyPair.getValue().getEncoded())));
//		for(byte a:keyPair.getValue().getEncoded())
//			System.out.print(a+",");
//
//		byte[] encoded={48,-126,1,34,48,13,6,9,42,-122,72,-122,-9,13,1,1,1,5,0,3,-126,1,15,0,48,-126,1,10,2,-126,1,1,0,-38,-30,44,-67,-126,41,89,87,65,-3,63,-29,-74,58,-63,51,60,-122,103,-75,-24,-92,-112,71,-58,64,-67,-13,50,110,16,-79,-42,-34,42,13,-66,42,-2,-94,102,12,-42,-17,64,-109,127,83,60,53,-93,-112,69,123,94,-96,-36,100,-12,63,117,94,97,-4,24,82,25,12,53,102,-59,30,28,-42,-7,-27,37,71,104,49,44,-81,23,76,-64,7,-111,-54,110,-122,57,-25,-97,34,61,120,-17,98,93,-43,-114,114,-22,107,-54,111,22,-106,95,93,24,-120,38,-8,68,106,78,64,8,-120,29,71,35,-14,-7,36,-70,123,-18,95,-126,-20,-81,-99,38,-96,69,23,-47,-32,-79,-12,-7,99,66,76,-108,-37,3,75,23,-11,-38,-35,-83,24,-49,-100,107,122,-110,10,53,76,68,106,-81,-121,69,74,103,-75,100,-62,19,107,-71,79,-98,-37,57,59,4,67,-44,-56,73,-113,-67,64,108,114,-117,-34,35,52,93,66,-41,-71,-2,80,35,-40,-99,-114,-40,71,-21,56,-97,8,-77,-95,-67,118,67,-118,1,8,53,-14,-41,-65,-107,-126,28,100,88,105,-110,-128,94,1,51,-34,39,8,98,-41,-6,3,-49,-35,107,-101,-125,-42,-83,-39,-81,-17,46,-2,104,-69,2,3,1,0,1};
//		String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2uIsvYIpWVdB/T/jtjrBMzyGZ7XopJBHxkC98zJuELHW3ioNvir+omYM1u9Ak39TPDWjkEV7XqDcZPQ/dV5h/BhSGQw1ZsUeHNb55SVHaDEsrxdMwAeRym6GOeefIj1472Jd1Y5y6mvKbxaWX10YiCb4RGpOQAiIHUcj8vkkunvuX4Lsr50moEUX0eCx9PljQkyU2wNLF/Xa3a0Yz5xrepIKNUxEaq+HRUpntWTCE2u5T57bOTsEQ9TISY+9QGxyi94jNF1C17n+UCPYnY7YR+s4nwizob12Q4oBCDXy17+VghxkWGmSgF4BM94nCGLX+gPP3Wubg9at2a/vLv5ouwIDAQAB";
////		RSAPublicKey rsaPublicKey=new RSAPublicKeyImpl();
//	}
}
