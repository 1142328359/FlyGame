package cn.ytit.fly;

import java.util.Random;

/** 小敌机，是一个飞行物，同时还是敌人 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2; // 敌机移动速度（移动步数）

	// 构造方法，初始化数据
	public Airplane() {
		this.image = ShootGame.airplane;		//初始化敌机图片
		this.width	= image.getWidth();			//初始化敌机的宽
		this.height	= image.getHeight();		//初始化敌机的高
		Random rand = new Random();				//随机数对象	
		this.x = rand.nextInt(ShootGame.WIDTH-this.width);	//横坐标变化的范围（0至（窗口宽-乱机宽)之内的随机数）
		this.y = -this.height;					// 敌机的纵坐标（负敌机的高）
//		this.y = 250;							//为了让图片在面板上可见
	}

	/**	重写走步方法	*/
	public  void step(){
		y += speed;		// y向下（加步长）
	}
	
	/** 重写越界处理方法*/
	public boolean outOfBounds(){
		return this.y > ShootGame.HEIGHT;
	}

	/**	重写getScore()	*/
	@Override
	public int getScore() {
		return 5; // 击中小敌机得5分
	}
	

}
