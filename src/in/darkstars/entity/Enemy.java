package in.darkstars.entity;

import static in.darkstars.helper.Helper.ANIMATION_DELAY;
import in.darkstars.exception.IllegalMovementException;
import in.darkstars.helper.Helper;
import in.darkstars.helper.Helper.State;
import in.darkstars.helper.SoundFactory;
import in.darkstars.helper.SoundFactory.SoundType;
import in.darkstars.helper.SpriteSheetFactory;
import in.darkstars.main.JTank;
import in.darkstars.state.GamePlay;
import in.darkstars.state.GamePlay.Direction;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 * @author dis-card
 * 
 *         Jul 11, 2014
 * 
 */
/**
 * @author dis-card
 *
 * Jul 27, 2014
 *
 */
public abstract class Enemy {

	protected float speed = 20.0f;
	protected float posX;
	protected float posY;
	protected Animation up, down, left, right, enemy;
	private Animation spawn;
	private Animation shield;
	private double timeElapsed;
	private double shieldTime = 5000;
	private Sound destroyed;
	private Player player;

	protected int health;
	private TMap map;
	protected CodeName codeName;
	private State currentState;
	public static enum CodeName {
		Tango, Alpha, Mamba, Python, Sweeper, Houdini, SharpShooter, Cobra
	};

	public static final float TANGO_SPEED = 1f, ALPHA_SPEED = 1f,
			MAMBA_SPEED = 1f, PYTHON_SPEED = 1f, SWEEPER_SPEED = 1f,
			HOUDINI_SPEED = 2f, SHARP_SHOOTER_SPEED = 1f, COBRA_SPEED = 1f;

	private Bullet[] bulletList;

	protected Direction direc;
	private Random random;

	/**
	 * @param posX x coordinate of the position where we want to spawn the enemy.
	 * @param posY y coordinate of the position where we want to spawn the enemy.
	 * @param map	map of the current stage, idea is every enemy has to has his map so that it can travel through it.
	 * @param player reference of the player, enemy can use this reference to get details abt the player in real time.
	 * @throws SlickException
	 */
	public Enemy(int posX, int posY, TMap map, Player player)
			throws SlickException {

		this.posX = posX;
		this.posY = posY;
		this.map = map;
		this.player = player;
		this.destroyed = SoundFactory.getSound(SoundType.Explosion);
		bulletList = new Bullet[5];
		SpriteSheet jTankSpriteSheet = SpriteSheetFactory.getSpriteSheet();
		this.random = new Random();
		spawn = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(16, 6),
				jTankSpriteSheet.getSubImage(17, 6),
				jTankSpriteSheet.getSubImage(18, 6),
				jTankSpriteSheet.getSubImage(19, 6),
				jTankSpriteSheet.getSubImage(19, 6),
				jTankSpriteSheet.getSubImage(18, 6),
				jTankSpriteSheet.getSubImage(17, 6),
				jTankSpriteSheet.getSubImage(16, 6) }, ANIMATION_DELAY);
		spawn.setLooping(false);
		spawn.draw(posX, posY);
		direc = Direction.DOWN;
		shield = new Animation(new Image[] {
				jTankSpriteSheet.getSubImage(16, 9),
				jTankSpriteSheet.getSubImage(16, 10) }, 1);
		shield.draw(posX, posY);
		this.setCurrentState(State.Persue);

	}

	/**
	 * method to render the enemy.
	 */
	public void render() {
		if (!spawn.isStopped()) {
			spawn.draw(posX, posY);
		} else if (this.health > 0) {
			switch (direc) {
			case UP:
				enemy = up;
				break;
			case DOWN:
				enemy = down;
				break;
			case LEFT:
				enemy = left;
				break;
			case RIGHT:
				enemy = right;
				break;
			}
			enemy.draw(posX, posY);
			if (!(timeElapsed > shieldTime)) {
				shield.draw(posX, posY);
			}
		}
	}

	/**
	 * updates the position of enemy, this method will contain much more logic.
	 * @param delta is the millisecond after which the update method is called.
	 * 
	 */
	public void update(long delta) {

		timeElapsed += delta;
		if ( timeElapsed % shieldTime == 30 )
			changeState();
		System.err.println(getCurrentState());
		if (spawn.isStopped()) {
			if ( this.getCurrentState() == State.Persue ) {
			//if (isPlayerOnRadar() && player.isChangedPosition()) {
				
				try {
					Node currentNode = findOptimalPath( player.getPosX(), player.getPosY());
					while (currentNode.getParent() != null )
					{
						System.err.print(currentNode.getX()+","+currentNode.getY());
						System.err.print("|");
						currentNode = currentNode.getParent();
					}
				} catch (IllegalMovementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				switch (direc) {
				case UP:
					if (!inCollision(posX, posY - (delta / 1000.0f) * speed)) {
						posY -= (delta / 1000.0f) * speed;
						enemy.update(delta);
					} else {
						changeDirection();
					}
					break;
				case DOWN:
					if (!inCollision(posX, posY + (delta / 1000.0f) * speed)) {
						posY += (delta / 1000.0f) * speed;
						enemy.update(delta);
					} else {
						changeDirection();
					}
					break;
				case LEFT:
					if (!inCollision(posX - (delta / 1000.0f) * speed, posY)) {
						posX -= (delta / 1000.0f) * speed;
						enemy.update(delta);
					} else {
						changeDirection();
					}
					break;
				case RIGHT:
					if (!inCollision(posX + (delta / 1000.0f) * speed, posY)) {
						posX += (delta / 1000.0f) * speed;
						enemy.update(delta);
					} else {
						changeDirection();
					}
					break;
				}
			}

		}
	}

	/**
	 * This method will change the current state of the enemy randomly.
	 * they can go from roam to persue etc. 
	 */
	private void changeState() {
		
		int stateChanger = Helper.getRandomNumber(3);
		switch( stateChanger )
		{
		case 1:
			setCurrentState(State.Roam);
			break;
		case 2:
			setCurrentState(State.Persue);
			break;
		case 3:
			setCurrentState(State.Search);
			break;
		}
	}
	
	/**
	 * This method will chage the state of the enemy to a given state.
	 * @param changeToState state to change to.
	 */
	private void changeState ( State changeToState )
	{
		setCurrentState(changeToState);
		
	}

	/**
	 * finds the optimal path between the two positions, parameters are the coordiantes of destination position. Source will be the current position of the enemy.
	 * @param posX x coordinate of the destination.
	 * @param posY y coordinate of the destination.
	 * @throws IllegalMovementException 
	 */
	public Node findOptimalPath(float posX, float posY) throws IllegalMovementException {
		System.err.println("In optimal path");
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();
		int x = (int) (this.posX / GamePlay.SIZE);
		int y = (int) (this.posY / GamePlay.SIZE);
		Node goalNode = new Node((int) (player.getPosX() / GamePlay.SIZE),
				(int) (player.getPosY() / GamePlay.SIZE));
		Node sourceNode = new Node (x, y);
		System.err.println(sourceNode);
		System.err.println(goalNode);
		openList.add(sourceNode);
		Node currentNode = null;
		while (!openList.isEmpty()) {
			currentNode = minimalCostNode(openList);
			if (currentNode.equals(goalNode))
				break;
			else {
				openList.remove(currentNode);
				closedList.add(currentNode);
				ArrayList<Node> adjacenyList = getAdjacentList(currentNode);
				
				for (Node adjacentNode : adjacenyList) {
					
					if (!openList.contains(adjacentNode)
							&& !closedList.contains(adjacentNode)
							/*&& !map.getBlocked()[adjacentNode.getX() - 1 ][adjacentNode
									.getY()]*/) {
						
						adjacentNode.setHeuristics(goalNode.getX(), goalNode.getY());
						adjacentNode.setMovementCost( currentNode);
						adjacentNode.setParent(currentNode);
						openList.add(adjacentNode);						
						System.err.println("Inside adding nodes");

					}
					else if ( openList.contains(adjacentNode))
					{
						if ( (currentNode.getMovementCost() + adjacentNode.getMovementCost()) < adjacentNode.getMovementCost() )
						{
							adjacentNode.setParent(currentNode);
						}
					}
				}
			}
		}
		
	
		
		System.err.println();		
		return currentNode;
		
	}

	/**
	 * 
	 *  used to fetch the adjacency list of a given node.
	 *  
	 * @param currentNode is the node for which we want to get the adjacency list.
	 * @return a list which contains all the nodes which are adjacent to "currentNode".
	 * 
	 */
	private ArrayList<Node> getAdjacentList(Node currentNode) {
		ArrayList<Node> adjacenyList = new ArrayList<Node>();
		int x = currentNode.getX();
		int y = currentNode.getY();

		if ((x == 0 || x == map.getWidth()) || (y == 0 || y == map.getHeight())) {

			if (x == 0 && y == 0)

			{
				adjacenyList.add(new Node(x + 1, y));
				adjacenyList.add(new Node(x + 1, y + 1));
				adjacenyList.add(new Node(x, y + 1));
			} else if (x == 0 && y == map.getHeight()) {
				adjacenyList.add(new Node(x + 1, y));
				adjacenyList.add(new Node(x + 1, y - 1));
				adjacenyList.add(new Node(x, y - 1));
			} else if (x == map.getWidth() && y == 0) {
				adjacenyList.add(new Node(x - 1, y));
				adjacenyList.add(new Node(x - 1, y + 1));
				adjacenyList.add(new Node(x, y + 1));

			} else if (x == map.getWidth() && y == map.getHeight()) {
				adjacenyList.add(new Node(x - 1, y));
				adjacenyList.add(new Node(x - 1, y - 1));
				adjacenyList.add(new Node(x, y - 1));

			} else if (x == 0) {
				adjacenyList.add(new Node(x + 1, y));
				adjacenyList.add(new Node(x + 1, y + 1));
				adjacenyList.add(new Node(x + 1, y - 1));
				adjacenyList.add(new Node(x, y + 1));
				adjacenyList.add(new Node(x, y - 1));
			} else if (x == map.getWidth()) {
				adjacenyList.add(new Node(x - 1, y));
				adjacenyList.add(new Node(x - 1, y + 1));
				adjacenyList.add(new Node(x - 1, y - 1));
				adjacenyList.add(new Node(x, y + 1));
				adjacenyList.add(new Node(x, y - 1));

			} else if (y == 0) {
				adjacenyList.add(new Node(x, y + 1));
				adjacenyList.add(new Node(x - 1, y + 1));
				adjacenyList.add(new Node(x + 1, y + 1));
				adjacenyList.add(new Node(x + 1, y));
				adjacenyList.add(new Node(x - 1, y));

			} else if (y == map.getHeight()) {
				adjacenyList.add(new Node(x, y - 1));
				adjacenyList.add(new Node(x - 1, y - 1));
				adjacenyList.add(new Node(x + 1, y - 1));
				adjacenyList.add(new Node(x + 1, y));
				adjacenyList.add(new Node(x - 1, y));
			}

		} else {

			adjacenyList.add(new Node(x - 1, y));
			adjacenyList.add(new Node(x - 1, y - 1));
			adjacenyList.add(new Node(x - 1, y + 1));
			adjacenyList.add(new Node(x + 1, y));
			adjacenyList.add(new Node(x - 1, y - 1));
			adjacenyList.add(new Node(x - 1, y + 1));
			adjacenyList.add(new Node(x, y - 1));
			adjacenyList.add(new Node(x, y + 1));

		}

		return adjacenyList;
	}
	
	/**
	 * 
	 * @param openList
	 * @return node which has the minimal total cost.
	 */
	
	private Node minimalCostNode(ArrayList<Node> openList) {
		Node minimalCostNode = openList.get(0);

		for (int i = 1; i < openList.size(); i++) {
			Node node = openList.get(i);
			if (minimalCostNode.getTotalCost() > node.getTotalCost())
				minimalCostNode = node;

		}
		return minimalCostNode;

	}
	
	/** 
	 * play the destruction sound. 
	 */
	
	public void makeDestroyedSound() {
		destroyed.play();
	}

		
	/**
	 * @return whether the player is within the radar range or not.
	 */
	private boolean isPlayerOnRadar() {
		boolean onRadar = false;
		float netPosX = (float) Math.sqrt(posX * posX - player.getPosX()
				* player.getPosX());
		float netPosY = (float) Math.sqrt(posY * posY - player.getPosY()
				* player.getPosY());
		float distance = (float) Math.sqrt(netPosX + netPosY);
		System.err.println(distance);
		if (distance < 400) {
			onRadar = true;
		}
		return onRadar;
	}

	
	/**
	 *  changes the direction of enemy in a random manner. 
	 */
	protected void changeDirection() {

		int randomDirec = random.nextInt(4);
		switch (randomDirec) {
		case 0:
			direc = Direction.UP;
			break;
		case 1:
			direc = Direction.DOWN;
			break;
		case 2:
			direc = Direction.LEFT;
			break;
		case 3:
			direc = Direction.RIGHT;
			break;
		}
	}

	/**
	 * @param posX x co-ordinate of the enemy's position.
	 * @param posY y coordinate of the enemy's position.
	 * @return whether the enemy is in collision with the obstacles or not.
	 */
	protected boolean inCollision(float posX, float posY) {
		boolean collided = false;

		if (Helper.isOutOfScreen(posX, posY)) {
			collided = true;
		} else {
			for (Rectangle2D.Float obstacle : map.getObstaclesList()) {
				if (obstacle.intersects(new Rectangle2D.Float(posX, posY,
						GamePlay.SIZE, GamePlay.SIZE))) {
					collided = true;
					break;
				}
			}
		}
		return collided;
	}

	public void fire() {

	}

	/**
	 * @param posX x coordinate of the bullet's position.
	 * @param posY y coordinate of the bullet's position.
	 * @return whether the enemy is hit with bullet or not ( collision detection with the bullet).
	 */
	public boolean isHit(float posX, float posY) {
		boolean isHit = false;
		Rectangle2D.Float playerBounds = new Rectangle2D.Float(this.posX,
				this.posY, GamePlay.SIZE, GamePlay.SIZE);
		Rectangle2D.Float bulletBounds = new Rectangle2D.Float(posX, posY,
				Bullet.WIDTH, Bullet.HEIGHT);
		if (playerBounds.intersects(bulletBounds)) {
			isHit = true;
		}
		return isHit;
	}


	/**
	 * @return the direc
	 */
	public Direction getDirec() {
		return direc;
	}

	/**
	 * @return the life
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setHealth(int life) {
		this.health = life;
	}

	/**
	 * @return the posX
	 */
	public float getPosX() {
		return posX;
	}

	/**
	 * @return the posY
	 */
	public float getPosY() {
		return posY;
	}

	/**
	 * @return the currentState
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * @param currentState the currentState to set
	 */
	private void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

}
