package in.darkstars.entity.tank;

import org.newdawn.slick.SlickException;

import in.darkstars.entity.Enemy;
import in.darkstars.entity.TMap;
import in.darkstars.helper.Helper;

/**
 * @author dis-card
 * 
 *         Jul 13, 2014
 * 
 */
public class TankFactory {

	public static Enemy getTank(int posX, int posY, TMap map) throws SlickException {
		Enemy tank = null;
		switch (Helper.getRandomNumber(8)) {
		case 0:
			tank = new Tango(posX, posY, map);
			break;
		case 1:
			tank = new Alpha(posX, posY, map);
			break;
		case 2:
			tank = new Mamba(posX, posY, map);
			break;
		case 3:
			tank = new Python(posX, posY, map);
			break;
		case 4:
			tank = new Sweeper(posX, posY, map);
			break;
		case 5:
			tank = new Houdini(posX, posY, map);
			break;
		case 6:
			tank = new SharpShooter(posX, posY, map);
			break;
		case 7:
			tank = new Cobra(posX, posY, map);
			break;
		}
		return tank;
	}

}
