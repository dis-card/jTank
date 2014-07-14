package in.darkstars.entity;

import static in.darkstars.main.JTank.SIZE;
import in.darkstars.helper.Helper;
import in.darkstars.main.JTank;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
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
	
	private ArrayList<Rectangle2D.Float> obstaclesList, freePathList;
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
	
	
	


	private void init()
	{
		random = new Random();
		blocked = new boolean[this.getWidth()][this.getHeight()];
		obstaclesList = new ArrayList<Rectangle2D.Float>();
		freePathList = new ArrayList<Rectangle2D.Float>();
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				int tileId = this.getTileId(x, y, 0);
				String value = this.getTileProperty(tileId, "blocked", "false");
				if (value.equals("true")) {
					blocked[x][y] = true;
					obstaclesList.add(new Rectangle2D.Float(x * SIZE, y * SIZE, SIZE,
							SIZE));
				}
				else
				{
					freePathList.add(new Rectangle2D.Float(x * SIZE, y*SIZE, SIZE, SIZE));
				}
			}
		}
		
	}
	
	public boolean inCollision( Rectangle2D.Float boundedBox )
	{
		boolean collided = false;
		for ( Rectangle2D.Float obstacle : this.obstaclesList)
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
		/*
		int coordinates [] = new int[2];
		switch (Helper.getRandomNumber(3))
		{
		case 0:
			coordinates[0] = 0;
			coordinates[1] = 0;
			break;
		case 1:
			coordinates[0] = JTank.WIDTH / 2;
			coordinates[1] = 0; 
			break;
		case 2:
			coordinates[0] = JTank.WIDTH - SIZE;
			coordinates[1] = 0;
			break;
		}
		
		return coordinates;
		*/
		
		// Tried to generate random co-ordinates but have some problem
		boolean valid = false;
		Rectangle2D.Float rec = null;
		while (!valid)
		{
			rec =  freePathList.get(random.nextInt(freePathList.size()));
			if (!inCollision(rec))
				valid = true;
			
		}
		return new int[] {(int)rec.getX(),(int)rec.getY()};
		
	}
	
	/**
	 * @return the obstaclesList
	 */
	public ArrayList<Rectangle2D.Float> getObstaclesList() {
		return obstaclesList;
	}



	/**
	 * @param obstaclesList the obstaclesList to set
	 */
	public void setObstaclesList(ArrayList<Rectangle2D.Float> obstaclesList) {
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

}
