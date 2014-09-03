package uk.poliakov.crowdnavtest;

public class NavCell {	
	public final int x;
	public final int y;
	public final int cost;
	
	public final boolean isWalkable;
	
	public final int dbg_srcPixelCount;
	public final int dbg_srcWalkableCount;
	
	public NavCell(int x, int y, int cost, boolean isWalkable,
			int dbg_srcPixelCount, int dbg_srcWalkableCount) {
		this.x = x;
		this.y = y;
		this.cost = cost;
		this.isWalkable = isWalkable;
		this.dbg_srcPixelCount = dbg_srcPixelCount;
		this.dbg_srcWalkableCount = dbg_srcWalkableCount;
	}
}
