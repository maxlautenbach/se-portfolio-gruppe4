package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;
import model.Credit;
import view.listeners.ButtonListener;
import view.listeners.TypeSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.Locale;


public class View extends JFrame {

    private static View instance;

    private Controller controller;
    private JPanel topPanel, centerPanel, bottomPanel;
    private BorderLayout outerLayout;
    private GroupLayout centerLayout;
    private JLabel title,
            amountLabel, interestRateLabel, termLabel,
            amountSymbol, interestRateSymbol, termSymbol,
            paymentPeriodLabel,
            interestLabel, interestSymbol, interestResult;
    private JTextField amountField, interestRateField, termField;
    private JComboBox creditTypeSelection;
    private JRadioButton periodMonth, periodYear;
    private ButtonGroup periodTimeSelection;
    private JList savedCreditList;
    private JScrollPane creditListScrollPane;
    private JSeparator resultSeparator;
    private JButton saveButton, cancelButton, calculateButton;
    private int height, width;



    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getCalculateButton() {
        return calculateButton;
    }

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

    public void onCalculateClick() {

        controller.getCredit().setParameters(Double.parseDouble(amountField.getText()),
                getSelectedButtonText(periodTimeSelection),
                Double.parseDouble(interestRateField.getText()),
                Integer.parseInt(termField.getText()),
                getSelectedButtonText(periodTimeSelection),
                Credit.creditTypes.valueOf(creditTypeSelection.getSelectedItem().toString().toUpperCase(Locale.ROOT)));
        controller.getCredit().calculateEndAmount();
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

    public void onCancelClick() {
        amountField.setText("");
        interestRateField.setText("");
        termField.setText("");
    }

    private void initalize() {

        try {
            FlatLightLaf.setup();
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        height = 600;
        width = 700;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Credit Calculator");
        setSize(width,height);
        setLocation(300,200);

        /*
        * Ininalisieren der Ui Elemente
        * */

        topPanel = new JPanel();
        centerPanel = new JPanel();
        bottomPanel = new JPanel();

        title = new JLabel("Kredit 1");
        amountLabel = new JLabel("Kreditbetrag");
        interestRateLabel = new JLabel("Zinssatz");
        termLabel = new JLabel("Laufzeit");
        interestLabel = new JLabel("Zinsen");
        amountSymbol = new JLabel("€");
        interestRateSymbol = new JLabel("%");
        termSymbol = new JLabel("M");
        paymentPeriodLabel = new JLabel("Zahlungsryhtmus");
        interestSymbol = new JLabel("€");
        resultSeparator = new JSeparator();
        cancelButton = new JButton("Abbrechen");
        saveButton = new JButton("Speichern");
        calculateButton = new JButton("Berechnen");

        amountField = new JTextField();
        interestRateField = new JTextField();
        termField = new JTextField();
        interestResult = new JLabel();

        creditTypeSelection = new JComboBox();
        creditTypeSelection.addItem("Faelligkeitskredit");
        creditTypeSelection.addItem("Annuitaetenkredit");
        creditTypeSelection.addItem("Abzahlungskredit");
        creditTypeSelection.setMaximumSize(new Dimension(width*3/4,20));
        creditTypeSelection.addActionListener(new TypeSelectionListener(this));

        periodMonth = new JRadioButton("monatlich");
        periodYear = new JRadioButton("jährlich");
        periodTimeSelection = new ButtonGroup();
        periodTimeSelection.add(periodMonth);
        periodTimeSelection.add(periodYear);


        savedCreditList = new JList(new String[]{"s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8"});
        creditListScrollPane = new JScrollPane();
        creditListScrollPane.setViewportView(savedCreditList);
        creditListScrollPane.setMaximumSize(new Dimension(width/4,height));
        savedCreditList.setToolTipText("Wähle einen gespeicherten Kredit");
        savedCreditList.setLayoutOrientation(JList.VERTICAL);

        /*
        * Erstellen und formatieren der Layouts
        * */

        outerLayout = new BorderLayout();
        outerLayout.setHgap(10);
        getContentPane().setLayout(outerLayout);

        topPanel.add(title, SwingConstants.CENTER);
        add(topPanel, BorderLayout.NORTH);

        calculateButton.addActionListener(new ButtonListener(this));
        bottomPanel.add(calculateButton);
        saveButton.addActionListener(new ButtonListener(this));
        bottomPanel.add(saveButton);
        cancelButton.addActionListener(new ButtonListener(this));
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);

        centerLayout = new GroupLayout(centerPanel);
        centerPanel.setLayout(centerLayout);
        centerLayout.setAutoCreateGaps(true);
        centerLayout.setAutoCreateContainerGaps(true);

        centerLayout.setHorizontalGroup(centerLayout.createSequentialGroup()
            .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(centerLayout.createSequentialGroup()
                    .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(creditTypeSelection)
                        .addGroup(centerLayout.createSequentialGroup()
                            .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(amountLabel)
                                .addComponent(interestRateLabel)
                                .addComponent(termLabel)
                                .addComponent(paymentPeriodLabel)
                                .addComponent(interestLabel)
                            )
                            .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(amountField)
                                .addComponent(interestRateField)
                                .addComponent(termField)
                                .addComponent(periodMonth)
                                .addComponent(periodYear)
                                .addComponent(interestResult)
                            )
                        )
                    )
                    .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(amountSymbol)
                        .addComponent(interestRateSymbol)
                        .addComponent(termSymbol)
                        .addComponent(interestSymbol)
                    )
                )
                .addComponent(resultSeparator)
            )
            .addComponent(creditListScrollPane)
        );
        centerLayout.setVerticalGroup(centerLayout.createSequentialGroup()
            .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addGroup(centerLayout.createSequentialGroup()
                    .addComponent(creditTypeSelection)
                    .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(amountLabel)
                        .addComponent(amountField)
                        .addComponent(amountSymbol)
                    )
                    .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(interestRateLabel)
                        .addComponent(interestRateField)
                        .addComponent(interestRateSymbol)
                    )
                    .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(termLabel)
                        .addComponent(termField)
                        .addComponent(termSymbol)
                    )
                    .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(paymentPeriodLabel)
                        .addGroup(centerLayout.createSequentialGroup()
                            .addComponent(periodMonth)
                            .addComponent(periodYear)
                        )
                    )
                    .addComponent(resultSeparator)
                    .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(interestLabel)
                        .addComponent(interestResult)
                        .addComponent(interestSymbol)
                    )
                )
                .addComponent(creditListScrollPane)
            )
        );

        add(centerPanel, BorderLayout.CENTER);

    }


}