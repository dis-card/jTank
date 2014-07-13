package in.darkstars.entity.tank;

import static in.darkstars.helper.Helper.ANIMATION_DELAY;
import in.darkstars.entity.Enemy;
import in.darkstars.entity.TMap;
import in.darkstars.entity.Enemy.CodeName;
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
public class Mamba extends Enemy {
	
	public static final int LIFE = 5;
	private static final float SPEED = 1.0f;

	public Mamba(int posX, int posY, TMap map) throws SlickException {
		super(posX, posY, map);
		SpriteSheet jTankSpriteSheet = SpriteSheetFactory.getSpriteSheet();
			super.up = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(8, 2),
					jTankSpriteSheet.getSubImage(9, 2) }, ANIMATION_DELAY);
			super.left = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(10, 2),
					jTankSpriteSheet.getSubImage(11, 2) }, ANIMATION_DELAY);
			super.down = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(12, 2),
					jTankSpriteSheet.getSubImage(13, 2) }, ANIMATION_DELAY);
			super.right = new Animation(new Image[] {
					jTankSpriteSheet.getSubImage(14, 2),
					jTankSpriteSheet.getSubImage(15, 2) }, ANIMATION_DELAY);
			super.speed = SPEED;
			super.health = LIFE;
			super.codeName = CodeName.Mamba;
			super.enemy = down;
	}
	/*
	public void update(long delta) {
		
		timeElapsed += delta;
		if (spawn.isStopped()) {
			switch (direc) {
			case UP:
				if (!inCollision(posX, posY - speed)) {
					posY -= speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			case DOWN:
				if (!inCollision(posX, posY + speed)) {
					posY += speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			case LEFT:
				if (!inCollision(posX - speed, posY)) {
					posX -= speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			case RIGHT:
				if (!inCollision(posX + speed, posY)) {
					posX += speed;
					enemy.update(delta);
				} else {
					changeDirection();
				}
				break;
			}

		}
		
	}*/

}
