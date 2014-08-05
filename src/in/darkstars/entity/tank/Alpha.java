package in.darkstars.entity.tank;

import static in.darkstars.helper.Helper.ANIMATION_DELAY;
import in.darkstars.entity.Enemy;
import in.darkstars.entity.Player;
import in.darkstars.entity.TMap;
import in.darkstars.helper.SpriteSheetFactory;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author dis-card
 *
 * Jul 13, 2014
 *
 */
public class Alpha extends Enemy {

	public static final int LIFE = 4;
	private static final float SPEED = 25.0f;
	public Alpha(int posX, int posY, TMap map, Player player) throws SlickException {
		super(posX, posY, map, player);
		SpriteSheet jTankSpriteSheet = SpriteSheetFactory.getSpriteSheet();
			super.up = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(8, 1),
					jTankSpriteSheet.getSubImage(9, 1) }, ANIMATION_DELAY);
			super.left = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(10, 1),
					jTankSpriteSheet.getSubImage(11, 1) }, ANIMATION_DELAY);
			super.down = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(12, 1),
					jTankSpriteSheet.getSubImage(13, 1) }, ANIMATION_DELAY);
			super.right = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(14, 1),
					jTankSpriteSheet.getSubImage(15, 1) }, ANIMATION_DELAY);
			super.speed = SPEED;
			super.health = LIFE;
			super.codeName = CodeName.Alpha;
			super.enemy = down;
			//super.direc = Direction.UP;
	}
		

}
