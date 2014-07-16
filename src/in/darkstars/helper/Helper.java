package in.darkstars.helper;

import static in.darkstars.main.JTank.HEIGHT;
import static in.darkstars.state.GamePlay.SIZE;
import static in.darkstars.main.JTank.WIDTH;

import java.awt.Rectangle;
import java.util.Random;

import org.newdawn.slick.SpriteSheet;

/**
 * @author dis-card
 *
 * Jul 11, 2014
 *
 */
public class Helper {
	
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;
	private static Random randomNumGenerator = new Random();
	public static final String SPRITE_SHEET_PATH = "resources/maps/jTankSpriteSheet.png";
	public static final int ANIMATION_DELAY = 100;
	public static boolean isInCollision(Rectangle boundedBoxPlayer, Rectangle boundedBoxObject)
	{
		boolean collided = false;
		if (boundedBoxObject.intersects(boundedBoxPlayer))
			collided = true;		
		return collided;
	}
	public static boolean isOutOfScreen(float posX, float posY) {
		if (posX > (WIDTH - SIZE) || posX < 0 || posY > (HEIGHT - SIZE) || posY < 0) {
			return true;
		}
		else
			return false;
	}
	
	public static int getRandomNumber( int maxLimit )
	{
		return randomNumGenerator.nextInt(maxLimit);
	}

}
