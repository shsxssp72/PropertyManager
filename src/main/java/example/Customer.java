package example;

public class Customer
{
	private Integer customerID;
	private String customerName;
	private String customerTelephone;
	private String gradeType;
	private Integer totalAmount;

	public Integer getCustomerID()
	{
		return customerID;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public Integer getTotalAmount()
	{
		return totalAmount;
	}

	public void setCustomerID(Integer customerID)
	{
		this.customerID = customerID;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public void setTotalAmount(Integer totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public String getCustomerTelephone()
	{
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone)
	{
		this.customerTelephone = customerTelephone;
	}

	public String getGradeType()
	{
		return gradeType;
	}

	public void setGradeType(String gradeType)
	{
		this.gradeType = gradeType;
	}
}