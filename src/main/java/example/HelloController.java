package example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//public class HelloController
//{
//	@RequestMapping("/index")
//	public String index()
//	{
//		return "welcome";
//	}
//}

@RestController
@RequestMapping("/home")
public class HelloController
{
	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private OrderMapper orderMapper;

	@RequestMapping(value = "/user")
	public String user()
	{
		Customer customer = customerMapper.findUserByName("abc");
		return customer.getCustomerName() + "---" + customer.getTotalAmount();
	}

	@RequestMapping(value = "/order")
	public String order()
	{
		Order order = orderMapper.getByID(3);
		return order.getCustomerName() + "/" + order.getTelephoneID() + "/" + order.getOrderAmount()+"/"+order.getOrderTime();
	}
}