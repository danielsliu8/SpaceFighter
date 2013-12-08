/*
 *  I made a JFrame that implements a KeyListener. Translates keyboard commands to Space methods.
 */
import java.awt.event.*;
import javax.swing.*;

public class SFrame extends JFrame implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	private Space space;
	private boolean[] keys;
	/*  Keys:
	 *  (0) W - move player up
	 *  (1) A - move player left
	 *  (2) S - move player down
	 *  (3) D - move player right
	 *  
	 *  (4) J - fire once
	 *  (5) K - fire thrice
	 *  
	 *  (6) Q - quit
	 *  
	 */
	
	public SFrame(Space sub, String title) {
		super(title);
		space = sub;
		keys = new boolean[7];
	}
	
	private void action() {
		if (keys[0]) {space.moveFighterUp();}
		if (keys[1]) {space.moveFighterDown();}
		if (keys[2]) {space.moveFighterLeft();}
		if (keys[3]) {space.moveFighterRight();}
		if (keys[4]) {space.fighterShoot();}
		if (keys[5]) {space.fighterTriple();}
		if (keys[6]) {System.exit(0);}
	}
	
	//controls for multiple key presses
	private void setKeys(KeyEvent event, boolean live) {
		if (event.getKeyCode()==KeyEvent.VK_W) {keys[0] = live;}
		if (event.getKeyCode()==KeyEvent.VK_S) {keys[1] = live;}
		if (event.getKeyCode()==KeyEvent.VK_A) {keys[2] = live;}
		if (event.getKeyCode()==KeyEvent.VK_D) {keys[3] = live;}
		if (event.getKeyCode()==KeyEvent.VK_J) {keys[4] = live;}
		if (event.getKeyCode()==KeyEvent.VK_K) {keys[5] = live;}
		if (event.getKeyCode()==KeyEvent.VK_Q) {keys[6] = live;}
		action();
	}
	
	public void keyPressed(KeyEvent event) {setKeys(event, true);}
	public void keyReleased(KeyEvent event) {setKeys(event, false);}
	public void keyTyped(KeyEvent event) {}
}
