/*
 *  Space is the object that holds most everything that happens in this game. Contains a Fighter,
 *  some invaders, projectiles, and a grid that tracks where everything is. Space has methods 
 *  relating to how elements interact with each other. Space also handles collisions between 
 *  the different elements it contains.
 */
public class Space {
	
	//keeps track of time
	private int counter;
	
	//grid constants
	public final static int gridWidth = 600*Simulate.cellSize;
	public final static int gridHeight = 350*Simulate.cellSize;
	public final static int RIGHT_BORDER = gridWidth-5;
	public final static int BOTTOM_BORDER = gridHeight-11;
	public final static int LEFT_BORDER = 5;
	public final static int TOP_BORDER = 0;
	public final static int numberOfShots = 100;
	
	//color constants
	public final static int EMPTY = 0;
	public final static int BEAM = 1;
	public final static int BLUEBEAM = 2;
	public final static int MISSILE = 3;
	public final static int INVADER = 10;
	public final static int FIGHTER = 11;
	
	//Invader constants
	public final static int TOTAL_INV_NUMBER = 100;
	public final static int INV_SPEED = 5;
	public final static int INV_HP = 1;
	public final static int WAVE_SIZE = 6;
	public final static int WAVE_START_POS = 20;
	public final static int WAVE_INTERVAL = 105;
	public final static int STEP_INTERVAL = 5;
	private Invader[] invaders;
	
	//Player constants
	public final static int DEFAULT_SPEED = 7;
	public final static int DEFAULT_HP = 5;
	public final static double DEFAULT_DRAG = 1.25;
	public final static int invulnTime = 10;
	public final static int projectileSpeed = 8;
	public final static int DEFAULT_COOLDOWN = 3;
	public final static int TRIPLE_COOLDOWN = 8;
	private int cooldown;
	private int shotCoords[][]; //0: valid bit, 1: x coord, 2: y coord
	private Fighter fighter;
	
	private int grid[][];
	
	//construct Space.
	public Space() {
		counter = 1;
		cooldown = 0;
		grid = new int[gridWidth][gridHeight];
		shotCoords = new int[numberOfShots][3];
		fighter = new Fighter(gridWidth/2, gridHeight*4/5);
		for (int[] f: fighter.getCoordinates()) {grid[f[0]][f[1]] = FIGHTER;}
		invaders = new Invader[TOTAL_INV_NUMBER];
	}
	
	public int contents(int i, int j) {return grid[i][j];}
	
	//methods that deal with the Fighter
	public void setFighterSpeed(int s) {fighter.setSpeed(s);}
	public int getFighterSpeed() {return fighter.getSpeed();}
	public void clearFighter() {for (int f[]: fighter.getCoordinates()) {grid[f[0]][f[1]] = EMPTY;}}
	private void setFighter() {for (int f[]: fighter.getCoordinates()) {grid[f[0]][f[1]] = FIGHTER;}}
	public void moveFighterUp() {fighter.resetUp();}
	public void moveFighterDown() {fighter.resetDown();}
	public void moveFighterRight() {fighter.resetRight();}
	public void moveFighterLeft() {fighter.resetLeft();}
	
	//shoot a single projectile
	public void fighterShoot() {
		//manage cooldown
		if (cooldown > 0) {return;}
		else {cooldown = DEFAULT_COOLDOWN;}
		
		for (int[] coords : shotCoords) {
			if (coords[0] == 0) {
				coords[0] = 1;
				coords[1] = fighter.topX();
				coords[2] = fighter.top();
				grid[coords[1]][coords[2]] = BEAM;
				break;
			}
		}
	}
	
	//shoot three projectiles
	public void fighterTriple() {
		//manage cooldown
		if (cooldown > 0) {return;}
		else {cooldown = TRIPLE_COOLDOWN;}
		//count deals with if we have enough projectiles to fire 3. 
		//there may be some problems with this, might have a leak in the # of projectiles
		int x = 0, y = 0, count = 0;
		for (int[] coords : shotCoords) {
			if (coords[0] == 0) {
				coords[0] = 1;
				if (count == 0) {coords[1] = fighter.topX();
				} else if (count == 1) {coords[1] = fighter.topX()-5;
				} else if (count == 2) {coords[1] = fighter.topX()+5;}
				coords[2] = fighter.top();
				if (count == 0) {
					x = coords[1];
					y = coords[2];
				}
				count++;
				if (count >= 3) {break;}
			}
		}
		if (count < 3) {return;}
		grid[x][y] = BEAM;
		grid[x+5][y] = BEAM;
		grid[x-5][y] = BEAM;
	}
	
	//pretty self-explanatory 
	public Space update() throws EndGameException {
		updatePlayer();
		updateProjectiles();
		try {updateInvaders();}
		catch (EndGameException e) {throw e;}
		counter++;
		return this;
	}
	
	public void updatePlayer() {
		fighter.decrementInvuln();
		cooldown--;
		clearFighter();
		if (fighter.movingDown()) {fighter.moveDown();}
		if (fighter.movingUp()) {fighter.moveUp();}
		if (fighter.movingLeft()) {fighter.moveLeft();}
		if (fighter.movingRight()) {fighter.moveRight();}
		if (fighter.getInvuln()%2 == 0) {setFighter();}
	}
	
	public void updateProjectiles() {
		for (int[] shot: shotCoords) { 
			if (shot[0] == 1 && shot[2] <= 10) {
				shot[0] = 0;
				grid[shot[1]][shot[2]] = EMPTY;
			} else if (shot[0] == 1) {
				if (grid[shot[1]][shot[2]] == BLUEBEAM) {
					grid[shot[1]][shot[2]] = EMPTY;
					shot[2]-=projectileSpeed;
					grid[shot[1]][shot[2]] = BLUEBEAM;
				} else {
					grid[shot[1]][shot[2]] = EMPTY;
					shot[2]-=projectileSpeed;
					grid[shot[1]][shot[2]] = BEAM;
				}
			}
		}
	}
	
	//very inefficient. checks every coordinate of every invader.
	public void updateInvaders() throws EndGameException {
		if (counter%WAVE_INTERVAL == 0) {sendWave();}
		for (int n = 0; n < invaders.length; n++) {
			if (invaders[n] != null) {
		
				boolean invaderDead = false;
				for (int i = invaders[n].getCoordinates().length-1; i >= 0; i--) {
					int x = invaders[n].getCoordinates()[i][0];
					int y = invaders[n].getCoordinates()[i][1];
					if (y > BOTTOM_BORDER - 30) {
						invaderDead = true; 
						break;
					} else if (grid[x][y] == BEAM || grid[x][y+1] == BEAM || grid[x][y-1] == BEAM) {
						invaders[n].hit();
						if (grid[x][y+1] == BEAM) {removeShot(x, y+1);}
						else if (grid[x][y-1] == BEAM) {removeShot(x, y-1);}
						else {removeShot(x, y);}
						if (invaders[n].dead()) {invaderDead = true; break;}
					} else if (grid[x][y] == FIGHTER && !fighter.invulnerable()) {fighter.hit();}
				}
				if (invaderDead) {
					remove(invaders[n]);
					invaders[n] = null;
				} else if (counter%STEP_INTERVAL == 0 && counter > WAVE_INTERVAL) {
					move(invaders[n]);
				}
			}
		}
	}
	
	public void removeShot(int x, int y) {
		grid[x][y] = EMPTY;
		for (int i = 0; i < shotCoords.length; i++) {
			if (shotCoords[i][1] == x && shotCoords[i][2] == y) {shotCoords[i][0] = 0;}
		}
	}
	
	//methods that deal with invaders
	public void remove(Invader invader) {
		int[][] coord = invader.getCoordinates();
		for (int i = invader.getCoordinates().length-1; i >=0; i--) {
			grid[coord[i][0]][coord[i][1]] = EMPTY;
		}
	}
	
	public void move(Invader invader) {
		int[][] coord = invader.getCoordinates();
		for (int i = invader.getCoordinates().length-1; i >=0; i--) {
			grid[coord[i][0]][coord[i][1]] = EMPTY;
			grid[coord[i][0]][coord[i][1]+INV_SPEED] = INVADER;
		}
		invader.move();
	}
	
	public void sendWave() {
		int divide = gridWidth/(WAVE_SIZE+1);
		int invader_count = 1;
		for (int i = 0; i < invaders.length; i++) {
			if (invader_count > WAVE_SIZE) {break;}
			if (invaders[i] == null) {
				placeInvader(invader_count*divide, WAVE_START_POS);
				invader_count++;
			}
		}
	}
	
	public void placeInvader(int i, int j) {
		boolean placed = false;
		int n;
		for (n = 0; n < invaders.length; n++) {
			if (invaders[n] == null) {
				invaders[n] = new Invader(i, j);
				placed = true;
				break;
			}
		}
		if (!placed) {return;}
		for (int[] coord: invaders[n].getCoordinates()) {grid[coord[0]][coord[1]] = INVADER;}
	}
}
