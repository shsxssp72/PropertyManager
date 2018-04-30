package example;

import com.Property.Entity.Building;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

//@Controller
//public class HelloController
//{
//	@RequestMapping("/index")
//	public String index()
//	{
//		return "welcome";
//	}
//}

//@RestController
//@RequestMapping("/home")
//public class HelloController
//{
//	@Autowired
//	private CustomerMapper customerMapper;
//
//	@Autowired
//	private OrderMapper orderMapper;
//
//	@RequestMapping(value = "/user")
//	public String user()
//	{
//		Customer customer = customerMapper.findUserByName("abc");
//		return customer.getCustomerName() + "---" + customer.getTotalAmount();
//	}
//
//	@RequestMapping(value = "/order")
//	public String order()
//	{
//		Order order = orderMapper.getByID(3);
//		return order.getCustomerName() + "/" + order.getTelephoneID() + "/" + order.getOrderAmount()+"/"+order.getOrderTime();
//	}
//}

@RestController
//@RequestMapping("/")
public class HelloController
{
	@Autowired
	private buildingMapper buildingMapperEntity;

	@RequestMapping(value="/Test", method=RequestMethod.GET)
	public ModelAndView index()
	{
//		List<TestList> testLists = new ArrayList<>();
//		TestList bean =new TestList("1","2","3","4","5","0.76/1.861");
//		testLists.add(bean);
//		bean=new TestList("7","8","9","10","11","0.52/1.561");
//		testLists.add(bean);
//		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
////		ModelAndView modelAndView=new ModelAndView("template_index");
//		modelAndView.addObject("testLists",testLists);


		List<Building> testLists=buildingMapperEntity.getAll();
		ModelAndView modelAndView=new ModelAndView("dashboard_Test");
		modelAndView.addObject("testLists",testLists);
		return modelAndView;
	}


}