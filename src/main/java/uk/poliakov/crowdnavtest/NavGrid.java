package uk.poliakov.crowdnavtest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class NavGrid {
	public final int horizontalCells;
	public final int verticalCells;

	public NavCell[] cells;

	private HashMap<NavCell, NavCell> prev;
	private HashMap<NavCell, Integer> dist;

	public NavGrid(int horizontalCells, int verticalCells, double worldWidth, double worldHeight, NavCell[] cells) {
		this.horizontalCells = horizontalCells;
		this.verticalCells = verticalCells;
		this.cells = cells;

		calcPaths();
	}

	public List<NavCell> getNeighbours(NavCell cell) {
		ArrayList<NavCell> r = new ArrayList<>();
		ArrayList<NavCell> result = new ArrayList<>();

		if (cell.x > 0)
			r.add(getCell(cell.x - 1, cell.y));

		if (cell.x < (horizontalCells - 1))
			r.add(getCell(cell.x + 1, cell.y));

		if (cell.y > 0)
			r.add(getCell(cell.x, cell.y - 1));

		if (cell.y < (verticalCells - 1))
			r.add(getCell(cell.x, cell.y + 1));

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

		return getCell(target_x, target_y);
	}

	public NavCell getCell(int cellX, int cellY) {
		return cells[cellY * horizontalCells + cellX];
	}

	public NavCell getNextCell(NavCell cell) {
		return prev.get(cell);
	}
}
