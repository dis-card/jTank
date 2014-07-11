package in.darkstars.entity;

import static in.darkstars.main.JTank.SIZE;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author dis-card
 *
 * Jul 11, 2014
 *
 */
public class TMap extends TiledMap {
	
	private ArrayList<Rectangle> obstaclesList;
	private boolean blocked [][];

	/**
	 * @param ref
	 * @throws SlickException
	 */
	public TMap(String path) throws SlickException {
		super(path);
		init();
		
	}
	
	
	
	/**
	 * @return the obstaclesList
	 */
	public ArrayList<Rectangle> getObstaclesList() {
		return obstaclesList;
	}



	/**
	 * @param obstaclesList the obstaclesList to set
	 */
	public void setObstaclesList(ArrayList<Rectangle> obstaclesList) {
		this.obstaclesList = obstaclesList;
	}



	/**
	 * @return the blocked
	 */
	public boolean[][] getBlocked() {
		return blocked;
	}



	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(boolean[][] blocked) {
		this.blocked = blocked;
	}



	private void init()
	{
		blocked = new boolean[this.getWidth()][this.getHeight()];
		obstaclesList = new ArrayList<Rectangle>();
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				int tileId = this.getTileId(x, y, 0);
				String value = this.getTileProperty(tileId, "blocked", "false");
				if (value.equals("true")) {
					blocked[x][y] = true;
					obstaclesList.add(new Rectangle(x * SIZE, y * SIZE, SIZE,
							SIZE));
				}
			}
		}
		
	}
	
	public boolean inCollision( Rectangle boundedBox )
	{
		boolean collided = false;
		for ( Rectangle obstacle : this.obstaclesList)
		{
			if ( boundedBox.intersects(obstacle))
			{
				collided = true;
				break;
			}
		}
		return collided;
	}
	
	
	
	

}
