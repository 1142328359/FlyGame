package cn.ytit.fly;
/**	Award�����ӿ�	*/
public interface Award {
	public static final int DOUBLE_FIRE = 0;	//����ֵ
	public static final int LIFE = 1;	//��
	
	public abstract int getType();		//��ȡ�������ͣ�����ֵ0Ϊ����ֵ�򷵻�ֵ1Ϊ��
}
