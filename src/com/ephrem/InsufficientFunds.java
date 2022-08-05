package com.ephrem;

import javax.swing.*;
/**  Atm Machine programing

 * Ephrem Kidane

 */

public class InsufficientFunds extends Exception{
    public InsufficientFunds() {
        JOptionPane frame = new JOptionPane();
        JOptionPane.showMessageDialog(frame, "Insufficient Funds!");
    }

}
