package uk.poliakov.crowdnavtest.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import uk.poliakov.crowdnavtest.NavGridBuilder;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public MainWindow() {
		super("Crowd nav test");
		setSize(800, 600);
		
		try {
			BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("testmap.png"));
			
			setContentPane(new NavGridView(img, NavGridBuilder.buildFromImage(img, 32, 32, 1, 1)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}	
}
