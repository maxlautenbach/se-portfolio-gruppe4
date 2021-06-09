package controller;

import model.Credit;

public class Calculation {
    public static double calculateEndAmount(double loanAmount, double interestRate, int interestPeriod, String paymentRhythm, Credit.creditTypes creditType) {
        double interestSum = 0;
        double actInterestRate;
        if(paymentRhythm.equals("monatlich")) {
            actInterestRate = (Math.pow(1 + interestRate / 100.0, 1.0 / 12.0) - 1);
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

    private static double calculateMaturityCredit(double loanAmount, double interestRate, double interestPeriod) {
        return loanAmount * interestRate * interestPeriod;
    }

    private static double calculateAnnuityCredit(double loanAmount, double interestRate, int interestPeriod) {
        double periodFactor = Math.pow(1+interestRate, interestPeriod); //Exponentialrechung
        double annuity = loanAmount * (periodFactor * interestRate)/(periodFactor-1);
        double interestSum = 0;
        double remainingDept = loanAmount;
        for (int i = 0; i<interestPeriod; i++){
            double interest = loanAmount * interestRate;
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
