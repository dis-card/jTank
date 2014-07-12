package in.darkstars.entity;

import in.darkstars.helper.Helper;
import in.darkstars.helper.SpriteSheetFactory;
import in.darkstars.main.JTank;
import in.darkstars.main.JTank.Direction;

import java.awt.Rectangle;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
/**
 * @author dis-card
 * 
 *         Jul 11, 2014
 * 
 */
public class Enemy {

	private static final int TANGO_LIFE = 3;
	private static final int ALPHA_LIFE = 4;
	private static final int MAMBA_LIFE = 5;
	private static final int PYTHON_LIFE = 6;
	private static final int SWEEPER_LIFE = 7;
	private static final int HOUDINI_LIFE = 8;
	private static final int SHARP_SHOOTER_LIFE = 9;
	private static final int COBRA_LIFE = 10;
	private float speed = 1;
	private int posX;
	private int posY;
	private Animation up, down, left, right, enemy;
	private int health;
	private TMap map;
	private CodeName codeName;
	public static enum CodeName {Tango, Alpha, Mamba, Python, Sweeper, Houdini, SharpShooter, Cobra};

	private Direction direc;
	private Random random;

	public Enemy(int posX, int posY, TMap map) throws SlickException {

		this.posX = posX;
		this.posY = posY;
		this.map = map;
		this.direc = Direction.DOWN;
		this.random = new Random();
		SpriteSheet jTankSpriteSheet = SpriteSheetFactory.getSpriteSheet();
		switch (random.nextInt(8)) {
		case 0:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 0), jTankSpriteSheet.getSubImage(9, 0)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 0), jTankSpriteSheet.getSubImage(11, 0)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 0), jTankSpriteSheet.getSubImage(13, 0)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 0), jTankSpriteSheet.getSubImage(15, 0)},100,false);			
			this.health = TANGO_LIFE;
			this.codeName = CodeName.Tango;
			break;
		case 1:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 1), jTankSpriteSheet.getSubImage(9, 1)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 1), jTankSpriteSheet.getSubImage(11, 1)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 1), jTankSpriteSheet.getSubImage(13, 1)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 1), jTankSpriteSheet.getSubImage(15, 1)},100,false);			
			this.speed = 1;
			this.health = ALPHA_LIFE;
			this.codeName = CodeName.Alpha;
			break;
		case 2:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 2), jTankSpriteSheet.getSubImage(9, 2)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 2), jTankSpriteSheet.getSubImage(11, 2)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 2), jTankSpriteSheet.getSubImage(13, 2)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 2), jTankSpriteSheet.getSubImage(15, 2)},100,false);			
			this.speed = 1;
			this.health = MAMBA_LIFE;
			this.codeName = CodeName.Mamba;
			break;
		case 3:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 3), jTankSpriteSheet.getSubImage(9, 3)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 3), jTankSpriteSheet.getSubImage(11, 3)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 3), jTankSpriteSheet.getSubImage(13, 3)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 3), jTankSpriteSheet.getSubImage(15, 3)},100,false);			
			this.speed = 2;
			this.health = PYTHON_LIFE;
			this.codeName = CodeName.Python;
			break;
		case 4:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 4), jTankSpriteSheet.getSubImage(9, 4)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 4), jTankSpriteSheet.getSubImage(11, 4)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 4), jTankSpriteSheet.getSubImage(13, 4)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 4), jTankSpriteSheet.getSubImage(15, 4)},100,false);			
			this.speed = 2;
			this.health = SWEEPER_LIFE;
			this.codeName = CodeName.Sweeper;					
			break;
		case 5:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 5), jTankSpriteSheet.getSubImage(9, 5)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 5), jTankSpriteSheet.getSubImage(11, 5)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 5), jTankSpriteSheet.getSubImage(13, 5)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 5), jTankSpriteSheet.getSubImage(15, 5)},100,false);			
			this.speed = 3;
			this.health = HOUDINI_LIFE;
			this.codeName = CodeName.Houdini;
			break;
		case 6:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 6), jTankSpriteSheet.getSubImage(9, 6)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 6), jTankSpriteSheet.getSubImage(11, 6)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 6), jTankSpriteSheet.getSubImage(13, 6)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 6), jTankSpriteSheet.getSubImage(15, 6)},100,false);			
			this.speed = 2;
			this.health = SHARP_SHOOTER_LIFE;
			this.codeName = CodeName.SharpShooter;
			break;
		case 7:
			this.up = new Animation(new Image[] {jTankSpriteSheet.getSubImage(8, 7), jTankSpriteSheet.getSubImage(9, 7)} , 100, false);
			this.left = new Animation(new Image[] {jTankSpriteSheet.getSubImage(10, 7), jTankSpriteSheet.getSubImage(11, 7)},100,false);
			this.down = new Animation(new Image[] {jTankSpriteSheet.getSubImage(12, 7), jTankSpriteSheet.getSubImage(13, 7)},100,false);
			this.right = new Animation(new Image[] {jTankSpriteSheet.getSubImage(14, 7), jTankSpriteSheet.getSubImage(15, 7)},100,false);			
			this.speed = 1;
			this.health = COBRA_LIFE;
			this.codeName = CodeName.Cobra;
			break;
		}
	}



	/**
	 * @return the direc
	 */
	public Direction getDirec() {
		return direc;
	}

	/**
	 * @param direc
	 *            the direc to set
	 */
	public void setDirec(Direction direc) {
		this.direc = direc;
	}

	/**
	 * @return the life
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setHealth(int life) {
		this.health = life;
	}	

	/**
	 * @return the posX
	 */
	public float getPosX() {
		return posX;
	}

	/**
	 * @param posX
	 *            the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	

	/**
	 * @return the posY
	 */
	public float getPosY() {
		return posY;
	}

	/**
	 * @param posY
	 *            the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	
	public void render() {
		if (this.health > 0) {
			switch (direc) {
			case UP:
				enemy = up;
				break;
			case DOWN:
				enemy = down;
				break;
			case LEFT:
				enemy = left;
				break;
			case RIGHT:
				enemy = right;
				break;
			}
			enemy.draw(posX, posY);
		} else {
			

		}
	}

	public void update() {
		switch (direc) {
		case UP:
			if (!inCollision(posX, posY - speed)) {
				posY -= speed;
			} else {
				changeDirection();
			}
			break;
		case DOWN:
			if (!inCollision(posX, posY + speed)) {
				posY += speed;
			} else {
				changeDirection();
			}
			break;
		case LEFT:
			if (!inCollision(posX - speed, posY)) {
				posX -= speed;
			} else {
				changeDirection();
			}
			break;
		case RIGHT:
			if (!inCollision(posX + speed, posY)) {
				posX += speed;
			} else {
				changeDirection();
			}
			break;
		}

	}

	/**
	 * 
	 */
	private void changeDirection() {

		int randomDirec = random.nextInt(4);
		switch (randomDirec) {
		case 0:
			direc = Direction.UP;
			break;
		case 1:
			direc = Direction.DOWN;
			break;
		case 2:
			direc = Direction.LEFT;
			break;
		case 3:
			direc = Direction.RIGHT;
			break;
		}
	}

	private boolean inCollision(float posX, float posY) {
		boolean collided = false;

		if (Helper.isOutOfScreen(posX, posY)) {
			collided = true;
		} else {
			for (Rectangle obstacle : map.getObstaclesList()) {
				if (obstacle.intersects(new Rectangle((int) posX, (int) posY,
						JTank.SIZE, JTank.SIZE))) {
					collided = true;
					break;
				}
			}
		}
		return collided;
	}

	public boolean isHit(float posX, float posY) {
		boolean isHit = false;
		Rectangle playerBounds = new Rectangle((int) this.posX,
				(int) this.posY, JTank.SIZE, JTank.SIZE);
		Rectangle bulletBounds = new Rectangle((int) posX, (int) posY,
				Bullet.WIDTH, Bullet.HEIGHT);
		if (playerBounds.intersects(bulletBounds)) {
			isHit = true;
		}
		return isHit;
	}
}
