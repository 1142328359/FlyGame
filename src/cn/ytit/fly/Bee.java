package cn.ytit.fly;

import java.util.Random;

/**	С�۷���һ����������п��Ի�ý���*/
public class Bee extends FlyingObject implements Award {
	private int xSpeed = 1;		//�����ߵĲ���
	private int ySpeed = 2;		//�����ߵĲ���
	private int awardType;      //��������(0��1)���������
	
	/**	���췽��	 ����ʼ��С�۷�ĳ�Ա*/
	public Bee(){
		this.image = ShootGame.bee;		//��ʼ��С�۷�ͼƬ
		this.width	= image.getWidth();			//��ʼ��С�۷�Ŀ�
		this.height	= image.getHeight();		//��ʼ��С�۷�ĸ�
		Random rand = new Random();				//���������	
		this.x = rand.nextInt(ShootGame.WIDTH-this.width);	//������仯�ķ�Χ��0�������ڿ�-�һ���)֮�ڵ��������
		this.y = -this.height;					// С�۷�������꣨��С�۷�ĸߣ�
//		this.y = 300;							//Ϊ����ͼƬ������Ͽɼ�
		
		//���������������(0��1)
		awardType = rand.nextInt(2);
	}

	@Override
	/** ��дgetType()����ý������� */
	public int getType(){
		return awardType;		//���ؽ�������0��1
	}
	
	/**	�߲�����	*/
	public  void step(){
		x += xSpeed;			//�����ƶ��������ƶ�
		y += ySpeed;			//y���£��ӣ�
		
		if(x >= ShootGame.WIDTH - this.width ){		//С�۷������ұ�������
			xSpeed = -1;
		}
		
		if(x <= 0){									//С�۷��������������
			xSpeed = 1;
		}
	}
	
	/** ��дԽ�紦����*/
	public boolean outOfBounds(){
		return this.y > ShootGame.HEIGHT;
	}

}
