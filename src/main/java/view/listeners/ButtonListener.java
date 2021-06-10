package view.listeners;

import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    private final View view;

    public ButtonListener(View view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getCalculateButton()){
            view.onCalculateClick();
        }else if(e.getSource() == view.getCancelButton()){
            view.onCancelClick();
        }else if(e.getSource() == view.getSaveButton()){
            view.onSaveClick();
        }
    }

}
