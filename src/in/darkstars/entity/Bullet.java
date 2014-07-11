package in.darkstars.entity;

import java.awt.Rectangle;

import in.darkstars.main.JTank;
import in.darkstars.main.JTank.Direction;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author dis-card
 * 
 *         Jul 10, 2014
 * 
 */
public class Bullet {

	private float posX;
	private float posY;
	private float velocity;
	private boolean exploded;
	public static final int HEIGHT = 5;
	public static final int WIDTH = 5;
	private Direction bulletDirection;
	private Animation explosion;
	private boolean positionAdjusted;

	public Bullet(float x, float y, Direction tankDirection)
			throws SlickException {
		this.posX = x;
		this.posY = y;
		this.bulletDirection = tankDirection;
		this.velocity = 5;
		init();
	}

	private void init() throws SlickException {
		SpriteSheet explosionSheet = new SpriteSheet("resources/explosion.png",
				36, 38);
		explosion = new Animation(explosionSheet, 100);
		explosion.setLooping(false);
	}

	/**
	 * @return the exploded
	 */
	public boolean isExploded() {
		return exploded;
	}

	/**
	 * @param exploded
	 *            the exploded to set
	 */
	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}

	/**
	 * @return the explosion
	 */
	public Animation getExplosion() {
		return explosion;
	}

	/**
	 * @param explosion
	 *            the explosion to set
	 */
	public void setExplosion(Animation explosion) {
		this.explosion = explosion;
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
	public void setPosX(float posX) {
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
	public void setPosY(float posY) {
		this.posY = posY;
	}

	/**
	 * @return the velocity
	 */
	public float getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public void update() {
		if (!exploded) {
			
			switch (bulletDirection) {
			case UP:
				posY -= velocity;
				break;
			case DOWN:
				posY += velocity;
				break;
			case LEFT:
				posX -= velocity;
				break;
			case RIGHT:
				posX += velocity;
				break;
			}
	
		}

	}

	public void render(Graphics g) {

		if (!exploded) {
		
			g.drawOval(posX, posY, WIDTH, HEIGHT);
		} else {
			if (!positionAdjusted) {
				switch (bulletDirection) {
				case UP:
				case DOWN:
					posX -= JTank.SIZE / 2;
					positionAdjusted = true;
					break;
				case LEFT:
				case RIGHT:
					posY -= JTank.SIZE / 2;
					positionAdjusted = true;
					break;

				}
			}
			this.explosion.draw(posX, posY);
		}
	}

	public boolean isOutOfScreen() {
		if (posX > JTank.WIDTH || posX < 0 || posY > JTank.HEIGHT || posY < 0)
			return true;
		else
			return false;
	}

}
