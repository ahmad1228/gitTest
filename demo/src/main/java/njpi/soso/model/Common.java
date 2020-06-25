package njpi.gittest.model;

import java.text.DecimalFormat;

import servicePackage.NetPackage;
import servicePackage.SuperPackage;
import servicePackage.TalkPackage;

public class Common 
{
	public static TalkPackage talkPackage=new TalkPackage(58, 500, 30);
	public static NetPackage netPackage=new NetPackage(68, 3*1024);
	public static SuperPackage superPackage=new SuperPackage(78, 200, 50, 1*1024);
	
	public static String dataFormat(double data)
	{
		DecimalFormat formatData=new DecimalFormat("#.0");
		return formatData.format(data);		
	}
	public static double sub(double num1,double num2)
	{
		return (num1*10-num2*10)/10;
	}
	public enum ConsumType
	{
		TALK,SMS,NETWORK;
	}
}
