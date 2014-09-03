package uk.poliakov.crowdnavtest.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import uk.poliakov.crowdnavtest.Agent;
import uk.poliakov.crowdnavtest.NavGrid;
import uk.poliakov.crowdnavtest.NavGridBuilder;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public MainWindow() {
		super("Crowd nav test");
		setSize(800, 600);
		
		try {
			BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("testmap.png"));
			NavGrid navGrid = NavGridBuilder.buildFromImage(img, 32, 32, 1, 1);
			
			ArrayList<Agent> agents = new ArrayList<>();
			
			for (int i=0; i<500; i++)
				agents.add(new Agent(new Vector2D(Math.random(), Math.random()), navGrid));
			
			setContentPane(new NavGridView(img, navGrid, agents));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}	
}
