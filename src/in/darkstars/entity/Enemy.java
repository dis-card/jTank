package in.darkstars.entity;

import in.darkstars.helper.Helper;
import in.darkstars.main.JTank;
import in.darkstars.main.JTank.Direction;

import java.awt.Rectangle;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author dis-card
 * 
 *         Jul 11, 2014
 * 
 */
public class Enemy {

	private static final int HOUDINI_LIFE = 3;
	private static final int SHARP_SHOOTER_LIFE = 5;
	private static final int TANK_LIFE = 10;
	private float speed = 1;
	private int posX;
	private int posY;
	private Image up, down, left, right, enemy;
	private int health;
	private TMap map;
	private TankType tankType;
	public static enum TankType {Cobra,SharpShooter,Houdini,BlackMamba};

	private Direction direc;
	private Random random;

	public Enemy(int posX, int posY, TMap map) throws SlickException {

		this.posX = posX;
		this.posY = posY;
		this.map = map;
		this.direc = Direction.DOWN;
		this.random = new Random();
		switch (random.nextInt(4)) {
		case 0:
			this.up = new Image("resources/images/upTank.jpg");
			this.down = new Image("resources/images/downTank.jpg");
			this.left = new Image("resources/images/leftTank.jpg");
			this.right = new Image("resources/images/rightTank.jpg");
			health = TANK_LIFE;
			tankType = TankType.Cobra;
			break;
		case 1:
			this.up = new Image("resources/images/upSharpShooter.png");
			this.down = new Image("resources/images/downSharpShooter.png");
			this.left = new Image("resources/images/leftSharpShooter.png");
			this.right = new Image("resources/images/rightSharpShooter.png");
			speed = 2;
			health = SHARP_SHOOTER_LIFE;
			tankType = TankType.SharpShooter;
			break;
		case 2:
		case 3:
			this.up = new Image("resources/images/upHoudini.png");
			this.down = new Image("resources/images/downHoudini.png");
			this.left = new Image("resources/images/leftHoudini.png");
			this.right = new Image("resources/images/rightHoudini.png");
			speed = 4;
			health = HOUDINI_LIFE;
			tankType = TankType.Houdini;
			break;
		}
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
				JTank.SIZE, JTank.SIZE);
		if (playerBounds.intersects(bulletBounds)) {
			isHit = true;
		}
		return isHit;
	}
}
