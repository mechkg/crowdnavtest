package uk.poliakov.crowdnavtest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Agent {
	private static final double speed = 0.05;

	private final NavGrid grid;

	public Vector2D pos;

	public Agent(Vector2D pos, NavGrid grid) {
		this.grid = grid;
		this.pos = pos;
	}

	public void update(double dt, List<Agent> agents) {

		NavCell curCell = grid.getCellFromWorldCoords(pos);
		NavCell targetCell = grid.getNextCell(curCell);

		Vector2D toTarget = Vector2D.ZERO;

		if (targetCell != null)
			toTarget = grid.getCellCenter(targetCell).subtract(pos).normalize();

		ArrayList<Agent> neighbours = new ArrayList<>();

		for (Agent a : agents) {
			if (a == this)
				continue;
			if (Vector2D.distance(pos, a.pos) < 0.01)
				neighbours.add(a);
		}

		Vector2D away = Vector2D.ZERO;

		for (Agent a : neighbours) {
			away = away.add(pos.subtract(a.pos).normalize());
		}

		if (!neighbours.isEmpty())
			away.scalarMultiply(1.0 / neighbours.size());

		Vector2D res = toTarget.scalarMultiply(0.6).add(away.scalarMultiply(0.4));

		pos = pos.add(res.scalarMultiply(speed * dt));
	}
}
