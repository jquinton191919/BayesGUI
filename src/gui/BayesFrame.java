package gui;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.Bayes;

import java.awt.*;

public class BayesFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	static final int MIN = 0;
	static final int MAX = 10000;
	static final int INITIAL = 5000;
	static final int HALF = 575;
	public JSlider s1, s2, s3, s4, s5,
	x1, x2, x3, x4, x5;
	public JTextField b1, b2, b3, b4, b5,
	t1, t2, t3, t4, t5, bfText;
	public JLabel label0, label1, label2, label3, label4, label5, label6, label7, label8, label9;
	
	public JSlider[] sliders = new JSlider[] {s1,s2,s3,s4,s5,x1,x2,x3,x4,x5};
	public JTextField[] textFields = new JTextField[] {b1,b2,b3,b4,b5,t1,t2,t3,t4,t5};
	public JLabel [] labels = new JLabel[] {label0, label1, label2, label3, label4, label5, label6, label7, label8, label9};
	public String []names = new String []
			{"Prior Probability, Pr(H): ", "Success Rate, Pr(E | H): ","False Positive Rate, Pr(E | ~H): ",
				"Total Probability, Pr(E): ","Posterior Probability, Pr(H | E): ", "Alternative Prior Probability, Pr(~H): ",
				"Absence of Evidence Given Hypothesis, Pr(~E | H): ", "Absence of Evidence Given Alternative Hypothesis, Pr(~E | ~H): ",
				"Total Absence of Evidence Probability, Pr(~E): ","Posterior Probability Given Absence Of Evidence, Pr(H | ~E): "};
	
	public Bayes bt;
	
	public double 
	pH, //prior probability 
	pEgivenH, //success rate
	pEgivenAltH, //false positive rate
	pE, //total probability
	pHgivenE, //posterior probability
	pAltH, //alternative hypothesis
	pAltEgivenH, //absence of evidence given hypothesis
	pAltEgivenAltH, //absence of evidence given alternative hypothesis
	pAltE, //absence of evidence
	pHgivenAltE; //posterior probability given absence of evidence
	

	public BayesFrame(Bayes b) {		
		super("Bayes Theorem");
		bt = b;
		setSize(1350,205); // default size is 0,0
		setLocation(10,200); // default is 0,0 (top left corner)
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//get the frame's contentpane, instantiate the layout type and then add the layout type to the 
		//content pane
		
		Container pane = getContentPane();
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		
		//the LISTENER!
		BayesChange bayesChange = new BayesChange();
		
		//set up the gui with a loop
		for(int i=0; i<=9; i++) {
			labels[i] = new JLabel(names[i].toString());
			sliders[i] = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
			sliders[i].addChangeListener(bayesChange);
			if (i > 2) sliders[i].setEnabled(false);
			
			switch (i) {
				case 0: pH = (double) sliders[i].getValue() / MAX; break;					
				case 1: pEgivenH = (double) sliders[i].getValue() / MAX; break;
				case 2: pEgivenAltH = (double) sliders[i].getValue() / MAX; break;
				case 3: pE = (double) sliders[i].getValue() / MAX; break;
				case 4: pHgivenE = (double) sliders[i].getValue() / MAX; break;
				case 5: pAltH = (double) sliders[i].getValue() / MAX; break;
				case 6: pAltEgivenH = (double) sliders[i].getValue() / MAX; break;
				case 7: pAltEgivenAltH = (double) sliders[i].getValue() / MAX; break;
				case 8: pAltE = (double) sliders[i].getValue() / MAX; break;
				case 9: pHgivenAltE = (double) sliders[i].getValue() / MAX; break;
				default: break;
			}
			
			textFields[i] = new JTextField(Double.toString(pH), 15);
			textFields[i].setEditable(false);
			
			pane.add(labels[i]);
			pane.add(sliders[i]);
			pane.add(textFields[i]);
			
			if(i == 0){
				layout.putConstraint(SpringLayout.WEST, labels[i], 5, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 5, SpringLayout.NORTH, pane);
				
				layout.putConstraint(SpringLayout.WEST, sliders[i], 10, SpringLayout.EAST, labels[i]);
				layout.putConstraint(SpringLayout.NORTH, sliders[i], 5, SpringLayout.NORTH, pane);
				
				layout.putConstraint(SpringLayout.WEST, textFields[i], 10, SpringLayout.EAST, sliders[i]);
				layout.putConstraint(SpringLayout.NORTH, textFields[i], 5, SpringLayout.NORTH, pane);
			}
			
			else if(i == 5) {
				layout.putConstraint(SpringLayout.WEST, labels[i], HALF, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 5, SpringLayout.NORTH, pane);
				
				layout.putConstraint(SpringLayout.WEST, sliders[i], 10, SpringLayout.EAST, labels[i]);
				layout.putConstraint(SpringLayout.NORTH, sliders[i], 5, SpringLayout.NORTH, pane);
				
				layout.putConstraint(SpringLayout.WEST, textFields[i], 10, SpringLayout.EAST, sliders[i]);
				layout.putConstraint(SpringLayout.NORTH, textFields[i], 5, SpringLayout.NORTH, pane);
			}
			
			else if(i > 5) {
				layout.putConstraint(SpringLayout.WEST, labels[i], HALF, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 10, SpringLayout.SOUTH, labels[i-1]);
				
				layout.putConstraint(SpringLayout.WEST, sliders[i], 10, SpringLayout.EAST, labels[i]);
				layout.putConstraint(SpringLayout.NORTH, sliders[i], 10, SpringLayout.SOUTH, sliders[i-1]);
				
				layout.putConstraint(SpringLayout.WEST, textFields[i], 10, SpringLayout.EAST, sliders[i]);
				layout.putConstraint(SpringLayout.NORTH, textFields[i], 5, SpringLayout.SOUTH, textFields[i-1]);
			}
			
			else{
				layout.putConstraint(SpringLayout.WEST, labels[i], 5, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 10, SpringLayout.SOUTH, labels[i-1]);
				
				layout.putConstraint(SpringLayout.WEST, sliders[i], 10, SpringLayout.EAST, labels[i]);
				layout.putConstraint(SpringLayout.NORTH, sliders[i], 10, SpringLayout.SOUTH, sliders[i-1]);
				
				
				layout.putConstraint(SpringLayout.WEST, textFields[i], 10, SpringLayout.EAST, sliders[i]);
				layout.putConstraint(SpringLayout.NORTH, textFields[i], 5, SpringLayout.SOUTH, textFields[i-1]);
			}
		}
		
		//Bayes Factor tab
		JLabel bFactor = new JLabel("Bayes Factor: ");
		bfText = new JTextField(Double.toString(bt.getBayesFactor()), 15);
		bfText.setEditable(false);
		pane.add(bFactor);
		pane.add(bfText);
		layout.putConstraint(SpringLayout.WEST, bFactor, 450, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, bFactor, 10, SpringLayout.SOUTH, labels[9]);
		layout.putConstraint(SpringLayout.WEST, bfText, 10, SpringLayout.EAST, bFactor);
		layout.putConstraint(SpringLayout.NORTH, bfText, 10, SpringLayout.SOUTH, labels[9]);
		
		
		
	  }
	
	/**
	 * Listener for the scrollers
	 * */
	private class BayesChange implements ChangeListener {
		
		
		@Override
		public void stateChanged(ChangeEvent ce) {
			//set prior probability 
			pH = (double) sliders[0].getValue()/MAX;
			textFields[0].setText(Double.toString(pH));
			
			//and sets the alternative prior probability
			pAltH = bt.getAlternativeHypothesis();
			sliders[5].setValue((int) (pAltH * MAX));
			textFields[5].setText(Double.toString(pAltH));
			
			//set success rate
			pEgivenH = (double) sliders[1].getValue() / MAX;
			textFields[1].setText(Double.toString(pEgivenH));
			
			//and also set the absence of evidence given the hypothesis
			pAltEgivenH = bt.getAbsenceOfEvidenceConditional();
			sliders[6].setValue((int) (pAltEgivenH * MAX));
			textFields[6].setText(Double.toString(pAltEgivenH));
		
			//set false positive rate
			pEgivenAltH = (double) sliders[2].getValue() / MAX;
			textFields[2].setText(Double.toString(pEgivenAltH));
			
			//and also P(~E | ~H)
			pAltEgivenAltH = bt.getAbsenceOfEvidenceAltConditional();
			sliders[7].setValue((int) (pAltEgivenAltH * MAX));
			textFields[7].setText(Double.toString(pAltEgivenAltH));
			
			
			//also find BT and its alternative
			bt = new Bayes(pH, pEgivenH, pEgivenAltH, true);
			
			pHgivenE = bt.getPosterior();
			sliders[4].setValue((int) (bt.getPosterior()* MAX));
			textFields[4].setText(Double.toString(pHgivenE));
			pHgivenAltE = bt.getAbsenceOfEvidence();
			sliders[9].setValue((int) (bt.getAbsenceOfEvidence() * MAX));
			textFields[9].setText(Double.toString(pHgivenAltE));
			
			//should also set the total probability and its alternative
			pE = bt.getTotalProbability();
			sliders[3].setValue((int) (pE * MAX));
			textFields[3].setText(Double.toString(pE));
			pAltE = bt.getAltTotalProbability();
			sliders[8].setValue((int) (pAltE * MAX));
			textFields[8].setText(Double.toString(pAltE));
			
			//and finally, set Bayes Factor
			bfText.setText(Double.toString(bt.getBayesFactor()));
			
		}

	}

}
