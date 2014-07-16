package in.darkstars.entity;

import in.darkstars.helper.SoundFactory;
import in.darkstars.helper.SoundFactory.SoundType;
import in.darkstars.helper.SpriteSheetFactory;
import in.darkstars.main.JTank;
import in.darkstars.main.JTank.*;
import in.darkstars.state.GamePlay;
import in.darkstars.state.GamePlay.Direction;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
	public static final int HEIGHT = 3;
	public static final int WIDTH = 3;
	private Direction bulletDirection;
	private Animation explosion;
	private boolean positionAdjusted;
	private Sound hitSound, firedSound;

	public Bullet(float x, float y, Direction tankDirection)
			throws SlickException {
		this.posX = x;
		this.posY = y;
		this.bulletDirection = tankDirection;
		this.velocity = 5;
		this.hitSound = SoundFactory.getSound(SoundType.BulletHit);
		this.firedSound = SoundFactory.getSound(SoundType.BulletFired);
		init();
	}

	private void init() throws SlickException {
		
		SpriteSheet jTankSpriteSheet = SpriteSheetFactory.getSpriteSheet();
		explosion = new Animation(new Image[] {jTankSpriteSheet.getSubImage(16, 8),jTankSpriteSheet.getSubImage(17, 8),jTankSpriteSheet.getSubImage(18, 8)}, 100);
		explosion.setLooping(false);
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
					posX -= GamePlay.SIZE / 2;
					break;
				case LEFT:
				case RIGHT:
					posY -= GamePlay.SIZE / 2;
					break;

				}
				positionAdjusted = true;
			}
			this.explosion.draw(posX, posY);
		}
	}
	
	public void makeFiredSound() throws SlickException
	{
		firedSound.play();
	}
	
	public void makeHitSound() throws SlickException
	{
		hitSound.play();
	}
	

	public boolean isOutOfScreen() {
		if (posX > JTank.WIDTH || posX < 0 || posY > JTank.HEIGHT || posY < 0)
			return true;
		else
			return false;
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



}
