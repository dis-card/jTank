package in.darkstars.entity.tank;

import in.darkstars.entity.Enemy;
import in.darkstars.entity.Player;
import in.darkstars.entity.TMap;
import in.darkstars.helper.Helper;

import org.newdawn.slick.SlickException;

/**
 * @author dis-card
 * 
 *         Jul 13, 2014
 * 
 */
public class TankFactory {

	public static Enemy getTank(int posX, int posY, TMap map, Player player) throws SlickException {
		Enemy tank = null;
		switch (Helper.getRandomNumber(8)) {
		case 0:
			tank = new Tango(posX, posY, map, player);
			break;
		case 1:
			tank = new Alpha(posX, posY, map, player);
			break;
		case 2:
			tank = new Mamba(posX, posY, map, player);
			break;
		case 3:
			tank = new Python(posX, posY, map, player);
			break;
		case 4:
			tank = new Sweeper(posX, posY, map, player);
			break;
		case 5:
			tank = new Houdini(posX, posY, map, player);
			break;
		case 6:
			tank = new SharpShooter(posX, posY, map, player);
			break;
		case 7:
			tank = new Cobra(posX, posY, map, player);
			break;
		}
		return tank;
	}

}
