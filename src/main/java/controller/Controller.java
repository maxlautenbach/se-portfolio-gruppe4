package controller;

//Import models and views here

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Credit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.View;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Controller{
    private static Controller instance;

    private View view;
    private Credit credit = new Credit();
    private HashMap<Integer, Float> creditMap;
    private static int nextId;

    private Controller() {

    }

    private static void initNextId() {
        try {
            FileReader reader = new FileReader("prog.log");
            nextId += Integer.parseInt(String.valueOf(reader.read()));
        } catch (FileNotFoundException e) {
            try {
                FileWriter file = new FileWriter("prog.log");
                nextId = 1;
                file.write(1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveNextId(){
        FileWriter file = null;
        try {
            file = new FileWriter("prog.log");
            file.write(nextId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Controller getInstance() {
        initNextId();

        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public void createObject(float loanAmount, float interestRate, String interestPeriod, int period, String periodUoM, float repaymentAmount, String repaymentPeriod, String creditType){
        credit.setAllAtributes(loanAmount, interestRate, interestPeriod, period, periodUoM, repaymentAmount, repaymentPeriod, creditType);
    }

    public void saveObject(){
        JSONParser jsonParser = new JSONParser();
        JSONArray creditList = null;
        try {
            FileReader reader = new FileReader("credits.json");
            creditList = (JSONArray) jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter("credits.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            if (creditList == null){
                creditList = new JSONArray();
                JSONObject object = (JSONObject) jsonParser.parse(this.convertObjectToJSON());
                JSONObject creditJSON = new JSONObject();
                creditJSON.put(nextId, object);
                creditList.add(creditJSON);
                nextId += 1;
                saveNextId();
            }
            file.write(creditList.toJSONString());
            file.flush();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String convertObjectToJSON(){
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(this.credit);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            json = "ERROR";
            e.printStackTrace();
        }
        return json;
    }

    private void calculateEndAmount(){
        int interestPeriod = 0;
        int repaymentPeriod = 0;
        float endAmount = 0;
        String repaymentPeriodInWords = this.credit.getRepaymentPeriod();
        float repaymentAmount = this.credit.getRepaymentAmount();
        int period = 0;
        float loanAmount = this.credit.getLoanAmount();
        float rate = this.credit.getInterestRate();
        switch (this.credit.getInterestPeriod()){
            case "monthly":
                if(this.credit.getRepaymentPeriod().equals("yearly")){
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
                    rate = (float) (100 * ((Math.pow(1 + this.credit.getInterestRate() / 100, 1.0 / 12.0)) - 1));
                }
                repaymentPeriod = 1;
                break;
            case "yearly":
                repaymentPeriod = 12;
                break;
        }
        switch (this.credit.getPeriodUoM()){
            case "months":
                period = this.credit.getPeriod();
                break;
            case "years":
                period = this.credit.getPeriod() * 12;
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

        this.credit.setEndAmount(endAmount);
    }

}