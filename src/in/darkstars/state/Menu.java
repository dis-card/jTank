package in.darkstars.state;

import in.darkstars.main.JTank;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * @author dis-card
 *
 * Jul 15, 2014
 *
 */
public class Menu extends BasicGameState{
	
	private static final String START = "1. Start";
	private static final String EXIT = "2. Exit";
	private float posX = JTank.WIDTH / 2 ;
	private float posY = JTank.HEIGHT / 4;
	private boolean invalidKey = false;
	

	public void init(GameContainer container, StateBasedGame gameState)
			throws SlickException {
		
		
	}

	public void render(GameContainer container, StateBasedGame gameState, Graphics g)
			throws SlickException {

		g.drawString(START, posX, posY);
		g.drawString(EXIT, posX, posY+40);
		g.drawString("Please press 1 or 2", posX, posY + 80);

		
	}

	public void update(GameContainer container, StateBasedGame gameState, int delta)
			throws SlickException {
		invalidKey = false;
		Input input = container.getInput();
		if ( input.isKeyPressed(Input.KEY_ESCAPE)  || input.isKeyPressed(Input.KEY_2))
		{
			System.exit(0);
		}
		else if ( input.isKeyPressed(Input.KEY_1))
		{
			
			gameState.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		
	}

	public int getID() {
		return 0;
	}

}
