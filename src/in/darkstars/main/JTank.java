package in.darkstars.main;

import in.darkstars.state.GamePlay;
import in.darkstars.state.Menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author dis-card
 *
 * Jul 15, 2014
 *
 */
public class JTank extends StateBasedGame {

	private static final String TITLE = "jTank";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;

	/**
	 * @param name
	 */
	public JTank(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		addState (new Menu());
		addState (new GamePlay());
		enterState(0);
		
		
	}
	
	public static void main ( String args[] ) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(new JTank(TITLE));
		container.setDisplayMode( WIDTH, HEIGHT, false);
		container.start();
	}

}
