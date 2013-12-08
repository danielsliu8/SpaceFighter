
public class Invader {

	/* An enemy looks like this:
	 * 
	 *      
	 *      *       *
	 *       *     *
	 *     *********** 
	 *    ***  ***  ***
	 *  *****************
	 *  *****************
	 *  *  ***********  *
	 *  *  *         *  *
	 *      ***   ***
	 *  
	 */
	
	private int coordinates[][];
	private int hitPoints;
	private boolean dead;
	
	public Invader(int i, int j) {
		int[][] temp = {
				{i+4,j},{i-4,j},
				{i+3,j+1},{i-3,j+1},
				{i-5,j+2},{i-4,j+2},{i-3,j+2},{i-2,j+2},{i-1,j+2},{i,j+2},
				{i+5,j+2},{i+4,j+2},{i+3,j+2},{i+2,j+2},{i+1,j+2},
				{i-6,j+3},{i-5,j+3},{i-4,j+3},{i-1,j+3},{i,j+3},
				{i+6,j+3},{i+5,j+3},{i+4,j+3},{i+1,j+3},
				{i-8,j+4},{i-7,j+4},{i-6,j+4},{i-5,j+4},{i-4,j+4},{i-3,j+4},{i-2,j+4},{i-1,j+4},{i,j+4},
				{i+8,j+4},{i+7,j+4},{i+6,j+4},{i+5,j+4},{i+4,j+4},{i+3,j+4},{i+2,j+4},{i+1,j+4},
				{i-8,j+5},{i-7,j+5},{i-6,j+5},{i-5,j+5},{i-4,j+5},{i-3,j+5},{i-2,j+5},{i-1,j+5},{i,j+5},
				{i+8,j+5},{i+7,j+5},{i+6,j+5},{i+5,j+5},{i+4,j+5},{i+3,j+5},{i+2,j+5},{i+1,j+5},
				{i-8,j+6},{i-5,j+6},{i-4,j+6},{i-3,j+6},{i-2,j+6},{i-1,j+6},{i,j+6},
				{i+8,j+6},{i+5,j+6},{i+4,j+6},{i+3,j+6},{i+2,j+6},{i+1,j+6},
				{i-8,j+7},{i-5,j+7},{i+8,j+7},{i+5,j+7},
				{i-4,j+8},{i-3,j+8},{i-2,j+8},
				{i+4,j+8},{i+3,j+8},{i+2,j+8}
		};
		coordinates = temp;
		dead = false;
		hitPoints = Space.INV_HP;
	}
	
	public int[][] getCoordinates() {return coordinates;}
	public boolean dead() {return dead;}
	public int bottom() {return coordinates[coordinates.length-1][1];}
	public int[][] getHitCoordinates() {
		return null;
	}
	
	public void move() {
		for (int[] coord: coordinates) {
			coord[1] += Space.INV_SPEED;
		}
	}
	
	public void hit() {
		hitPoints--;
		if (hitPoints <= 0) {dead = true;}
	}
}
