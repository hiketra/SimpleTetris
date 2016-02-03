import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;


/**
 * Contains the data necessary to graphically represent a block's state. ComplexShape classes
 * are composed of several block objects. The canvas for which the game is 'painted' is represented
 * by a 2D array of largely invisible block objects - their visibility toggled by whether an active
 * complex shape object currently is on their allocated coordinates or if the complex shape object is 
 * visible but not active(this is the case of blocks which have reached the bottom of the screen).
 * 
 * The data of most use to the developer is found in the instance variables which contain values for
 * the colour of the block, its coordinates, whether it is active and visible (a block that is active
 * is part of a complex shape that is movable by the user, a block that is visible is painted on the canvas).
 * 
 * @author emma
 * @see ComplexShape
 * @see Canvas
 */
public class Block {
	private Color iColour; //the colour of the block
	private int iX; //the x-coordinate in pixels of the block
	private int iY; //the y-coordinate in pixels of the block
	
	private boolean iIsActive;//whether the block forms part of the in focus complex shape
	private boolean iIsVisible;//whether the block is visible on the canvas
	
	public static final int WIDTH = 20;/*the width in pixels of all blocks - informs the graphical
	 									graphical representation of the blocks in the canvas*/
	public static final int HEIGHT = 20; /*the height in pixels of the block, plays a similar role
										to the width constant*/
	
	/**
	 * Default constructor, initialises the instance variables with the parameters passed
	 * 
	 * @param aColour
	 * @param aX
	 * @param aY
	 * @param aIsActive
	 * @param aIsVisible
	 */
	public Block(Color aColour, int aX, int aY, boolean aIsActive, boolean aIsVisible){
		iColour = aColour;
		iX = aX;
		iY = aY;
		iIsActive = aIsActive;
		iIsVisible = aIsVisible;
	}
	
	/**
	 * Clone constructor
	 * 
	 * @param block
	 */
	public Block(Block block){
		iColour = block.getColour();
		iX = block.getX();
		iY = block.getY();
		iIsVisible = block.isVisible();
		iIsActive = block.isActive();
	}
	
	/**
	 * Accessor method for the iIsActive instance variable
	 * 
	 * @param aIsActive
	 */
	public void setIsActive(boolean aIsActive){
		iIsActive = aIsActive;
	}
	
	/**
	 * Accessor method for the iColour instance variable
	 * @return iColour
	 */
	public Color getColour(){
		return iColour;
	}
	
	/**
	 * Mutator method for the x coordinate
	 * @param aX
	 */
	public void setX(int aX){
		iX=aX;
	}
	
	/**
	 * Mutator method for the y coordinate
	 * @param aY
	 */
	public void setY(int aY){
		iY=aY;
	}
	
	/**
	 * Quick method to move a block by a specified number of places to the left.
	 * @param placesToMoveBy
	 */
	public void moveLeft(int placesToMoveBy){
		setX(getX()-(placesToMoveBy*Block.WIDTH));
	}
	
	/**
	 * Quick method to move a block by a specified number of places to the right.
	 * @param placesToMoveBy
	 */
	public void moveRight(int placesToMoveBy){
		setX(getX()+(placesToMoveBy*Block.WIDTH));
	}
	
	/**
	 * Quick method to move a block by a specified number of places up. Included for consistency
	 * but should serve no practical application with-in the context of a Tetris game.
	 * 
	 * @param placesToMoveBy
	 */
	public void moveUp(int placesToMoveBy){
		setY(getY()-(placesToMoveBy*Block.HEIGHT));
	}
	
	/**
	 * Quick method to move a block by a specified number of places down.
	 * 
	 * @param placesToMoveBy
	 */
	public void moveDown(int placesToMoveBy){
		setY(getY()+(placesToMoveBy*Block.HEIGHT));
	}
	
	/**
	 * Get the x coordinate in pixels
	 * @return iX
	 */
	public int getX(){
		return iX;
	}
	
	/**
	 * Get the standardised position of x. This is the x coordinate in pixels divided by the block width.
	 * @return iX/WIDTH
	 */
	public int getStdX(){
		return iX/WIDTH;
	}
	
	/**
	 * Get the standardised position of y. This is the y coordinate in pixels divided by the block height.
	 * @return iY
	 */
	public int getY(){
		return iY;
	}
	
	/**
	 * Get the standardised position of y. This is the y coordinate in pixels divided by the block height.
	 * @return iY/HEIGHT
	 */
	public int getStdY(){
		return iY/HEIGHT;
	}
	
	/**
	 * Swing paint method. Called by the canvas class. Will print only if visible.
	 * @param g
	 * @see Canvas
	 */
	public void paintSquare(Graphics g){
		if(iIsVisible){
			g.setColor(iColour);
			g.fillRect(iX, iY, WIDTH, HEIGHT);
			g.drawRect(iX, iY, WIDTH, HEIGHT);
		}
	}
	
	/**
	 * Returns boolean value determined by whether block has positive y coordinate
	 * @return iY>-1
	 */
	public boolean isPositiveY(){
		return iY>-1;
	}
	
	/**
	 * Returns boolean value determined by whether block has positive x coordinate
	 * @return iX-WIDTH>-1
	 */
	public boolean isPositiveX(){
		return iX-WIDTH>-1;
	}
	/**
	 * Returns true if block does not exceed the width of the canvas
	 * @return iX+WIDTH<Canvas.WIDTH
	 */
	public boolean isNotMaxX(){
		return iX+WIDTH<Canvas.WIDTH;
	}
	/**
	 * Returns true if block is not exceeding maximum height of the canvas.
	 * Practically, this translates to whether the block is about to fall of the canvas/has reached the bottom or not.
	 * @return
	 */
	public boolean isNotMaxY(){
		return iY+HEIGHT<Canvas.HEIGHT;
	}
	
	/**
	 * Accessor method for visibility instance variable
	 * @return iIsVisible
	 */
	public boolean isVisible(){
		return iIsVisible;
	}
	
	/**
	 * Mutator method for visibility of block
	 * @param visible
	 */
	public void setIsVisible(boolean visible){
		iIsVisible = visible;
	}
	
	/**
	 * Returns true if the block is part of a ComplexShape that's in focus
	 * @return iIsActive
	 * @see ComplexShape
	 */
	public boolean isActive(){
		return iIsActive;
	}
	
	/**
	 * Debugging tool, prints out the standardised coordinates and pixels of the block
	 */
	public void printCoords(){
		System.out.println("\t Std X: " + getStdX() + " Std Y: " + getStdY() +
				"\n \t X: " + getX() + " Y: " + getY());
		
	}
}
