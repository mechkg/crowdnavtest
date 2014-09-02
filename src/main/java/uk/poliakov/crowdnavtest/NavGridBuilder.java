package uk.poliakov.crowdnavtest;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class NavGridBuilder {
	public static NavGrid buildFromImage(BufferedImage img, int horizontalCells, int verticalCells, double worldWidth, double worldHeight) throws IOException {
		
		int mapHeight = img.getHeight();
		int mapWidth = img.getWidth();
		
		if ((mapHeight % verticalCells) != 0 || (mapWidth % horizontalCells) != 0)
			throw new RuntimeException ("Nav map must span a whole number of cells");
		
		int ppc_v = mapHeight / verticalCells; // vertical pixels per cell
		int ppc_h = mapHeight / horizontalCells; // horizontal pixels per cell
		
		NavCell[] cellInfo = new NavCell[horizontalCells * verticalCells];
		
		int cnt = 0;
		
		for (int y=0; y<verticalCells; y++)
			for (int x=0; x<horizontalCells; x++) {
				
				int off_x = x * ppc_h;
				int off_y = y * ppc_v;
				
				int wcount = 0;
				
				for (int i=0; i<ppc_h; i++)
					for (int j=0; j<ppc_v; j++) {
						if (img.getRGB(off_x + i, off_y + j) == 0xffffffff)
							wcount ++;
					}
				
				boolean walkable =  (wcount / (double)(ppc_h * ppc_v)) > 0.8;
						
				cellInfo[cnt++] = new NavCell(walkable, ppc_v * ppc_h, wcount);
			}
		
		return new NavGrid(horizontalCells, verticalCells, worldWidth, worldHeight, cellInfo);
	}	
}
