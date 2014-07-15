package in.darkstars.entity;

import static in.darkstars.helper.Helper.ANIMATION_DELAY;
import in.darkstars.helper.SpriteSheetFactory;
import in.darkstars.main.JTank;
import in.darkstars.main.JTank.Direction;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author dis-card
 * 
 *         Jul 13, 2014
 * 
 */
public class Player {

	private static final int SIZE = 16;
	private static final int HEIGHT = 16;
	private static final int WIDTH = 16;
	private static final float SPEED = 50.0f;
	private static final int BULLET_MAX = 5;
	private Animation up, down, left, right, player;
	private float posY;
	private float posX;
	private Direction direction;
	private long score;
	private TMap map;
	private Animation spawn;
	private int health = 10;
	private int timeElapsed;
	private int shieldTime = 5000;
	private Bullet bulletList[];

	public Player(float posX, float posY, TMap map) throws SlickException {

		SpriteSheet jTankSpriteSheet = SpriteSheetFactory.getSpriteSheet();
		this.up = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(0, 0),
				jTankSpriteSheet.getSubImage(1, 0) }, ANIMATION_DELAY, false);
		this.left = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(2, 0),
				jTankSpriteSheet.getSubImage(3, 0) }, ANIMATION_DELAY, false);
		this.down = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(4, 0),
				jTankSpriteSheet.getSubImage(5, 0) }, ANIMATION_DELAY, false);
		this.right = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(6, 0),
				jTankSpriteSheet.getSubImage(7, 0) }, ANIMATION_DELAY, false);

		player = this.up;
		direction = Direction.UP;
		this.posX = posX;
		this.posY = posY;
		spawn = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(16, 6),
				jTankSpriteSheet.getSubImage(17, 6),
				jTankSpriteSheet.getSubImage(18, 6),
				jTankSpriteSheet.getSubImage(19, 6),
				jTankSpriteSheet.getSubImage(19, 6),
				jTankSpriteSheet.getSubImage(18, 6),
				jTankSpriteSheet.getSubImage(17, 6),
				jTankSpriteSheet.getSubImage(16, 6) }, ANIMATION_DELAY);
		spawn.setLooping(false);
		spawn.draw(posX, posY);
		this.map = map;
		bulletList = new Bullet[BULLET_MAX];
	}

	public void changeDirectionTo(Direction direc) {
		switch (direc) {
		case UP:
			player = up;
			direction = Direction.UP;
			break;
		case DOWN:
			player = down;
			direction = Direction.DOWN;
			break;
		case LEFT:
			player = left;
			direction = Direction.LEFT;
			break;
		case RIGHT:
			player = right;
			direction = Direction.RIGHT;
			break;
		}
	}

	public void update(int delta) {
		switch (direction) {
		case UP:
			if (!isBlocked(posX, posY - (delta /1000.0f) * SPEED)) {
				posY -=  (delta /1000.0f) * SPEED;
				player.update(delta);
			}
			break;
		case DOWN:
			if (!isBlocked(posX, posY + (delta /1000.0f) * SPEED)) {
				posY +=  (delta /1000.0f) * SPEED;
				player.update(delta);
			}
			break;
		case LEFT:
			if (!isBlocked(posX - (delta /1000.0f) * SPEED, posY)) {
				posX -=  (delta /1000.0f) * SPEED;
				player.update(delta);
			}
			break;
		case RIGHT:
			if (!isBlocked(posX + (delta /1000.0f) *  SPEED, posY)) {
				posX +=  (delta /1000.0f) * SPEED;
				player.update(delta);
			}
			break;
		}

	}

	public void fire() throws SlickException {
		for (int i = 0; i < bulletList.length; i++) {
			if (bulletList[i] == null) {
				switch (direction) {
				case UP:
					bulletList[i] = new Bullet(posX + SIZE / 2, posY, direction);
					bulletList[i].makeFiredSound();
					break;
				case DOWN:
					bulletList[i] = new Bullet(posX + SIZE / 2, posY + SIZE,
							direction);
					bulletList[i].makeFiredSound();
					break;
				case RIGHT:
					bulletList[i] = new Bullet(posX + SIZE, posY + SIZE / 2,
							direction);
					bulletList[i].makeFiredSound();
					break;
				case LEFT:
					bulletList[i] = new Bullet(posX, posY + SIZE / 2, direction);
					bulletList[i].makeFiredSound();
					break;
				}
					break;
			}
		}
	}

	private boolean isBlocked(float x, float y) {
		boolean inCollision = false;

		Rectangle2D.Float playerBounds = new Rectangle2D.Float( x, y, (float)SIZE, (float)SIZE);
		if (x > (JTank.WIDTH - SIZE) || x < 0 || y > (JTank.HEIGHT - SIZE) || y < 0) {
			inCollision = true;
		} else {
			if (map.inCollision(playerBounds))
				inCollision = true;

		}
		return inCollision;
	}

	public void render() {
		if (!spawn.isStopped()) {
			spawn.draw(posX, posY);
		} else if (this.health > 0) {
			switch (direction) {
			case UP:
				player = up;
				break;
			case DOWN:
				player = down;
				break;
			case LEFT:
				player = left;
				break;
			case RIGHT:
				player = right;
				break;
			}
			player.draw(posX, posY);
		}
	}
	
	public void updateBullets(long delta)
	{
		for (int i = 0; i < bulletList.length; i++) {
			Bullet bullet = bulletList[i];
			if (bullet != null) {
				if (bullet.isOutOfScreen()
						|| (bullet.isExploded() && bullet.getExplosion()
								.isStopped())) {
					bulletList[i] = null;
				} else if (map.inCollision(new Rectangle2D.Float(
						 bullet.getPosX(), bullet.getPosY(),
						Bullet.WIDTH, Bullet.HEIGHT))) {
//					bullet.makeHitSound();
					bullet.setExploded(true);
					bullet.getExplosion().update(delta);
				}else {
					bullet.update();
				}

			}
		}
	}

	/**
	 * @return the bulletList
	 */
	public Bullet[] getBulletList() {
		return bulletList;
	}

	/**
	 * @param bulletList the bulletList to set
	 */
	public void setBulletList(Bullet[] bulletList) {
		this.bulletList = bulletList;
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
	 * @return the score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(long score) {
		this.score = score;
	}
}
