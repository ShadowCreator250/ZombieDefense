import java.util.ArrayList;
import java.util.List;

public abstract class PathCell extends Cell {

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
	 * [0] - to the right [1] - above [2] - to the left [3] - below mathematical
	 * sense of rotation
	 * 
	 * @return the array
	 */
	public boolean[] evaluateLocationsOfNeighbouringPathCells() {
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

	public abstract void evaluatePathSectionType();

	protected abstract String getBgImageName();

	public void setDirection(int rotation) {
		super.setRotation(rotation * 90);
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
