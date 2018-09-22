package cn.ytit.fly;
/**	�������ࣨС�л���С�۷䣬Ӣ�ۻ����ӵ��� */
import java.awt.image.BufferedImage;;
public abstract class FlyingObject {
	protected int x;				//������
	protected int y;				//������
	protected int width;			//��
	protected int height;			//��
	protected BufferedImage image;	//ͼƬ		

	//��Ա����
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
	
	/**	�߲�����	*/
	public abstract void step();
	
	/** Խ�紦���� */
	public abstract boolean outOfBounds();
	
	public boolean shootBy(Bullet b){
		int x1 = this.x;				//�������x����
		int x2 = this.x +this.width;	//�������x����+������Ŀ�
		int y1 = this.y;				//�������y����
		int y2 = this.y + this.height;	//�������y����+������ĸ�
		
		int x = b.x;
		int y = b.y;
		
		boolean flag = (x>x1 && x<x2)&&(y>y1 && y<y2);		//�����ﱻ�ӵ�ײ��
		return flag;
	}
}
