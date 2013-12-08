/*
 *  Fighter is the little space ship that you control. This class contains variables and methods
 *  relating to the Fighter. 
 */
public class Fighter {
	
	/*
	 *  A Fighter looks like this:
	 * 
	 *              * 
	 *             ***    
	 *         *  *****  *
	 *         * ******* *
	 *         ***** *****
	 *         ***     ***
	 *        **         **
	 *       *             *
	 */
	
	//pertinent variables
	private int[][] coordinates;
	private int hitPoints;
	private int speed;
	private int invuln;
	
	//determine how fast the fighter is moving in a particular direction
	private double leftBuffer;
	private double rightBuffer;
	private double upBuffer;
	private double downBuffer;
	
	/*
	 * 	Create a Fighter. The starting x and y coordinates are passed in.
	 */
	public Fighter(int i, int j) {
		//this creates the shape of the fighter
		int[][] temp = {
				{i,j-4},
				{i-1,j-3},{i,j-3},{i+1,j-3},
				{i-5,j-2},{i-2,j-2},{i-1,j-2},{i,j-2},{i+1,j-2},{i+2,j-2},{i+5,j-2},
				{i-5,j-1},{i-3,j-1},{i-2,j-1},{i-1,j-1},{i,j-1},{i+1,j-1},{i+2,j-1},{i+3,j-1},{i+5,j-1},
				{i-5,j},{i-4,j},{i-3,j},{i-2,j},{i-1,j},{i+1,j},{i+2,j},{i+3,j},{i+4,j},{i+5,j},
				{i-5,j+1},{i-4,j+1},{i-3,j+1},{i+3,j+1},{i+4,j+1},{i+5,j+1},
				{i-6,j+2},{i-5,j+2},{i+5,j+2},{i+6,j+2},
				{i-7,j+3},{i+7,j+3}
		};
		coordinates = temp;
		hitPoints = Space.DEFAULT_HP;
		speed = Space.DEFAULT_SPEED;
		invuln = 0;
		
		leftBuffer = 0;
		rightBuffer = 0;
		upBuffer = 0;
		downBuffer = 0;
	}
	
	//methods that deal with accessing/changing some variables
	public void setSpeed(int s) {speed = s;}
	public int getSpeed() {return speed;}
	public void setInvuln() {invuln = Space.invulnTime;}
	public int getInvuln() {return invuln;}
	public boolean invulnerable() {return invuln > 0;}
	public void decrementInvuln() {if (invuln > 0) {invuln--;}}
	
	//move in a particular direction, defined by the horizontal and vertical
	public void move(int horizontal, int vertical) {
		for (int[] coord: coordinates) {
			coord[0]+=horizontal;
			coord[1]+=vertical;
		}
	}
	
	//these methods control for moving in particular directions
	public void moveUp() {
		if (canMoveUp()) {move(0, -(int)upBuffer);}
		upBuffer /= Space.DEFAULT_DRAG;
	}
	public void moveDown() {
		if (canMoveDown()) {move(0, (int)downBuffer);}
		downBuffer /= Space.DEFAULT_DRAG;
	}
	public void moveRight() {
		if (canMoveRight()) {move((int)rightBuffer, 0);}
		rightBuffer /= Space.DEFAULT_DRAG;
	}
	public void moveLeft() {
		if (canMoveLeft()) {move(-(int)leftBuffer, 0);}
		leftBuffer /= Space.DEFAULT_DRAG;
	}
	
	//tells us if the fighter is moving in a specific direction
	public boolean movingDown() {return downBuffer >= 1;}
	public boolean movingRight() {return rightBuffer >= 1;}
	public boolean movingLeft() {return leftBuffer >= 1;}
	public boolean movingUp() {return upBuffer >= 1;}
	
	//stop the fighter's motion in a specific direction
	public void resetDown() {downBuffer = speed;}
	public void resetUp() {upBuffer = speed;}
	public void resetLeft() {leftBuffer = speed;}
	public void resetRight() {rightBuffer = speed;}
	
	//border detection
	public boolean canMoveLeft() {return left() > Space.LEFT_BORDER + 5;}
	public boolean canMoveRight() {return right() < Space.RIGHT_BORDER - 5;}
	public boolean canMoveUp() {return top() > Space.TOP_BORDER + 10;}
	public boolean canMoveDown() {return top() < Space.BOTTOM_BORDER - 30;}
	
	//return information about the edges of the fighter
	public int top() {return coordinates[0][1];}
	public int topX() {return coordinates[0][0];}
	public int left() {return coordinates[coordinates.length-2][0];}
	public int right() {return coordinates[coordinates.length-1][0];}
	public int bottom() {return coordinates[coordinates.length-1][1];}
	
	public int[][] getCoordinates() {return coordinates;}
	public void hit() throws EndGameException {
		hitPoints--;
		setInvuln();
		if (hitPoints <= 0) {throw new EndGameException("You Lose");}
	}
}
