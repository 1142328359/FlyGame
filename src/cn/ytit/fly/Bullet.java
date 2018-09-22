package cn.ytit.fly;

/**	�ӵ���һ��������	*/
public class Bullet extends FlyingObject {
	public int speed = 3;	//�ƶ�����
	
	/**	���췽������ʼ���ӵ��ĳ�Ա	*/
	public Bullet(int x,int y){
		this.image = ShootGame.bullet;			//��ʼ���ӵ�ͼƬ
		this.width	= image.getWidth();			//��ʼ���ӵ��Ŀ�
		this.height	= image.getHeight();		//��ʼ���ӵ��ĸ�
		this.x = x;								//����Ӣ�ۻ���x�������
		this.y = y;								//����Ӣ�ۻ���y�������
	}
	
	/**	��д�߲�����	*/
	public void step(){
		y -= speed;		// y����(��)
	}
	
	/** ��дԽ�紦����*/
	public boolean outOfBounds(){
		return this.y < -this.height;
	}

}
