package model;

import java.util.Locale;

import controller.Controller;
import view.View;

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
    private View view;

    public Credit(){
        Controller control = Controller.getInstance();
        view = control.getView();
    }
    @Override
    public String toString(){
        try {
            StringBuilder typeName = new StringBuilder(creditType.toString().toLowerCase(Locale.ROOT));
            typeName.replace(0, 1, String.valueOf(typeName.charAt(0)).toUpperCase(Locale.ROOT));
            return repaymentAmount + "€ - " + typeName;
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void setParameters(double loanAmount, double interestRate, int interestPeriod, String paymentRhythm, creditTypes creditType) {
        setLoanAmount(loanAmount);
        setPaymentRhythm(paymentRhythm);
        setInterestRate(interestRate);
        setInterestPeriod(interestPeriod);
        setCreditType(creditType);

    }

    public double getLoanAmount() {
        return loanAmount;
    }
    public void setLoanAmount(double loanAmount) {
        //>0, <1,7 * 10^308, no letters but its an double so proving that here does not make that much sense
        this.loanAmount = loanAmount;
        if(loanAmount <= 0)
        {
            view.errorMessage("Der Kreditbetrag darf nicht kleiner/gleich null sein. Bitte korrigieren Sie Ihre Eingabe.");
        }
        else if(interestRate < 1.7E308)
        {
            view.errorMessage("Der Kreditbetrag ist zu hoch. Bitte korrigieren Sie Ihre Eingabe.");
        }
        else{
            this.interestRate = interestRate;
        }
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        //>0, <100
        if(interestRate <= 0)
        {
            view.errorMessage("Der Zinssatz darf nicht kleiner/gleich null sein. Bitte korrigieren Sie Ihre Eingabe.");
        }
        else if(interestRate >= 100)
        {
            view.errorMessage("Der Zinssatz darf nicht größer/gleich 100 sein. Bitte korrigieren Sie Ihre Eingabe.");
        }
        else{
            this.interestRate = interestRate;
        }

    }
    public int getInterestPeriod() {
        return interestPeriod;
    }
    public void setInterestPeriod(int interestPeriod) {
        if(interestPeriod <= 0)
        {
            view.errorMessage("Die Laufzeit darf nicht kleiner/gleich null sein. Bitte korrigieren Sie Ihre Eingabe.");
        }
        else{
            this.interestPeriod = interestPeriod;
        }
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
        // either monatlich or jährlich other ones are not allowed here
        if (paymentRhythm.equals("monatlich") || paymentRhythm.equals("jährlich")){
            this.paymentRhythm = paymentRhythm;
        }
        else {view.errorMessage("Bitte wählen Sie erneut den gewünschten Zahlungsrhythmus.");}
    }
    public model.Credit.creditTypes getCreditType() {
        return creditType;
    }
    public void setCreditType(model.Credit.creditTypes creditType) {
        //because of the enum, plausibility check is already given, but exception handling for errors would make sense
        this.creditType = creditType;
    }
    public double getInterestSum() {
        return interestSum;
    }
    public void setInterestSum(double interestSum) {
        this.interestSum = interestSum; //does it make sense to let that be public?
    }
}
