package in.darkstars.main;

import in.darkstars.entity.Bullet;
import in.darkstars.entity.Enemy;
import in.darkstars.entity.Player;
import in.darkstars.entity.TMap;
import in.darkstars.entity.tank.TankFactory;
import in.darkstars.helper.SpriteSheetFactory;

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

import static in.darkstars.helper.Helper.ANIMATION_DELAY;

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
	private static final int TILEWIDTH = 16;
	public static final int TILEHEIGHT = 16;
	public static final int SIZE = 16;
	private static final int NUMBER_OF_ENEMIES_PER_FRAME = 10;

	private int statusCode = 0;
	private TMap map;
	private int mapPosX = 0;
	private int mapPosY = 0;

	private Player player;
	private Enemy enemyList[];

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
			 gameContainer.setTargetFrameRate(60);
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
		map.render(mapPosX, mapPosY, 0);

		/* Rendering player on the screen */
		player.render();

		/* Rendering bullets on the screen */
		for (int i = 0; i < player.getBulletList().length; i++) {
			Bullet bullet = player.getBulletList()[i];
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
		map.render(mapPosX, mapPosY, 1);
		g.drawString("Score "+Long.toString(player.getScore()), 90, 10);

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		map = new TMap("resources/maps/newStageOne.tmx"); // Initializing the map.
		player = new Player(0,JTank.HEIGHT - TILEHEIGHT,map);
		enemyList = new Enemy[NUMBER_OF_ENEMIES_PER_FRAME]; // Initializing the
															// list for enemies.
		for (int i = 0; i < enemyList.length; i++) // Creating enemies and
													// adding them to the list.
		{
			int posX = map.getValidPos()[0];
			int posY = map.getValidPos()[1];
			enemyList[i] = TankFactory.getTank(posX, posY, map);
			//enemyList[i].render();
		}
		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		Input input = container.getInput();

		/* Key press handling code */

		if (input.isKeyDown(Input.KEY_UP)) {
			player.changeDirectionTo(Direction.UP);
			player.update(delta);

		} else if (input.isKeyDown(Input.KEY_DOWN)) {

			player.changeDirectionTo(Direction.DOWN);
			player.update(delta);

		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			
			player.changeDirectionTo(Direction.RIGHT);
			player.update(delta);

		} else if (input.isKeyDown(Input.KEY_LEFT)) {

			player.changeDirectionTo(Direction.LEFT);
			player.update(delta);
			
		} else if (input.isKeyPressed(Input.KEY_SPACE)) {
			player.fire();

		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {

			System.exit(statusCode);
		}

		/* Code to update bullets */
		player.updateBullets(delta);

		/* Code to update the enemies */
		for (int i = 0; i < enemyList.length; i++) {
			Enemy enemy = enemyList[i];
			if (enemy != null && enemy.getHealth() > 0 ) {
				enemy.update(delta);
			}
			else
			{
				enemy.makeDestroyedSound();
				long score = player.getScore();
				player.setScore(++score);
				int posX = map.getValidPos()[0];
				int posY = map.getValidPos()[1];
				enemyList[i] = TankFactory.getTank(posX, posY, map); 
			}
		}
		
		/* Code to update the health and bullet explosion when enemy is hit by a bullet*/
		for (int i = 0; i < player.getBulletList().length; i++) {
			Bullet bullet = player.getBulletList()[i];
			if (bullet == null || bullet.isExploded())
				continue;
			else {
				for (int j = 0; j < enemyList.length; j++) {
					Enemy enemy = enemyList[j];
					if ( enemy != null )
					{
						if ( enemy.isHit(bullet.getPosX(),bullet.getPosY()) )
						{
							enemy.setHealth(enemy.getHealth() - 1);
							bullet.setExploded(true);
						} 
					}
				}
			}
		}
	}

}
