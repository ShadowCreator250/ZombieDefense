import java.util.ArrayList;
import java.util.List;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * Defines the plan for all path cells, that result in the whole path. Needs to
 * be specialized to one of its subclasses.
 */
public abstract class PathCell extends Cell {

	private static final String DEAD_END_PATH_IMAGE_NAME = "dead_end-path.png";
	private static final String STRAIGHT_PATH_IMAGE_NAME = "straight-path.png";
	private static final String CURVE_PATH_IMAGE_NAME = "curve-path.png";
	private static final String T_PATH_IMAGE_NAME = "t-path.png";
	private static final String CROSS_PATH_IMAGE_NAME = "cross-path.png";
	private static final String DOT_PATH_IMAGE_NAME = "dot-path.png";

	public static final int PATH_WIDTH = 48;

	private PathSectionType pathSectionType;
	private int hCost;
	private int gCost;
	private PathCell parent;

	public enum PathSectionType {
		DOT(0), DEAD_END(1), STRAIGHT(2), CURVE(2), T(3), CROSS(4);

		private final int conectionCount;

		private PathSectionType(int conectionCount) {
			this.conectionCount = conectionCount;
		}

		public int getConectionCount() {
			return conectionCount;
		}
	}

	public PathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}

	/**
	 * @see checkAddObstacleClick()
	 */
	@Override
	public void act() {
		checkAddObstacleClick();
	}

	/**
	 * checks if this PathCell is clicked with a tool to place an {@link Obstacle}
	 * and places it
	 */
	private void checkAddObstacleClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1 && Greenfoot.getMouseInfo() != null) {
			if(!(getWorld().getObjectsAt(getX(), getY(), Obstacle.class).size() > 0)) {
				CursorImage.MouseState mouseState = getWorld().getCursorImage().getMouseState();
				if(mouseState == CursorImage.MouseState.PLACE_MINE_FIELD && getWorld().haveEnoughCoins(MineField.PRICE)) {
					getWorld().addObject(new MineField(), getX(), getY());
					getWorld().getCoinsCounter().add(-MineField.PRICE);
				} else if(mouseState == CursorImage.MouseState.PLACE_SLIME_FIELD && getWorld().haveEnoughCoins(SlimeField.PRICE)) {
					getWorld().addObject(new SlimeField(), getX(), getY());
					getWorld().getCoinsCounter().add(-SlimeField.PRICE);
				}
			}
		}
	}

	/**
	 * sets the image rotation to a multiple of 90 degrees<br>
	 * 0-left, 1-down, 2-left, 3-up
	 * 
	 * @param rotation the factor for 90
	 */
	public void setImageRotation(int rotation) {
		GreenfootImage img = getImage();
		img.rotate(rotation * 90);
		setImage(img);

	}

	/**
	 * 
	 * @return a List containing the {@link PathCell}s that neighbour this cell
	 */
	public List<PathCell> getNeighbouringPathCells() {
		List<Cell> neighbourCells = getNeighbourCells(false);
		List<PathCell> neighbourPathCells = new ArrayList<>(neighbourCells.size());
		for (Cell cell : neighbourCells) {
			if(cell instanceof PathCell) {
				neighbourPathCells.add((PathCell) cell);
			}
		}
		return neighbourPathCells;
	}

	/**
	 * evaluate to which sides there exists a {@link PathCell} as neighbour
	 * 
	 * [0] - to the right<br>
	 * [1] - below<br>
	 * [2] - to the left<br>
	 * [3] - above<br>
	 * clockwise rotation
	 * 
	 * @return the array representing the four sides
	 */
	public boolean[] evaluateExistenceOfNeighbouringPathCells() {
		boolean[] result = { false, false, false, false };
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		for (PathCell pathCell : neighbourPathCells) {
			int deltaX = pathCell.getGridX() - this.getGridX();
			int deltaY = pathCell.getGridY() - this.getGridY();
			int index = Math.abs((-2 * deltaY) + 1 - deltaX);
			result[index] = true;
		}
		return result;
	}

	/**
	 * evaluate the existence of walls that could neighbour the cell<br>
	 * <br>
	 * [0] - to the right<br>
	 * [1] - below<br>
	 * [2] - to the left<br>
	 * [3] - above<br>
	 * (clockwise rotation)<br>
	 * <br>
	 * if left & above = only left is true<br>
	 * if left & bottom = only left is true<br>
	 * if right & above = only right is true<br>
	 * if right & below = only right is true<br>
	 * 
	 * @return the array that indicates where a wall is.
	 */
	public boolean[] evaluateExistenceOfWorldEdgesAsNeighbours() {
		boolean[] result = { false, false, false, false };
		if((getGridX() - 1 == -1 && getGridY() - 1 == -1) || (getGridX() - 1 == -1 && getGridY() + 1 == GameWorld.GRID_SIZE_Y)) {
			// left side top/bottom
			result[2] = true;
		} else if((getGridX() + 1 == GameWorld.GRID_SIZE_X && getGridY() - 1 == -1)
				|| (getGridX() + 1 == GameWorld.GRID_SIZE_X && getGridY() + 1 == GameWorld.GRID_SIZE_Y)) {
			// right side top/bottom
			result[0] = true;
		} else {
			if(getGridX() - 1 == -1) { // left border
				result[2] = true;
			}
			if(getGridY() - 1 == -1) { // top border
				result[3] = true;
			}
			if(getGridX() + 1 == GameWorld.GRID_SIZE_X) { // right border
				result[0] = true;
			}
			if(getGridY() + 1 == GameWorld.GRID_SIZE_Y) { // bottom border
				result[1] = true;
			}
		}

		return result;
	}

	/**
	 * evaluates to which side of the cell is a neighbour or a world edge<br>
	 * * <br>
	 * [0] - to the right<br>
	 * [1] - below<br>
	 * [2] - to the left<br>
	 * [3] - above<br>
	 * 
	 * @return an array representing that
	 */
	public boolean[] logicalOrNeighbouringPathCellsWithWorldEdges() {
		boolean[] result = evaluateExistenceOfNeighbouringPathCells();
		boolean[] we = evaluateExistenceOfWorldEdgesAsNeighbours();
		for (int i = 0; i < result.length; i++) {
			result[i] = result[i] || we[i];
		}
		return result;
	}

	/**
	 * evaluate the type of path section that influences the visualization
	 */
	public abstract void evaluatePathSectionType();

	/**
	 * evaluate the type of path section that influences the visualization based on
	 * the count of neighbours and in which direction they are located
	 */
	public void evaluatePathSectionType(int neighboursCount, boolean[] neighboursExisting) {
		switch (neighboursCount) {
			case 1:
				setPathSectionType(PathSectionType.DEAD_END);
				setImage(new ImageCombiner(getBgImageName(), DEAD_END_PATH_IMAGE_NAME).combineToGFImg());
				for (int i = 0; i < neighboursExisting.length; i++) {
					if(neighboursExisting[i]) {
						setImageRotation(i);
					}
				}
				break;
			case 2:
				if((neighboursExisting[0] && neighboursExisting[2]) || (neighboursExisting[1] && neighboursExisting[3])) {
					setPathSectionType(PathSectionType.STRAIGHT);
					setImage(new ImageCombiner(getBgImageName(), STRAIGHT_PATH_IMAGE_NAME).combineToGFImg());
					if(neighboursExisting[1]) {
						setImageRotation(1);
					} else {
						setImageRotation(0);
					}
				} else {
					setPathSectionType(PathSectionType.CURVE);
					setImage(new ImageCombiner(getBgImageName(), CURVE_PATH_IMAGE_NAME).combineToGFImg());
					for (int i = 0; i < neighboursExisting.length; i++) {
						if(neighboursExisting[i] && neighboursExisting[(i + 1) % neighboursExisting.length]) {
							setImageRotation(i);
						}
					}
				}
				break;
			case 3:
				setPathSectionType(PathSectionType.T);
				setImage(new ImageCombiner(getBgImageName(), T_PATH_IMAGE_NAME).combineToGFImg());
				for (int i = 1; i < neighboursExisting.length + 1; i++) {
					if(neighboursExisting[(neighboursExisting.length + i - 1) % neighboursExisting.length]
							&& neighboursExisting[i % neighboursExisting.length] && neighboursExisting[(i + 1) % neighboursExisting.length]) {
						setImageRotation(i - 1);
					}
				}
				break;
			case 4:
			case 5:
				setPathSectionType(PathSectionType.CROSS);
				setImage(new ImageCombiner(getBgImageName(), CROSS_PATH_IMAGE_NAME).combineToGFImg());
				break;
			case 0:
			default:
				setPathSectionType(PathSectionType.DOT);
				setImage(new ImageCombiner(getBgImageName(), DOT_PATH_IMAGE_NAME).combineToGFImg());
				break;
		}
	}

	public PathSectionType getPathSectionType() {
		return pathSectionType;
	}

	public void setPathSectionType(PathSectionType pathSectionType) {
		this.pathSectionType = pathSectionType;
	}

	/**
	 * a method for the {@link AStarAlgorithm}
	 * 
	 * @return the fCost of this Cell
	 */
	public int fCost() {
		return gCost + hCost;
	}

	/**
	 * a method for the {@link AStarAlgorithm}
	 * 
	 * @return the hCost of this Cell
	 */
	public int gethCost() {
		return hCost;
	}

	/**
	 * a method for the {@link AStarAlgorithm}
	 * 
	 * @return the gCost of this Cell
	 */
	public int getgCost() {
		return gCost;
	}

	/**
	 * a method for the {@link AStarAlgorithm}
	 * 
	 * @param hCost the hCost for this Cell
	 */
	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	/**
	 * a method for the {@link AStarAlgorithm}
	 * 
	 * @param hCost the gCost for this Cell
	 */
	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	/**
	 * a method for the {@link AStarAlgorithm}
	 * 
	 * @return the Parent of this Cell - is the cell that comes before this in the
	 *         Path
	 */
	public PathCell getParent() {
		return parent;
	}

	/**
	 * sets the parent of this Cell<br>
	 * a method for the {@link AStarAlgorithm}
	 * 
	 * @param parentNode the parent node
	 */
	public void setParent(PathCell parentNode) {
		this.parent = parentNode;
	}

}
