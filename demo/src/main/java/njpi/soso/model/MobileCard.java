package njpi.gittest.model;

import java.io.Serializable;

import servicePackage.ServicePackage;

public class MobileCard implements Serializable
{

	private String cardNumber;
	private String userName;
	private String passWord;
	private ServicePackage serPackage;
	private double consumAmount;
	private double money;
	private int realTalkTime;
	private int realSMSCount;
	private int realFlow;
	
	public MobileCard() 
	{
	}
	
	public MobileCard(String cardNumber, String userName, String passWord,
			ServicePackage serPackage, double consumAmount, double money) 
	{
		this.cardNumber = cardNumber;
		this.userName = userName;
		this.passWord = passWord;
		this.serPackage = serPackage;
		this.consumAmount = consumAmount;
		this.money = money;
	}

	public void showMeg()
	{
		System.out.println("卡号："+this.cardNumber+"\t用户名："+this.userName+"\t当前余额："+this.money+"元。");
		this.serPackage.showInfo();
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public ServicePackage getSerPackage() {
		return serPackage;
	}
	public void setSerPackage(ServicePackage serPackage) {
		this.serPackage = serPackage;
	}
	public double getConsumAmount() {
		return consumAmount;
	}
	public void setConsumAmount(double consumAmount) {
		this.consumAmount = consumAmount;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getRealTalkTime() {
		return realTalkTime;
	}
	public void setRealTalkTime(int realTalkTime) {
		this.realTalkTime = realTalkTime;
	}
	public int getRealSMSCount() {
		return realSMSCount;
	}
	public void setRealSMSCount(int realSMSCount) {
		this.realSMSCount = realSMSCount;
	}
	public int getRealFlow() {
		return realFlow;
	}
	public void setRealFlow(int realFlow) {
		this.realFlow = realFlow;
	}
}