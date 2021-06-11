package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;
import model.Credit;
import view.listeners.ButtonListener;
import view.listeners.CreditListListener;
import view.listeners.RadioButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;


public class View extends JFrame {
    private static View instance;

    private final Controller controller;
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
    private JScrollPane creditListScrollPane;
    private JSeparator resultSeparator;
    private JList<Credit> savedCreditList;
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

    public void errorMessage(String errorMessage){
        JDialog errorDialog = new JDialog();
        errorDialog.setSize(200,100);
        errorDialog.setLocation(400,400);
        errorDialog.setTitle("An Error occurred");
        JPanel errorPanel = new JPanel();
        BorderLayout errorLayout = new BorderLayout();
        errorPanel.setLayout(errorLayout);
        errorDialog.add(errorPanel);
        errorPanel.add( new JLabel(errorMessage) , BorderLayout.CENTER);
        errorDialog.setVisible(true);
        JButton button = new JButton("Close");
        button.addActionListener(e -> errorDialog.dispose());
        errorDialog.add(button,BorderLayout.SOUTH);
    }

    public void onSaveClick() {
        onCalculateClick();
        controller.saveObject();
        if(controller.loadAllObjects() != null){
            savedCreditList.setListData(controller.loadAllObjects());
        }
        savedCreditList.repaint();
    }

    public void onCalculateClick() {
        try{
        controller.getCredit().setParameters(Double.parseDouble(amountField.getText()),
                Double.parseDouble(interestRateField.getText()),
                Integer.parseInt(termField.getText()),
                getSelectedButtonText(periodTimeSelection),
                Credit.creditTypes.valueOf(Objects.requireNonNull(creditTypeSelection.getSelectedItem()).toString().toUpperCase(Locale.ROOT)));
        controller.calculateEndAmount();
        interestResult.setText(String.valueOf(controller.getCredit().getInterestSum()));
        }catch (Exception e){
            e.printStackTrace();
        }
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

    public void getValuesFromCredit(){
        switch (String.valueOf( controller.getCredit().getCreditType()).toUpperCase(Locale.ROOT)){
            case "FÄLLIGKEITSKREDIT" : creditTypeSelection.setSelectedItem("Fälligkeitskredit"); break;
            case "ANNUITÄTENKREDIT" : creditTypeSelection.setSelectedItem("Annuitätenkredit"); break;
            case "ABZAHLUNGSKREDIT" : creditTypeSelection.setSelectedItem("Abzahlungskredit"); break;
        }
        amountField.setText(String.valueOf( controller.getCredit().getLoanAmount()));
        interestRateField.setText(String.valueOf( controller.getCredit().getInterestRate()));
        termField.setText(String.valueOf( controller.getCredit().getInterestPeriod()));
        switch (controller.getCredit().getPaymentRhythm()){
            case "monatlich" : periodTimeSelection.setSelected(periodMonth.getModel(),true);break;
            case "jährlich" : periodTimeSelection.setSelected(periodYear.getModel(),true);break;
        }
        interestResult.setText("");
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
        setSize(800,350);
        setLocation(300,200);
        setResizable(false);

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

        amountField = new JTextField();
        interestRateField = new JTextField();
        termField = new JTextField();
        interestResult = new JLabel();

        resultSeparator = new JSeparator();
        cancelButton = new JButton("Abbrechen");
        saveButton = new JButton("Speichern");
        calculateButton = new JButton("Berechnen");

        creditTypeSelection = new JComboBox<>();
        creditTypeSelection.addItem("Fälligkeitskredit");
        creditTypeSelection.addItem("Annuitätenkredit");
        creditTypeSelection.addItem("Abzahlungskredit");

        periodMonth = new JRadioButton("monatlich");
        periodMonth.addActionListener(new RadioButtonListener(this));
        periodYear = new JRadioButton("jährlich");
        periodYear.addActionListener(new RadioButtonListener(this));
        periodTimeSelection = new ButtonGroup();
        periodTimeSelection.add(periodMonth);
        periodTimeSelection.add(periodYear);

        savedCreditList = new JList<>(controller.loadAllObjects());
        creditListScrollPane = new JScrollPane();
        creditListScrollPane.setViewportView(savedCreditList);
        creditListScrollPane.setMaximumSize(new Dimension(200,400));
        savedCreditList.setToolTipText("Wähle einen gespeicherten Kredit");
        savedCreditList.setLayoutOrientation(JList.VERTICAL);
        savedCreditList.addListSelectionListener(new CreditListListener(this.controller));

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
            .addGap(15,20,25)
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
            .addGap(15,20,25)
            .addComponent(creditListScrollPane)
        );
        centerLayout.setVerticalGroup(centerLayout.createSequentialGroup()
            .addGroup(centerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addGroup(centerLayout.createSequentialGroup()
                    .addGap(15,20,25)
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
                    .addGap(15,20,25)
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

    public JButton getSaveButton() {
        return saveButton;
    }
    public JButton getCancelButton() {
        return cancelButton;
    }
    public JButton getCalculateButton() {
        return calculateButton;
    }
    public ButtonGroup getPeriodTimeSelection() {
        return periodTimeSelection;
    }
    public JList<Credit> getSavedCreditList() {
        return savedCreditList;
    }
    public void setTermSymbol(char symbol) {
        this.termSymbol.setText(String.valueOf(symbol));
    }
}