package view.listeners;

import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Locale;

public class RadioButtonListener implements ActionListener {

    private View view;

    public RadioButtonListener(View view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.setTermSymbol( getSelectedButtonText(view.getPeriodTimeSelection()).toUpperCase(Locale.ROOT).charAt(0) );
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

}
