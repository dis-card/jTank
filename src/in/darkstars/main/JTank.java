package in.darkstars.main;



import in.darkstars.entity.Bullet;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
	private static final int TILEWIDTH = 32;
	private static final int TILEHEIGHT = 32;
	private static final int SIZE = 32;
	private static final float SPEED = 4;

	private int statusCode = 0;
	private TiledMap map;
	private int mapPosX = 0;
	private int mapPosY = 0;
	private Animation up, down, left, right, sprite;
	private float posY = 0;
	private float posX = 0;
	private boolean blocked[][];
	private ArrayList<Rectangle> obstaclesList;
	private boolean fire = false;
	private Bullet bulletList[];
	//private Bullet bulletArray[];
	
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
		//	gameContainer.setTargetFrameRate(3);
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

		map.render(mapPosX, mapPosY);
		sprite.draw((int)posX,(int)posY);
		for (int i= 0; i < bulletList.length; i++)
		{
			if (bulletList[i] != null )
				bulletList[i].render(g);
		}
		

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		obstaclesList = new ArrayList<Rectangle>();
		bulletList = new Bullet[5];

		map = new TiledMap("./resources/one.tmx");
		blocked = new boolean[map.getWidth()][map.getHeight()];		
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileId = map.getTileId(x, y, 0);
				String value = map.getTileProperty(tileId, "blocked", "false");
				if (value.equals("true"))
				{
					blocked[x][y] = true;
					obstaclesList.add( new Rectangle(x*SIZE, y*SIZE, SIZE, SIZE));
				}
			}
		}
	
	
		

		SpriteSheet upTankSheet = new SpriteSheet("resources/upTankSheet.jpg",
				TILEWIDTH, TILEHEIGHT);
		SpriteSheet rightTankSheet = new SpriteSheet("resources/rightTank.jpg",
				TILEWIDTH, TILEHEIGHT);
		SpriteSheet leftTankSheet = new SpriteSheet("resources/leftTank.jpg",
				TILEWIDTH, TILEHEIGHT);
		SpriteSheet downTankSheet = new SpriteSheet("resources/downTank.jpg",
				TILEWIDTH, TILEHEIGHT);
		up = new Animation(upTankSheet, 300);
		right = new Animation(rightTankSheet, 300);
		left = new Animation(leftTankSheet, 300);
		down = new Animation(downTankSheet, 300);
		sprite = up;
		posX = 0;
		posY = HEIGHT - TILEHEIGHT;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_UP)) {
			sprite = up;
			if (!isBlocked(posX, posY - SPEED,"UP")) {
				posY -= SPEED;
				sprite.update(delta);
			}

		} else if (input.isKeyDown(Input.KEY_DOWN)) {

			sprite = down;
			if (!isBlocked(posX, posY + SPEED,"DOWN")) {
				posY += SPEED;
				sprite.update(delta);
			}

		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			sprite = right;
			if (!isBlocked(posX + SPEED, posY,"RIGHT")) {
				posX = posX + SPEED;
				sprite.update(delta);
			}

		} else if (input.isKeyDown(Input.KEY_LEFT)) {

			sprite = left;

			if (!isBlocked(posX - SPEED, posY,"LEFT")) {
				posX -= SPEED;				
				sprite.update(delta);
			}
		} else if (input.isKeyPressed(Input.KEY_SPACE))
		{
			for (int i = 0; i < bulletList.length; i++ )
			{
				if ( bulletList[i] == null )
				{
					bulletList[i] = new Bullet(posX, posY);
					break;
				}
			}
			 
		}
		else if (input.isKeyPressed(Input.KEY_ESCAPE)) {

			System.exit(statusCode);
		}
		

	}

	private boolean isBlocked(float x, float y, String direc) {
		boolean result = false;
		
		Rectangle playerBounds = new Rectangle((int)x,(int)y,SIZE,SIZE);
		if (x > (WIDTH - SIZE) || x < 0 || y > (HEIGHT - SIZE) || y < 0)
		{
			result = true;
		}
		else 
		{
			for (Rectangle obstacle : obstaclesList)
			{
				if ( playerBounds.intersects(obstacle) )
				{
					result = true;
					break;
				}
			}
		}
				
		return result;
	}
}
