package view.listeners;

import view.View;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;

public class CreditListListener implements ListSelectionListener {

    private View view;

    public CreditListListener(View view){
        this.view = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        view.getSavedCreditList().getSelectedValue();
    }
}
