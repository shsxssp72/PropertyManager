package example;


import java.sql.Timestamp;

public class Order
{
	private int orderID;
	private String customerName;
	private String telephoneID;
	private Timestamp orderTime;
	private double orderAmount;
	private boolean isFinished;

	public Timestamp getOrderTime()
	{
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime)
	{
		this.orderTime=orderTime;
	}

	public boolean isFinished()
	{
		return isFinished;
	}

	public void setFinished(boolean finished)
	{
		isFinished=finished;
	}

	public int getOrderID()
	{
		return orderID;
	}

	public void setOrderID(int orderID)
	{
		this.orderID=orderID;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName=customerName;
	}

	public String getTelephoneID()
	{
		return telephoneID;
	}

	public void setTelephoneID(String telephoneID)
	{
		this.telephoneID=telephoneID;
	}

	public double getOrderAmount()
	{
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount)
	{
		this.orderAmount=orderAmount;
	}
}
