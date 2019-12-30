package gui;

/*************************************************************
 * @file: TextPanel.java 
* @source: adapted from Horstmann and Cornell, Core Java
* @history: Visualization Course, Spring 2003, Chee Yap
*************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*************************************************************
 *	TextPanel Class (with main method)
*************************************************************/

class TextPanel extends JPanel {
// override the paintComponent method
// THE MAIN DEMO OF THIS EXAMPLE:

public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Font f = new Font("SansSerif", Font.BOLD, 14);
	Font fi = new Font("SansSerif", Font.BOLD + Font.ITALIC, 14);
	FontMetrics fm = g.getFontMetrics(f);
	FontMetrics fim = g.getFontMetrics(fi);
	int cx = 75; int cy = 100;
	g.setFont(f);
	g.drawString("Hello, ", cx, cy);
	cx += fm.stringWidth("Hello, ");
	g.setFont(fi);
	g.drawString("World!", cx, cy);
	
	} //paintComponent

}