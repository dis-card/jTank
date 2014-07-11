package in.darkstars.entity;

import static in.darkstars.main.JTank.SIZE;
import in.darkstars.main.JTank;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author dis-card
 *
 * Jul 11, 2014
 *
 */
public class TMap extends TiledMap {
	
	private ArrayList<Rectangle> obstaclesList, freePathList;
	private boolean blocked [][];
	private Random random;

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
		random = new Random();
		blocked = new boolean[this.getWidth()][this.getHeight()];
		obstaclesList = new ArrayList<Rectangle>();
		freePathList = new ArrayList<Rectangle>();
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				int tileId = this.getTileId(x, y, 0);
				String value = this.getTileProperty(tileId, "blocked", "false");
				if (value.equals("true")) {
					blocked[x][y] = true;
					obstaclesList.add(new Rectangle(x * SIZE, y * SIZE, SIZE,
							SIZE));
				}
				else
				{
					freePathList.add(new Rectangle(x * SIZE, y*SIZE, SIZE, SIZE));
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
	
	public int[] getValidPos()
	{
		Rectangle rec =  freePathList.get(random.nextInt(freePathList.size()));
		return new int[] {(int)rec.getX(),(int)rec.getY()};
		
	}
}
