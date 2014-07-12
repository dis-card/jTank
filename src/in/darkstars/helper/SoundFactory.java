package in.darkstars.helper;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * @author dis-card
 * 
 *         Jul 12, 2014
 * 
 */
public class SoundFactory {

	public static final String BULLET_FIRED_SOUND_PATH = "resources/sounds/bulletFired.ogg";
	public static final String BULLET_HIT_SOUND_PATH = "resources/sounds/bulletHit.ogg";
	public static final String EXPLSION_SOUND_PATH = "resources/sounds/explosion.ogg";
	public static enum SoundType { BulletFired, BulletHit, Explosion};
	private static Sound bulletFired; 
	private static Sound bulletHit; 
	private static Sound explosion; 

	public static Sound getSound(SoundType type) throws SlickException {
		Sound sound = null;
		switch (type) {
		case BulletFired:
			if ( bulletFired == null)
				bulletFired = new Sound (BULLET_FIRED_SOUND_PATH);
			sound = bulletFired;
			break;
		case BulletHit:
			if ( bulletHit == null)
				bulletHit = new Sound (BULLET_HIT_SOUND_PATH);
			sound = bulletHit;
			break;
		case Explosion:
			if (explosion == null)
				explosion = new Sound (EXPLSION_SOUND_PATH);
			sound = explosion;
			break;
		}
		return sound;
	}
}
