//package example.Config;
//
//import example.Interceptor.InterceptorTest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class webconfig extends WebMvcConfigurationSupport
//{
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry)
//	{
//		registry.addViewController("/Sample").setViewName("welcome");
//		super.addViewControllers(registry);
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry)
//	{
//		registry.addInterceptor(new InterceptorTest()).addPathPatterns("/**").excludePathPatterns("/Sample","/index","/Test","/welcome");
//		super.addInterceptors(registry);
//	}
//}
