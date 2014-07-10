package in.darkstars.entity;

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
	private float height = 5;
	private float width = 5;
	
	public Bullet(float x, float y)
	{
		this.posX = x;
		this.posY = y;
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
		g.drawOval(posX, posY-velocity, width, height);
		
	}
	
	
	

}
