package njpi.soso.servicePackage;

import njpi.soso.model.Common;
import njpi.soso.model.MobileCard;
import njpi.soso.service.CallService;
import njpi.soso.service.NetService;
import njpi.soso.service.SendService;

import java.io.Serializable;



public class SuperPackage extends ServicePackage implements CallService, SendService, NetService,Serializable
{

	private int talkTime;
	private int smsCount;
	private int flow;
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
	public int getFlow() {
		return flow;
	}
	public void setFlow(int flow) {
		this.flow = flow;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public SuperPackage(double money,int talkTime, int smsCount, int flow) {
		//super.setPrice(money);
		this.price=money;
		this.talkTime = talkTime;
		this.smsCount = smsCount;
		this.flow = flow;
	}
	public void showInfo()
	{
		System.out.println("超人套餐：通话时长为"+this.talkTime+"分钟/月，短信条数为"+this.smsCount+"条/月，上网流量为"+this.flow/1024+"GB/月，月资费是"+this.price+"元/月。");
	}
	@Override
	public int netPlay(int flow, MobileCard card) throws Exception
	{
		int temp=flow;
		for (int i = 0; i < flow; i++) 
		{
			
			if (this.flow-card.getRealFlow()>=1) 
			{
				card.setRealFlow(card.getRealFlow()+1);
			}
			else if (card.getMoney()>=0.1) {
				card.setRealFlow(card.getRealFlow()+1);
				card.setMoney(Common.sub(card.getMoney(), 0.1));
				card.setConsumAmount(card.getConsumAmount()+0.1);
			}
			else {
				temp=i;
				throw new Exception("本次已使用流量"+i+"MB，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
	}
	@Override
	public int sendMessage(int count, MobileCard card) throws Exception {

		int temp=count;
		for (int i = 0; i < count; i++) 
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
}