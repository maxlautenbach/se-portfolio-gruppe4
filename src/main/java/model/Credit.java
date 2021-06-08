package model;

import java.lang.Math;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Credit{
    private double loanAmount; // Kreditbetrag
    private double interestRate; //Zinssatz
    private int interestPeriod; // Laufzeit
    //private int period;
    private String periodUoM;
    private double repaymentAmount; //zurückzuzahlende Betrag
    //private String repaymentPeriod;
    private String paymentRhythm; // braucht man meiner Meinung nach gar nicht für die Berechnung
    public enum creditTypes {FAELLIGKEITSKREDIT, ANNUITAETENKREDIT, ABZAHLUNGSKREDIT}
    private creditTypes creditType; //Kreditarten
    private double interestSum; //aufaddierten Zinsen
    private double interest; //Zinsen der Periode
    private double repayment; //Tilgung
    private double annuity; //Annuität
    private double remainingDept; //Restschuld
    private double actInterestRate;

    public Credit(){
    }
    public void setParameters(double loanAmount, String periodUoM, double interestRate, int interestPeriod, String paymentRhythm, creditTypes creditType) {
        this.loanAmount = loanAmount;
        this.periodUoM = periodUoM;
        this.interestRate = interestRate/100;
        this.interestPeriod = interestPeriod;
        this.paymentRhythm = paymentRhythm;
        this.creditType = creditType;
    }
    public void calculateEndAmount() {
        interestSum = 0;
        if(paymentRhythm == "monatlich") {
            DecimalFormat df = new DecimalFormat("#.#####");
            df.setRoundingMode(RoundingMode.CEILING);
            actInterestRate = Double.parseDouble(df.format(Math.pow(interestRate, 1.0 / 12.0)));
            System.out.println(actInterestRate);
        }
        else {actInterestRate = interestRate;}
        switch (creditType) {
            case FAELLIGKEITSKREDIT:
                calculateMaturityCredit();
                break;
            case ANNUITAETENKREDIT:
                calculateAnnuityCredit();
                break;
            case ABZAHLUNGSKREDIT:
                calculateInstallmentCredit();
                break;
        }
    }
    private void calculateMaturityCredit() {
        interestSum = loanAmount * interestRate * interestPeriod;
        repaymentAmount = loanAmount + interestSum;
    }
    private void calculateAnnuityCredit() {
        double periodFactor = Math.pow(1+interestRate, interestPeriod); //Exponentialrechung
        annuity = loanAmount * (periodFactor * interestRate)/(periodFactor-1);
        remainingDept = loanAmount;
        for (int i = 0; i<interestPeriod; i++){
            interest = loanAmount * interestRate;
            interestSum = interestSum + interest;
            repayment = annuity - interest;
            remainingDept = remainingDept-repayment;
        }
        repaymentAmount = interestSum + loanAmount;
    }
    private void calculateInstallmentCredit() {
        repayment = loanAmount / interestPeriod;
        remainingDept = loanAmount;
        for (int i = 0; i<interestPeriod; i++) {
            interest = remainingDept * interestRate;
            interestSum = interestSum + interest;
            remainingDept = remainingDept - repayment;
        }
        repaymentAmount = interestSum + loanAmount;
    }
    public double getLoanAmount() {
        return loanAmount;
    }
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    public int getInterestPeriod() {
        return interestPeriod;
    }
    public void setInterestPeriod(int interestPeriod) {
        this.interestPeriod = interestPeriod;
    }
    public double getRepaymentAmount() {
        return repaymentAmount;
    }
    public void setRepaymentAmount(double repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }
    public String getPaymentRhythm() {
        return paymentRhythm;
    }
    public void setPaymentRhythm(String paymentRhythm) {
        this.paymentRhythm = paymentRhythm;
    }
    public model.Credit.creditTypes getCreditType() {
        return creditType;
    }
    public void setCreditType(model.Credit.creditTypes creditType) {
        this.creditType = creditType;
    }
    public double getInterestSum() {
        return interestSum;
    }
    public void setInterestSum(double interestSum) {
        this.interestSum = interestSum;
    }
}
