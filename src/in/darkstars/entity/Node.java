package in.darkstars.entity;




/**
 * @author dis-card
 * 
 * This class has been written with A* pathfinding algorithm in mind.
 *
 * Jul 19, 2014
 *
 */
public class Node {
	
	private int x;
	private int y;
	private int movementCost;				//	Movement cost
	private int heuristics;				//	Heuristics
	private Node parent;

	/**
	 * @return the parent node of the current node. For the source node this will be null;
	 * 
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node(int x, int y) {

		this.x = x;
		this.y = y;
	}
	
	/**
	 * calculate and set the heurustics for the node.
	 * 
	 * @param desX x coordinate of the destination for which heuristics will be calculated.
	 * @param desY y coordinate of the destination for which heuristics will be calculated.
	 * 
	 */
	public void setHeuristics ( int desX, int desY )
	{
		int heuristics = 0;
		int souX = this.x;
		int souY = this.y;
		while ( desX != souX && desY != souY )
		{
			if (souX > desX )
			{
				souX--;				
			}
			else if ( souX < desX )
			{
				souX++;				
			}
			else if ( souY > desY )
			{
				souY--;
			}
			else if ( souY < desY )
			{
				souY++;
			}
			
			heuristics++;
		}
		setHeuristics(heuristics);
		
	}
	/**
	 * @return the movementCost
	 */
	public int getMovementCost() {
		return movementCost;
	}
	/**
	 * @param movementCost the movementCost to set
	 */
	public void setMovementCost(int movementCost) {
		this.movementCost = movementCost;
	}
	/**
	 * @return the heuristics
	 */
	public int getHeuristics() {
		return heuristics;
	}
	/**
	 * @param heuristics the heuristics to set
	 */
	public void setHeuristics(int heuristics) {
		this.heuristics = heuristics;
	}
	/**
	 * @return the totalCost
	 */
	public int getTotalCost() {
		return movementCost +  heuristics;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals( Object o )
	{
		boolean isEqual = false;
		
		 if (o instanceof Node ) {
			 Node n = (Node) o;
			 if ( this.x == n.getX() && this.y == n.getY())
				 isEqual = true;	 
		 }
		return isEqual;
	}

	public void setMovementCost(Node des ) {
		
		int desX = des.getX();
		int desY = des.getY();
		int movementCost = 0;
		
		
		if ( Math.abs( this.x - desX ) == 1 && Math.abs( this.y - desY ) == 1 )
		{
			movementCost = 14;
		}
		else if ( Math.abs(this.x - desX) == 1 && Math.abs(this.y - desY ) == 0 )
		{
			movementCost = 10;
		}
		else if ( Math.abs(this.x - desX) == 0 && Math.abs(this.y - desY ) == 1 )
		{
			movementCost = 10;
		}
		else 
		{
			System.err.println("Error");
		}
		this.movementCost = movementCost;
		
	}
	
	public String toString ()
	{
		
		return this.x + "," + this.y + "|";
		
	}

}

