package cn.ytit.fly;

import java.util.Random;

/** С�л�����һ�������ͬʱ���ǵ��� */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2; // �л��ƶ��ٶȣ��ƶ�������

	// ���췽������ʼ������
	public Airplane() {
		this.image = ShootGame.airplane;		//��ʼ���л�ͼƬ
		this.width	= image.getWidth();			//��ʼ���л��Ŀ�
		this.height	= image.getHeight();		//��ʼ���л��ĸ�
		Random rand = new Random();				//���������	
		this.x = rand.nextInt(ShootGame.WIDTH-this.width);	//������仯�ķ�Χ��0�������ڿ�-�һ���)֮�ڵ��������
		this.y = -this.height;					// �л��������꣨���л��ĸߣ�
//		this.y = 250;							//Ϊ����ͼƬ������Ͽɼ�
	}

	/**	��д�߲�����	*/
	public  void step(){
		y += speed;		// y���£��Ӳ�����
	}
	
	/** ��дԽ�紦����*/
	public boolean outOfBounds(){
		return this.y > ShootGame.HEIGHT;
	}

	/**	��дgetScore()	*/
	@Override
	public int getScore() {
		return 5; // ����С�л���5��
	}
	

}
