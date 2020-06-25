package njpi.soso.servicePackage;

import njpi.soso.model.Common;
import njpi.soso.model.MobileCard;
import njpi.soso.service.NetService;

import java.io.Serializable;



public class NetPackage extends ServicePackage implements NetService,Serializable
{
	private int flow;
	private double price;
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
	public NetPackage(double money,int flow) 
	{
		//super.setPrice(money);
		this.price=money;
		this.flow = flow;
	}
	public void showInfo()
	{
		System.out.println("网虫套餐：上网流量为"+this.flow/1024+"GB/月，月资费是"+this.price+"元/月。");
	}
	/*@Override*/
	/*public int netPlay2(int flow, MobileCard card) 
	{
		return 0;
	}*/
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
}