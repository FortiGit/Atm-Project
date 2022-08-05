package com.ephrem;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

/**  Atm Machine programing
 * Ephrem Kidane
 */

public class ATM extends JFrame{
    // Data fields that are for the look of the program
    public JLabel pinLabel;
    public JPasswordField pinField;
    public JPanel welcomePanel;
    public JFrame welcomeFrame;
    public JButton nextButton;

    static final int width = 500, height = 500,
            textFieldWidth = 225, textFieldHeight = 25;

    private final JCheckBox checkingRadio = new JCheckBox("Checking Account");
    private final JCheckBox savingsRadio = new JCheckBox("Savings Account");
    private final JTextField entry = new JTextField("");
    private final JOptionPane frame = new JOptionPane();

    // The two required objects for the checking and savings accounts
    private static Account checking = new Account().new Checking();
    private static Account savings = new Account().new Savings();

    // Present numbers to the user formatted for dollars
    private static DecimalFormat df = new DecimalFormat("$0.00");

    // Method creates a Checking and Savings account based on starting values
    public static void makeAccounts(double checkingStartingBalance,
                                    double savingsStartingBalance) {

        checking.setBalance(checkingStartingBalance);
        savings.setBalance(savingsStartingBalance);
    }

    // Error handles invalid input like blanks, letters, and negative numbers
    public void errorValidNumber() {
        JOptionPane.showMessageDialog(frame, "Please enter a valid amount. " +
                "\n If withdrawing, please use $10 increments.");
    }

    /**
     * Action Listeners
     */

    // Action listener for the Withdrawal button
    class WithdrawButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // First checks for negative number and increment of 20
                if (getEntryValue() > 0 && getEntryValue() % 10 == 0) {
                    // Checks radio selection
                    if (checkingRadio.isSelected()) {
                        checking.withdraw(getEntryValue());
                        JOptionPane.showMessageDialog(frame, df.format(getEntryValue()) +
                                " withdrawn from Checking.");
                    } else if (savingsRadio.isSelected()) {
                        savings.withdraw(getEntryValue());
                        JOptionPane.showMessageDialog(frame, df.format(getEntryValue()) +
                                " withdrawn from Savings.");
                    }
                    clearEntryValue();
                } else errorValidNumber();
                clearEntryValue();
            } catch (InsufficientFunds insufficientFunds) {
                System.out.println("Caught in main.");
            }
        }
    }

    // Action listener for the Deposit button
    class DepositButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // First checks for positive number
            if (getEntryValue() > 0) {
                // Then checks for radio selection
                if (checkingRadio.isSelected()) {
                    checking.deposit(getEntryValue());
                    JOptionPane.showMessageDialog(frame, df.format(getEntryValue()) +
                            " deposited into Checking.");
                } else if (savingsRadio.isSelected()) {
                    savings.deposit(getEntryValue());
                    JOptionPane.showMessageDialog(frame, df.format(getEntryValue()) +
                            " deposited into Savings.");
                }
                clearEntryValue();
            } else errorValidNumber();
            clearEntryValue();
        }
    }

    // Action listener for the Transfer To button
    class TransferToButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // First checks for positive number
                if (getEntryValue() > 0) {
                    // Then checks for radio selection
                    if (checkingRadio.isSelected()) {
                        // Separate methods for transferFrom and transferTo
                        savings.transferFrom(getEntryValue());
                        checking.transferTo(getEntryValue());
                        JOptionPane.showMessageDialog(frame, df.format(getEntryValue()) +
                                " transferred from Savings to Checking.");
                    } else if (savingsRadio.isSelected()) {
                        checking.transferFrom(getEntryValue());
                        savings.transferTo(getEntryValue());
                        JOptionPane.showMessageDialog(frame, df.format(getEntryValue()) +
                                " transferred from Checking to Savings.");
                    }
                    clearEntryValue();
                } else errorValidNumber();
                clearEntryValue();
            } catch (InsufficientFunds insufficientFunds) {
                System.out.println("Caught in main.");
            }
        }
    }

    // Action listener for the Transfer To button
    class BalanceButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Only checks for radio selection
            if (checkingRadio.isSelected()) {
                JOptionPane.showMessageDialog(frame,
                        "Your checking account balance is: \n" +
                                df.format(checking.getBalance()));
            } else if (savingsRadio.isSelected()) {
                JOptionPane.showMessageDialog(frame,
                        "Your savings account balance is: \n" +
                                df.format(savings.getBalance()));
            } else errorValidNumber();
            clearEntryValue();
        }
    }
    class ReportListenerButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Only checks for radio selection
                if (checkingRadio.isSelected()) {
                    JOptionPane.showMessageDialog(frame,
                            "You made the following transaction on"+
                                    " checking Account\n"+checking.getReport()+" On Checking Account");
                } else if (savingsRadio.isSelected()) {
                    JOptionPane.showMessageDialog(frame,
                            "You made : \n" +" transaction on "+
                                    savings.getReport()+" Saving account");
                } else errorValidNumber();
                clearEntryValue();

        }
    }
    class PinFieldAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            try {
                if (Character.isLetter(Integer.parseInt(pinField.getText()))) {
                    pinField.setText("");
                    welcomeFrame.setVisible(false);
                    setVisible(true);
                }
                else {
                    throw new NumberFormatException();
                }
                } catch(NumberFormatException tm){
                        JOptionPane.showMessageDialog(frame, "Invalid Entry: Please Enter four Numbers only.");
                    }

        }
    }

    class NextButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {



                pinField.setText("");
                pinField.requestFocus(true);
                welcomeFrame.setVisible(false);
                setVisible(true);

        }
    }

        class ClearButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if (checkingRadio.isSelected()) {
                JOptionPane.showMessageDialog(frame,
                        "Report has been cleared from : \n" +checking.getClear()+ " Checking Account");
            } else if (savingsRadio.isSelected()) {
                JOptionPane.showMessageDialog(frame,
                        "Report has been cleared from : \n" + savings.getClear()+" Saving Account");
            } else errorValidNumber();
            clearEntryValue();
        }
        }

    /**
     * Constructor of the ATM object. This constructor creates the panels,
     * sets their layouts, and adds them to the panel. It then creates the
     * Checking and Savings accounts and applies the action listeners to
     * each of the buttons.

     */
    public ATM(double checkingStartingBalance, double savingsStartingBalance) {

        super("ATM Machine");
       Image image = Toolkit.getDefaultToolkit().getImage("images.png");



        this.setContentPane(new JPanel(){
           public void paintComponent(Graphics g){
               super.paintComponent(g);
               g.drawImage(image,100,20,null);


           }
       });
        pack();
        setVisible(true);
               /////////////////
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         nextButton = new JButton("Next");

         //  Welcome Frames

        JCheckBox menu = new JCheckBox("Menu");
        menu.add(checkingRadio);
        menu.add(savingsRadio);
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER,200,20));
        welcomePanel.setPreferredSize(new Dimension(400,200));
        welcomeFrame = new JFrame("Welcome to the ATM");
        welcomeFrame.setLocation(200,200);
        welcomePanel.setPreferredSize(new Dimension(400,200));
        welcomeFrame.getContentPane().add(welcomePanel);
        welcomeFrame.pack();
        welcomeFrame.setVisible(true);
        ////////////////

        setLayout(new GridBagLayout());
        GridBagConstraints layout = new GridBagConstraints();
        setFrame(width, height);
        JPanel buttonPanel = new JPanel();
        JPanel textEntry = new JPanel();
        setResizable(false);
        layout.gridy = 2;
        getContentPane().setBackground(Color.GRAY);
        add(buttonPanel);
        add(textEntry, layout);
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        textEntry.setLayout(new GridLayout(1, 1));
        // Data fields for the construction of the JFrame elements
        JButton withdrawButton = new JButton("Withdraw");
        buttonPanel.add(withdrawButton);
        JButton depositButton = new JButton("Deposit");
        buttonPanel.add(depositButton);
        JButton transferToButton = new JButton("Transfer To");
        buttonPanel.add(transferToButton);
        JButton report = new JButton("Print Receipt");
        buttonPanel.add(report);
        JButton clear = new JButton("Clear");
        buttonPanel.add(clear);
         pinLabel = new JLabel("Enter pin and Press Enter");
         pinField = new JPasswordField(10);

        welcomePanel.add(pinLabel);
        welcomePanel.add(pinField);
        JButton balanceButton = new JButton("Balance");
        buttonPanel.add(balanceButton);
        ButtonGroup radios = new ButtonGroup();
       radios.add(checkingRadio);
        radios.add(savingsRadio);
        buttonPanel.add(checkingRadio);
        buttonPanel.add(savingsRadio);
        entry.setPreferredSize(new Dimension(textFieldWidth, textFieldHeight));
        checkingRadio.setSelected(true);
        textEntry.add(entry);
        //add(menu);

        // Creates the checking and savings accounts
        makeAccounts(checkingStartingBalance, savingsStartingBalance);

        // Action listeners
        withdrawButton.addActionListener(new WithdrawButtonListener());
        depositButton.addActionListener(new DepositButtonListener());
        transferToButton.addActionListener(new TransferToButtonListener());
        balanceButton.addActionListener(new BalanceButtonListener());
        report.addActionListener(new ReportListenerButton());
        clear.addActionListener(new ClearButton());
        pinField.addActionListener(new PinFieldAction());
        nextButton.addActionListener(new NextButtonAction());
    }



    // This method returns the text in the text entry field
    public double getEntryValue() {
        try {
            return Double.parseDouble(entry.getText());
        } catch (NumberFormatException e) {
            System.out.println("Caught in getEntryValue\n");
            clearEntryValue();
            return 0;
        }
    }

    // Clears the text entry field
    public void clearEntryValue() {
        entry.setText("");
    }

    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void display() {
        welcomeFrame.setVisible(true);
        setVisible(false);
    }

    public static void main(String[] args) {
        // Creates a new ATM object that will have $25 in Checking account and $50 in Saving account.
        ATM frame = new ATM(25, 50);
        frame.display();
    }
}
