package in.darkstars.helper;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import static in.darkstars.helper.Helper.*;

/**
 * @author dis-card
 *
 * Jul 12, 2014
 *
 */
public class SpriteSheetFactory{
	
	private static SpriteSheet jTankSpriteSheet;	
	
	public static SpriteSheet getSpriteSheet () throws SlickException 
	{
		if ( jTankSpriteSheet == null )
			jTankSpriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, TILE_WIDTH, TILE_HEIGHT);
		return jTankSpriteSheet;
	}
	
	

}
