package model;

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
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        //>0, <100
        this.interestRate = interestRate;
    }
    public int getInterestPeriod() {
        return interestPeriod;
    }
    public void setInterestPeriod(int interestPeriod) {
        //>0, <2000
        this.interestPeriod = interestPeriod;
    }
    public double getRepaymentAmount() {
        return repaymentAmount;
    }
    public void setRepaymentAmount(double repaymentAmount) { //does it make sense to make this one public?
        this.repaymentAmount = repaymentAmount;
    }
    public String getPaymentRhythm() {
        return paymentRhythm;
    }
    public void setPaymentRhythm(String paymentRhythm) {
        // either monatlich or jährlich other ones are not allowed here
        this.paymentRhythm = paymentRhythm;
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
