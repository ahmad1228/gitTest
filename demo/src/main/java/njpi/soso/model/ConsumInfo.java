package njpi.gittest.model;

import java.io.Serializable;

public class ConsumInfo implements Serializable
{

	private String cardNumber;
	private String type;
	private int consumData;
	
	public ConsumInfo() 
	{
		super();
	}
	public ConsumInfo(String cardNumber, String type, int consumData) 
	{
		super();
		this.cardNumber = cardNumber;
		this.type = type;
		this.consumData = consumData;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getConsumData() {
		return consumData;
	}
	public void setConsumData(int consumData) {
		this.consumData = consumData;
	}
	
}
