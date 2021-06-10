package controller;

import model.Credit;

import static model.Credit.creditTypes.*;

public class Calculation {


    public static double calculateEndAmount(double loanAmount, double interestRate, int interestPeriod, String paymentRhythm, Credit.creditTypes creditType) {
        double interestSum = 0;
        double actInterestRate;
        if(paymentRhythm.equals("monatlich")) {
            actInterestRate = interestRate /100.0;
            actInterestRate = actInterestRate/12;
        }
        else {actInterestRate = interestRate / 100.0;}
        switch (creditType) {
            case FAELLIGKEITSKREDIT:
                interestSum = calculateMaturityCredit(loanAmount, actInterestRate, interestPeriod);
                break;
            case ANNUITAETENKREDIT:
                interestSum = calculateAnnuityCredit(loanAmount, actInterestRate, interestPeriod);
                break;
            case ABZAHLUNGSKREDIT:
                interestSum = calculateInstallmentCredit(loanAmount, actInterestRate, interestPeriod);
                break;
        }
        return interestSum;
    }

    private static double calculateMaturityCredit(double loanAmount, double interestRate, double interestPeriod) {
        return loanAmount * interestRate * interestPeriod;
    }

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
