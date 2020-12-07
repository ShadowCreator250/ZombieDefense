import java.util.ArrayList;
import java.util.List;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public abstract class PathCell extends Cell {

	public static final String DEAD_END_PATH_IMAGE_NAME = "dead_end-path-temp.png";
	public static final String STRAIGHT_PATH_IMAGE_NAME = "straight-path-temp.png";
	public static final String CURVE_PATH_IMAGE_NAME = "curve-path-temp.png";
	public static final String T_PATH_IMAGE_NAME = "t-path-temp.png";
	public static final String CROSS_PATH_IMAGE_NAME = "cross-path-temp.png";
	public static final String DOT_PATH_IMAGE_NAME = "dot-path-temp.png";
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

	@Override
	public void act() {
		checkAddObstacleClick();
	}

	private void checkAddObstacleClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1) {
			if(!(getWorld().getObjectsAt(getX(), getY(), Obstacle.class).size() > 0)) {
				GameState.MouseState mouseState = getWorld().getGameState().getMouseState();
				if(mouseState == GameState.MouseState.PLACE_MINE_FIELD && getWorld().getGameState().haveEnoughCoins(MineField.PRICE)) {
					getWorld().addObject(new MineField(), getX(), getY());
					getWorld().getGameState().getCoinsCounter().add(-MineField.PRICE);
				} else if(mouseState == GameState.MouseState.PLACE_SLIME_FIELD && getWorld().getGameState().haveEnoughCoins(SlimeField.PRICE)) {
					getWorld().addObject(new SlimeField(), getX(), getY());
					getWorld().getGameState().getCoinsCounter().add(-SlimeField.PRICE);
				}
			}
		}
	}

	protected abstract String getBgImageName();

	public void setImageRotation(int rotation) {
		GreenfootImage img = getImage();
		img.rotate(rotation * 90);
		setImage(img);

	}

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
	 * [0] - to the right<br>
	 * [1] - below<br>
	 * [2] - to the left<br>
	 * [3] - above<br>
	 * clockwise rotation
	 * 
	 * @return the array
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
	 * [0] - to the right<br>
	 * [1] - below<br>
	 * [2] - to the left<br>
	 * [3] - above<br>
	 * clockwise rotation
	 * 
	 * @return the array
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

	public boolean[] logicalOrNeighbouringPathCellsWithWorldEdges() {
		boolean[] result = evaluateExistenceOfNeighbouringPathCells();
		boolean[] we = evaluateExistenceOfWorldEdgesAsNeighbours();
		for (int i = 0; i < result.length; i++) {
			result[i] = result[i] || we[i];
		}
		return result;
	}

	public abstract void evaluatePathSectionType();

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

	public int fCost() {
		return gCost + hCost;
	}

	public int gethCost() {
		return hCost;
	}

	public int getgCost() {
		return gCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public PathCell getParent() {
		return parent;
	}

	public void setParent(PathCell currentNode) {
		this.parent = currentNode;
	}

}
