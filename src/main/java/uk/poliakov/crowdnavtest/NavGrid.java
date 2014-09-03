package uk.poliakov.crowdnavtest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class NavGrid {
	public final int horizontalCells;
	public final int verticalCells;

	private final double worldWidth;
	private final double worldHeight;

	public NavCell[] cells;

	private HashMap<NavCell, NavCell> prev;
	private HashMap<NavCell, Integer> dist;

	public NavGrid(int horizontalCells, int verticalCells, double worldWidth, double worldHeight, NavCell[] cells) {
		this.horizontalCells = horizontalCells;
		this.verticalCells = verticalCells;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.cells = cells;

		calcPaths();
	}

	public List<NavCell> getNeighbours(NavCell cell) {
		ArrayList<NavCell> r = new ArrayList<>();
		ArrayList<NavCell> result = new ArrayList<>();

		if (cell.x > 0)
			r.add(getCellByIndex(cell.x - 1, cell.y));

		if (cell.x < (horizontalCells - 1))
			r.add(getCellByIndex(cell.x + 1, cell.y));

		if (cell.y > 0)
			r.add(getCellByIndex(cell.x, cell.y - 1));

		if (cell.y < (verticalCells - 1))
			r.add(getCellByIndex(cell.x, cell.y + 1));

		for (NavCell c : r)
			if (c.isWalkable)
				result.add(c);

		return result;
	}

	public int getTravelCost(NavCell from, NavCell to) {
		return (from.cost + to.cost) / 2;
	}

	private void calcPaths() {

		prev = new HashMap<>();
		dist = new HashMap<>();

		NavCell target = getTargetCell();

		dist.put(target, 0);

		Set<NavCell> q = new HashSet<NavCell>();

		for (NavCell c : cells)
			q.add(c);

		while (!q.isEmpty()) {

			NavCell v = null;
			int dv = Integer.MAX_VALUE;

			for (NavCell c : q) {
				int d = (dist.containsKey(c)) ? dist.get(c) : Integer.MAX_VALUE;

				if (dv > d) {
					dv = d;
					v = c;
				}
			}

			if (v == null)
				break;
			else
				q.remove(v);

			System.out.println("Processing " + v.x + ", " + v.y);

			for (NavCell n : getNeighbours(v)) {
				if (!q.contains(n))
					continue;

				int dn = (dist.containsKey(n)) ? dist.get(n) : Integer.MAX_VALUE;
				int dd = dv + getTravelCost(v, n);

				if (dd < dn) {
					dist.put(n, dd);
					prev.put(n, v);
				}
			}
		}
	}

	public NavCell getTargetCell() {
		int target_x = horizontalCells / 2;
		int target_y = verticalCells / 2;

		return getCellByIndex(target_x, target_y);
	}

	public NavCell getCellFromWorldCoords(Vector2D coords) {
		int x = (int) (coords.getX() / worldWidth * horizontalCells);

		if (x > horizontalCells - 1)
			x = horizontalCells - 1;

		if (x < 0)
			x = 0;

		int y = (int) (coords.getY() / worldHeight * verticalCells);

		if (y > verticalCells - 1)
			y = verticalCells - 1;
		
		if (y < 0)
			y = 0;
		
		return getCellByIndex(x, y);
	}
	
	public Vector2D getCellCenter(NavCell cell) {
		double wx = worldWidth / (double)horizontalCells;
		double wy = worldHeight / (double)verticalCells;
		
		return new Vector2D((cell.x + 0.5) * wx, (cell.y + 0.5) * wy); 
	}

	public NavCell getCellByIndex(int cellX, int cellY) {
		return cells[cellY * horizontalCells + cellX];
	}

	public NavCell getNextCell(NavCell cell) {
		return prev.get(cell);
	}
}
