#pragma once
#include <ostream>
#include <sstream>

#ifndef BAYES_H
#define BAYES_H

class Bayes
	
{
	friend ostream &operator<<(ostream &out, Bayes &bt);
private:
	double pH, //prior probability
		pEgivenH, // success rate
		pEgivenAltH, //false positive rate
		pE, //total probability
		pAltH, //alternative prior probability
		pAltEgivenH, //absence of evidence given prior
		pAltEgivenAltH, //absence of evidence given alternative hypothesis
		pAltE, //absence of evidence

		pHgivenE, //posterior probability
		pHgivenAltE; //posterior probability given absence of evidence
public:

	//constructor for longform bayes
	Bayes(double prior, double succRate, double falsePos, bool l){
		pH = prior;
		pEgivenH = succRate;
		pEgivenAltH = falsePos;
		pAltH = 1 - pH;
		pAltEgivenH = 1 - pEgivenH;
		pAltEgivenAltH = 1 - pEgivenAltH;

		pE = (pH * pEgivenH) + (pAltH * pEgivenAltH);
		pAltE = (pH * pAltEgivenH) + (pAltH * pAltEgivenAltH);

		pHgivenE = (pH * pEgivenH) / ((pH * pEgivenH) + (pAltH * pEgivenAltH));
		pHgivenAltE = (pH * pAltEgivenH) / ((pH * pAltEgivenH) + (pAltH * pAltEgivenAltH));


	};

	//constructor for short form bayes
	Bayes(double prior, double succRate, double totalProbability){
		pH = prior;
		pEgivenH = succRate;
		pE = totalProbability;
		pAltH = 1 - pH;
		pAltEgivenH = 1 - pEgivenH;
		pAltE = 1 - pE;

		pEgivenAltH = ((pE - pEgivenH*pH) / pAltH);
		pAltEgivenAltH = ((pAltE - pAltEgivenH*pH) / pAltH);

		pHgivenE = pH * pEgivenH / pE;
		pHgivenAltE = pH * pAltEgivenH / pAltE;
	};

	//destructor
	//~Bayes(void){};

	double getPrior(){ return pH; }

	double getSuccessRate() { return pEgivenH; }

	double getFalsePositive() { return pEgivenAltH;	}

	double getTotalProbability() { return pE; }

	double getAlternativeHypothesis() { return pAltH; }

	double getAbsenceGivenHypothesis() { return pAltEgivenH; }

	double getAbsenceGivenAltHypothesis() { return pAltEgivenAltH; }

	double getAbsenceOfEvidence() { return pAltE; }

	double getPosterior() { return pHgivenE; }

	double getAbsenceOfEvidenceGivenHypothesis() { return pHgivenAltE; }

	//FYI this overloaded addition operator assumes the prior for both Bayes objects are the same
	const Bayes operator+(Bayes &b) {
		double newSuccess, newFalsePos;
		newSuccess = b.getSuccessRate() * pEgivenH; 
		newFalsePos = b.getFalsePositive() * pEgivenAltH;
		Bayes newBT = Bayes(b.getPrior(), newSuccess, newFalsePos, true);
		return newBT;
	}

	//this overloaded ++ operator basically redoes a Bayesian update using the posterior as the new prior and keeps the success rate and total probability constant
	const Bayes operator++(int b) {
		Bayes BTplusplus(pHgivenE, pEgivenH, pEgivenAltH, true);
		return BTplusplus;
	}

	//this overloaded -- operator basically redoes a Bayesian update using the posterior as the new prior while using Pr(~E | H) and Pr(~E) as the other inputs
	const Bayes operator--(int b) {
		Bayes BTplusplus(getPosterior(), getAbsenceGivenHypothesis(), getAbsenceGivenAltHypothesis(), true);
		return BTplusplus;
	}

	//converts from double to string
	string convert(double d) {
		std::ostringstream oss;
		oss << d;
		return oss.str();
	}


};

ostream &operator << (ostream &out, Bayes &bt) {
	out << "Pr(H | E) = " << bt.convert(bt.getPosterior()) << endl;
	return out;
}

#endif