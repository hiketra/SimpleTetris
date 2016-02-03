import java.awt.Color;
import java.awt.Graphics;


/**
 * Represents the shapes that form from a series of blocks.
 * 
 * @see Block
 * @see LShape
 * @see TShape
 * @see Line
 * @see Cube
 * 
 * @author emma
 */
public abstract class ComplexShape {
	
	private Color iColour; //the colour of the shape
	private Block[] iBlocks; //the array of block objects that compose the shape
	private boolean isActive; //
	private int[][] iStdCoords = new int[4][2];//[x][0] = x-coord, [x][1] = y-coord where x corresponds to block index in iBlocks
	private int[][] iCoords = new int [4][4];//same format as iStdCoords but contains the pixel coordinates instead
	
	/**
	 * Default constructor. Randomly generates the colour and defines coordinates to be
	 * at the top of the screen and horizontally center.
	 * 
	 * isActive is set to true by default since the general context of a Tetris game implies
	 * that when wanting to initialise a new ComplexShape it will be the next active block.
	 */
	public ComplexShape(){
		iBlocks = new Block[4];
		iColour = ValidColour.randomValidColour().getColour();
		defineBlocks();
		setStdCoords();
		setCoords();
		isActive = true;
	}
	
	/**
	 * Clone constructor.
	 * 
	 * @param toCopy
	 */
	public ComplexShape(ComplexShape toCopy){
		this.iColour = toCopy.getColour();
		this.iBlocks = toCopy.getBlocks();
		this.isActive = toCopy.isActive();
		this.iStdCoords = toCopy.getStdCoords();
		this.iCoords = toCopy.getCoords();
	}
	
	
	/**
	 * Accessor method for the array of Block objects that compose the shape
	 * @return
	 */
	public Block[] getBlocks(){
		return iBlocks;
	}
	
	/**
	 * Mutator method for the array of Block objects that compose the shape
	 * @param blocks
	 */
	public void setBlocks(Block[] blocks){
		iBlocks= blocks;
	}
	
	/**
	 * Set and update the standard coords based on the location of the Block objects
	 */
	public void setStdCoords(){
		for(int i=0; i<4; i++){
			iStdCoords[i][0] = iBlocks[i].getStdX();
			iStdCoords[i][1] = iBlocks[i].getStdY();
		}
	}
	
	/**
	 * Set and update the normal coords based on the location of the Block objects
	 */
	public void setCoords(){
		for(int i=0; i<4; i++){
			iCoords[i][0] = iBlocks[i].getX();
			iCoords[i][1] = iBlocks[i].getY();
		}
	}
	
	/**
	 * Get the standard coords array
	 * @return iStdCoords
	 */
	public int[][] getStdCoords(){
		setStdCoords();
		return iStdCoords;
	}
	
	/**
	 * Get the coords array
	 * @return iCoords
	 */
	public int[][] getCoords(){
		setCoords();
		return iCoords;
	}
	
	/**
	 * Accessor method for colour of shape
	 * @return iColour
	 */
	public Color getColour(){
		return iColour;
	}
	
	/**
	 * Define the positioning of the blocks in the block array based on the shape
	 */
	public abstract void defineBlocks();
	
	/**
	 * Perform a rotation on the blocks of the shape based on its shape
	 */
	public abstract void rotateBlocks();
	
	/**
	 * Check if a rotation can be performed based on the location of the shape on the board
	 * 
	 * @param blocksInPlace
	 */
	public abstract boolean isValidRotation(Block[][] blocksInPlace);
	
	/**
	 * Debugging tool. Prints the coords of all the blocks of the complex shape.
	 */
	public abstract void printCoords();
	
	/**
	 * Moves the entire shape to the left by 1 place
	 */
	public void moveLeft(){
		for(int i=0; i<4; i++){
			iBlocks[i].moveLeft(1);
		}
	}
	
	/**
	 * Moves the entire shape to the right by 1 place
	 */
	public void moveRight(){
		for(int i=0; i<4; i++){
			iBlocks[i].moveRight(1);
		}
	}
	
	/**
	 * Moves the entire shape down by 1 place
	 */
	public void moveDown(){
		for(int i=0; i<4; i++){
			iBlocks[i].moveDown(1);
		}
	}
	
	/**
	 * Checks whether a downward move can be performed on the canvas
	 * without any collisions or exiting the boundaries of the canvas
	 * 
	 * @param blocksInPlace
	 */
	public boolean isValidDownwardMove(Block[][] blocksInPlace){
		Block maxYBlock = getBlockAtIndex(getMaxYBlockIndex());
		if(!maxYBlock.isNotMaxY()){
			//maxYBlock is at bottom of screen!
			return false;
		}
		else{
			/*check maxYBlocks(e.g blocks closest to bottom of canvas
			 * [there can be multiple blocks closest to the bottom]), 
			 * then see whether there is a visible block below them
			 * in the blocksInPlace array using getStdX() and getStdY()-1 as indexes
			 */
			Block[] bottomBlocks = getBottomBlocks();
			for(int i=0; i<4; i++){
				if(bottomBlocks[i]!=null){
					int xCoord = bottomBlocks[i].getStdX();
					int yCoord = bottomBlocks[i].getStdY() + 1;
					if(blocksInPlace[xCoord][yCoord].isVisible()){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 *	Checks whether a move to the right can be performed on the canvas
	 * without any collisions or exiting the boundaries of the canvas
	 * @param blocksInPlace
	 */
	public boolean isValidRightMove(Block[][] blocksInPlace){
		Block maxXBlock = getBlockAtIndex(getMaxXBlockIndex());
		if(!maxXBlock.isNotMaxX()){
			return false;
		}
		else{
			Block[] rightBlocks = getRightBlocks();
			for(int i=0; i<4; i++){
				if(rightBlocks[i]!=null){
					int xCoord = rightBlocks[i].getStdX()+1;
					int yCoord = rightBlocks[i].getStdY();
					if(blocksInPlace[xCoord][yCoord].isVisible()){
						return false;
					}
 				}
			}
		}
		return true;
	}
	
	/**
	 *	Checks whether a move to the left can be performed on the canvas
	 * without any collisions or exiting the boundaries of the canvas
	 * @param blocksInPlace
	 */
	public boolean isValidLeftMove(Block[][] blocksInPlace){
		Block minXBlock = getBlockAtIndex(getMinXBlockIndex());
		if(!minXBlock.isPositiveX()){
			return false;
		}
		else{
			Block[] leftBlocks = getLeftBlocks();
			for(int i=0; i<4; i++){
				if(leftBlocks[i]!=null){
					int xCoord = leftBlocks[i].getStdX() - 1;
					int yCoord = leftBlocks[i].getStdY();
					if(blocksInPlace[xCoord][yCoord].isVisible()){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns the index of the block with the greatest y-coordinate
	 * 
	 * @return indexValue
	 */
	public int getMaxYBlockIndex(){
		int max = -1;
		int indexValue = -1;
		for(int i=0; i<4; i++){
			int current = iBlocks[i].getStdY();
			if(max<current){
				max=current;
				indexValue = i;
			}
		}
		return indexValue;
	}
	
	/**
	 * Returns the index of the block with the greatest (right-most) x-coordinate
	 * @return indexValue
	 */
	public int getMaxXBlockIndex(){
		int max = -1;
		int indexValue = -1;
		for(int i=0; i<4; i++){
			int current = iBlocks[i].getStdX();
			if(max<current){
				max=current;
				indexValue = i;
			}
		}
		return indexValue;
	}
	
	/**
	 * Returns the standard y coord of the block with the greatest y-coordinate
	 * @return max
	 */
	public int getMaxStdYCoord(){
		int max = -1;
		for(int i=0; i<4; i++){
			int current = iBlocks[i].getStdY();
			if(max<current){
				max=current;
			}
		}
		return max;
	}
	
	/**
	 * Returns the index of the block with the smallest (left-most) x-coordinate
	 * @return index
	 */
	public int getMinXBlockIndex(){
		int min = getMaxStdXCoord();
		int index = -1;
		for(int i=0; i<4; i++){
			int current = iBlocks[i].getStdX();
			if(min>=current){
				min=current;
				index = i;
			}
		}
			
		return index;
	}
	
	/**
	 * Returns the standard coordinate of the block with the smallest Y coordinate
	 * @return min
	 */
	public int getMinStdYCoord(){
		int min = getMaxStdYCoord();
		for(int i=0; i<4; i++){
			int current = iBlocks[i].getStdY();
			if(min>=current){
				min=current;
			}
		}
			
		return min;
	}
	
	/**
	 * Returns the standard coordinate of the block with the largest X coordinate
	 * @return max
	 */
	public int getMaxStdXCoord(){
		int max = -1;
		for(int i=0; i<4; i++){
			int current = iBlocks[i].getStdX();
			if(max<current){
				max=current;
			}
		}
		return max;
	}
	
	/**
	 * Returns the standard coordinate of the block with the smallest X coordinate
	 * @return min
	 */
	public int getMinStdXCoord(){
		int min = getMaxStdYCoord();
		for(int i=0; i<4; i++){
			int current = iBlocks[i].getStdX();
			if(min>=current){
				min=current;
			}
		}
			
		return min;
	}
	
	/**
	 * Returns an array of all blocks that are left-most. Entries for block which are not
	 * left-most are null.
	 * @return blocks
	 */
	public Block[] getLeftBlocks(){
		Block[] blocks = {null, null, null, null};
		
		for(int i=0; i<4; i++){
			Block focusBlock = iBlocks[i];
			int yCoordOfFocusBlock = focusBlock.getStdY();
			int xCoordOfFocusBlock = focusBlock.getStdX();
			
			boolean isRightBlock = true;
			
			for(int j=0; j<4; j++){
				if(i!=j && yCoordOfFocusBlock==iBlocks[j].getStdY() && xCoordOfFocusBlock>iBlocks[j].getStdX()){
					isRightBlock = false;
				}
			}
			
			if(isRightBlock){
				blocks[i] = focusBlock;
			}
		}
		
		return blocks;
	}
	
	/**
	 * Returns an array of all blocks that are right-most. Entries for block which are not
	 * right-most are null.
	 * @return blocks
	 */
	public Block[] getRightBlocks(){
		Block[] blocks = {null, null, null, null};
		
		for(int i=0; i<4; i++){
			Block focusBlock = iBlocks[i];
			int yCoordOfFocusBlock = focusBlock.getStdY();
			int xCoordOfFocusBlock = focusBlock.getStdX();
			
			boolean isRightBlock = true;
			
			for(int j=0; j<4; j++){
				if(i!=j && yCoordOfFocusBlock==iBlocks[j].getStdY() && xCoordOfFocusBlock<iBlocks[j].getStdX()){
					isRightBlock = false;
				}
			}
			
			if(isRightBlock){
				blocks[i] = focusBlock;
			}
		}
		
		return blocks;
	}
	/**
	 * Returns an array of all blocks that are downward-most. Entries for block which are not
	 * downward-most are null.
	 * @return blocks
	 */
	public Block[] getBottomBlocks(){
		Block[] blocks = {null, null, null, null};
		
		for(int i=0; i<4; i++){
			Block focusBlock = iBlocks[i];
			int yCoordOfFocusBlock = focusBlock.getStdY();
			int xCoordOfFocusBlock = focusBlock.getStdX();
			
			boolean isBottomBlock = true;
			
			for(int j=0; j<4; j++){
				if(i!=j && xCoordOfFocusBlock==iBlocks[j].getStdX() && yCoordOfFocusBlock<iBlocks[j].getStdY()){
					isBottomBlock=false;
				}
			}
			
			if(isBottomBlock){
				blocks[i] = focusBlock;
			}
		}
		
		return blocks;
	}
	
	/**
	 * Get the maximum y-coordinate of the shape in non-standardised coords(pixel location)
	 */
	public int getMaxYCoord(){
		return getMaxStdYCoord()*Block.HEIGHT;
	}
	/**
	 * Get the minimum y-coordinate of the shape in non-standardised coords(pixel location)
	 */
	public  int getMinYCoord(){
		return getMinYCoord()*Block.HEIGHT;
	}
	/**
	 * Get the maximum x-coordinate of the shape in non-standardised coords(pixel location)
	 */
	public int getMaxXCoord(){
		return getMaxStdXCoord()*Block.WIDTH;
	}
	
	/**
	 * Get the minimum x-coordinate of the shape in non-standardised coords(pixel location_
	 */
	public int getMinXCoord(){
		return getMinStdXCoord()*Block.WIDTH;
	}
	
	/**
	 * Gets the block at a specific index in the blocks array
	 * @param index
	 * @return iBlocks[index]
	 */
	public Block getBlockAtIndex(int index){
		return iBlocks[index];
	}
	
	/**
	 * Mutator method for isActive boolean - determines whether shape is moving or not
	 * @param isActive
	 */
	public void setIsActive(boolean isActive){
		for(int i=0; i<4; i++){
			iBlocks[i].setIsActive(isActive);
		}
		this.isActive=isActive;
	}
	
	/**
	 * Accessor method for isActive boolean - specifies whether shape is moving or not
	 * @return
	 */
	public boolean isActive(){
		return isActive;
	}
	
	/**
	 * Logic for painting shape.
	 * @param g
	 */
	public void paintShape(Graphics g){
		for(int i=0; i<4; i++){
			iBlocks[i].paintSquare(g);
		}
	}
}
