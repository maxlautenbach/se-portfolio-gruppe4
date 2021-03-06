package controller;

import model.Credit;
/**
 * Klasse, in der die Kalkulationslogik implementiert ist
 */
public class Calculation {

    /**
     * @param loanAmount
     * @param interestRate
     * @param interestPeriod
     * @param paymentRhythm
     * @param creditType
     * Eingabewerte werden gesetzt, Ergebnisse zurückgesetzt, Kalkulation je nach Kredittyp aufgerufen
     * @return interestSum
     */
    public static double calculateEndAmount(double loanAmount, double interestRate, int interestPeriod, String paymentRhythm, Credit.creditTypes creditType) {
        double interestSum = 0;
        double actInterestRate;
        if(paymentRhythm.equals("monatlich")) {
            actInterestRate = interestRate /100.0;
            actInterestRate = actInterestRate/12;
        }
        else {actInterestRate = interestRate / 100.0;}
        switch (creditType) {
            case FÄLLIGKEITSKREDIT:
                interestSum = calculateMaturityCredit(loanAmount, actInterestRate, interestPeriod);
                break;
            case ANNUITÄTENKREDIT:
                interestSum = calculateAnnuityCredit(loanAmount, actInterestRate, interestPeriod);
                break;
            case ABZAHLUNGSKREDIT:
                interestSum = calculateInstallmentCredit(loanAmount, actInterestRate, interestPeriod);
                break;
        }
        return interestSum;
    }
    /**
     * @param loanAmount
     * @param interestRate
     * @param interestPeriod
     * Methode zur Kalkulation eines einfachen Fälligkeitskredits
     * @return interestSum
     */
    private static double calculateMaturityCredit(double loanAmount, double interestRate, double interestPeriod) {
        return loanAmount * interestRate * interestPeriod;
    }
    /**
     * @param loanAmount
     * @param interestRate
     * @param interestPeriod
     * Methode zur Kalkulation eines Annuitätenkredits
     * @return interestSum
     */
    private static double calculateAnnuityCredit(double loanAmount, double interestRate, int interestPeriod) {
        double periodFactor = Math.pow(1+interestRate, interestPeriod); //Exponentialrechung
        double annuity = loanAmount * (periodFactor * interestRate)/(periodFactor-1);
        double interestSum = 0;
        double remainingDept = loanAmount;
        for (int i = 0; i<interestPeriod; i++){
            double interest = remainingDept * interestRate;
            interestSum = interestSum + interest;
            double repayment = annuity - interest;
            remainingDept = remainingDept-repayment;
        }
        return interestSum;
    }
    /**
     * @param loanAmount
     * @param interestRate
     * @param interestPeriod
     * Methode zur Kalkulation eines Abzahlungskredits
     * @return interestSum
     */
    private static double calculateInstallmentCredit(double loanAmount, double interestRate, int interestPeriod) {
        double repayment = loanAmount / interestPeriod;
        double remainingDept = loanAmount;
        double interestSum = 0;
        for (int i = 0; i<interestPeriod; i++) {
            double interest = remainingDept * interestRate;
            interestSum = interestSum + interest;
            remainingDept = remainingDept - repayment;
        }
        return interestSum;
    }
}
