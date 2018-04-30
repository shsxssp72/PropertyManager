package example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Property.Utility.CryptoUtil;

import java.security.Key;

@SpringBootApplication
@MapperScan("example.CustomerMapper")
public class DemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}
}