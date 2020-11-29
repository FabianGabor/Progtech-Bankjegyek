package com.fabiangabor.bankjegyek;

import javax.swing.*;
import java.awt.*;

public class Utils {
    public static Integer Sum(JTextField[][] squares, int index, boolean vertical) {
        int sum = 0;
        int num;
        Integer[] countNum = {0,0,0,0,0};
        String squareText;

        for (int i=0; i<5; i++)
        {
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

    public static void calculateRowColSum(JPanel jPanel, JTextField[][] squares, JTextField textField, boolean editor) {
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
}
