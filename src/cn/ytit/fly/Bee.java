package cn.ytit.fly;

import java.util.Random;

/**	小蜜蜂是一个飞行物，击中可以获得奖励*/
public class Bee extends FlyingObject implements Award {
	private int xSpeed = 1;		//横着走的步数
	private int ySpeed = 2;		//竖着走的步数
	private int awardType;      //奖励类型(0或1)，随机产生
	
	/**	构造方法	 ，初始化小蜜蜂的成员*/
	public Bee(){
		this.image = ShootGame.bee;		//初始化小蜜蜂图片
		this.width	= image.getWidth();			//初始化小蜜蜂的宽
		this.height	= image.getHeight();		//初始化小蜜蜂的高
		Random rand = new Random();				//随机数对象	
		this.x = rand.nextInt(ShootGame.WIDTH-this.width);	//横坐标变化的范围（0至（窗口宽-乱机宽)之内的随机数）
		this.y = -this.height;					// 小蜜蜂的纵坐标（负小蜜蜂的高）
//		this.y = 300;							//为了让图片在面板上可见
		
		//随机产生奖励类型(0或1)
		awardType = rand.nextInt(2);
	}

	@Override
	/** 重写getType()，获得奖励类型 */
	public int getType(){
		return awardType;		//返回奖励类型0或1
	}
	
	/**	走步方法	*/
	public  void step(){
		x += xSpeed;			//向左移动或向右移动
		y += ySpeed;			//y向下（加）
		
		if(x >= ShootGame.WIDTH - this.width ){		//小蜜蜂碰到右壁往左走
			xSpeed = -1;
		}
		
		if(x <= 0){									//小蜜蜂碰到左壁往右走
			xSpeed = 1;
		}
	}
	
	/** 重写越界处理方法*/
	public boolean outOfBounds(){
		return this.y > ShootGame.HEIGHT;
	}

}
