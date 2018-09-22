package cn.ytit.fly;

/**	子弹是一个飞行物	*/
public class Bullet extends FlyingObject {
	public int speed = 3;	//移动步数
	
	/**	构造方法，初始化子弹的成员	*/
	public Bullet(int x,int y){
		this.image = ShootGame.bullet;			//初始化子弹图片
		this.width	= image.getWidth();			//初始化子弹的宽
		this.height	= image.getHeight();		//初始化子弹的高
		this.x = x;								//根据英雄机的x坐标得来
		this.y = y;								//根据英雄机的y坐标得来
	}
	
	/**	重写走步方法	*/
	public void step(){
		y -= speed;		// y向上(减)
	}
	
	/** 重写越界处理方法*/
	public boolean outOfBounds(){
		return this.y < -this.height;
	}

}
