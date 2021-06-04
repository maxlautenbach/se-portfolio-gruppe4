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

    public void calculateEndAmount(){
        int interestPeriod = 0;
        int repaymentPeriod = 0;
        float endAmount = 0;
        String repaymentPeriodInWords = this.getRepaymentPeriod();
        float repaymentAmount = this.getRepaymentAmount();
        int period = 0;
        float loanAmount = this.getLoanAmount();
        float rate = this.getInterestRate();
        switch (this.getInterestPeriod()){
            case "monthly":
                if(this.getRepaymentPeriod().equals("yearly")){
                    repaymentPeriodInWords = "monthly";
                    repaymentAmount /= 12.0;
                }
                interestPeriod = 1;
                break;
            case "yearly":
                interestPeriod = 12;
                break;
        }
        switch (repaymentPeriodInWords){
            case "monthly":
                if(interestPeriod == 12){
                    interestPeriod = 1;
                    rate = (float) (100 * ((Math.pow(1 + this.getInterestRate() / 100, 1.0 / 12.0)) - 1));
                }
                repaymentPeriod = 1;
                break;
            case "yearly":
                repaymentPeriod = 12;
                break;
        }
        switch (this.getPeriodUoM()){
            case "months":
                period = this.getPeriod();
                break;
            case "years":
                period = this.getPeriod() * 12;
                break;
        }

        for(int i = 1; i <= period; i++){
            if(i % interestPeriod == 0){
                endAmount += (loanAmount * rate);
            }
            if(i % repaymentPeriod == 0){
                loanAmount -= repaymentAmount;
            }
        }

        this.setEndAmount(endAmount);
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