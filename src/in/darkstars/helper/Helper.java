package in.darkstars.helper;

import in.darkstars.main.JTank;

import java.awt.Rectangle;
import static in.darkstars.main.JTank.*;

/**
 * @author dis-card
 *
 * Jul 11, 2014
 *
 */
public class Helper {
	
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

}
