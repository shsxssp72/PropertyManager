package com.Property.Entity;

public class Suggestion
{
	private String suggestion_id;
	private String prprt_id;
	private String suggestion_type;
	private String suggestion_detail;

	public String getSuggestion_id()
	{
		return suggestion_id;
	}

	public void setSuggestion_id(String suggestion_id)
	{
		this.suggestion_id=suggestion_id;
	}

	public String getPrprt_id()
	{
		return prprt_id;
	}

	public void setPrprt_id(String prprt_id)
	{
		this.prprt_id=prprt_id;
	}

	public String getSuggestion_type()
	{
		return suggestion_type;
	}

	public void setSuggestion_type(String suggestion_type)
	{
		this.suggestion_type=suggestion_type;
	}

	public String getSuggestion_detail()
	{
		return suggestion_detail;
	}

	public void setSuggestion_detail(String suggestion_detail)
	{
		this.suggestion_detail=suggestion_detail;
	}
}
