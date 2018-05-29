package example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Property.Utility.CryptoUtil;

import java.security.Key;

//@SpringBootApplication
//@MapperScan("example.CustomerMapper")
public class DemoApplication
{
	public static void main(String[] args)
	{
		//SpringApplication.run(DemoApplication.class, args);
		CryptoUtil b=new CryptoUtil();
		Key a=b.generateAESKey();
		String hello="HelloWorld";
		String c=b.AESEncrypt(hello,a);
		System.out.println(c);
		System.out.println(b.AESDecrypt(c,a));
		System.out.println(b.Sha2Encoder(hello,b.getRandomString(4)));
	}
}