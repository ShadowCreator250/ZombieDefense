import java.util.List;

/**
 * 
 * A special path cell that represents the end of the path, where the survivors
 * base gate is located.
 *
 */
public class EndPathCell extends PathCell {

	private BaseGate gate = new BaseGate();

	/**
	 * Creates an endpath cell object on the given place
	 * 
	 * @param gridX - number of the cell in horizontal direction
	 * @param gridY - number of the cell in vertical direction
	 */
	public EndPathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}

	/**
	 * Declares this cell as the end of the path at the world edge and looks for its
	 * neighbour cells.
	 */
	@Override
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size() + 1, logicalOrNeighbouringPathCellsWithWorldEdges());
	}

	/**
	 * spawns the gate on the End Cell, rotates and positions it correctly according
	 * to the wall that the cell is touching
	 */
	public void spawnBaseGate() {
		boolean[] wallsExistence = evaluateExistenceOfWorldEdgesAsNeighbours();
		int xOffset = 0;
		int yOffset = 0;
		if(wallsExistence[2]) {
			gate.setRotation(-90);
			xOffset = -32;
		} else if(wallsExistence[0]) {
			gate.setRotation(90);
			xOffset = 32;
		} else if(wallsExistence[3]) {
			gate.setRotation(180);
			yOffset = -32;
		} else if(wallsExistence[1]) {
			gate.setRotation(0);
			yOffset = 32;
		}
		getWorld().addObject(gate, getX() + xOffset, getY() + yOffset);
	}

	public BaseGate getGate() {
		return gate;
	}

}
