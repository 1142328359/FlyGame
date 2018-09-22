package cn.ytit.fly;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** 射击游戏主程序类，用于测试 */
public class ShootGame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400; // 窗口宽
	public static final int HEIGHT = 654; // 窗口高

	public static BufferedImage background; // 背景图
	public static BufferedImage start; // 开始图
	public static BufferedImage pause; // 暂停图
	public static BufferedImage gameover; // 游戏结束图
	public static BufferedImage airplane; // 敌机
	public static BufferedImage bee; // 小蜜蜂
	public static BufferedImage bullet; // 子弹
	public static BufferedImage hero0; // 英雄机0
	public static BufferedImage hero1; // 英雄机1
	
	//游戏的当前状态:一共四种：运行，暂停，结束，开始
	private int state= 0;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;

	// 静态代码块，加载图片，初始化静态资源（图片、声音等）
	static {
		try {
			background = ImageIO.read(ShootGame.class
					.getResource("background.png"));
			airplane = ImageIO
					.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			gameover = ImageIO
					.read(ShootGame.class.getResource("gameover.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 创建对象 1个英雄机，多个子弹，多个敌人（小敌机，小蜜蜂）
	Hero hero = new Hero(); // 创建英雄机对象
	Bullet[] bullets = {}; // 创建子弹数组
	FlyingObject[] enemyflyings = {}; // 创建敌人数组(小敌机 + 小蜜蜂)

	/*
	 * // 用构造方法，初始化对象成员，为了在面板上画对象，用于测试 public ShootGame() { bullets = new
	 * Bullet[1]; bullets[0] = new Bullet(100, 200); enemyflyings = new
	 * FlyingObject[2]; enemyflyings[0] = new Airplane(); enemyflyings[1] = new
	 * Bee(); }
	 */
	/** 随机产生敌人(小敌机 + 小蜜蜂) */
	public FlyingObject nextOne() {
		Random rand = new Random();
		int x = rand.nextInt(20); // 产生一个0-19之间的随机数（包含0和19）
		if (x < 4) { // 随机数小于4的话，产生一只小蜜蜂
			return new Bee();
		} else { // 4=<随机数<=19的话，产生一个小敌机
			return new Airplane();
		}
	}

	int enemyEnterIndex = 0; // 计数器

	/** 敌人入场 */
	public void enemyEnterAction() { // 10毫秒就调用一次
		enemyEnterIndex++;
		if (enemyEnterIndex % 40 == 0) { // 10*40 = 400毫秒走一次，每隔400毫秒生成一个敌人
			FlyingObject one = nextOne(); // 生成了一个敌人（小敌机 + 小蜜蜂）
			enemyflyings = Arrays.copyOf(enemyflyings, enemyflyings.length + 1); // 数组的扩容
			enemyflyings[enemyflyings.length - 1] = one;			//将产生的敌人放到数组的最后一个位置
		}

		// 0,40,80,120,160,...
	}
	
	/** 飞行物走步  */
	public void stepAcion(){	//每隔10毫秒就调用一次
		hero.step();			//每隔100ms，英雄机图片就切换一次
		
		//敌人（小敌机 + 小蜜蜂）走步
		for(int i=0;i<enemyflyings.length;i++){
			FlyingObject f = enemyflyings[i];//取敌人数组中的每个敌人
			f.step();						//敌人（小敌机+小蜜蜂）
		}
		
		//子弹走步
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];//取子弹数组中的每颗子弹
			b.step();						//子弹走步
		}
	}
	
	int bulletIndex = 0; 	//子弹入场计数器
	/** 子弹入场 */
	public void bulletAction(){		//每隔10ms执行一次
		bulletIndex++;
		if(bulletIndex % 30 == 0){		//每隔300ms发射一次
			Bullet [] b = hero.shoot();		//产生子弹（1颗或2颗）
			//把bullets数组扩容
			this.bullets = Arrays.copyOf(bullets, bullets.length + b.length);
			//System.out.println(bullets.length);
			/*这个代码的扩展性不好，在增加子弹时需要更改代码
			if(b.length == 1){		//只生产1颗子弹
				bullets[bullets.length-1] = b[0];
				//bullets[0] = b[0];	//代码错误，原因是bullets数组的第三个元素以后的元素没有装对象
			}else{					//只产生2颗子弹
				bullets[bullets.length-1] = b[0];
				bullets[bullets.length-2] = b[0];
//				bullets[0] = b[0];
//				bullets[1] = b[1];
			}
			*/
		//扩展性好，无论生成多少颗子弹，1,2,3，...都不需要更改代码
		System.arraycopy(b, 0, bullets, bullets.length - b.length, b.length);
		//bullets.length - b.length:表示扩容后的数组长度减去复制的数组长度，即可得到复制所需要的位置
		//System.out.println(bullets.length);
		}
	}
	/** 删除越界的对象 （小敌机、小蜜蜂、子弹）*/
	public void outOfBoundsAction() {		//每隔10ms走一次
		/*
		 * 遍历enemyFlyings数组，判断每个敌人时候越界，如果敌人越界，则从数组中删除它（方法1,交换，缩容，2）
		 * 遍历bullets数组，判断每个子弹时候越界，如果子弹越界，则从数组中删除它（方法1,2）
		 */
		//方法1
		/*
		for(int i = 0;i < enemyflyings.length;i++){		//遍历敌机数组
			FlyingObject f = enemyflyings[i];			//获取每一个敌人
			if(f.outOfBounds()){					//敌人越界了
				//交换第i个元素与最后一个元素
				FlyingObject temp = enemyflyings[i];
				enemyflyings[i] = enemyflyings[enemyflyings.length - 1];
				enemyflyings[enemyflyings.length - 1] = temp;
				//缩容
				enemyflyings = Arrays.copyOf(enemyflyings, 
						enemyflyings.length - 1);
			}
		}
		
		for(int i = 0;i < bullets.length;i++){		//遍历子弹数组
			FlyingObject s = bullets[i];			//获取每颗子弹
			if(s.outOfBounds()){				//子弹越界了
				//交换第i个元素与最后一个元素
				Bullet temp = bullets[i];
				bullets[i] = bullets[bullets.length - 1];
				bullets[bullets.length -1] =  temp;
				//缩容
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
			}
		}
		*/
		//方法2
		int index = 0;		//1.作为liveEnemyflyings下标的计数器。2.记录了未越界对象的个数
		FlyingObject [] liveEnemyflyings = new FlyingObject[enemyflyings.length];//保留未越界的敌人
		for(int i = 0;i < enemyflyings.length;i++){		//遍历敌机数组
			FlyingObject f = enemyflyings[i];			//获取每一个敌人
			if(!f.outOfBounds()){					//敌人未越界
				liveEnemyflyings[index] = f;
				index++;
			}
		}
		enemyflyings = Arrays.copyOf(liveEnemyflyings,index);
		
		index = 0;		//1.作为liveBullets下标的计数器。2.记录了未越界对象的个数
		Bullet [] liveBullets = new Bullet[bullets.length];//保留未越界的敌人
		for(int i = 0;i < bullets.length;i++){		//遍历敌机数组
			Bullet b = bullets[i];			//获取每一个敌人
			if(!b.outOfBounds()){					//敌人未越界
				liveBullets[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(liveBullets,index);
	}
	
	public int score = 0;
	
	/** 敌人被子弹击中*/
	public void bangAction(){		//定时发生，每隔10ms走一次
		/*
		 * 遍历敌人数组
		 * 获取每一个敌人
		 * 遍历子弹数组
		 * 获取子弹数组
		 * 判断敌人是否被子弹击中
		 *   判断子弹打中的是小敌机：玩家得5分，且小敌机消失
		 *   判断子弹打中的是小蜜蜂：英雄机可能的命，也可能得双倍火力值40，且小蜜蜂消失
		 *  
		 */
		for(int j = 0;j<bullets.length;j++){	//遍历子弹数组
			int i = -1;				//接收被撞子弹下标
			Bullet b = bullets[j];		//获取子弹
			i=bang(b);
			if(i>-1){

				Bullet t = bullets[j];
				bullets[j] = bullets[bullets.length - 1];
				bullets[bullets.length - 1] = t;
				
				//将敌人数组缩容
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
				break;

			}
				
		}
	}
	
	/** 一个敌人被多个子弹击中*/
	public int bang(Bullet b){
		int index = -1;							//存储被撞敌人下标
		int count = -1;							//存储被撞子弹下标
		for(int i = 0; i<enemyflyings.length;i++){	//遍历敌人数组
			FlyingObject obj = enemyflyings[i];
			
			if(obj.shootBy(b)){
				count = 0;
				index = i;
				break;
				}
			}
		
			if(index != -1){			//被击中
				FlyingObject enemy = enemyflyings[index];
				//如果enemy是小敌机
				if(enemy instanceof Enemy){			//为什么是Enemy？而不是Airplane？
					Enemy e = (Enemy)enemy;
					score += e.getScore();		//击中一个小敌机得5分
				}
				//如果enemy是小蜜蜂
				if(enemy instanceof Award){			//为什么是Award？而不是Bee？
					Award a = (Award)enemy;
					int type = a.getType();
					
					switch(type){
					case Award.DOUBLE_FIRE:
						//奖励火力值
						hero.addDoubleFire();
						break;
					case Award.LIFE:
						//奖励命
						hero.addLife();
						break;
					}
				}

			//让子弹击中的敌人从屏幕上消失
			//将敌人数组中的index与最后一个元素交换
			FlyingObject temp = enemyflyings[index];
			enemyflyings[index] = enemyflyings[enemyflyings.length - 1];
			enemyflyings[enemyflyings.length - 1] = temp;
			
			//将敌人数组缩容
			enemyflyings = Arrays.copyOf(enemyflyings, enemyflyings.length - 1);
			
		}
			return count;
	}
	/** 检查游戏是否结束*/
	public void chechGameOverAction(){
		if(isGameOver()){		//游戏结束
			state = GAME_OVER;
		}		
	}

	/** 判断游戏结束方法*/
	public boolean isGameOver(){
		//遍历敌人数组
		for(int i = 0; i<this.enemyflyings.length;i++){
			FlyingObject enemy = enemyflyings[i];
			if(hero.hit(enemy)){			//英雄机和敌机撞上
				hero.substractLife();		//英雄机减命
				hero.clearDoubleFire();		//英雄机的火力值清零
				//删除敌人
				//让子弹击中的敌人从屏幕上消失
				//将敌人数组中的index与最后一个元素交换
				FlyingObject temp = enemyflyings[i];
				enemyflyings[i] = enemyflyings[enemyflyings.length - 1];
				enemyflyings[enemyflyings.length - 1] = temp;
				
				//将敌人数组缩容
				enemyflyings = Arrays.copyOf(enemyflyings, enemyflyings.length - 1);
			}
			
		}
		//当英雄机的命小于0时，游戏结束
		return hero.getLife() <= 0;
	}
	/** 启动程序的执行方法 */
	public void action() {
		
		MouseAdapter l =new MouseAdapter(){
			//重写MouseListener所有抽象方法（5个） （3个）
			 public void mouseClicked(MouseEvent e) {
				 if(state == START){
					 state = RUNNING;
				 }
				 if(state == GAME_OVER){
					 hero = new Hero();
					 enemyflyings = new FlyingObject[0];
					 bullets = new Bullet[0];
					 score = 0;
					 state = START;
				 }
			 }
			 public void mouseEntered(MouseEvent e) {// 鼠标进入
				 if(state == PAUSE){	// 暂停状态下运行
					 state = RUNNING;
				 }
			 }
			 public void mouseExited(MouseEvent e) { // 鼠标退出
				 if (state != GAME_OVER) { // 游戏未结束，则设置其为暂停
						state = PAUSE;
					}
			 }
		};
		
		MouseAdapter ll =new MouseAdapter(){
			//重写（2个）（1个）
			public void mouseMoved(MouseEvent e){
				if(state == RUNNING){	//鼠标移动
					int x = e.getX();	//获得鼠标的x坐标
					int y = e.getY();	//获得鼠标的y坐标
					hero.moved(x,y);
				}
				
			}
		};
		
		this.addMouseListener(l);			//安装侦听器
		this.addMouseMotionListener(ll);	//安装侦听器

		
		Timer timer = new Timer(); // 创建一个定时器对象
		int intervel = 10; // 时间间隔，10毫秒

		
		timer.schedule(new TimerTask() { // 匿名内部类的运用
					public void run() { // 每隔10毫秒运行一次
						if(state == RUNNING){
						// System.out.println("产生敌人");
						enemyEnterAction(); // 敌人入场
						bulletAction();		//子弹入场
						stepAcion();		//飞行物走步
						//System.out.println("越界处理前，敌人个数"+enemyflyings.length);
						outOfBoundsAction();//删除越界对象(小敌机、小蜜蜂、子弹)
						bangAction();//敌人被子弹击中
						//System.out.println("越界处理后，敌人个数"+enemyflyings.length);
						chechGameOverAction();//检查游戏是否结束
						}
						repaint();			//重画，让系统调用paint();
					}

				}, intervel, intervel);
	}

	/** 重写paint() */
	public void paint(Graphics g) {
		// System.out.println("系统自动调用paint()");
		// 画背景图
		g.drawImage(ShootGame.background, 0, 0, null);
		// 画小敌机 + 画小蜜蜂
		paintEnemyFlyings(g);
		// 画子弹
		paintBullets(g);
		// 画英雄机
		paintHero(g);
		//画分和画命
		paintScoreAndLife(g);
		//画状态
		paintState(g);
	}

	/** 画敌人 */
	public void paintEnemyFlyings(Graphics g) {
		for (int i = 0; i < enemyflyings.length; i++) { // 遍历敌人（小敌机+小蜜蜂）数组
			FlyingObject f = enemyflyings[i]; // 获取每一个敌人
			g.drawImage(f.image, f.getX(), f.getY(), null); // 画每一个敌人
		}
	}

	/** 画子弹 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) { // 遍历子弹数组
			Bullet b = bullets[i]; // 获取每一颗子弹
			g.drawImage(b.image, b.getX(), b.getY(), null); // 画每一颗子弹
		}
	}

	/** 画英雄机 */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.getX(), hero.getY(), null);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("飞机大战"); // 创建窗口框架
		ShootGame game = new ShootGame(); // 创建游戏面板
		frame.add(game); // 将游戏面板加入到窗口框架中
		frame.setSize(WIDTH, HEIGHT); // 设置窗口的宽和高
		frame.setAlwaysOnTop(true); // 设置窗口总是在最顶端
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置单击关闭按钮退出程序
		frame.setLocationRelativeTo(null); // 设置窗口居中显示
		frame.setVisible(true); // 1、设置窗口可见；2、让系统快速地调用paint()。

		game.action(); // 为了精简main方法，启动程序的执行方法
	}
	/** 画分和画命*/
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 14));
		g.drawString("SCORE: " + score, 10, 24);
		g.drawString("LIFE: " + hero.getLife(), 10, 45);
	}
	
	/** 画状态*/
	public void paintState(Graphics g){
		switch(state){
		case ShootGame.START:
			g.drawImage(ShootGame.start,0,0,null);
			break;
		case ShootGame.PAUSE:
			g.drawImage(ShootGame.pause,0,0,null);
			break;
		case ShootGame.GAME_OVER:
			g.drawImage(ShootGame.gameover,0,0,null);
			break;
		}
		
		
		
	}

}
