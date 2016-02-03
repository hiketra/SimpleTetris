import java.awt.Color;

/**
 * Contains the logic to rotate a Line shape in addition to
 * specifying the specific coordinates/positioning of the blocks
 * that compose said shape.
 * 
 * @author emma
 *
 */

public class Line extends ComplexShape {
	
	public static final int TOP_BLOCK=0;
	public static final int UPPER_BLOCK=1;
	public static final int LOWER_BLOCK=2;
	public static final int BOTTOM_BLOCK=3;
	
	public boolean isVertical = true;
	
	public Line(ComplexShape test){
		super(test);
	}
	
	public Line(){
		super();
	}
	
	@Override
	public void defineBlocks() {
		int xCoord = Canvas.WIDTH/2 - 10;
		int yCoord = -20;
		
		Block[] blocks = getBlocks();
		
		Color colour = getColour();
		
		blocks[TOP_BLOCK] = new Block(colour, xCoord, yCoord, true, true);
		/*[X]
		 *[ ]
		 *[ ]
		 *[ ]
		 * */
		blocks[UPPER_BLOCK] = new Block(colour, xCoord, yCoord+Block.HEIGHT, true, true);
		/*[ ]
		 *[X]
		 *[ ]
		 *[ ]
		 * */
		blocks[LOWER_BLOCK] = new Block(colour, xCoord, yCoord+(Block.HEIGHT*2), true, true);
		/*[ ]
		 *[ ]
		 *[X]
		 *[ ]
		 * */
		blocks[BOTTOM_BLOCK] = new Block(colour, xCoord, yCoord+(Block.HEIGHT*3), true, true);
		/*[ ]
		 *[ ]
		 *[ ]
		 *[X]
		 * */
	}

	@Override
	public void rotateBlocks() {
		Block[] blocks = getBlocks();
		if(isVertical){
			/*
			 * [ ][X][ ][ ]   [ ][ ][ ][ ]
			 * [ ][X][ ][ ]--\[ ][ ][ ][ ]
			 * [ ][X][ ][ ]--/[X][X][X][X]
			 * [ ][X][ ][ ]   [ ][ ][ ][ ]
			 */
			blocks[TOP_BLOCK].setX(blocks[TOP_BLOCK].getX()+(Block.WIDTH*2));
			blocks[TOP_BLOCK].setY(blocks[TOP_BLOCK].getY()+(Block.HEIGHT*2));
			
			blocks[UPPER_BLOCK].setX(blocks[UPPER_BLOCK].getX()+Block.WIDTH);
			blocks[UPPER_BLOCK].setY(blocks[UPPER_BLOCK].getY()+Block.HEIGHT);
			
			//blocks[LOWER_BLOCK].setY(blocks[LOWER_BLOCK].getY()+Block.HEIGHT);
			
			blocks[BOTTOM_BLOCK].setX(blocks[BOTTOM_BLOCK].getX()-Block.WIDTH);
			blocks[BOTTOM_BLOCK].setY(blocks[BOTTOM_BLOCK].getY()-Block.HEIGHT);
			
			isVertical = false;
		}
		else{
			/*
			 * [ ][ ][ ][ ]   [ ][X][ ][ ]
			 * [ ][ ][ ][ ]--\[ ][X][ ][ ]
			 * [X][X][X][X]--/[ ][X][ ][ ]
			 * [ ][ ][ ][ ]   [ ][X][ ][ ]
			 */
			blocks[TOP_BLOCK].setX(blocks[TOP_BLOCK].getX()-(Block.WIDTH*2));
			blocks[TOP_BLOCK].setY(blocks[TOP_BLOCK].getY()-(Block.HEIGHT*2));
			
			blocks[UPPER_BLOCK].setX(blocks[UPPER_BLOCK].getX()-Block.WIDTH);
			blocks[UPPER_BLOCK].setY(blocks[UPPER_BLOCK].getY()-Block.HEIGHT);
			
			blocks[BOTTOM_BLOCK].setX(blocks[BOTTOM_BLOCK].getX()+Block.HEIGHT);
			blocks[BOTTOM_BLOCK].setY(blocks[BOTTOM_BLOCK].getY()+Block.HEIGHT);
			
			isVertical = true;
		}

	}

	@Override
	public void printCoords() {
		//implementation not necessary

	}
	
	public boolean isValidRotation(Block[][] blocksInPlace){
		if(isVertical){
			Line verticalToHorizontal = new Line();
			verticalToHorizontal.setBlocks(getBlocks());
			if(!verticalToHorizontal.isValidRightMove(blocksInPlace)){
				return false;
			}
			else{
				verticalToHorizontal.moveRight();
				boolean canMoveRight = verticalToHorizontal.isValidRightMove(blocksInPlace);
				verticalToHorizontal.moveLeft();
				if(!canMoveRight){
					return false;
				}
			}
		}
		else{
			Line horizontalToVertical = new Line();
			horizontalToVertical.setBlocks(getBlocks());
			return horizontalToVertical.isValidDownwardMove(blocksInPlace);
		}
		return true;
	}
	
	/**
	 * Line shapes only have two rotation states - vertical or not vertical(horizontal), this is
	 * specified in a boolean instance variable and informs the rotation logic.
	 * 
	 * @param aIsVertical
	 */
	public void setIsVertical(boolean aIsVertical){
		isVertical = aIsVertical;
	}
	
}
