package com.Property.Domain;

public class Staff
{
	private String staff_id;
	private String staff_name;
	private String gender;
	private String tel;
	private String address;
	private String position;
	private double salary;
	private boolean elec_qlfy;
	private boolean plbr_qlfy;

	public String getStaff_id()
	{
		return staff_id;
	}

	public void setStaff_id(String staff_id)
	{
		this.staff_id = staff_id;
	}

	public String getStaff_name()
	{
		return staff_name;
	}

	public void setStaff_name(String staff_name)
	{
		this.staff_name = staff_name;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public double getSalary()
	{
		return salary;
	}

	public void setSalary(double salary)
	{
		this.salary = salary;
	}

	public boolean isElec_qlfy()
	{
		return elec_qlfy;
	}

	public void setElec_qlfy(boolean elec_qlfy)
	{
		this.elec_qlfy = elec_qlfy;
	}

	public boolean isPlbr_qlfy()
	{
		return plbr_qlfy;
	}

	public void setPlbr_qlfy(boolean plbr_qlfy)
	{
		this.plbr_qlfy = plbr_qlfy;
	}
}
