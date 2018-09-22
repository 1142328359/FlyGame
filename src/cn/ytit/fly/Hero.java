package cn.ytit.fly;

import java.awt.image.BufferedImage;

/**	Ӣ�ۻ���һ��������*/
public class Hero extends FlyingObject{
	private int doubleFire;		//����ֵ
	private int life;			//��
	
	BufferedImage [] images = {};	//��hero0,hero1ͼƬ
	public int index = 0;			//Э��Ӣ�ۻ�ͼƬ���л�����
	
	/**	���췽������ʼ��Ӣ�ۻ��ĳ�Ա	*/
	public Hero(){
		this.image = ShootGame.hero0;			//��ʼ��Ӣ�ۻ�ͼƬ
		this.width	= image.getWidth();			//��ʼ��Ӣ�ۻ��Ŀ�
		this.height	= image.getHeight();		//��ʼ��Ӣ�ۻ��ĸ�
		this.x = 150;							//Ӣ�ۻ�������λ�ù̶�
		this.y = 400;
		
		doubleFire = 0;							//Ĭ�ϻ���ֵΪ0��������
		life = 3;								//Ĭ����3����
		
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};		//hero0,hero1ͼƬ
	}
	
	/**	�߲�����	*/
	public  void step(){	//ÿ��10ms����һ��
		index++;			//10,20,30,...
		int a = index / 10;
		int b = a % 2;
		image = images[b];//0,1,0,1		ÿ��100ms�л�һ��
	}		
	//index:1,2,3,...,10,...,20
	//a:0,0,0,...,1,...,2
	//b:0,0,0,...,1,...,0
	
	/* Ӣ�ۻ������ӵ� */
	public Bullet[] shoot(){
		int len = this.width/4;		//����д��1/4 * width����õ�0
		int he = 15;
		//����ǵ�������
		if(doubleFire == 0){
			Bullet [] bs = new Bullet[1];
			//����һ���ӵ�
			bs[0] = new Bullet(this.x + 2*len,y-he);//�ӵ���x��Ӣ�ۻ��� x + 1/2Ӣ�ۻ��Ŀ��
			return bs;						//�ӵ���y��Ӣ�ۻ���y - 20
		}else{//�����˫������
			Bullet [] bs = new Bullet[2];
			//���������ӵ�
			bs[0] = new Bullet(this.x + 1*len,y-he);//�ӵ���x��Ӣ�ۻ��� x + 1/4Ӣ�ۻ��Ŀ��
			bs[1] = new Bullet(this.x + 3*len,y-he);//�ӵ���x��Ӣ�ۻ��� x + 3/4Ӣ�ۻ��Ŀ��
			doubleFire -= 2;		//ÿ����һ��˫���������ͼ��ٻ���ֵ2
			return bs;	
		}
	}

	/** Ӣ�ۻ��ƶ� */
	public void moved(int x,int y){	//����x��y����
		this.x = x - width/2;
		this.y = y - height/2;
	}
	
	/** ��дԽ�紦����*/
	public boolean outOfBounds(){
		return false;			//��Զ��Խ��
	}
	
	/** ����*/
	public void addLife(){
		
		this.life++;		//������1��
	}
	/** �û���ֵ*/
	public void addDoubleFire(){
		
		this.doubleFire +=40;
	}
	
	/** Ӣ�ۻ�ײ�ϵл�*/
	public boolean hit(FlyingObject enemy){
		/*
		//����1
		int x1 = enemy.x;
		int x2 = enemy.x + this.width + enemy.width;
		int y1 = enemy.y;
		int y2 = enemy.y + this.height +enemy.width;
		int x = this.x;
		int y = this.y;
		return (x > x1 && x < x2)&&(y > y1 && y < y2);
		*/
		
		//����2
		int x1 = enemy.x - this.width/2;
		int x2 = enemy.x + enemy.width + this.width/2;
		int y1 = enemy.y - this.height/2;
		int y2 = enemy.y + enemy.height + this.height/2;
		int x = this.x + this.width/2;
		int y = this.y + this.height/2;
		return (x > x1 && x < x2)&&(y > y1 && y < y2);
	}
	
	/** ��Ӣ�ۻ���������*/
	public int getLife(){
		return this.life;
	}
	
	/** Ӣ�ۻ����л�ײ�Ϻ󣬼���*/
	public void substractLife(){
		this.life--;
	}
	
	/** һ��Ӣ�ۻ����л�ײ�ϣ��������Ļ���ֵ�ж�ߣ���Ҫ����*/
	public void clearDoubleFire(){
		this.doubleFire = 0;
	}
}
