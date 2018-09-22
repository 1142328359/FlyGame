package cn.ytit.fly;

import java.awt.image.BufferedImage;

/**	英雄机是一个飞行物*/
public class Hero extends FlyingObject{
	private int doubleFire;		//火力值
	private int life;			//命
	
	BufferedImage [] images = {};	//存hero0,hero1图片
	public int index = 0;			//协助英雄机图片的切换索引
	
	/**	构造方法，初始化英雄机的成员	*/
	public Hero(){
		this.image = ShootGame.hero0;			//初始化英雄机图片
		this.width	= image.getWidth();			//初始化英雄机的宽
		this.height	= image.getHeight();		//初始化英雄机的高
		this.x = 150;							//英雄机出生的位置固定
		this.y = 400;
		
		doubleFire = 0;							//默认火力值为0，即单发
		life = 3;								//默认有3条命
		
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};		//hero0,hero1图片
	}
	
	/**	走步方法	*/
	public  void step(){	//每隔10ms调用一次
		index++;			//10,20,30,...
		int a = index / 10;
		int b = a % 2;
		image = images[b];//0,1,0,1		每隔100ms切换一次
	}		
	//index:1,2,3,...,10,...,20
	//a:0,0,0,...,1,...,2
	//b:0,0,0,...,1,...,0
	
	/* 英雄机发射子弹 */
	public Bullet[] shoot(){
		int len = this.width/4;		//不能写成1/4 * width，会得到0
		int he = 15;
		//如果是单倍火力
		if(doubleFire == 0){
			Bullet [] bs = new Bullet[1];
			//产生一颗子弹
			bs[0] = new Bullet(this.x + 2*len,y-he);//子弹的x：英雄机的 x + 1/2英雄机的宽度
			return bs;						//子弹的y：英雄机的y - 20
		}else{//如果是双倍火力
			Bullet [] bs = new Bullet[2];
			//产生两颗子弹
			bs[0] = new Bullet(this.x + 1*len,y-he);//子弹的x：英雄机的 x + 1/4英雄机的宽度
			bs[1] = new Bullet(this.x + 3*len,y-he);//子弹的x：英雄机的 x + 3/4英雄机的宽度
			doubleFire -= 2;		//每发射一次双倍火力，就减少活力值2
			return bs;	
		}
	}

	/** 英雄机移动 */
	public void moved(int x,int y){	//鼠标的x，y坐标
		this.x = x - width/2;
		this.y = y - height/2;
	}
	
	/** 重写越界处理方法*/
	public boolean outOfBounds(){
		return false;			//永远不越界
	}
	
	/** 得命*/
	public void addLife(){
		
		this.life++;		//命数加1条
	}
	/** 得火力值*/
	public void addDoubleFire(){
		
		this.doubleFire +=40;
	}
	
	/** 英雄机撞上敌机*/
	public boolean hit(FlyingObject enemy){
		/*
		//方法1
		int x1 = enemy.x;
		int x2 = enemy.x + this.width + enemy.width;
		int y1 = enemy.y;
		int y2 = enemy.y + this.height +enemy.width;
		int x = this.x;
		int y = this.y;
		return (x > x1 && x < x2)&&(y > y1 && y < y2);
		*/
		
		//方法2
		int x1 = enemy.x - this.width/2;
		int x2 = enemy.x + enemy.width + this.width/2;
		int y1 = enemy.y - this.height/2;
		int y2 = enemy.y + enemy.height + this.height/2;
		int x = this.x + this.width/2;
		int y = this.y + this.height/2;
		return (x > x1 && x < x2)&&(y > y1 && y < y2);
	}
	
	/** 看英雄机命的条数*/
	public int getLife(){
		return this.life;
	}
	
	/** 英雄机被敌机撞上后，减命*/
	public void substractLife(){
		this.life--;
	}
	
	/** 一旦英雄机被敌机撞上，不管他的火力值有多高，都要归零*/
	public void clearDoubleFire(){
		this.doubleFire = 0;
	}
}
