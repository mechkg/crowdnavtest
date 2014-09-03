package uk.poliakov.crowdnavtest.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uk.poliakov.crowdnavtest.NavCell;
import uk.poliakov.crowdnavtest.NavGrid;

@SuppressWarnings("serial")
public class NavGridView extends JPanel {
	public final NavGrid grid;
	private final BufferedImage mapImage;

	public final int cellWidth = 16;
	public final int cellHeight = 16;

	public Map<NavCell, Color> highlights = new HashMap<>();

	public NavGridView(BufferedImage mapImage, final NavGrid grid) {
		this.mapImage = mapImage;
		this.grid = grid;

		resetHighlights();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int mx = e.getX();
				int my = e.getY();

				if (mx > 0 && mx < grid.horizontalCells * cellWidth && my > 0 && my < grid.verticalCells * cellHeight) {
					int cellX = mx / cellWidth;
					int cellY = my / cellHeight;

					NavCell cell = grid.getCell(cellX, cellY);

					/*
					 * JOptionPane.showMessageDialog(null, "Cell X: " + cellX +
					 * "\nCell Y: " + cellY + "\nWalkable: " + cell.isWalkable +
					 * "\nWalkable count: " + cell.dbg_srcWalkableCount +
					 * "\nSrc pixel count: " + cell.dbg_srcPixelCount);
					 */

					resetHighlights();

					highlights.put(cell, Color.CYAN);

					NavCell next = grid.getNextCell(cell);

					while (next != null) {
						highlights.put(next, Color.RED);
						next = grid.getNextCell(next);
					}

					repaint();
				}
			}
		});
	}

	private void resetHighlights() {
		highlights.clear();
		highlights.put(grid.getTargetCell(), Color.GREEN);
	}

	@Override
	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;

		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, getWidth(), getHeight());

		g.drawImage(mapImage, 0, 0, null);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		for (int x = 0; x < grid.horizontalCells; x++)
			for (int y = 0; y < grid.verticalCells; y++) {
				NavCell cell = grid.getCell(x, y);

				if (cell.isWalkable)
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.BLACK);

				g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);

				if (highlights.containsKey(cell))
					g.setColor(highlights.get(cell));
				else
					g.setColor(Color.BLUE);

				g.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
			}
	}

}
