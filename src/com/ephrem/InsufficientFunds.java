package com.ephrem;

import javax.swing.*;
/**  Atm Machine programing
 *  Group Member
 * Ephrem Kidane
 * Jean Amani Paul
 * Suzan Gehad
 */

public class InsufficientFunds extends Exception{
    public InsufficientFunds() {
        JOptionPane frame = new JOptionPane();
        JOptionPane.showMessageDialog(frame, "Insufficient Funds!");
    }

}
