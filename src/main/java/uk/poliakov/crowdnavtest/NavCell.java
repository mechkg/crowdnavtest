package uk.poliakov.crowdnavtest;

public class NavCell {
	
	public final boolean isWalkable;
	public final int dbg_srcPixelCount;
	public final int dbg_srcWalkableCount;
	
	public NavCell(boolean isWalkable, int dbg_srcPixelCount,
			int dbg_srcWalkableCount) {
		super();
		this.isWalkable = isWalkable;
		this.dbg_srcPixelCount = dbg_srcPixelCount;
		this.dbg_srcWalkableCount = dbg_srcWalkableCount;
	}


}
