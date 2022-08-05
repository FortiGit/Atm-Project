package com.ephrem;

import javax.swing.*;
import java.io.*;
/**  Atm Machine programing
 *  Group Member
 * Ephrem Kidane
 * Jean Amani Paul
 * Suzan Gehad
 */
public class Account {
    private final JOptionPane frame = new JOptionPane();
    public String getReport() {
        String report = read("Transfer.txt");
        if(report==(null))
            return "No Transaction made";
        else {
            return report;
        }
    }

    /**
         * The only needed data field is balance. This will be used separately
         * by each of the sub classes. So Checking will have its own balance,
         * and Savings its own. They will not merge.
         */

        private double balance;
        String report;


    public boolean getClear() {
        File f = new File("Transfer.txt");
        return f.delete();
    }

    // Default constructor for account is needed for the sub classes
        public Account() {

        }

        // Sets the balance of the Account
        public void setBalance(double balance) {
            this.balance = balance;
        }

        // Returns the balance of the account
        public double getBalance() {
            return this.balance;
        }

        /**
         * Sub Classes
         */

        // Subclass for Checking accounts
        public class Checking extends Account {

            public Checking() {
            }
        }

        // Subclass for Savings accounts
        public class Savings extends Account {

            public Savings() {
            }
        }

        /**
         * Methods
         */

        // Method for Withdraw action listener
        public void withdraw(double withdrawAmount) throws InsufficientFunds {

            // If the desired amound would bring balance less than 0, throws error
            if (this.balance - withdrawAmount < 0) {
                throw new InsufficientFunds();
            }

            this.balance = this.balance - withdrawAmount;
            String balance = String.valueOf(this.balance);
            writeAcc("\n"+balance,"Transfer.txt");
        }

        // Method for the Deposit action listener
        public void deposit(double depositAmount) {
            this.balance = this.balance + depositAmount;
            String balance = String.valueOf(this.balance);
            writeAcc("\n"+balance,"Transfer.txt");
        }


        // Method for the Transfer To action listener that adds amount
        public void transferTo(double transferAmount) {
            this.balance = this.balance + transferAmount;
            String balance = String.valueOf(this.balance);
            writeAcc("\n"+balance,"Transfer.txt");
        }

        // Method for the Transfer To action listener that removes amount
        public void transferFrom(double transferAmount) throws InsufficientFunds {
            // Checks to ensure sufficient funds
            if (this.balance - transferAmount < 0) {
                throw new InsufficientFunds();
            }

            this.balance = this.balance - transferAmount;
            String balance = String.valueOf(this.balance);
            writeAcc("\n"+balance,"Transfer.txt");
        }
    public void writeAcc(String str, String file){
        try{
            FileOutputStream fin = new FileOutputStream(file,true);
            BufferedOutputStream tm = new BufferedOutputStream(fin);
            byte [] bn = str.getBytes();
            tm.write(bn);
            tm.flush();
            tm.close();
            fin.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  String read(String file){
        String str ="";
        try{
            FileInputStream tmn = new FileInputStream(file);
            BufferedInputStream fin = new BufferedInputStream(tmn);
            int i=0;
            while ((i=fin.read())!=-1){
                str+=(char)i;

            }
        } catch (FileNotFoundException b){
            JOptionPane.showMessageDialog(frame,
                    "You are trying to access a deleted file");

        } catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }
}
