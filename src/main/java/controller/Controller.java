package controller;

//Import models and views here

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Credit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Controller{
    private static Controller instance;

    private View view;
    private Credit credit = new Credit();
    private HashMap<Integer, Float> creditMap;
    private static int nextId;

    private Controller() {

    }

    private static void initNextId(){
        try {
            FileReader file = new FileReader("credits.json");
            JSONParser jsonParser = new JSONParser();
            JSONArray creditList = (JSONArray) jsonParser.parse(file);
            JSONObject lastAddedCredit = (JSONObject) creditList.get(creditList.size()-1);
            ArrayList<Integer> keySet = new ArrayList<Integer>(lastAddedCredit.keySet());
            nextId = Integer.parseInt(String.valueOf(keySet.get(0)));
            nextId += 1;
        } catch (ParseException | IOException e) {
            nextId = 1;
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
            System.out.println("credits.json created");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter("credits.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            if (creditList == null) {
                creditList = new JSONArray();
            }
            JSONObject object = (JSONObject) jsonParser.parse(this.convertObjectToJSON());
            JSONObject creditJSON = new JSONObject();
            creditJSON.put(nextId, object);
            creditList.add(creditJSON);
            nextId += 1;
            file.write(creditList.toJSONString());
            file.flush();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void loadObjectById(int id){
        try {
            FileReader fileReader = new FileReader("credits.json");
            JSONParser jsonParser = new JSONParser();
            JSONArray creditList = (JSONArray) jsonParser.parse(fileReader);
            JSONObject credit = (JSONObject) creditList.get(id-1);
            System.out.println("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
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