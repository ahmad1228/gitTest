package njpi.soso.servicePackage;

import java.io.Serializable;

import njpi.soso.model.Common;
import njpi.soso.model.MobileCard;
import njpi.soso.service.CallService;
import njpi.soso.service.SendService;


public class TalkPackage extends ServicePackage implements CallService, SendService,Serializable
{
	private int talkTime;
	private int smsCount;
	private double price;

	public int getTalkTime() {
		return talkTime;
	}
	public void setTalkTime(int talkTime) {
		this.talkTime = talkTime;
	}
	public int getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public TalkPackage(double money,int talkTime, int smsCount) 
	{
		//super.setPrice(money);
		this.price=money;
		this.talkTime = talkTime;
		this.smsCount = smsCount;
	}
	public void showInfo()
	{
		System.out.println("话痨套餐：通话时长为"+this.talkTime+"分钟/月，短信条数为"+this.smsCount+"条/月，资费为"+/*super.getPrice()*/this.price+"元/月。");
	}
	@Override
	public int call(int minCount, MobileCard card) throws Exception
	{
		int temp=minCount;
		for (int i = 0; i < minCount; i++) 
		{
			if (this.talkTime-card.getRealTalkTime()>=1) 
			{
				card.setRealTalkTime(card.getRealTalkTime()+1);
			}
			else if(card.getMoney()>=0.2)
			{
				card.setRealTalkTime(card.getRealTalkTime()+1);
				card.setMoney(Common.sub(card.getMoney(), 0.2));
				card.setConsumAmount(card.getConsumAmount()+0.2);
			}
			else {
				temp=i;
				throw new Exception("本次已通话"+i+"分钟，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
	}
	
	public int sendMessage(int smsCount,MobileCard card) throws Exception
	{
		int temp=smsCount;
		for (int i = 0; i < smsCount; i++) 
		{
			if (this.smsCount-card.getRealSMSCount()>=1) 
			{
				card.setRealSMSCount(card.getRealSMSCount()+1);
			}
			else if (card.getMoney()>=0.1) 
			{
				card.setRealSMSCount(card.getRealSMSCount()+1);
				card.setMoney(Common.sub(card.getMoney(), 0.1));
				card.setConsumAmount(card.getConsumAmount()+0.1);
			}
			else 
			{
				temp=i;
				throw new Exception("本次已发送短信"+i+"条，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
	}
}
