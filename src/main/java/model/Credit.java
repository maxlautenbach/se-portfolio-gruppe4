package model;

import java.util.Locale;

public class Credit{
    private double loanAmount; // Kreditbetrag
    private double interestRate; //Zinssatz
    private int interestPeriod; // Laufzeit
    //private int period;
    private String periodUoM;
    private double repaymentAmount; //zurückzuzahlende Betrag
    //private String repaymentPeriod;
    private String paymentRhythm; // braucht man meiner Meinung nach gar nicht für die Berechnung
    public enum creditTypes {FÄLLIGKEITSKREDIT, ANNUITÄTENKREDIT, ABZAHLUNGSKREDIT}
    private creditTypes creditType; //Kreditarten
    private double interestSum; //aufaddierten Zinsen
    private double interest; //Zinsen der Periode
    private double repayment; //Tilgung
    private double annuity; //Annuität
    private double remainingDept; //Restschuld
    private double actInterestRate;

    public Credit(){
    }
    @Override
    public String toString(){
        try {
            StringBuilder typeName = new StringBuilder(creditType.toString().toLowerCase(Locale.ROOT));
            typeName.replace(0, 1, String.valueOf(typeName.charAt(0)).toUpperCase(Locale.ROOT));
            return loanAmount + "€ - " + typeName;
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void setParameters(double loanAmount, String periodUoM, double interestRate, int interestPeriod, String paymentRhythm, creditTypes creditType) {
        this.loanAmount = loanAmount;
        this.periodUoM = periodUoM;
        this.interestRate = interestRate;
        this.interestPeriod = interestPeriod;
        this.paymentRhythm = paymentRhythm;
        this.creditType = creditType;
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
