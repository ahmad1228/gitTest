package njpi.soso.test;

import njpi.soso.model.Common;
import njpi.soso.model.MobileCard;
import njpi.soso.utils.CardUtil;

import java.util.Scanner;

public class SosoMgr 
{
	Scanner input=new Scanner(System.in);
	CardUtil cardutil=new CardUtil();

	public	void mainMenu()
	{
		cardutil.readFile();
		int choice=0;
		 while (true) 
		 {
			 System.out.println("*****************欢迎使用嗖嗖移动业务大厅****************");
			 System.out.println("1.用户登录  2.用户注册  3.使用嗖嗖  4.话费充值  5.资费说明  6.退出系统");
			 System.out.print("请选择：");
			 choice=input.nextInt();
			 switch (choice) {
			case 1:
				System.out.print("请输入手机卡号：");
				String number=input.next();
				System.out.print("请输入密码：");
				String passWord=input.next();
				boolean result=cardutil.isExistCard(number, passWord);
				if (!result) 
				{
					System.out.println("用户名或密码错误！");
					break;
				}
				System.out.println("登录成功！");
				cardMenu(number);
				break;
			
			case 2:
				registCard();
				break;
			case 3:
				System.out.print("请输入您的卡号：");
				cardutil.userSoso(input.next());
				break;
			case 4:
				System.out.print("请输入您的卡号：");
				String chargeNumber=input.next();
				System.out.print("请输入充值金额：");
				double chargeMoney=input.nextDouble();
				cardutil.chargeMoney(chargeNumber, chargeMoney);
				break;
			case 5:
				System.out.println("*****资费说明*****");
				cardutil.showDescription();
				break;
			case 6:
				cardutil.saveFile();
				System.out.println("谢谢使用！");
				return;
			default:
				System.out.println("序号输入错误！");
				break;
			}
		 } 
	}
	public void cardMenu(String number)
	{
		int choice=0;
		boolean flag=true;
		 while (flag) 
		 {
			 System.out.println("********嗖嗖移动用户菜单*********");
			 System.out.println("1.本月账单查询");
			 System.out.println("2.套餐余量查询");
			 System.out.println("3.打印消费详单");
			 System.out.println("4.套餐变更");
			 System.out.println("5.办理退网");
			 System.out.print("请选择（输入1-5选择功能，其他键返回上一级）：");
			 choice=input.nextInt();
			 switch (choice) {
			case 1:
				System.out.println("*****本月账单查询*****");
				cardutil.showAmountDetail(number);
				break;
			case 2:
				System.out.println("*****套餐余量查询*****");
				cardutil.showRemainDetail(number);
				break;
			case 3:
				System.out.println("*****打印消费详单*****");
				cardutil.printAmountDetail(number);
				break;
			case 4:
				System.out.println("*****套餐变更*****");
				System.out.print("请输入要变更的套餐序号(1.话痨套餐  2.网虫套餐  3.超人套餐)");
				int packageNum=input.nextInt();
                 cardutil.changingPack(number, packageNum);
				break;
			case 5:
				System.out.println("*****办理退网*****");
				cardutil.delCard(number);
				break;
			default:
				flag=false;
				break;
			}
		 } 	
	}
	public void registCard()
	{

		MobileCard card=new MobileCard();
		System.out.println("*****可选择的卡号*****");
		String[] newNumbers=cardutil.getNewNumbers(9);
		int i=0;
		for (; i < newNumbers.length; i++) 
		{
			System.out.print((i+1)+"."+newNumbers[i]+"\t");
			if ((i+1)%3==0) 
			{
				System.out.println();
			}
		}
		int choiceNumber=0;
		System.out.print("请选择卡号（输入1-9的序号）：");
		do
		{
			choiceNumber=input.nextInt();
			if (choiceNumber>9||choiceNumber<1) 
			{
				System.out.print("序号输入错误！请重新输入：");
			}
		}while(choiceNumber>9||choiceNumber<1);
		card.setCardNumber(newNumbers[choiceNumber-1]);
		boolean flag;
		int choicePackage=0;
		System.out.print("\n1.话痨套餐\t2.网虫套餐\t3.超人套餐\n请选择套餐（输入序号）：");
		do
		{
			flag=true;
			choicePackage=input.nextInt();
			switch (choicePackage) 
			{
			case 1:
				card.setSerPackage(Common.talkPackage);
				break;
			case 2:
				card.setSerPackage(Common.netPackage);
				break;
			case 3:
				card.setSerPackage(Common.superPackage);
				break;
			default:
				System.out.print("序号输入错误！重新输入：");
				flag=false;
				break;
			}
		}while (!flag);
		System.out.print("请输入姓名：");
		card.setUserName(input.next());
		System.out.print("请输入密码：");
		card.setPassWord(input.next());
		System.out.print("请输入预存话费金额：");
		double money=0;
		do {
			money=input.nextDouble();
			if (money<card.getSerPackage().getPrice()) {
				System.out.print("您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
			}
		} while (money<card.getSerPackage().getPrice());
		card.setMoney(card.getMoney()+money);
		cardutil.addCard(card);
		System.out.print("注册成功！");
		card.showMeg();
	}
}