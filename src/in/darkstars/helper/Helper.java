package in.darkstars.helper;

import java.awt.Rectangle;

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

}
