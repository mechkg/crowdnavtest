package uk.poliakov.crowdnavtest;

public class NavGrid {
	public final int horizontalCells;
	public final int verticalCells;
	
	public NavCell[] cellInfo;

	public NavGrid(int horizontalCells, int verticalCells, double worldWidth, double worldHeight, NavCell[] cellInfo ) {
		this.horizontalCells = horizontalCells;
		this.verticalCells = verticalCells;
		this.cellInfo = cellInfo; 
	}
	
	public NavCell getCell (int cellX, int cellY) {
		return cellInfo[cellY*horizontalCells + cellX];
	}
	
	
}
