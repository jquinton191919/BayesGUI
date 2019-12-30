package gui;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.Bayes;

import java.awt.*;

public class BayesFrameTest extends JFrame {
	private static final long serialVersionUID = 1L;
	static final int MIN = 0;
	static final int MAX = 10000;
	static final int INITIAL = 5000;
	static final int HALF = 575;
	public JSlider s1, s2, s3, s4, s5,
	x1, x2, x3, x4, x5;
	public JTextField b1, b2, b3, b4, b5,
	t1, t2, t3, t4, t5;
	
	public static Bayes bt;
	
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

	public BayesFrameTest(Bayes b) {		
		super("Bayes Theorem");
		bt = b;
		setSize(1350,185); // default size is 0,0
		setLocation(10,200); // default is 0,0 (top left corner)
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//get the frame's contentpane, instantiate the layout type and then add the layout type to the 
		//content pane
		
		Container pane = getContentPane();
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		
		//the LISTENER!
		BayesChange bayesChange = new BayesChange();
		
		
		/**
		 * layout for the first slider
		 */
		
		JLabel prior = new JLabel("Prior Probability, Pr(H): ");
		s1 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		s1.addChangeListener(bayesChange);
		s1.setName("pH");
		pH = (double) s1.getValue() / MAX;
		b1 = new JTextField(Double.toString(pH), 15);		
		b1.setEditable(false);

		pane.add(prior);
		pane.add(s1);
		pane.add(b1);
		//the .WEST of pH is 5 from the .WEST of the pane, and the .NORTH of pH is 5 from the .NORTH of the pane
		layout.putConstraint(SpringLayout.WEST, prior, 5, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, prior, 5, SpringLayout.NORTH, pane);
		
		//the .WEST of s1 is 10 from the .EAST of pH, and the .NORTH of s1 is 5 from the .NORTH of the pane
		layout.putConstraint(SpringLayout.WEST, s1, 10, SpringLayout.EAST, prior);
		layout.putConstraint(SpringLayout.NORTH, s1, 5, SpringLayout.NORTH, pane);
		
		//etc.
		layout.putConstraint(SpringLayout.WEST, b1, 10, SpringLayout.EAST, s1);
		layout.putConstraint(SpringLayout.NORTH, b1, 5, SpringLayout.NORTH, pane);
		
		
		
		/**
		 * layout for the second slider
		 */
		
		JLabel succRate = new JLabel("Success Rate, Pr(E | H): ");
		s2 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		s2.addChangeListener(bayesChange);
		s2.setName("pEgivenH");
		pEgivenH = (double) s2.getValue() / MAX;
		b2 = new JTextField(Double.toString(pEgivenH), 15);
		b2.setEditable(false);

		pane.add(succRate);
		pane.add(s2);
		pane.add(b2);
		
		layout.putConstraint(SpringLayout.WEST, succRate, 5, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, succRate, 10, SpringLayout.SOUTH, prior);
		
		layout.putConstraint(SpringLayout.WEST, s2, 10, SpringLayout.EAST, succRate);
		layout.putConstraint(SpringLayout.NORTH, s2, 10, SpringLayout.SOUTH, s1);
		
		
		layout.putConstraint(SpringLayout.WEST, b2, 10, SpringLayout.EAST, s2);
		layout.putConstraint(SpringLayout.NORTH, b2, 5, SpringLayout.SOUTH, b1);
		
		
		
		/**
		 * layout for the third slider
		 */
		
		JLabel falsePos = new JLabel("False Positive Rate, Pr(E | ~H): ");
		s3 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		s3.addChangeListener(bayesChange);
		s3.setName("pEgivenNotH");
		pEgivenAltH = (double) s3.getValue() / MAX;
		b3 = new JTextField(Double.toString(pEgivenAltH), 15);
		b3.setEditable(false);

		pane.add(falsePos);
		pane.add(s3);
		pane.add(b3);
		
		layout.putConstraint(SpringLayout.WEST, falsePos, 5, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, falsePos, 10, SpringLayout.SOUTH, succRate);
		
		layout.putConstraint(SpringLayout.WEST, s3, 10, SpringLayout.EAST, falsePos);
		layout.putConstraint(SpringLayout.NORTH, s3, 10, SpringLayout.SOUTH, s2);
		
		
		layout.putConstraint(SpringLayout.WEST, b3, 10, SpringLayout.EAST, s3);
		layout.putConstraint(SpringLayout.NORTH, b3, 5, SpringLayout.SOUTH, b2);
		
		
		/**
		 * layout for the fourth slider
		 */
		
		JLabel pTot = new JLabel("Total Probability, Pr(E): ");
		s4 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		s4.setName("pTotal");
		s4.setEnabled(false);
		s4.setToolTipText("Total probability, or Pr(E), is equal to Pr(H) * Pr(E | H) + Pr(~H) * Pr(E | ~H). " +
				"Use Success Rate or False Positive Rate to change Total Probability");
		pE = (double) s4.getValue() / MAX;
		b4 = new JTextField(Double.toString(pE), 15);
		b4.setEditable(false);

		pane.add(pTot);
		pane.add(s4);
		pane.add(b4);
		
		layout.putConstraint(SpringLayout.WEST, pTot, 5, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, pTot, 10, SpringLayout.SOUTH, falsePos);
		
		layout.putConstraint(SpringLayout.WEST, s4, 10, SpringLayout.EAST, pTot);
		layout.putConstraint(SpringLayout.NORTH, s4, 10, SpringLayout.SOUTH, s3);
		
		layout.putConstraint(SpringLayout.WEST, b4, 10, SpringLayout.EAST, s4);
		layout.putConstraint(SpringLayout.NORTH, b4, 5, SpringLayout.SOUTH, b3);
		
		
		
		/**
		 * layout for the fifth (BAYES!) slider
		 */
		
		JLabel bayes = new JLabel("Posterior Probability, Pr(H | E): ");
		s5 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		s5.setEnabled(false);
		s5.setName("posterior");
		pHgivenE = (double) s5.getValue() / MAX;
		b5 = new JTextField(Double.toString(pHgivenE), 15);
		b5.setEditable(false);

		pane.add(bayes);
		pane.add(s5);
		pane.add(b5);
		
		layout.putConstraint(SpringLayout.WEST, bayes, 5, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, bayes, 10, SpringLayout.SOUTH, pTot);
		
		layout.putConstraint(SpringLayout.WEST, s5, 10, SpringLayout.EAST, bayes);
		layout.putConstraint(SpringLayout.NORTH, s5, 10, SpringLayout.SOUTH, s4);
		
		layout.putConstraint(SpringLayout.WEST, b5, 10, SpringLayout.EAST, s5);
		layout.putConstraint(SpringLayout.NORTH, b5, 5, SpringLayout.SOUTH, b4);
		
		/*******************************************************************************/
		
		/***
		 * Layout for P(~H)
		 */
		
		JLabel alt = new JLabel("Alternative Prior Probability, Pr(~H): ");
		x1 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		x1.setEnabled(false);
		pAltH = (double) s1.getValue() / MAX;
		t1 = new JTextField(Double.toString(pAltH), 15);		
		t1.setEditable(false);

		pane.add(alt);
		pane.add(x1);
		pane.add(t1);
		
		layout.putConstraint(SpringLayout.WEST, alt, HALF, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, alt, 5, SpringLayout.NORTH, pane);
		
		layout.putConstraint(SpringLayout.WEST, x1, 10, SpringLayout.EAST, alt);
		layout.putConstraint(SpringLayout.NORTH, x1, 5, SpringLayout.NORTH, pane);
		
		layout.putConstraint(SpringLayout.WEST, t1, 10, SpringLayout.EAST, x1);
		layout.putConstraint(SpringLayout.NORTH, t1, 5, SpringLayout.NORTH, pane);
		
		
		
		/***
		 * Layout for P(~E | H)
		 */
		
		JLabel altSuccRate = new JLabel("Absence of Evidence Given Hypothesis, Pr(~E | H): ");
		x2 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		x2.setEnabled(false);
		pAltEgivenH = (double) x2.getValue() / MAX;
		t2 = new JTextField(Double.toString(pAltEgivenH), 15);		
		t2.setEditable(false);

		pane.add(altSuccRate);
		pane.add(x2);
		pane.add(t2);
		
		layout.putConstraint(SpringLayout.WEST, altSuccRate, HALF, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, altSuccRate, 10, SpringLayout.SOUTH, alt);
		
		layout.putConstraint(SpringLayout.WEST, x2, 10, SpringLayout.EAST, altSuccRate);
		layout.putConstraint(SpringLayout.NORTH, x2, 10, SpringLayout.SOUTH, x1);
		
		layout.putConstraint(SpringLayout.WEST, t2, 10, SpringLayout.EAST, x2);
		layout.putConstraint(SpringLayout.NORTH, t2, 5, SpringLayout.SOUTH, t1);
		
		/***
		 * Layout for P(~E | ~H)
		 */
		
		JLabel altFalsePos = new JLabel("Absence of Evidence Given Alternative Hypothesis, Pr(~E | ~H): ");
		x3 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		x3.setEnabled(false);
		pAltEgivenAltH = (double) x3.getValue() / MAX;
		t3 = new JTextField(Double.toString(pAltEgivenAltH), 15);
		t3.setEditable(false);
		
		pane.add(altFalsePos);
		pane.add(x3);
		pane.add(t3);
		
		layout.putConstraint(SpringLayout.WEST, altFalsePos, HALF, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, altFalsePos, 10, SpringLayout.SOUTH, altSuccRate);
		
		layout.putConstraint(SpringLayout.WEST, x3, 10, SpringLayout.EAST, altFalsePos);
		layout.putConstraint(SpringLayout.NORTH, x3, 10, SpringLayout.SOUTH, x2);
		
		layout.putConstraint(SpringLayout.WEST, t3, 10, SpringLayout.EAST, x3);
		layout.putConstraint(SpringLayout.NORTH, t3, 5, SpringLayout.SOUTH, t2);
		
		
		/***
		 * Layout for P(~E)
		 */
		
		JLabel altTot = new JLabel("Total Absence of Evidence Probability, Pr(~E): ");
		x4 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		x4.setEnabled(false);
		pAltE = (double) x4.getValue() / MAX;
		t4 = new JTextField(Double.toString(pAltE), 15);
		t4.setEditable(false);
		
		pane.add(altTot);
		pane.add(x4);
		pane.add(t4);
		
		layout.putConstraint(SpringLayout.WEST, altTot, HALF, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, altTot, 10, SpringLayout.SOUTH, altFalsePos);
		
		layout.putConstraint(SpringLayout.WEST, x4, 10, SpringLayout.EAST, altTot);
		layout.putConstraint(SpringLayout.NORTH, x4, 10, SpringLayout.SOUTH, x3);
		
		layout.putConstraint(SpringLayout.WEST, t4, 10, SpringLayout.EAST, x4);
		layout.putConstraint(SpringLayout.NORTH, t4, 5, SpringLayout.SOUTH, t3);
		
		/***
		 * Layout for (ABSENCE OF EVIDENCE!) P(H | ~E) 
		 */
		
		JLabel absenceOfEvidence = new JLabel("Posterior Probability Given Absence Of Evidence, Pr(H | ~E): ");
		absenceOfEvidence.setToolTipText("Absence of evidence is evidence of absence");
		x5 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INITIAL);
		x5.setEnabled(false);
		pHgivenAltE = (double) x5.getValue() / MAX;
		t5 = new JTextField(Double.toString(pHgivenAltE), 15);
		t5.setEditable(false);
		
		pane.add(absenceOfEvidence);
		pane.add(x5);
		pane.add(t5);
		
		layout.putConstraint(SpringLayout.WEST, absenceOfEvidence, HALF, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, absenceOfEvidence, 10, SpringLayout.SOUTH, altTot);
		
		layout.putConstraint(SpringLayout.WEST, x5, 10, SpringLayout.EAST, absenceOfEvidence);
		layout.putConstraint(SpringLayout.NORTH, x5, 10, SpringLayout.SOUTH, x4);
		
		layout.putConstraint(SpringLayout.WEST, t5, 10, SpringLayout.EAST, x5);
		layout.putConstraint(SpringLayout.NORTH, t5, 10, SpringLayout.SOUTH, t4);
	  }
	
	/**
	 * Listener for the scrollers
	 * */
	private class BayesChange implements ChangeListener {
		
		
		@Override
		public void stateChanged(ChangeEvent ce) {
			//set prior probability 
			pH = (double) s1.getValue()/MAX;
			b1.setText(Double.toString(pH));
			
			//and sets the alternative prior probability
			pAltH = bt.getAlternativeHypothesis();
			x1.setValue((int) (pAltH * MAX));
			t1.setText(Double.toString(pAltH));
			
			//set success rate
			pEgivenH = (double) s2.getValue() / MAX;
			b2.setText(Double.toString(pEgivenH));
			
			//and also set the absence of evidence given the hypothesis
			pAltEgivenH = bt.getAbsenceOfEvidenceConditional();
			x2.setValue((int) (pAltEgivenH * MAX));
			t2.setText(Double.toString(pAltEgivenH));
		
			//set false positive rate
			pEgivenAltH = (double) s3.getValue() / MAX;
			b3.setText(Double.toString(pEgivenAltH));
			
			//and also P(~E | ~H)
			pAltEgivenAltH = bt.getAbsenceOfEvidenceAltConditional();
			x3.setValue((int) (pAltEgivenAltH * MAX));
			t3.setText(Double.toString(pAltEgivenAltH));
			
			
			//also find BT and its alternative
			bt = new Bayes(pH, pEgivenH, pEgivenAltH, true);
			
			pHgivenE = bt.getPosterior();
			s5.setValue((int) (bt.getPosterior()* MAX));
			b5.setText(Double.toString(pHgivenE));
			pHgivenAltE = bt.getAbsenceOfEvidence();
			x5.setValue((int) (bt.getAbsenceOfEvidence() * MAX));
			t5.setText(Double.toString(pHgivenAltE));
			
			//should also set the total probability and its alternative
			pE = bt.getTotalProbability();
			s4.setValue((int) (pE * MAX));
			b4.setText(Double.toString(pE));
			pAltE = bt.getAltTotalProbability();
			x4.setValue((int) (pAltE * MAX));
			t4.setText(Double.toString(pAltE));
			
		}

	}

}
