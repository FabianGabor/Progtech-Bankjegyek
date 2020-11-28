package com.fabiangabor.bankjegyek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Bankjegyek {
    //private final JPanel gui = new JPanel(new BorderLayout(25, 25));
    //private final JPanel gui = new JPanel(new GridLayout(2, 1)); // működik, de félbe osztja
    private final JPanel gui = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    private JTextField[][] squares = new JTextField[5][5]; // pálya mérete
    private JPanel bankjegyPanel;
    private JPanel controlPanel;
    private int countBankjegyek[] = new int[5];

    Bankjegyek() {
        initializeGui();
    }

    public final JComponent getGui() {
        return gui;
    }

    public final void initializeGui() {
        // alap ablak
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 300;
        c.weightx = 0.75;
        c.weighty = 0.75;
        c.gridx = 0;
        c.gridy = 1;

        // 6x6 panel (5x5 tabla + 1 oszlop + 1 sor)
        bankjegyPanel = new JPanel(new GridLayout(0, 6)); // 5 oszlop + 1 az összegnek majd a pályán kivül.
        gui.add(bankjegyPanel,c);

        controlPanel = new JPanel(new GridLayout(0, 2)); //
        controlPanel.add(new JButton("Szerkeszt"));
        controlPanel.add(new JButton("Jatek"));

        c.weightx = 0.25;
        c.weighty = 0.25;
        c.ipady = 20;
        c.gridx = 0;
        c.gridy = 2;
        gui.add(controlPanel, c);

        // letrehozzuk a negyzeteket, 64x64 meretet lefoglalunk es elmentjuk a squares matrixban
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                JTextField textField = new JTextField();
                textField.setName("[" + i + "," + j + "]");
                textField.putClientProperty("id", i*10+j); // id a sor és oszlopindexből lesz.
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
                        int inputNum = Integer.parseInt(textField.getText());

                        if (inputNum<1 || inputNum>5){
                            JOptionPane.showMessageDialog(null,
                                    "Hiba: 1-5 közötti érték kell!", "Hiba",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            Integer mProperty = (Integer) textField.getClientProperty("id");
                            countBankjegyek[inputNum-1]++;
                            System.out.println(inputNum + ": " + countBankjegyek[inputNum-1]);

                            for (Component c : bankjegyPanel.getComponents()) {
                                if (c instanceof JTextField) {
                                    JTextField tf = ((JTextField)c);
                                    if (tf.getClientProperty("id").equals(mProperty)) {
                                        System.out.println(tf.getClientProperty("id"));
                                    }
                                    else {
                                        tf.setEnabled(false);
                                    }

                                    // ez ellenőrzi, hogy csak körülötte tudjam írni.
                                    // igen, mert az i index az első, azaz 10-es helyen van
                                    // konzolba is kiírom
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
                                    // majd akarom számolni, hogy ha 3 függőleges vagy vizszintes értéket beírok, akkor azt kezelje 1 bankjegynek
                                    // most annyit írok, amennyit akarok. De legalább csak viz/függ irányba enged
                                }
                            }


                        }
                    }
                });

                squares[i][j] = textField;
            }
        }

        // hozzaadjuk a bankjegyPanel-hez a letrehozott negyzeteket
        // az utolso oszlopba a sorok osszegenek fenntartott JLabel kerul
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                bankjegyPanel.add(squares[i][j]); // ez tárolja az 5x5 matrixot
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
            f.setResizable(false);
            f.add(bankjegyek.getGui());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);

            f.pack();

            //f.setMinimumSize(f.getSize());
            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }
}
