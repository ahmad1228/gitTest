package njpi.gittest.utils;

import njpi.gittest.model.Common;
import njpi.gittest.model.ConsumInfo;
import njpi.gittest.model.MobileCard;
import njpi.gittest.model.Scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CardUtil implements Serializable
{

	private  Map<String, MobileCard> cards;
	private  Map<String, List<ConsumInfo>> consumInfos;
	private  List<Scene> scenes;
	
	public void init()
	{
		MobileCard card1=new MobileCard("13965756432", "何玲玲", "123", Common.talkPackage, 58.0, 42.0);
		MobileCard card2=new MobileCard("13956712467", "黄露露", "123", Common.netPackage, 68.0, 32.0);
		MobileCard card3=new MobileCard("13911568956", "朱蓉蓉", "123", Common.superPackage, 78.0, 22.0);
		MobileCard card4=new MobileCard("13924221868", "桃跑跑", "123", Common.talkPackage, 78.0, 2.0);
		cards.put("13965756432", card1);
		cards.put("13956712467", card2);
		cards.put("13911568956", card3);
		cards.put("13924221868", card4);
		ConsumInfo info1=new ConsumInfo("13911568956", "通话", 14);
		ConsumInfo info2=new ConsumInfo("13911568956", "上网", 3*1024);
		ConsumInfo info3=new ConsumInfo("13911568956", "短信", 38);
		List<ConsumInfo> list=new ArrayList<ConsumInfo>();
		list.add(info1);
		list.add(info2);
		list.add(info3);
		consumInfos.put("13911568956", list);
	}
	
	public void readFile()
	{
		ObjectInputStream ois = null;
		try {
			File file=new File("soso.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			ois=new ObjectInputStream(new FileInputStream(file));
			cards=(Map<String, MobileCard>) ois.readObject();
			consumInfos=(Map<String, List<ConsumInfo>>) ois.readObject();
			scenes=(List<Scene>) ois.readObject();
		} catch (Exception e) {
			System.out.println("文件内容为空！然后开始初始化！");
			cards=new HashMap<String, MobileCard>();
			consumInfos=new HashMap<String, List<ConsumInfo>>();
			scenes=new ArrayList<Scene>();
			init();//初始化 cards 和   consumInfos
			initScenes();//初始化场景
			saveFile();
		}finally{
			if (ois!=null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void saveFile()
	{
		ObjectOutputStream oos = null;
		try {
			oos=new ObjectOutputStream(new FileOutputStream("soso.txt"));
			oos.writeObject(cards);
			oos.writeObject(consumInfos);
			oos.writeObject(scenes);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (oos!=null) 
			{
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//初始化场景
	public void initScenes()
	{
		scenes.add(new Scene("通话",90 , "问候客户，谁知其如此难缠 通话90分钟"));
		scenes.add(new Scene("通话",30, "询问妈妈身体状况 本地通话30分钟"));
		scenes.add(new Scene("短信",5, "参与环境保护实施方案问卷调查 发送短信5条"));
		scenes.add(new Scene("短信",50, "通知朋友换号，发送短信50条"));
		scenes.add(new Scene("流量",1*1024, "和女友微信视频聊天 使用流量1G"));
		scenes.add(new Scene("流量",2*1024,"晚上手机在线看韩剧，不留神睡着啦！使用流量2G"));		
	}
	//注册新卡
	public  void addCard(MobileCard card)
	{	
		cards.put(card.getCardNumber(), card);
	}
	//话费充值
	public void chargeMoney(String number,double money)
	{
		if (isExistCard(number)) 
		{
			if (money<50) 
			{
				System.out.println("对不起，最低充值金额为50元！");
				return;
			}
			MobileCard card=cards.get(number);
			card.setMoney(card.getMoney()+money);
			System.out.println("充值成功，当前话费余额为"+Common.dataFormat(card.getMoney())+"元。");
		}
		else 
		{
			System.out.println("抱歉，该卡号未注册，不能办理充值！");
			return;
		}
	}//
	//使用嗖嗖
	public void userSoso(String number)
	{		
		if (!isExistCard(number)) 
		{
			System.out.println("此卡号不存在，不能使用嗖嗖！");
			return;
		}
			MobileCard card=cards.get(number);
			ServicePackage pack = card.getSerPackage();
			int ranNum=0;
			int temp=0;
			Random random=new Random();
			do {
					ranNum = random.nextInt(6);
					Scene scene = scenes.get(ranNum);
					switch (ranNum) 
					{
					case 0:
					case 1:
						if (pack instanceof CallService) 
						{
							System.out.println(scene.getDesciption());
							CallService callService=(CallService) pack;
							try {
								temp=callService.call(scene.getData(), card);
							} catch (Exception e) {
								e.printStackTrace();
							}
							addConsumInfo(number, new ConsumInfo(number, scene.getType(), temp));
							break;
						}else 
						{
							continue;
						}
					case 2:
					case 3:
						if (pack instanceof SendService) 
						{
							System.out.println(scene.getDesciption());
							SendService sendService=(SendService) pack;
							try {
								temp=sendService.sendMessage(scene.getData(), card);
							} catch (Exception e) {
								e.printStackTrace();
							}
							addConsumInfo(number, new ConsumInfo(number, scene.getType(), temp));
							break;
						}
						else {
							continue;
						}
					case 4:
					case 5:
						if (pack instanceof NetService) 
						{
							System.out.println(scene.getDesciption());
							NetService netService=(NetService) pack;
							try {
								temp=netService.netPlay(scene.getData(), card);
							} catch (Exception e) {
								e.printStackTrace();
							}
							addConsumInfo(number, new ConsumInfo(number, scene.getType(), temp));
							break;
						}
						else {
							continue;
						}
					}
					break;
			} while (true);
	}
	//资费说明
	public void showDescription()
	{
		Reader reader = null;
		try {
			reader=new FileReader("套餐资费说明.txt");
			char[] content=new char[1024];
			StringBuffer sb=new StringBuffer();
			int length=0;
			while ((length=reader.read(content))!=-1) 
			{
				sb.append(content,0,length);
			}
			System.out.println(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			if (reader!=null) 
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//本月账单查询
	public  void showAmountDetail(String searchNumber)
	{
		if (!isExistCard(searchNumber)) 
		{
			System.out.println("此号码未注册，不能查询本月账单！");
			return;
		}
		MobileCard card = cards.get(searchNumber);
		StringBuffer meg=new StringBuffer();
		meg.append("您的卡号："+card.getCardNumber()+"\n当月账单：\n");
		meg.append("套餐资费："+card.getSerPackage().getPrice()+"元\n");
		meg.append("合计："+card.getConsumAmount()+"元\n");
		meg.append("账户余额："+card.getMoney()+"元");
		System.out.println(meg);
	}
	//套餐余量查询
	public  void showRemainDetail(String searchNumber)
	{
		if (!isExistCard(searchNumber)) 
		{
			System.out.println("此号码未注册，不能查询套餐余量！");
			return;
		}
		MobileCard card=cards.get(searchNumber);
		int remainTalkTime;
		int remainSmsCount;
		int remainFlow;
		ServicePackage pack=card.getSerPackage();
		StringBuffer meg=new StringBuffer();
		meg.append("您的卡号是"+searchNumber+"\n套餐内剩余：\n");
		if (pack instanceof TalkPackage) 
		{
			TalkPackage cardPackage=(TalkPackage) pack;
			remainTalkTime=cardPackage.getTalkTime()>card.getRealTalkTime()?cardPackage.getTalkTime()-card.getRealTalkTime():0;
			meg.append("通话时长："+remainTalkTime+"分钟\n");
			remainSmsCount=cardPackage.getSmsCount()>card.getRealSMSCount()?cardPackage.getSmsCount()-card.getRealSMSCount():0;
			meg.append("短信条数："+remainSmsCount+"条");
		}
		else if (pack instanceof NetPackage) 
		{
			NetPackage cardPackage=(NetPackage) pack;
			remainFlow=cardPackage.getFlow()>card.getRealFlow()?cardPackage.getFlow()-card.getRealFlow():0;
			meg.append("流量："+Common.dataFormat(remainFlow*1.0/1024)+"GB\n");
		}
		else if (pack instanceof SuperPackage) 
		{
			SuperPackage cardPackage=(SuperPackage) pack;
			remainTalkTime=cardPackage.getTalkTime()>card.getRealTalkTime()?cardPackage.getTalkTime()-card.getRealTalkTime():0;
			meg.append("通话时长："+remainTalkTime+"分钟\n");
			remainFlow=cardPackage.getFlow()>card.getRealFlow()?cardPackage.getFlow()-card.getRealFlow():0;
			meg.append("流量："+Common.dataFormat(remainFlow*1.0/1024)+"GB\n");
			remainSmsCount=cardPackage.getSmsCount()>card.getRealSMSCount()?cardPackage.getSmsCount()-card.getRealSMSCount():0;
			meg.append("短信条数："+remainSmsCount+"条");	
		}	
		System.out.println(meg.toString());
}
	//打印消费详单
	public  void printAmountDetail(String number)
	{
		if (!isExistCard(number)) 
		{
			System.out.println("此号码未注册，不能打印详单！");
			return;
		}
		Writer fileWriter=null;
		try {
			File folder=new File("ConsumInfos");
			if (!folder.exists()) 
			{
				folder.mkdir();
			}
			File file=new File(folder,number+"消费记录.txt");
			fileWriter=new FileWriter(file);
			Set<String> numbers = consumInfos.keySet();
			boolean isExist=false;
			List<ConsumInfo> infos=new ArrayList<ConsumInfo>();
			for (String str : numbers) 
			{
				if (str.equals(number)) 
				{
					infos=consumInfos.get(str);
					isExist= true;
					break;
				}
			}
			if (isExist) 
			{
				StringBuffer content=new StringBuffer("******"+number+"消费记录******\r\n");
				content.append("序号\t类型\t数据（通话（分钟）/上网（MB）/短信（条））\r\n");
				for (int i = 0; i < infos.size(); i++) 
				{
					ConsumInfo info = infos.get(i);
					content.append((i+1)+".\t"+info.getType()+"\t"+info.getConsumData()+"\r\n");
				}
				System.out.println(content.toString());
				fileWriter.write(content.toString());
				fileWriter.flush();
				System.out.println("消费记录打印完毕！");
			}
			else {
				System.out.println("对不起，不存在此号码的消费记录，不能打印！");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fileWriter!=null){
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
		/*
		Writer fileWriter=null;
		try {
			fileWriter=new FileWriter(number+"消费记录.txt");
			Set<String> numbers = consumInfos.keySet();
			Iterator<String> it=numbers.iterator();
			List<ConsumInfo> infos=new ArrayList<ConsumInfo>();
			boolean isExist=false;
			while (it.hasNext()) 
			{
				String searchNum=it.next();
				if (searchNum.equals(number)) 
				{
					infos=consumInfos.get(searchNum);
					isExist= true;
					break;
				}
			}
			if (isExist) 
			{
				StringBuffer content=new StringBuffer("******"+number+"消费记录******\n");
				content.append("序号\t类型\t数据（通话（分钟）/上网（MB）/短信（条））\n");
				for (int i = 0; i < infos.size(); i++) 
				{
					ConsumInfo info = infos.get(i);
					content.append((i+1)+".\t"+info.getType()+"\t"+info.getConsumData()+"\n");
				}
				System.out.println(content.toString());
				System.out.println("消费记录打印完毕！");
			}
			else {
				System.out.println("对不起，不存在此号码的消费记录，不能打印！");
			}		
		} catch (Exception e) 
		{
			e.printStackTrace();
		}finally
		{
			if (fileWriter!=null) 
			{
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	*/
	//套餐变更
	public  void changingPack(String number,int packNum)
	{
		ServicePackage pack;
		MobileCard card;
		 if (isExistCard(number)) 
		 {
			 	if(packNum==1)
				{
					pack=Common.talkPackage;
				}
				else if (packNum==2) 
				{
					pack=Common.netPackage;
				}
				else 
				{
					pack=Common.superPackage;
				}
			 
			 	card=cards.get(number);			 
			 	if (card.getSerPackage().getClass().getName().equals(pack.getClass().getName())) 
				{
					System.out.println("对不起，您已经是该套餐用户，无须更换套餐");
					return;
				}
				else if (card.getMoney()>=pack.getPrice()) 
				{
					card.setMoney(card.getMoney()-pack.getPrice());
					card.setSerPackage(pack);
					card.setRealFlow(0);
					card.setRealSMSCount(0);
					card.setRealTalkTime(0);
					card.setConsumAmount(pack.getPrice());
					System.out.println("更换套餐成功！");
					pack.showInfo();
				}
				else 
				{
					System.out.println("对不起，您的余额不足以支付当前套餐月租，请充值后再更换");
					return;
				}
		}
		 else 
		 {
			System.out.println("对不起，该卡号未注册，不能更换套餐！");
			return;
		}
	}
	//办理退网
	public  void delCard(String delNumber)
	{
		if (isExistCard(delNumber)) 
		{
			cards.remove(delNumber);
			System.out.println("卡号"+delNumber+"办理退网成功！");
		}
		else 
		{
			System.out.println("抱歉，该卡号未注册，不能办理退网！");
		}
	}
	//根据卡号和密码验证该卡是否注册
	public  boolean isExistCard(String number,String passWord)
	{
		Set<String> numbers = cards.keySet();
		for (String str : numbers) 
		{
			if (str.equals(number)&&cards.get(str).getPassWord().equals(passWord)) 
			{
				return true;
			}
		}
		return false;
	}
		/*
		Set<String> numbers=cards.keySet();
		Iterator<String> it=numbers.iterator();
		while (it.hasNext()) {
			String searchNum=it.next();
			if (searchNum.equals(number)&&(cards.get(searchNum).getPassWord().equals(passWord))) 
			{
				return true;
			}
		}
		return false;
		*/
	//根据卡号验证该卡是否注册
	public boolean isExistCard(String searchNumber)
	{
		Set<String> numbers = cards.keySet();
		for (String str : numbers) 
		{
			if (str.equals(searchNumber)) 
			{
				return true;
			}
		}
		return false;
	}
	//生成随机卡号：
	public  String createNumber()
	{
		String number;
		boolean isExist=false;
		do 
		{
			number="139"+(int) (Math.random()*90000000+10000000);
			Set<String> cardNumbers=cards.keySet();
			for (String cardNumber : cardNumbers) 
			{
				if (cardNumber.equals(number)) 
				{
					isExist=true;
					break;
				}
			}
		}while(isExist);
		return number;
	}
	//生成指定个数的卡号列表
	public  String[] getNewNumbers(int count)
	{
		String[] strs=new String[count];
		for (int i = 0; i < count; i++) 
		{
			strs[i]=createNumber();
		}
		return strs;
	} 
	//添加指定卡号的消费记录
	public void addConsumInfo(String number,ConsumInfo info)
	{
		if (!isExistCard(number)) 
		{
			System.out.println("此号码未注册，不能添加消费记录！");
			return;
		}
		Set<String> numbers = consumInfos.keySet();
		List<ConsumInfo> infos=new ArrayList<ConsumInfo>();
		boolean isExist=false;
		for (String str : numbers) 
		{
			if (str.equals(number)) 
			{
				isExist=true;
				infos=consumInfos.get(str);
				infos.add(info);
				System.out.println("已添加一条消费记录");
				break;
			}
		}
		if (!isExist) 
		{
			infos.add(info);
			consumInfos.put(number, infos);
			System.out.println("不存在此卡的消费记录，已添加一条消费记录。");
		}	
	}
	//根据用户选择的套餐序号返回套餐对象
	/*public ServicePackage createPack(int packId)
	{
		if(packId==1)
		{
			return Common.talkPackage;
		}
		else if (packId==2) {
			return Common.netPackage;
		}
		else {
			return Common.superPackage;
		}
	}*/
	public void printConsumInfo(String number)
	{
		
	}
}
