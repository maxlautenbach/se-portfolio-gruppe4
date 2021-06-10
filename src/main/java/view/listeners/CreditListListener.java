package view.listeners;

import controller.Controller;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CreditListListener implements ListSelectionListener {

    private final Controller controller;

    public CreditListListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
         controller.setCredit( controller.getView().getSavedCreditList().getSelectedValue());
         controller.getView().getValuesFromCredit();
    }
}
