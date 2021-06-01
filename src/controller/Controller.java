package controller;

//Import models and views here

import model.Credit;
import view.View;

public class Controller{
    private static Controller instance;

    private View view;
    private Credit credit;

    private Controller(){
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

}