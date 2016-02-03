import java.awt.Color;


/**
 * Contains the logic to rotate an LShape shape in addition to
 * specifying the specific coordinates/positioning of the blocks
 * that compose said shape.
 * 
 * @author emma
 *
 */
public class LShape extends ComplexShape {
	
	private int rotationState = ANTICLOCK_0;
	
	public static final int ANTICLOCK_0 =0;
	public static final int ANTICLOCK_90 = 1;
	public static final int ANTICLOCK_180 = 2;
	public static final int ANTICLOCK_270 = 3;
	
	public static final int TOP_BLOCK_LEFT = 0;
	public static final int MIDDLE_BLOCK_LEFT = 1;
	public static final int BOTTOM_BLOCK_LEFT = 2;
	public static final int BOTTOM_BLOCK_RIGHT = 3;
	
	@Override
	public void defineBlocks() {
		
		int xCoord = Canvas.WIDTH/2 - 10;
		int yCoord = 0;
		
		Block[] blocks = getBlocks();
		Color colour = getColour();
		
		blocks[TOP_BLOCK_LEFT] = new Block(colour, xCoord, yCoord, true, true);
		/* [X]
		 * [ ]
		 * [ ][ ]
		 */
		blocks[MIDDLE_BLOCK_LEFT] = new Block(colour, xCoord, yCoord+Block.HEIGHT, true, true);
		/*[ ]
		 *[X]
		 *[ ][ ]
		 * */
		blocks[BOTTOM_BLOCK_LEFT] = new Block(colour, xCoord, yCoord+(2*Block.HEIGHT), true, true);
		/*[ ]
		 *[ ]
		 *[X][ ] 
		 */
		blocks[BOTTOM_BLOCK_RIGHT] = new Block(colour, xCoord + Block.WIDTH, yCoord+(2*Block.HEIGHT), true, true);
		/*[ ]
		 *[ ]
		 *[ ][X]
		 */
		setBlocks(blocks);
	}

	@Override
	public void rotateBlocks() {
		Block[] blocks = getBlocks();
		
		switch(rotationState){
		case ANTICLOCK_0:
			/*
			 *[ ][X][ ]    [ ][ ][X]
			 *[ ][X][ ] -->[X][X][X]
			 *[ ][X][X]    [ ][ ][ ]
			 *
			 * */
			blocks[TOP_BLOCK_LEFT].moveLeft(1);
			blocks[TOP_BLOCK_LEFT].setY(blocks[TOP_BLOCK_LEFT].getY()+Block.HEIGHT);
			
			blocks[BOTTOM_BLOCK_LEFT].moveRight(1);
			blocks[BOTTOM_BLOCK_LEFT].setY(blocks[BOTTOM_BLOCK_LEFT].getY()-Block.HEIGHT);
			
			blocks[BOTTOM_BLOCK_RIGHT].setY(blocks[BOTTOM_BLOCK_RIGHT].getY()-(Block.HEIGHT*2));
			break;
		case ANTICLOCK_90:
			/*
			 *[ ][ ][X]    [X][X][ ]
			 *[X][X][X] -->[ ][X][ ]
			 *[ ][ ][ ]    [ ][X][ ]
			 *
			 * */
			blocks[TOP_BLOCK_LEFT].moveRight(1);
			blocks[TOP_BLOCK_LEFT].moveDown(1);
			
			blocks[BOTTOM_BLOCK_LEFT].moveLeft(1);
			blocks[BOTTOM_BLOCK_LEFT].moveUp(1);
			
			blocks[BOTTOM_BLOCK_RIGHT].moveLeft(2);
			break;
		case ANTICLOCK_180:
			/*
			 *[X][X][ ]    [ ][ ][ ]
			 *[ ][X][ ] -->[X][X][X]
			 *[ ][X][ ]    [X][ ][ ]
			 *
			 * */
			blocks[TOP_BLOCK_LEFT].moveRight(1);
			blocks[TOP_BLOCK_LEFT].moveUp(1);
			
			blocks[BOTTOM_BLOCK_LEFT].moveLeft(1);
			blocks[BOTTOM_BLOCK_LEFT].moveDown(1);
			
			blocks[BOTTOM_BLOCK_RIGHT].moveDown(2);
			break;
		case ANTICLOCK_270:
			/*
			 *[ ][ ][ ]    [ ][X][ ]
			 *[X][X][X] -->[ ][X][ ]
			 *[X][ ][ ]    [ ][X][X]
			 *
			 * */
			blocks[TOP_BLOCK_LEFT].moveLeft(1);
			blocks[TOP_BLOCK_LEFT].moveUp(1);
			
			blocks[BOTTOM_BLOCK_LEFT].moveRight(1);
			blocks[BOTTOM_BLOCK_LEFT].moveDown(1);
			
			blocks[BOTTOM_BLOCK_RIGHT].moveRight(2);
			rotationState=-1;
			break;
		}
		rotationState++;
		
		setBlocks(blocks);

	}

	@Override
	public void printCoords() {
		Block[] blocks = getBlocks();
		
		System.out.println("Top block Coords: \n");
		blocks[TOP_BLOCK_LEFT].printCoords();
		
		System.out.println("Middle block Coords: \n");
		blocks[MIDDLE_BLOCK_LEFT].printCoords();
		
		System.out.println("Bottom block left Coords: \n");
		blocks[BOTTOM_BLOCK_LEFT].printCoords();
		
		System.out.println("Bottom block right Coords: \n");
		blocks[BOTTOM_BLOCK_RIGHT].printCoords();

	}

	@Override
	public boolean isValidRotation(Block[][] blocksInPlace) {
		switch(rotationState){
		case ANTICLOCK_0:
			if(isValidLeftMove(blocksInPlace)){
				if(isValidDownwardMove(blocksInPlace)){
					return isValidRightMove(blocksInPlace);
					}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		case ANTICLOCK_90:
			if(isValidLeftMove(blocksInPlace)){
				return isValidDownwardMove(blocksInPlace);
			}
			else{
				return false;
			}
		case ANTICLOCK_180:
			if(isValidRightMove(blocksInPlace)){
				return isValidDownwardMove(blocksInPlace);
			}
			else{
				return false;
			}
		case ANTICLOCK_270:
			return true;
		}
		return false;
	}
	

}
