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

/** �����Ϸ�������࣬���ڲ��� */
public class ShootGame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400; // ���ڿ�
	public static final int HEIGHT = 654; // ���ڸ�

	public static BufferedImage background; // ����ͼ
	public static BufferedImage start; // ��ʼͼ
	public static BufferedImage pause; // ��ͣͼ
	public static BufferedImage gameover; // ��Ϸ����ͼ
	public static BufferedImage airplane; // �л�
	public static BufferedImage bee; // С�۷�
	public static BufferedImage bullet; // �ӵ�
	public static BufferedImage hero0; // Ӣ�ۻ�0
	public static BufferedImage hero1; // Ӣ�ۻ�1
	
	//��Ϸ�ĵ�ǰ״̬:һ�����֣����У���ͣ����������ʼ
	private int state= 0;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;

	// ��̬����飬����ͼƬ����ʼ����̬��Դ��ͼƬ�������ȣ�
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

	// �������� 1��Ӣ�ۻ�������ӵ���������ˣ�С�л���С�۷䣩
	Hero hero = new Hero(); // ����Ӣ�ۻ�����
	Bullet[] bullets = {}; // �����ӵ�����
	FlyingObject[] enemyflyings = {}; // ������������(С�л� + С�۷�)

	/*
	 * // �ù��췽������ʼ�������Ա��Ϊ��������ϻ��������ڲ��� public ShootGame() { bullets = new
	 * Bullet[1]; bullets[0] = new Bullet(100, 200); enemyflyings = new
	 * FlyingObject[2]; enemyflyings[0] = new Airplane(); enemyflyings[1] = new
	 * Bee(); }
	 */
	/** �����������(С�л� + С�۷�) */
	public FlyingObject nextOne() {
		Random rand = new Random();
		int x = rand.nextInt(20); // ����һ��0-19֮��������������0��19��
		if (x < 4) { // �����С��4�Ļ�������һֻС�۷�
			return new Bee();
		} else { // 4=<�����<=19�Ļ�������һ��С�л�
			return new Airplane();
		}
	}

	int enemyEnterIndex = 0; // ������

	/** �����볡 */
	public void enemyEnterAction() { // 10����͵���һ��
		enemyEnterIndex++;
		if (enemyEnterIndex % 40 == 0) { // 10*40 = 400������һ�Σ�ÿ��400��������һ������
			FlyingObject one = nextOne(); // ������һ�����ˣ�С�л� + С�۷䣩
			enemyflyings = Arrays.copyOf(enemyflyings, enemyflyings.length + 1); // ���������
			enemyflyings[enemyflyings.length - 1] = one;			//�������ĵ��˷ŵ���������һ��λ��
		}

		// 0,40,80,120,160,...
	}
	
	/** �������߲�  */
	public void stepAcion(){	//ÿ��10����͵���һ��
		hero.step();			//ÿ��100ms��Ӣ�ۻ�ͼƬ���л�һ��
		
		//���ˣ�С�л� + С�۷䣩�߲�
		for(int i=0;i<enemyflyings.length;i++){
			FlyingObject f = enemyflyings[i];//ȡ���������е�ÿ������
			f.step();						//���ˣ�С�л�+С�۷䣩
		}
		
		//�ӵ��߲�
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];//ȡ�ӵ������е�ÿ���ӵ�
			b.step();						//�ӵ��߲�
		}
	}
	
	int bulletIndex = 0; 	//�ӵ��볡������
	/** �ӵ��볡 */
	public void bulletAction(){		//ÿ��10msִ��һ��
		bulletIndex++;
		if(bulletIndex % 30 == 0){		//ÿ��300ms����һ��
			Bullet [] b = hero.shoot();		//�����ӵ���1�Ż�2�ţ�
			//��bullets��������
			this.bullets = Arrays.copyOf(bullets, bullets.length + b.length);
			//System.out.println(bullets.length);
			/*����������չ�Բ��ã��������ӵ�ʱ��Ҫ���Ĵ���
			if(b.length == 1){		//ֻ����1���ӵ�
				bullets[bullets.length-1] = b[0];
				//bullets[0] = b[0];	//�������ԭ����bullets����ĵ�����Ԫ���Ժ��Ԫ��û��װ����
			}else{					//ֻ����2���ӵ�
				bullets[bullets.length-1] = b[0];
				bullets[bullets.length-2] = b[0];
//				bullets[0] = b[0];
//				bullets[1] = b[1];
			}
			*/
		//��չ�Ժã��������ɶ��ٿ��ӵ���1,2,3��...������Ҫ���Ĵ���
		System.arraycopy(b, 0, bullets, bullets.length - b.length, b.length);
		//bullets.length - b.length:��ʾ���ݺ�����鳤�ȼ�ȥ���Ƶ����鳤�ȣ����ɵõ���������Ҫ��λ��
		//System.out.println(bullets.length);
		}
	}
	/** ɾ��Խ��Ķ��� ��С�л���С�۷䡢�ӵ���*/
	public void outOfBoundsAction() {		//ÿ��10ms��һ��
		/*
		 * ����enemyFlyings���飬�ж�ÿ������ʱ��Խ�磬�������Խ�磬���������ɾ����������1,���������ݣ�2��
		 * ����bullets���飬�ж�ÿ���ӵ�ʱ��Խ�磬����ӵ�Խ�磬���������ɾ����������1,2��
		 */
		//����1
		/*
		for(int i = 0;i < enemyflyings.length;i++){		//�����л�����
			FlyingObject f = enemyflyings[i];			//��ȡÿһ������
			if(f.outOfBounds()){					//����Խ����
				//������i��Ԫ�������һ��Ԫ��
				FlyingObject temp = enemyflyings[i];
				enemyflyings[i] = enemyflyings[enemyflyings.length - 1];
				enemyflyings[enemyflyings.length - 1] = temp;
				//����
				enemyflyings = Arrays.copyOf(enemyflyings, 
						enemyflyings.length - 1);
			}
		}
		
		for(int i = 0;i < bullets.length;i++){		//�����ӵ�����
			FlyingObject s = bullets[i];			//��ȡÿ���ӵ�
			if(s.outOfBounds()){				//�ӵ�Խ����
				//������i��Ԫ�������һ��Ԫ��
				Bullet temp = bullets[i];
				bullets[i] = bullets[bullets.length - 1];
				bullets[bullets.length -1] =  temp;
				//����
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
			}
		}
		*/
		//����2
		int index = 0;		//1.��ΪliveEnemyflyings�±�ļ�������2.��¼��δԽ�����ĸ���
		FlyingObject [] liveEnemyflyings = new FlyingObject[enemyflyings.length];//����δԽ��ĵ���
		for(int i = 0;i < enemyflyings.length;i++){		//�����л�����
			FlyingObject f = enemyflyings[i];			//��ȡÿһ������
			if(!f.outOfBounds()){					//����δԽ��
				liveEnemyflyings[index] = f;
				index++;
			}
		}
		enemyflyings = Arrays.copyOf(liveEnemyflyings,index);
		
		index = 0;		//1.��ΪliveBullets�±�ļ�������2.��¼��δԽ�����ĸ���
		Bullet [] liveBullets = new Bullet[bullets.length];//����δԽ��ĵ���
		for(int i = 0;i < bullets.length;i++){		//�����л�����
			Bullet b = bullets[i];			//��ȡÿһ������
			if(!b.outOfBounds()){					//����δԽ��
				liveBullets[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(liveBullets,index);
	}
	
	public int score = 0;
	
	/** ���˱��ӵ�����*/
	public void bangAction(){		//��ʱ������ÿ��10ms��һ��
		/*
		 * ������������
		 * ��ȡÿһ������
		 * �����ӵ�����
		 * ��ȡ�ӵ�����
		 * �жϵ����Ƿ��ӵ�����
		 *   �ж��ӵ����е���С�л�����ҵ�5�֣���С�л���ʧ
		 *   �ж��ӵ����е���С�۷䣺Ӣ�ۻ����ܵ�����Ҳ���ܵ�˫������ֵ40����С�۷���ʧ
		 *  
		 */
		for(int j = 0;j<bullets.length;j++){	//�����ӵ�����
			int i = -1;				//���ձ�ײ�ӵ��±�
			Bullet b = bullets[j];		//��ȡ�ӵ�
			i=bang(b);
			if(i>-1){

				Bullet t = bullets[j];
				bullets[j] = bullets[bullets.length - 1];
				bullets[bullets.length - 1] = t;
				
				//��������������
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
				break;

			}
				
		}
	}
	
	/** һ�����˱�����ӵ�����*/
	public int bang(Bullet b){
		int index = -1;							//�洢��ײ�����±�
		int count = -1;							//�洢��ײ�ӵ��±�
		for(int i = 0; i<enemyflyings.length;i++){	//������������
			FlyingObject obj = enemyflyings[i];
			
			if(obj.shootBy(b)){
				count = 0;
				index = i;
				break;
				}
			}
		
			if(index != -1){			//������
				FlyingObject enemy = enemyflyings[index];
				//���enemy��С�л�
				if(enemy instanceof Enemy){			//Ϊʲô��Enemy��������Airplane��
					Enemy e = (Enemy)enemy;
					score += e.getScore();		//����һ��С�л���5��
				}
				//���enemy��С�۷�
				if(enemy instanceof Award){			//Ϊʲô��Award��������Bee��
					Award a = (Award)enemy;
					int type = a.getType();
					
					switch(type){
					case Award.DOUBLE_FIRE:
						//��������ֵ
						hero.addDoubleFire();
						break;
					case Award.LIFE:
						//������
						hero.addLife();
						break;
					}
				}

			//���ӵ����еĵ��˴���Ļ����ʧ
			//�����������е�index�����һ��Ԫ�ؽ���
			FlyingObject temp = enemyflyings[index];
			enemyflyings[index] = enemyflyings[enemyflyings.length - 1];
			enemyflyings[enemyflyings.length - 1] = temp;
			
			//��������������
			enemyflyings = Arrays.copyOf(enemyflyings, enemyflyings.length - 1);
			
		}
			return count;
	}
	/** �����Ϸ�Ƿ����*/
	public void chechGameOverAction(){
		if(isGameOver()){		//��Ϸ����
			state = GAME_OVER;
		}		
	}

	/** �ж���Ϸ��������*/
	public boolean isGameOver(){
		//������������
		for(int i = 0; i<this.enemyflyings.length;i++){
			FlyingObject enemy = enemyflyings[i];
			if(hero.hit(enemy)){			//Ӣ�ۻ��͵л�ײ��
				hero.substractLife();		//Ӣ�ۻ�����
				hero.clearDoubleFire();		//Ӣ�ۻ��Ļ���ֵ����
				//ɾ������
				//���ӵ����еĵ��˴���Ļ����ʧ
				//�����������е�index�����һ��Ԫ�ؽ���
				FlyingObject temp = enemyflyings[i];
				enemyflyings[i] = enemyflyings[enemyflyings.length - 1];
				enemyflyings[enemyflyings.length - 1] = temp;
				
				//��������������
				enemyflyings = Arrays.copyOf(enemyflyings, enemyflyings.length - 1);
			}
			
		}
		//��Ӣ�ۻ�����С��0ʱ����Ϸ����
		return hero.getLife() <= 0;
	}
	/** ���������ִ�з��� */
	public void action() {
		
		MouseAdapter l =new MouseAdapter(){
			//��дMouseListener���г��󷽷���5���� ��3����
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
			 public void mouseEntered(MouseEvent e) {// ������
				 if(state == PAUSE){	// ��ͣ״̬������
					 state = RUNNING;
				 }
			 }
			 public void mouseExited(MouseEvent e) { // ����˳�
				 if (state != GAME_OVER) { // ��Ϸδ��������������Ϊ��ͣ
						state = PAUSE;
					}
			 }
		};
		
		MouseAdapter ll =new MouseAdapter(){
			//��д��2������1����
			public void mouseMoved(MouseEvent e){
				if(state == RUNNING){	//����ƶ�
					int x = e.getX();	//�������x����
					int y = e.getY();	//�������y����
					hero.moved(x,y);
				}
				
			}
		};
		
		this.addMouseListener(l);			//��װ������
		this.addMouseMotionListener(ll);	//��װ������

		
		Timer timer = new Timer(); // ����һ����ʱ������
		int intervel = 10; // ʱ������10����

		
		timer.schedule(new TimerTask() { // �����ڲ��������
					public void run() { // ÿ��10��������һ��
						if(state == RUNNING){
						// System.out.println("��������");
						enemyEnterAction(); // �����볡
						bulletAction();		//�ӵ��볡
						stepAcion();		//�������߲�
						//System.out.println("Խ�紦��ǰ�����˸���"+enemyflyings.length);
						outOfBoundsAction();//ɾ��Խ�����(С�л���С�۷䡢�ӵ�)
						bangAction();//���˱��ӵ�����
						//System.out.println("Խ�紦��󣬵��˸���"+enemyflyings.length);
						chechGameOverAction();//�����Ϸ�Ƿ����
						}
						repaint();			//�ػ�����ϵͳ����paint();
					}

				}, intervel, intervel);
	}

	/** ��дpaint() */
	public void paint(Graphics g) {
		// System.out.println("ϵͳ�Զ�����paint()");
		// ������ͼ
		g.drawImage(ShootGame.background, 0, 0, null);
		// ��С�л� + ��С�۷�
		paintEnemyFlyings(g);
		// ���ӵ�
		paintBullets(g);
		// ��Ӣ�ۻ�
		paintHero(g);
		//���ֺͻ���
		paintScoreAndLife(g);
		//��״̬
		paintState(g);
	}

	/** ������ */
	public void paintEnemyFlyings(Graphics g) {
		for (int i = 0; i < enemyflyings.length; i++) { // �������ˣ�С�л�+С�۷䣩����
			FlyingObject f = enemyflyings[i]; // ��ȡÿһ������
			g.drawImage(f.image, f.getX(), f.getY(), null); // ��ÿһ������
		}
	}

	/** ���ӵ� */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) { // �����ӵ�����
			Bullet b = bullets[i]; // ��ȡÿһ���ӵ�
			g.drawImage(b.image, b.getX(), b.getY(), null); // ��ÿһ���ӵ�
		}
	}

	/** ��Ӣ�ۻ� */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.getX(), hero.getY(), null);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("�ɻ���ս"); // �������ڿ��
		ShootGame game = new ShootGame(); // ������Ϸ���
		frame.add(game); // ����Ϸ�����뵽���ڿ����
		frame.setSize(WIDTH, HEIGHT); // ���ô��ڵĿ�͸�
		frame.setAlwaysOnTop(true); // ���ô������������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���õ����رհ�ť�˳�����
		frame.setLocationRelativeTo(null); // ���ô��ھ�����ʾ
		frame.setVisible(true); // 1�����ô��ڿɼ���2����ϵͳ���ٵص���paint()��

		game.action(); // Ϊ�˾���main���������������ִ�з���
	}
	/** ���ֺͻ���*/
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 14));
		g.drawString("SCORE: " + score, 10, 24);
		g.drawString("LIFE: " + hero.getLife(), 10, 45);
	}
	
	/** ��״̬*/
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
