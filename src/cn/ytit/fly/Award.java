package cn.ytit.fly;
/**	Award奖励接口	*/
public interface Award {
	public static final int DOUBLE_FIRE = 0;	//火力值
	public static final int LIFE = 1;	//命
	
	public abstract int getType();		//获取奖励类型，返回值0为火力值或返回值1为命
}
