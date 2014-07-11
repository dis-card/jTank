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

	private static final float SPEED = 1;
	private int posX;
	private int posY;
	private Image up, down, left, right, enemy;
	private TMap map;

	private Direction direc;
	private Random randomDirec;

	public Enemy(int posX, int posY, TMap map) throws SlickException {

		this.posX = posX;
		this.posY = posY;
		this.map = map;
		this.up = new Image("resources/upTank.jpg");
		this.down = new Image("resources/downTank.jpg");
		this.left = new Image("resources/leftTank.jpg");
		this.right = new Image("resources/rightTank.jpg");
		this.direc = Direction.DOWN;
		this.randomDirec = new Random();
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

	public void render() {
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
	}

	public void update() {
		switch (direc) {
		case UP:
			if (!inCollision(posX, posY - SPEED)) {
				posY -= SPEED;
			} else {
				changeDirection();
			}
			break;
		case DOWN:
			if (!inCollision(posX, posY + SPEED)) {
				posY += SPEED;
			} else {
				changeDirection();
			}
			break;
		case LEFT:
			if (!inCollision(posX - SPEED, posY)) {
				posX -= SPEED;
			} else {
				changeDirection();
			}
			break;
		case RIGHT:
			if (!inCollision(posX + SPEED, posY)) {
				posX += SPEED;
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
		
		int randomInt = randomDirec.nextInt(4);
		switch(randomInt)
		{
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

		/*if (direc == Direction.UP)
			direc = Direction.LEFT;
		else if (direc == Direction.LEFT)
			direc = Direction.DOWN;
		else if (direc == Direction.DOWN)
			direc = Direction.RIGHT;
		else if (direc == Direction.RIGHT)
			direc = Direction.UP;
	*/

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

}
