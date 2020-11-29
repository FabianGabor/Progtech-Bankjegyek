package com.fabiangabor.bankjegyek;

import javax.swing.*;
import java.awt.*;

public class Utils {

    private static final Color[] colors = {
            new Color(236,190,250),
            new Color(189, 169, 222),
            new Color(184, 184, 245),
            new Color(166, 187, 222),
            new Color(191, 218, 205)
    };

    public static Integer Sum(JTextField[][] squares, int index, boolean vertical) {
        int sum = 0;
        int num;
        Integer[] countNum = {0,0,0,0,0};
        String squareText;

        for (int i=0; i<5; i++) {
            if (vertical)
                squareText = squares[i][index].getText();
            else
                squareText = squares[index][i].getText();
            if (squareText.equals("")) squareText = "0"; // null kezeles
            num = Integer.parseInt( squareText );
            if (num > 0) { // [num - 1] kezeles
                if (countNum[num - 1] == 0 || countNum[num - 1] == null) {
                    countNum[num - 1]++;
                    sum += num;
                }
            }
        }
        return sum;
    }

    public static void calculateRowColSum(JPanel jPanel, JTextField[][] squares, int rowSums[], int colSums[], JTextField textField, boolean editor) {
        String id = (editor) ? "id" : "id-play";

        int row = (editor) ? Integer.parseInt(textField.getClientProperty(id).toString())/10 : Integer.parseInt(textField.getClientProperty(id).toString())/10;
        int rowSum = Sum(squares, row, false);
        String getName = (editor) ? String.valueOf(row * 10) : String.valueOf(row * 100);
        findLabelByName(jPanel, getName).setText(String.valueOf(rowSum));

        int col = (editor) ? Integer.parseInt(textField.getClientProperty(id).toString())%10 : Integer.parseInt(textField.getClientProperty(id).toString())%10%10;
        int colSum = Sum(squares, col, true);
        getName = (editor) ? String.valueOf(col + 50) : String.valueOf((col + 50)*10);
        findLabelByName(jPanel, getName).setText(String.valueOf(colSum));
    }

    public static JLabel findLabelByName(Container parent, String name) {
        if (name != null) {
            for (Component child : parent.getComponents()) {
                if (child instanceof JLabel) {
                    JLabel label = (JLabel)child;
                    if (name.equals(label.getName())) {
                        return label;
                    }
                }
            }
        }
        return null;
    }

    public static void check(JTextField jTextField, int[] countBankjegyek) {
        int inputNum = Integer.parseInt(jTextField.getText());

        if (inputNum<1 || inputNum>5){
            JOptionPane.showMessageDialog(null,
                    "Hiba: 1-5 közötti érték kell!", "Hiba",
                    JOptionPane.ERROR_MESSAGE);
        }
        else {
            if (countBankjegyek[inputNum-1]<3) {
                countBankjegyek[inputNum - 1]++;

                jTextField.setBackground(colors[inputNum-1]);
                /*
                if (countBankjegyekPlay[inputNum-1] == 3) {
                }
                 */

                 /*
                                for (Component c : bankjegyPanel.getComponents()) {
                                    if (c instanceof JTextField) {
                                        JTextField tf = ((JTextField) c);
                                        if (tf.getClientProperty("id").equals(mProperty)) {
                                            //System.out.println(tf.getClientProperty("id"));
                                        } else {
                                            tf.setEnabled(false);
                                        }

                                        // ez ellenőrzi, hogy csak körülötte tudjam írni.
                                        // igen, mert az i index az első, azaz 10-es helyen van
                                        // konzolba is kiírom
                                        if ((Integer) tf.getClientProperty("id") == mProperty + 10) {
                                            tf.setEnabled(true);
                                        }
                                        if ((Integer) tf.getClientProperty("id") == mProperty - 10) {
                                            tf.setEnabled(true);
                                        }
                                        if ((Integer) tf.getClientProperty("id") == mProperty + 1) {
                                            tf.setEnabled(true);
                                        }
                                        if ((Integer) tf.getClientProperty("id") == mProperty - 1) {
                                            tf.setEnabled(true);
                                        }
                                        // majd akarom számolni, hogy ha 3 függőleges vagy vizszintes értéket beírok, akkor azt kezelje 1 bankjegynek
                                        // most annyit írok, amennyit akarok. De legalább csak viz/függ irányba enged
                                    }
                                }

                                 */
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Max 3 azonos ertek!", "Hiba",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void print(JTextField[][] squares) {
        for (JTextField[] square : squares) {
            for (JTextField jTextField : square) {
                String text = jTextField.getText();
                System.out.print((text.length() > 0) ? text : " ");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }
}
