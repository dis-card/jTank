package in.darkstars.entity;

import static in.darkstars.helper.Helper.ANIMATION_DELAY;
import in.darkstars.helper.Helper;
import in.darkstars.helper.SoundFactory;
import in.darkstars.helper.SoundFactory.SoundType;
import in.darkstars.helper.SpriteSheetFactory;
import in.darkstars.main.JTank;
import in.darkstars.state.GamePlay;
import in.darkstars.state.GamePlay.Direction;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 * @author dis-card
 * 
 *         Jul 11, 2014
 * 
 */
public abstract class Enemy {

	protected float speed = 20.0f;
	protected float posX;
	protected float posY;
	protected Animation up, down, left, right, enemy;
	private Animation spawn;
	private Animation shield;
	private double timeElapsed;
	private double shieldTime = 5000;
	private Sound destroyed;

	protected int health;
	private TMap map;
	protected CodeName codeName;

	public static enum CodeName {
		Tango, Alpha, Mamba, Python, Sweeper, Houdini, SharpShooter, Cobra
	};

	public static final float TANGO_SPEED = 1f, ALPHA_SPEED = 1f,
			MAMBA_SPEED = 1f, PYTHON_SPEED = 1f, SWEEPER_SPEED = 1f,
			HOUDINI_SPEED = 2f, SHARP_SHOOTER_SPEED = 1f, COBRA_SPEED = 1f;

	private Bullet[] bulletList;

	protected Direction direc;
	private Random random;

	public Enemy(int posX, int posY, TMap map) throws SlickException {

		this.posX = posX;
		this.posY = posY;
		this.map = map;
		this.destroyed = SoundFactory.getSound(SoundType.Explosion);
		bulletList = new Bullet[5];
		SpriteSheet jTankSpriteSheet = SpriteSheetFactory.getSpriteSheet();
		this.random = new Random();
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
		direc = Direction.DOWN;
		shield = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(16, 9),
				jTankSpriteSheet.getSubImage(16, 10) }, 1);
		shield.draw(posX, posY);

	}

	public void render() {
		if (!spawn.isStopped()) {
			spawn.draw(posX, posY);
		} else if (this.health > 0) {
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
			if (!(timeElapsed > shieldTime)) {
				shield.draw(posX, posY);
			}
		}
	}

	public void update(long delta) {

		timeElapsed += delta;
		if (spawn.isStopped()) {
			switch (direc) {
			case UP:
				if (!inCollision(posX, posY - (delta /1000.0f) *  speed)) {
					posY -= (delta /1000.0f) * speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			case DOWN:
				if (!inCollision(posX, posY + (delta /1000.0f) * speed)) {
					posY += (delta /1000.0f) *  speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			case LEFT:
				if (!inCollision(posX - (delta /1000.0f) * speed, posY)) {
					posX -= (delta /1000.0f) * speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			case RIGHT:
				if (!inCollision(posX + (delta /1000.0f) * speed, posY)) {
					posX += (delta /1000.0f) * speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			}

		}
	}

	public void makeDestroyedSound() {
		destroyed.play();
	}

	/**
	 * 
	 */
	protected void changeDirection() {

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

	protected boolean inCollision(float posX, float posY) {
		boolean collided = false;

		if (Helper.isOutOfScreen(posX, posY)) {
			collided = true;
		} else {
			for (Rectangle2D.Float obstacle : map.getObstaclesList()) {
				if (obstacle.intersects(new Rectangle2D.Float( posX, posY,
						GamePlay.SIZE, GamePlay.SIZE))) {
					collided = true;
					break;
				}
			}
		}
		return collided;
	}
	
	public void fire()
	{
		
	}

	public boolean isHit(float posX, float posY) {
		boolean isHit = false;
		Rectangle2D.Float playerBounds = new Rectangle2D.Float( this.posX,
				this.posY, GamePlay.SIZE, GamePlay.SIZE);
		Rectangle2D.Float bulletBounds = new Rectangle2D.Float( posX,  posY,
				Bullet.WIDTH, Bullet.HEIGHT);
		if (playerBounds.intersects(bulletBounds)) {
			isHit = true;
		}
		return isHit;
	}

	/**
	 * @return the spawn
	 */
	public Animation getSpawn() {
		return spawn;
	}

	/**
	 * @return the direc
	 */
	public Direction getDirec() {
		return direc;
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
	 * @return the posY
	 */
	public float getPosY() {
		return posY;
	}

}
