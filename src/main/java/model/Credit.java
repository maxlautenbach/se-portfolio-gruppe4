package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Credit{

    private float loanAmount;
    private float interestRate;
    private String interestPeriod;
    private int period;
    private String periodUoM;
    private float repaymentAmount;
    private String repaymentPeriod;
    private String creditType;
    private float endAmount;

    public Credit(){

    }

    public void setAllAtributes(float loanAmount, float interestRate, String interestPeriod, int period, String periodUoM, float repaymentAmount, String repaymentPeriod, String creditType) {
        this.loanAmount = loanAmount;
        this.endAmount = loanAmount;
        this.interestRate = interestRate;
        this.interestPeriod = interestPeriod;
        this.period = period;
        this.periodUoM = periodUoM;
        this.repaymentAmount = repaymentAmount;
        this.repaymentPeriod = repaymentPeriod;
        this.creditType = creditType;
    }

    public void setEndAmount(float endAmount) {
        this.endAmount = endAmount;
    }

    public float getLoanAmount() {
        return loanAmount;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public String getInterestPeriod() {
        return interestPeriod;
    }

    public int getPeriod() {
        return period;
    }

    public String getPeriodUoM() {
        return periodUoM;
    }

    public float getRepaymentAmount() {
        return repaymentAmount;
    }

    public String getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public String getCreditType() {
        return creditType;
    }

    public float getEndAmount() {
        return endAmount;
    }
}