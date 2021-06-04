package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;

import javax.swing.*;
import java.awt.*;


public class View extends JFrame {

    private static View instance;

    private Controller controller;
    private JPanel centerPanel, bottomPanel;
    private BorderLayout outerLayout;
    private GroupLayout innerLayout;
    private JTextField betragField, zinssatzField, laufzeitField, zinsField;
    private JLabel betragLabel, zinssatzLabel, laufzeitLabel, zinsLabel;
    private JButton saveButton, cancelButton, calculateButton;



    private View(Controller controller) {
        this.controller = controller;
        initalize();
    }

    public static View getInstance(Controller controller) {

        if (instance == null) {
            instance = new View(controller);
        }

        return instance;
    }


    public void onSaveClick() {

    }

    private void initalize() {

        try {
            FlatLightLaf.setup();
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Credit Calculator");
        setSize(700, 600);
        setLocation(300,200);

        centerPanel = new JPanel();
        bottomPanel = new JPanel();
        betragField = new JTextField();
        zinssatzField = new JTextField();
        laufzeitField = new JTextField();
        zinsField = new JTextField();
        betragLabel = new JLabel("Kreditbetrag");
        zinssatzLabel = new JLabel("Zinssatz");
        laufzeitLabel = new JLabel("Laufzeit");
        zinsLabel = new JLabel("Zinsen");
        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save");
        calculateButton = new JButton("Calculate");

        outerLayout = new BorderLayout();
        outerLayout.setHgap(10);
        getContentPane().setLayout(outerLayout);

        add(new JLabel("Kredit 1"), BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        bottomPanel.add(calculateButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);

        innerLayout = new GroupLayout(centerPanel);
        centerPanel.setLayout(innerLayout);
        innerLayout.setAutoCreateGaps(true);
        innerLayout.setAutoCreateContainerGaps(true);
        innerLayout.setHorizontalGroup(
                innerLayout.createSequentialGroup()
                        .addGroup(innerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(betragLabel)
                                .addComponent(zinssatzLabel)
                                .addComponent(laufzeitLabel)
                                .addComponent(zinsLabel)
                        )
                        .addGroup(innerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(betragField)
                                .addComponent(zinssatzField)
                                .addComponent(laufzeitField)
                                .addComponent(zinsField)
                        )

        );
        innerLayout.setVerticalGroup(
                innerLayout.createSequentialGroup()
                        .addGroup(innerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(betragLabel)
                                .addComponent(betragField)
                        )
                        .addGroup(innerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(zinssatzLabel)
                                .addComponent(zinssatzField)
                        )
                        .addGroup(innerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(laufzeitLabel)
                                .addComponent(laufzeitField)
                        )
                        .addGroup(innerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(zinsLabel)
                                .addComponent(zinsField)
                        )
        );



    }


}