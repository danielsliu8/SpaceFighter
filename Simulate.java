import java.awt.*;
import javax.swing.*;

public class Simulate extends JPanel {
	
	private static final long serialVersionUID = 1L; //necessary, don't know why
	public static final int cellSize = 1; //leave at 1
	
	public static void main(String[] args) {
		
		//current Space, and Space used for double buffering
		Space space = new Space();
		Space dbspace = new Space();
		
		SFrame frame = new SFrame(space, "Space Invaders Clone");
		frame.setSize(Space.gridWidth*cellSize, (Space.gridHeight+10)*cellSize);
		frame.setFocusable(true);
		frame.addKeyListener(frame);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setDoubleBuffered(true);
		frame.add(panel);
		
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.black);
		canvas.setSize(Space.gridWidth*cellSize, Space.gridWidth*cellSize);
		frame.add(canvas);
		Graphics graphics = canvas.getGraphics();
		Graphics gdb = graphics.create();
		
		while(true) {
			dbspace = space;
			drawSpace(gdb, dbspace);
			
			//try updating, else end the game
			try {space = space.update();}
			catch (EndGameException e) {
				gdb.setColor(Color.red);
				gdb.drawString("YOU LOSE", 0, 0);
				try {Thread.sleep(10000);}
				catch (Exception ex) {}
				System.exit(0);
			}
			
			drawSpace(graphics, space);
			try {
				//not sure if the next line is necessary
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
				Thread.sleep(20);
			} catch(Exception e) {
				//do nothing
			}
		}
	}
	
	private static void drawSpace(Graphics graphics, Space space) {
		for (int i = 0; i < Space.gridWidth; i+=cellSize) {
			for (int j = 0; j < Space.gridHeight; j+=cellSize) {
				if (space.contents(i, j)==Space.INVADER) {
					graphics.setColor(Color.pink);
					graphics.fillRect(i, j, cellSize, cellSize);
				} else if (space.contents(i, j)==Space.FIGHTER) {
					graphics.setColor(Color.red);
					graphics.fillRect(i, j, cellSize, cellSize);
				} else if (space.contents(i, j)==Space.BEAM) {
					graphics.setColor(Color.green);
					graphics.fillRect(i, j, cellSize, cellSize);
				} else if (space.contents(i, j)==Space.BLUEBEAM) {
					graphics.setColor(Color.blue);
					graphics.fillRect(i, j, cellSize, cellSize);
				} else {
					graphics.setColor(Color.black);
					try {
						graphics.clearRect(i, j, cellSize, cellSize);
					} catch (Exception e) {}
				}
			}
		}
	}
}
