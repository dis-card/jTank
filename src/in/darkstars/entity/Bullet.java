package in.darkstars.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import in.darkstars.main.JTank;
import in.darkstars.main.JTank.Direction;

import org.newdawn.slick.Graphics;

/**
 * @author dis-card
 *
 * Jul 10, 2014
 *
 */
public class Bullet {
	
	private float posX;
	private float posY;
	private float velocity;
	public static final int HEIGHT = 5;
	public static final int WIDTH = 5;
	private Direction bulletDirection;
	
	public Bullet(float x, float y, Direction tankDirection)
	{
		this.posX = x;
		this.posY = y;
		this.bulletDirection = tankDirection;
		this.velocity = 5;
	}
	/**
	 * @return the posX
	 */
	public float getPosX() {
		return posX;
	}
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}
	/**
	 * @return the posY
	 */
	public float getPosY() {
		return posY;
	}
	/**
	 * @param posY the posY to set
	 */
	public void setPosY(float posY) {
		this.posY = posY;
	}
	/**
	 * @return the velocity
	 */
	public float getVelocity() {
		return velocity;
	}
	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	
	public void render(Graphics g)
	{
		switch(bulletDirection)
		{
		case UP:
			posY -= velocity;
			break;
		case DOWN:
			posY += velocity;
			break;
		case LEFT:
			posX -= velocity;
			break;
		case RIGHT:
			posX += velocity;
			break;	
		
		}
		g.drawOval(posX, posY, WIDTH, HEIGHT);
		
	} 
	
	public boolean isOutOfScreen()
	{
		if ( posX > JTank.WIDTH || posX < 0 || posY > JTank.HEIGHT || posY < 0 )
			return true;
		else
			return false;
	}
	
	public boolean isCollided(ArrayList<Rectangle> obstacleList)
	{
		boolean collided = false;
		Rectangle bulletBounds = new Rectangle((int)posX, (int)posY, WIDTH, HEIGHT);		
		for(Rectangle obstacles : obstacleList)
		{
			if ( bulletBounds.intersects(obstacles))
			{
				collided = true;
				break;
			}
		}
		return collided;
	}
	
	
	

}
