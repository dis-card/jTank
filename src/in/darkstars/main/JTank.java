package in.darkstars.main;

import in.darkstars.entity.Bullet;
import in.darkstars.entity.Enemy;
import in.darkstars.entity.TMap;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author dis-card
 * 
 *         Jul 9, 2014
 * 
 */
public class JTank extends BasicGame {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;

	public static enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

	public static final int NUMBER_OF_BULLETS_PER_FRAME = 5;
	private static final int TILEWIDTH = 32;
	private static final int TILEHEIGHT = 32;
	public static final int SIZE = 32;
	private static final float SPEED = 4;
	private static final int NUMBER_OF_ENEMIES_PER_FRAME = 10;
	private Direction tankDirection;

	private int statusCode = 0;
	private TMap map;
	private int mapPosX = 0;
	private int mapPosY = 0;
	private Animation explosion;
	private float posY = 0;
	private float posX = 0;
	private boolean blocked[][];

	private Bullet bulletList[];
	private Enemy enemyList[];
	private Image upTank;
	private Image leftTank;
	private Image downTank;
	private Image rightTank;
	private Image player;


	/**
	 * @param title
	 */
	public JTank() {
		super("JTank");

	}

	public static void main(String args[]) {
		try {
			AppGameContainer gameContainer = new AppGameContainer(new JTank());
			gameContainer.setDisplayMode(WIDTH, HEIGHT, false);
			// gameContainer.setTargetFrameRate(3);
			gameContainer.start();
		} catch (SlickException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @param jTank
	 * @return
	 */

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		/* Rendering map on the screen */
		map.render(mapPosX, mapPosY);
		
		/* Rendering player on the screen */
		player.draw((int) posX, (int) posY);

		/* Rendering bullets on the screen */
		for (int i = 0; i < bulletList.length; i++) {
			Bullet bullet = bulletList[i];
			if (bullet != null) {
				bullet.render(g);			
			}
			
		}
		
		/* Rendering enemies on the screen */
		for (int i = 0; i < enemyList.length; i++) {
			Enemy enemy = enemyList[i];
			if (enemy != null) {
				enemy.render();			
			}
			
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		map = new TMap("./resources/one.tmx");						// Initializing the map.
		bulletList = new Bullet[NUMBER_OF_BULLETS_PER_FRAME];		// Initializing the list for tracking player bullets.
		enemyList = new Enemy[NUMBER_OF_ENEMIES_PER_FRAME];			// Initializing the list for enemies.
		for ( int i = 0; i < enemyList.length; i++)					// Creating enemies and adding them to the list.
		{
			int posX = map.getValidPos()[0];
			int posY = map.getValidPos()[1];
			enemyList[i] = new Enemy(posX, posY, map);
		}
		upTank = new Image("resources/upTank.jpg");
		rightTank = new Image("resources/rightTank.jpg");
		leftTank = new Image("resources/leftTank.jpg");
		downTank = new Image("resources/downTank.jpg");

		

		player = upTank;
		tankDirection = Direction.UP;
		posX = 0;
		posY = HEIGHT - TILEHEIGHT;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		Input input = container.getInput();

		/* Key press handling code */

		if (input.isKeyDown(Input.KEY_UP)) {
			player = upTank;
			tankDirection = Direction.UP;
			if (!isBlocked(posX, posY - SPEED)) {
				posY -= SPEED;
			}

		} else if (input.isKeyDown(Input.KEY_DOWN)) {

			player = downTank;
			tankDirection = Direction.DOWN;
			if (!isBlocked(posX, posY + SPEED)) {
				posY += SPEED;
			}

		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			player = rightTank;
			tankDirection = Direction.RIGHT;
			if (!isBlocked(posX + SPEED, posY)) {
				posX = posX + SPEED;
			}

		} else if (input.isKeyDown(Input.KEY_LEFT)) {

			player = leftTank;
			tankDirection = Direction.LEFT;
			if (!isBlocked(posX - SPEED, posY)) {
				posX -= SPEED;
			}
		} else if (input.isKeyPressed(Input.KEY_SPACE)) {
			for (int i = 0; i < bulletList.length; i++) {
				if (bulletList[i] == null) {
					switch (tankDirection) {
					case UP:
						bulletList[i] = new Bullet(posX + SIZE / 2, posY,
								tankDirection);
						break;
					case DOWN:
						bulletList[i] = new Bullet(posX + SIZE / 2,
								posY + SIZE, tankDirection);
						break;
					case RIGHT:
						bulletList[i] = new Bullet(posX + SIZE,
								posY + SIZE / 2, tankDirection);
						break;
					case LEFT:
						bulletList[i] = new Bullet(posX, posY + SIZE / 2,
								tankDirection);
						break;
					}
					break;
				}
			}

		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {

			System.exit(statusCode);
		}
		

		/* Code to update bullets */
		for (int i = 0; i < bulletList.length; i++) {
			Bullet bullet = bulletList[i];
			if (bullet != null) {
				if (bullet.isOutOfScreen() || ( bullet.isExploded() && bullet.getExplosion().isStopped() ))
				{
					bulletList[i] = null;
				}
				else if (map.inCollision(new Rectangle((int) bullet.getPosX(), (int) bullet.getPosY(), Bullet.WIDTH, Bullet.HEIGHT)))
				{
						bullet.setExploded(true);			
						bullet.getExplosion().update(delta);
				}
				else
				{
					bullet.update();
				}
				
			}
		}
		
		/* Code to update the enemies */
		for ( int i = 0; i < enemyList.length; i++)
		{
			Enemy enemy = enemyList[i];
			if ( enemy != null )
			{
				enemy.update();
			}
		}

	}

	private boolean isBlocked(float x, float y) {
		boolean inCollision = false;

		Rectangle playerBounds = new Rectangle((int) x, (int) y, SIZE, SIZE);
		if (x > (WIDTH - SIZE) || x < 0 || y > (HEIGHT - SIZE) || y < 0) {
			inCollision = true;
		} else {
			if (map.inCollision(playerBounds))
				inCollision = true;

		}

		return inCollision;
	}
}
