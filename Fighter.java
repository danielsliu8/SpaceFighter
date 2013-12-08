
public class Fighter {
	
	/*
	 *  A Fighter looks like this:
	 * 
	 *              * 
	 *             ***    
	 *         *  *****  *
	 *  	   * ******* *
	 *         ***** *****
	 *         ***     ***
	 *        **         **
	 *       *             *
	 */
	
	private int[][] coordinates;
	private int hitPoints;
	private int speed;
	private int invuln;
	
	private double leftBuffer;
	private double rightBuffer;
	private double upBuffer;
	private double downBuffer;
	
	public Fighter(int i, int j) {
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
	
	public void setSpeed(int s) {speed = s;}
	public int getSpeed() {return speed;}
	public void setInvuln() {invuln = Space.invulnTime;}
	public int getInvuln() {return invuln;}
	public boolean invulnerable() {return invuln > 0;}
	public void decrementInvuln() {if (invuln > 0) {invuln--;}}
	
	public void move(int x, int y) {
		for (int[] coord: coordinates) {
			coord[0]+=x;
			coord[1]+=y;
		}
	}
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
	public boolean movingDown() {return downBuffer >= 1;}
	public boolean movingRight() {return rightBuffer >= 1;}
	public boolean movingLeft() {return leftBuffer >= 1;}
	public boolean movingUp() {return upBuffer >= 1;}
	
	public void resetDown() {downBuffer = speed;}
	public void resetUp() {upBuffer = speed;}
	public void resetLeft() {leftBuffer = speed;}
	public void resetRight() {rightBuffer = speed;}
	
	public boolean canMoveLeft() {return left() > Space.LEFT_BORDER + 5;}
	public boolean canMoveRight() {return right() < Space.RIGHT_BORDER - 5;}
	public boolean canMoveUp() {return top() > Space.TOP_BORDER + 10;}
	public boolean canMoveDown() {return top() < Space.BOTTOM_BORDER - 30;}
	
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
