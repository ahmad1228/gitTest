package njpi.gittest.model;

import java.io.Serializable;


public class Scene implements Serializable
{
	private String type;
	private int data;
	private String desciption;
	public Scene() 
	{
		super();
	}
	public Scene(String type, int data, String desciption) 
	{
		super();
		this.type = type;
		this.data = data;
		this.desciption = desciption;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	
	
	
}
