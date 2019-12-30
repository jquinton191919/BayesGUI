package backend;

public class Bayes {
	
	private double prior;
	private double posterior;
	private double likelihood;
	private double success;
	private double falsePos;
	private double totalProb;
	private double altHypothesis;
	private double altTotalProb;
	private double absenceOfEvidenceConditional;
	private double absenceOfEvidenceAltConditional;
	//private double bothSuccess;
	//private double bothFalsePos;
	
	private boolean isPriorSet = false;
	private boolean isSuccessRateSet = false;
	private boolean isFalsePositiveSet = false;
	private boolean isTotalProbSet = false;
	
	/* default constructor */
	public Bayes(){}
	
	/**short form Bayes Theorem
	 * 
	 * @param pH - prior probability; Pr(H)
	 * @param eGivenH - success rate / conditional probability; Pr(E | H)
	 * @param pE - total probability; Pr(E)
	 * 
	 * */
	public Bayes(double pH, double eGivenH, double pE) {
		if((pH > 1.00) || (pE > 1.00) || (eGivenH > 1.00)) {
			//well... probabilites can't be greater than 1...
		}
		
		else {
			this.prior = pH;
			this.success = eGivenH;
			this.totalProb = pE;
			if (pE == 0.0) {
				this.posterior = 0.0;
			}
			else {
				this.posterior = ((pH * eGivenH) / pE);
			}
			this.altHypothesis = 1 - pH;
			this.altTotalProb = 1 - pE;
			if (altHypothesis == 0.0) {
				this.falsePos = 0.0;
			}
			else {
				this.falsePos = ((pE - eGivenH*pH) / altHypothesis);
			}
			
			this.absenceOfEvidenceConditional = 1 - success;
			this.absenceOfEvidenceAltConditional = 1 - falsePos;
			if (falsePos == 0.0) {
				this.likelihood = 0.0;
			}
			else {
				this.likelihood = success / falsePos;
			}
			
		}
	}
	
	/**long form Bayes Theorem
	 * 
	 * @param pH - prior probability; Pr(H)
	 * @param eGivenH - success rate / conditional probability; Pr(E | H)
	 * @param eGivenNotH - false positive rate; Pr(E | ~H)
	 * @param l - extra variable basically to distinguish it from the short form
	 * */
	public Bayes(double pH, double eGivenH, double eGivenNotH, boolean l) {
		if((pH > 1.00) || (eGivenNotH > 1.00) || (eGivenH > 1.00)) {
			//well... probabilites can't be greater than 1...
		}
		
		else {
			this.prior = pH;
			this.success = eGivenH;
			this.falsePos = eGivenNotH;
			this.altHypothesis = 1 - pH;
			this.totalProb = (pH * eGivenH + altHypothesis * eGivenNotH);
			if (totalProb == 0.0) {
				this.posterior = 0.0;
			}
			else {
				this.posterior = ((pH * eGivenH) / (pH * eGivenH + altHypothesis * eGivenNotH));
			}
			
			this.altTotalProb = 1 - totalProb;
			this.absenceOfEvidenceConditional = 1 - success;
			this.absenceOfEvidenceAltConditional = 1 - falsePos;
			if (falsePos == 0.0) {
				this.likelihood = 0.0;
			}
			else {
				this.likelihood = success / falsePos;
			}
		}
	}
	
	/**
	 * Method to combine likelihood ratios. E.g.:
	 * You think A is 80% likely; my initial impression is that it's 60% likely.  After you and I talk, maybe we both should think 70%.  
	 * "Average your starting beliefs", or perhaps "do a weighted average, weighted by expertise" is a common heuristic.
	 * But sometimes, not only is the best combination not the average, it's more extreme than either original belief.
	 * Let's say Jane and James are trying to determine whether a particular coin is fair.  They both think there's an 80% chance the coin is 
	 * fair.  They also know that if the coin is unfair, it is the sort that comes up heads 75% of the time.
	 * Jane flips the coin five times, performs a perfect Bayesian update, and concludes there's a 65% chance the coin is unfair.  
	 * James flips the coin five times, performs a perfect Bayesian update, and concludes there's a 39% chance the coin is unfair.  
	 * The averaging heuristic would suggest that the correct answer is between 65% and 39%.  But a perfect Bayesian, hearing both Jane's 
	 * and James's estimates – knowing their priors, and deducing what evidence they must have seen - would infer that the coin was 83% likely to 
	 * be unfair.
	 * 
	 * James, to end up with a 39% posterior on the coin being heads-weighted, must have seen four heads and one tail:
	 * P(four heads and one tail| heads-weighted) = (0.75^4 * 0.25^1) = 0.079.  P(four heads and one tail | fair) = 0.031.  
	 * P(heads-weighted | five heads) = (0.2*0.079)/(0.2*0.079 + 0.8*0.031) = 0.39, which is the posterior belief James reports.
	 * Jane must similarly have seen five heads and zero tails. Plugging the total nine heads and one tail into Bayes theorem:
	 * P(heads-weighted | nine heads and a tail) = ( 0.2 * (0.75^9 * 0.25^1) ) / ( 0.2 * (0.75^9 * 0.25^1) + 0.8 * (0.5^9 * 0.5^1) ) = 0.83, 
	 * giving us a posterior belief of 83% that the coin is heads-weighted.) 
	 * 
	 * */
	public void combineLiklihoodRatios(Bayes first, Bayes second){
		double firstSuccess = first.getSuccessRate();
		double secondSuccess = second.getSuccessRate();
		
		double firstFalsePos = first.getFalsePositive();
		double secondFalsePos = second.getFalsePositive();
		
		this.success = firstSuccess * secondSuccess;
		this.falsePos = firstFalsePos * secondFalsePos;
		this.isSuccessRateSet = true;
		this.isFalsePositiveSet = true;
		
		this.absenceOfEvidenceConditional = 1 - success;
		this.absenceOfEvidenceAltConditional = 1 - falsePos;
		
		
	}
	
	
	/****Setters and Getters*****/
	
	public void setPrior(double p) {
		if(p < 1.00) {
			this.prior = p;
			this.altHypothesis = 1 - p;
			this.isPriorSet = true;
		}
	}
		
	public void setSuccessRate(double s) {
		if(s < 1.00) {
			this.success = s;
			this.absenceOfEvidenceConditional = 1 - s;
			this.isSuccessRateSet = true;
		}
	}
	
	public void setFalsePositive(double f) {
		if(f < 1.00) {
			this.falsePos = f;
			this.absenceOfEvidenceAltConditional = 1 - f;
			this.isFalsePositiveSet = true;
		}
	}
	
	public void setTotalProbability(double t) {
		if(t < 1.00) {
			this.totalProb = t;
			this.altTotalProb = 1 - t;
			this.isTotalProbSet = true;
		}
	}
	
	public double getPrior() {
		return this.prior;
	}
	
	public double getAlternativeHypothesis(){
		return this.altHypothesis;
	}
	
	public double getSuccessRate(){
		return this.success;
	}
	
	public double getFalsePositive(){
		return this.falsePos;
	}
	
	public double getTotalProbability(){
		return this.totalProb;
	}
	
	public double getBayesFactor(){
		return this.likelihood;
	}
	
	public double getPosterior() {
		if( (isPriorSet) && (isSuccessRateSet) && (isFalsePositiveSet) ) {
			this.posterior = (this.prior * this.success) / ((this.prior * this.success) + (this.altHypothesis * this.falsePos));
		}
		
		if( (isPriorSet) && (isSuccessRateSet) && (isTotalProbSet) ) {
			this.posterior = this.prior * this.success / this.totalProb;
			return this.posterior;
		}
		return this.posterior;
	}
	
	/**
	 * Absence of evidence is evidence of absence!
	 * */
	public double getAbsenceOfEvidence(){
		if (this.altTotalProb == 0) {
			return 0.0;
		}
		else {
		return (this.prior * this.absenceOfEvidenceConditional) / this.altTotalProb;
		}
	}
	
	public double getAbsenceOfEvidenceConditional() {
		return this.absenceOfEvidenceConditional;
	}
	
	public double getAbsenceOfEvidenceAltConditional(){
		return this.absenceOfEvidenceAltConditional;
	}
	
	public double getAltTotalProbability() {
		return this.altTotalProb;
	}
	
	
	/**
	 * Overloaded getter if one doesn't feel like instantiating this object
	 * @param pH: Prior probability
	 * @param pE: Total Probability/Probability of the evidence
	 * @param eGivenH: Success rate/conditional probability**/
	public double getPosterior(double pH, double eGivenH, double pE) {
		double hGivenE;
		if((pH > 1.00) || (pE > 1.00) || (eGivenH > 1.00)) {
			//what
			return - 1.0;
		}
		
		else{
			if(pE == 0.0) {
				hGivenE = 0;
			}
			else {
				hGivenE = ((pH * eGivenH) / pE);
			}
			
			return hGivenE;
		}
	}
	
	/**
	 * Overloaded getter if one doesn't feel like instantiating this object
	 * @param pH: Prior probability
	 * @param eGivenH: Success rate/conditional probability
	 * @param eGivenNotH: False positive rate
	 * @param l: extra parameter to distinguish it from other method**/
	public double getPosterior(double pH, double eGivenH, double eGivenNotH, boolean l) {
		if((pH > 1.00) || (eGivenNotH > 1.00) || (eGivenH > 1.00)) {
			//what
			return - 1.0;
		}
		
		else{
			double hGivenE = ((pH * eGivenH) / (pH * eGivenH)+((1 - pH) * eGivenNotH));
			return hGivenE;
		}
	}
	
	/*
	public double getBothSuccess() {
		return this.bothSuccess;
	}
	
	public double getBothFalsePos(){
		return this.bothFalsePos;
	}
	*/

}

