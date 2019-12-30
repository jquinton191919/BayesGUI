package main;

import gui.BayesFrame;
import backend.Bayes;

public class Intro {
	/** @author j. quinton (http://deusdiapente.wordpress.com)
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		Bayes bt = new Bayes(0.5, 0.5, 0.5, true);
		BayesFrame f = new BayesFrame(bt);
		f.setVisible(true);
	}

}
