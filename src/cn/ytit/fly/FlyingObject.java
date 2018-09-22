package cn.ytit.fly;
/**	飞行物类（小敌机，小蜜蜂，英雄机，子弹） */
import java.awt.image.BufferedImage;;
public abstract class FlyingObject {
	protected int x;				//横坐标
	protected int y;				//纵坐标
	protected int width;			//宽
	protected int height;			//高
	protected BufferedImage image;	//图片		

	//成员方法
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	/**	走步方法	*/
	public abstract void step();
	
	/** 越界处理方法 */
	public abstract boolean outOfBounds();
	
	public boolean shootBy(Bullet b){
		int x1 = this.x;				//飞行物的x坐标
		int x2 = this.x +this.width;	//飞行物的x坐标+飞行物的宽
		int y1 = this.y;				//飞行物的y坐标
		int y2 = this.y + this.height;	//飞行物的y坐标+飞行物的高
		
		int x = b.x;
		int y = b.y;
		
		boolean flag = (x>x1 && x<x2)&&(y>y1 && y<y2);		//飞行物被子弹撞上
		return flag;
	}
}
