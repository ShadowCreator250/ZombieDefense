import java.util.ArrayList;
import java.util.List;

public abstract class PathCell extends Cell {
	
	private PathSectionType pathSectionType;
	private int hCost;
	private int gCost;
	private PathCell parent;
	
	public enum PathSectionType {
		DOT, DEAD_END, STRAIGHT, CURVE, T, CROSS;
	}
	
	public PathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}
	
	public List<PathCell> getNeighbouringPathCells() {
		List<Cell> neighbourCells = getNeighbourCells(false);
		List<PathCell> neighbourPathCells = new ArrayList<>(neighbourCells.size());
		for (Cell cell : neighbourCells) {
			if(cell instanceof PathCell) {
				neighbourPathCells.add((PathCell)cell);
			}
		}
		return neighbourPathCells;
	}
	
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		switch (neighbourPathCells.size()) {
			case 1:
				this.pathSectionType = PathSectionType.DEAD_END;
				break;
			case 2:
				PathCell cell1 = neighbourPathCells.get(0);
				PathCell cell2 = neighbourPathCells.get(1);
				if(cell1.getGridX() == cell2.getGridX() || cell1.getGridY() == cell2.getGridY()) {
					this.pathSectionType = PathSectionType.STRAIGHT;
				} else {
					this.pathSectionType = PathSectionType.CURVE;
				}
				break;
			case 3:
				this.pathSectionType = PathSectionType.T;
				break;
			case 4:
				this.pathSectionType = PathSectionType.CROSS;
				break;
			case 0:
			default:
				this.pathSectionType = PathSectionType.DOT;
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
		return gCost+hCost;
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
