package com.fabiangabor.bankjegyek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Bankjegyek {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JTextField[][] squares = new JTextField[5][5];
    private JPanel bankjegyPanel;

    Bankjegyek() {
        initializeGui();
    }

    public final JComponent getGui() {
        return gui;
    }

    public final void initializeGui() {
        // alap ablak
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        // 6x6 panel (5x5 tabla + 1 oszlop + 1 sor)
        bankjegyPanel = new JPanel(new GridLayout(0, 6));
        gui.add(bankjegyPanel);

        // letrehozzuk a negyzeteket, 64x64 meretet lefoglalunk es elmentjuk a squares matrixban
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                JTextField textField = new JTextField();
                textField.setName("[" + i + "," + j + "]");
                textField.putClientProperty("id", i*10+j);
                textField.setMargin(buttonMargin);
                textField.setHorizontalAlignment(JTextField.CENTER);

                textField.setBackground(Color.WHITE);

                textField.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        check();
                        //System.out.println(textField.getName() + " : " + textField.getText());
                    }
                    public void removeUpdate(DocumentEvent e) {
                        check();
                        //System.out.println(textField.getName() + " : " + textField.getText());
                    }
                    public void insertUpdate(DocumentEvent e) {
                        check();
                        //System.out.println(textField.getName() + " : " + textField.getText());
                    }

                    public void check() {
                        if (Integer.parseInt(textField.getText())<1 || Integer.parseInt(textField.getText())>5){
                            JOptionPane.showMessageDialog(null,
                                    "Hiba: 1-5 közötti érték kell!", "Hiba",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            Integer mProperty = (Integer) textField.getClientProperty("id");

                            for (Component c : bankjegyPanel.getComponents()) {
                                if (c instanceof JTextField) {
                                    JTextField tf = ((JTextField)c);
                                    if (tf.getClientProperty("id").equals(mProperty)) {
                                        System.out.println(tf.getClientProperty("id"));
                                    }
                                    else {
                                        tf.setEnabled(false);
                                    }
                                    
                                    if ((Integer)tf.getClientProperty("id") == mProperty + 10) {
                                        tf.setEnabled(true);
                                    }
                                    if ((Integer)tf.getClientProperty("id") == mProperty - 10) {
                                        tf.setEnabled(true);
                                    }
                                    if ((Integer)tf.getClientProperty("id") == mProperty + 1) {
                                        tf.setEnabled(true);
                                    }
                                    if ((Integer)tf.getClientProperty("id") == mProperty - 1) {
                                        tf.setEnabled(true);
                                    }
                                    
                                }
                            }
                        }
                    }
                });

                squares[j][i] = textField;
            }
        }

        // hozzaadjuk a bankjegyPanel-hez a letrehozott negyzeteket
        // az utolso oszlopba a sorok osszegenek fenntartott JLabel kerul
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bankjegyPanel.add(squares[j][i]);
            }
            // jobb oszlop osszegek helye
            bankjegyPanel.add(new JLabel("", SwingConstants.CENTER));
        }
        // also sorba az oszlopok osszege kerul
        for (int i = 0; i < 6; i++) {
            bankjegyPanel.add(new JLabel("", SwingConstants.CENTER));
        }
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            Bankjegyek bankjegyek = new Bankjegyek();

            JFrame f = new JFrame("Bankjegyek");
            f.setMinimumSize(new Dimension(400, 400));
            f.add(bankjegyek.getGui());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);

            f.pack();

            f.setMinimumSize(f.getSize());
            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }
}
