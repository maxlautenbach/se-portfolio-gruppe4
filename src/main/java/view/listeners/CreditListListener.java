package view.listeners;

import controller.Controller;
import model.Credit;
import view.View;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;

public class CreditListListener implements ListSelectionListener {

    private Controller controller;

    public CreditListListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
         controller.setCredit((Credit) controller.getView().getSavedCreditList().getSelectedValue());
         controller.getView().getValuesFromCredit();
    }
}
