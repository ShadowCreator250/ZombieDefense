import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AStarAlgorithm {

	private List<PathCell> path;
	private PathCell startNode;
	private PathCell targetNode;

	public AStarAlgorithm(PathCell startNode, PathCell targetNode) {
		this.startNode = startNode;
		this.targetNode = targetNode;
		this.path = findPath();
	}

	private List<PathCell> findPath() {

		List<PathCell> openList = new ArrayList<>();
		Set<PathCell> closedSet = new HashSet<>();
		openList.add(startNode);
		while (!openList.isEmpty()) {
			PathCell currentNode = openList.get(0);
			for (int i = 1; i < openList.size(); i++) {
				if((openList.get(i).fCost() < currentNode.fCost()) || openList.get(i).fCost() == currentNode.fCost()) {
					if(openList.get(i).gethCost() < currentNode.gethCost()) {
						currentNode = openList.get(i);
					}
				}
			}
			openList.remove(currentNode);
			closedSet.add(currentNode);

			if(currentNode.equals(targetNode)) {
				return retracePath(startNode, targetNode);
			}

			for (Cell neighbour : currentNode.getNeighbourCells(false)) {
				if(!(neighbour instanceof PathCell) || closedSet.contains(neighbour)) {
					continue;
				}
				PathCell neighbour2 = (PathCell) neighbour;
				int newMovementCostToNeighbour = currentNode.getgCost() + getDistanceBetweenTwoNodes(currentNode, neighbour2);
				if(newMovementCostToNeighbour < neighbour2.getgCost() || !openList.contains(neighbour2)) {
					neighbour2.setgCost(newMovementCostToNeighbour);
					neighbour2.sethCost(getDistanceBetweenTwoNodes(neighbour2, targetNode));
					neighbour2.setParent(currentNode);

					if(!openList.contains(neighbour2)) {
						openList.add(neighbour2);
					}
				}
			}
		}
		return null;
	}

	private List<PathCell> retracePath(PathCell startNode, PathCell endNode) {
		List<PathCell> path = new ArrayList<>();
		PathCell currentNode = endNode;
		while (!currentNode.equals(startNode)) {
			path.add(currentNode);
			currentNode = currentNode.getParent();
		}
		Collections.reverse(path);
		return path;
	}

	private int getDistanceBetweenTwoNodes(Cell nodeA, Cell nodeB) {
		int result = 0;
		int diagonalCost = 14;
		int straightCost = 10;
		int distanceX = Math.abs(nodeA.getGridX() - nodeB.getGridX());
		int distanceZ = Math.abs(nodeA.getGridY() - nodeB.getGridY());
		if(distanceX > distanceZ) {
			result = diagonalCost * distanceZ + straightCost * (distanceX - distanceZ);
		} else {
			result = diagonalCost * distanceX + straightCost * (distanceZ - distanceX);
		}
		return result;
	}

	public List<PathCell> getPath() {
		return path;
	}

}
