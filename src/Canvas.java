import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;


/**
 * Represents the movement of the blocks and shapes, in addition to registering
 * the user's key presses. Forms part of the MainWindow
 * @author emma
 * @see MainWindow
 * @see Block
 * @see ComplexShape
 */
public class Canvas extends JPanel {
	
	public static final int WIDTH = 260; //the width of the canvas in pixels
	public static final int HEIGHT = 640; //the height of the canvas in pixels
	
	private int lineCount = 0; //the number of lines cleared
	
	ComplexShape activeBlock = new Line(); //first shape will always be a line
	
	private Block[][] blocksInPlace = new Block[13][32]; //array of blocks covering every possible coordinate
	
	/**
	 * Defines mouse listeners and key listeners in addition to initialising the blocksInPlace array. Also
	 * defines properties of the JPanel it extends.
	 */
	public Canvas(){
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				requestFocusInWindow();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				handleKeyPress(e);
			}
		});
		
		for(int i=0; i<13; i++){
			for(int j=0; j<32; j++){
				blocksInPlace[i][j] = new Block(Color.BLUE, 0, 0, false, false);
				//passing dummy default colour
			}
		}
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);
		setFocusable(true);
		setBackground(Color.WHITE);
	}
	
	/**
	 * Returns the number of lines cleared
	 * @return lineCount
	 */
	public int getLineCount(){
		return lineCount;
	}
	
	/**
	 * Contains all the logic to handle key presses, such as the logic for moving a shape
	 * to the left when the left key is pressed.
	 * 
	 * @param e
	 */
	public void handleKeyPress(KeyEvent e){
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT){
			//move the activeBlock to the left
			if(activeBlock.isValidLeftMove(blocksInPlace)){
				activeBlock.moveLeft();
				repaint();
			}
		}
		if (key == KeyEvent.VK_RIGHT){
			//move the activeBlock to the right
			if(activeBlock.isValidRightMove(blocksInPlace)){
				activeBlock.moveRight();
				repaint();
			}
		}
		if (key == KeyEvent.VK_DOWN){
			//move the activeBlock down
			if(activeBlock.isValidDownwardMove(blocksInPlace)){
				activeBlock.moveDown();
				repaint();
			}
		}
		if(key == KeyEvent.VK_UP){
			//rotate the activeBlock
			if(activeBlock.isValidRotation(blocksInPlace)){
				activeBlock.rotateBlocks();
				repaint();
			}
		}
		if(key == KeyEvent.VK_SPACE){
			//move the block down continuously until there are no more valid moves to be made
			while(activeBlock.isValidDownwardMove(blocksInPlace)){
				activeBlock.moveDown();
				repaint();
			}
		}
	}
	
	/**
	 * Move the activeBlock down one block.
	 */
	public void moveActiveBlock(){
		detectFullLine();//check if there is a full line
		if(activeBlock.isValidDownwardMove(blocksInPlace) ){
			activeBlock.moveDown();
			repaint();
		}
	}
	
	/**
	 * Logic to detect whether there is a full line. If there is a full line the appropriate
	 * blocks will be eliminated and the blocks above the line will adjust appropriately. In addition
	 * the lineCount will be updated.
	 */
	public void detectFullLine(){
		for(int i=0; i<32; i++){
			//looping through each horizontal line
			boolean isFullLine = true;
			for(int j=0;j<13; j++){
				if(!blocksInPlace[j][i].isVisible()){
					//if one of the blocks in the line is not visible
					isFullLine = false;
					//then there is no full line
				}
			}
			if(isFullLine){
				//if there is a full line
				lineCount++;//update the line count
				for(int a = i; a>0; a--){
					//loop through the blocksInPlace from the index of the full line upwards
					if(a<i){
						for(int b=0; b<13; b++){
							//move the blocks downward
							Block movedDownBlock = blocksInPlace[b][a];
							movedDownBlock.moveDown(1);
							blocksInPlace[b][a+1] = movedDownBlock;
							repaint();
						}
					}
					else{
						for(int b=0; b<13; b++){
							//make the detected line invisible and no longer active
							blocksInPlace[b][a].setIsActive(false);
							blocksInPlace[b][a].setIsVisible(false);
							repaint();
						}
					}
					}
				}
			}
		}
	/**
	 * Checking to see whether the activeBlock can continue to be 'active'. If it is
	 * no longer a valid activeBlock it will set in a fixed position and a new activeBlock
	 * will be generated. Such would be the case if the activeBlock had reached the bottom of 
	 * the canvas.
	 * 
	 * Returns true if it remains a valid activeBlock
	 */
	public boolean checkActiveBlockValid(){
		if(!activeBlock.isValidDownwardMove(blocksInPlace)){
			//if a valid downward move cannot be performed then it is no longer valid as an activeBlock
			int minStdYCoord = activeBlock.getMinStdYCoord();
			if(minStdYCoord<1){
				return false;
			}
			//if we have hit the last row it can no longer be a valid active block
			activeBlock.setIsActive(false);
			//deactivate
			int[][] stdCoords = activeBlock.getStdCoords();
			Block[] blocks = activeBlock.getBlocks();
			for(int i=0; i<4; i++){
				blocksInPlace[stdCoords[i][0]][stdCoords[i][1]] = blocks[i];
			}
			//place in static blocks array
			Random pickNextComplexShape = new Random();
			int shapeToUse = pickNextComplexShape.nextInt(4);
			switch(shapeToUse){
			case 0:
				activeBlock = new Cube();
				break;
			case 1:
				activeBlock = new LShape();
				break;
			case 2:
				activeBlock = new Line();
				break;
			case 3:
				activeBlock = new TShape();
				break;
			}
			if(!activeBlock.isValidDownwardMove(blocksInPlace)){
				minStdYCoord = activeBlock.getMinStdYCoord();
				if(minStdYCoord<1){
					return false;
				}
				}
			//create new active block
		}
		return true;
	}
	
	
	
	/**
	 * Generate a random x or y coord
	 * @param isXCoord
	 */
	public static int generateRandomCoord(boolean isXCoord){
		Random generator = new Random();
		if(isXCoord){
			return (generator.nextInt(Canvas.WIDTH/Block.WIDTH)*Block.WIDTH) -1;
		}
		else{
			return generator.nextInt(Canvas.HEIGHT/Block.HEIGHT)*Block.HEIGHT;
		}
		}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.WHITE);
		activeBlock.paintShape(g);
		for(int i=0; i<13; i++){
			for(int j=0; j<32; j++){
				blocksInPlace[i][j].paintSquare(g);
			}
		}
	}

	/**
	 * Returns the activeBlock
	 * @return activeBlock
	 */
	public ComplexShape getActiveBlock() {
		return activeBlock;
		
	}
}
