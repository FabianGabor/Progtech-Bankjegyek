package com.fabiangabor.bankjegyek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import com.fabiangabor.bankjegyek.Utils;

public class Bankjegyek {
    private final JPanel gui = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    private JTabbedPane tabbedPane;
    private JPanel gamePanel;

    private final JTextField[][] squares = new JTextField[5][5]; // pálya mérete
    private final JTextField[][] squaresPlay = new JTextField[5][5]; // pálya mérete
    private JPanel bankjegyPanel;
    private final int[] countBankjegyek = new int[5];
    private final int[] countBankjegyekPlay = new int[5];

    private final Color[] colors = {
            new Color(236,190,250),
            new Color(189, 169, 222),
            new Color(184, 184, 245),
            new Color(166, 187, 222),
            new Color(191, 218, 205)
            };

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
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;

        // 6x6 panel (5x5 tabla + 1 oszlop + 1 sor)
        bankjegyPanel = new JPanel(new GridLayout(0, 6)); // 5 oszlop + 1 az összegnek majd a pályán kivül.
        bankjegyPanel.setName("Editor");
        //gui.add(bankjegyPanel,c);

        gamePanel = new JPanel(new GridLayout(0, 6));
        gamePanel.setName("Play");

        tabbedPane = new JTabbedPane();
        tabbedPane.add(bankjegyPanel);
        tabbedPane.add(gamePanel);
        gui.add(tabbedPane, c);

        // letrehozzuk a negyzeteket, 64x64 meretet lefoglalunk es elmentjuk a squares matrixban
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                JTextField textField = new JTextField();
                JTextField textFieldPlay = new JTextField();
                textField.setName("[" + i + "," + j + "]");
                textFieldPlay.setName("[" + i + "," + j + "]-play");
                textField.putClientProperty("id", i*10+j); // id a sor és oszlopindexből lesz.
                textFieldPlay.putClientProperty("id-play", i*10+j); // id a sor és oszlopindexből lesz.
                textField.setMargin(buttonMargin);
                textFieldPlay.setMargin(buttonMargin);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textFieldPlay.setHorizontalAlignment(JTextField.CENTER);

                textField.setBackground(Color.WHITE);
                textField.setForeground(Color.white);
                textFieldPlay.setBackground(Color.WHITE);

                textField.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        check();
                        Utils.calculateRowColSum(bankjegyPanel, squares, textField, true);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        check();
                        Utils.calculateRowColSum(bankjegyPanel, squares, textField, true);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        check();
                        Utils.calculateRowColSum(bankjegyPanel, squares, textField, true);
                    }

                    public void check() {
                        //print();
                        int inputNum = Integer.parseInt(textField.getText());

                        if (inputNum<1 || inputNum>5){
                            JOptionPane.showMessageDialog(null,
                                    "Hiba: 1-5 közötti érték kell!", "Hiba",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            if (countBankjegyek[inputNum-1]<3) {
                                countBankjegyek[inputNum - 1]++;

                                textField.setBackground(colors[inputNum-1]);
                                if (countBankjegyek[inputNum-1] == 3) {

                                }

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
                });

                textFieldPlay.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        check();
                        Utils.calculateRowColSum(gamePanel, squaresPlay, textFieldPlay, false);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        check();
                        Utils.calculateRowColSum(gamePanel, squaresPlay, textFieldPlay, false);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        check();
                        Utils.calculateRowColSum(gamePanel, squaresPlay, textFieldPlay, false);
                    }

                    public void check() {
                        int inputNum = Integer.parseInt(textFieldPlay.getText());

                        if (inputNum<1 || inputNum>5){
                            JOptionPane.showMessageDialog(null,
                                    "Hiba: 1-5 közötti érték kell!", "Hiba",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            if (countBankjegyekPlay[inputNum-1]<3) {
                                countBankjegyekPlay[inputNum - 1]++;

                                textFieldPlay.setBackground(colors[inputNum-1]);
                                if (countBankjegyekPlay[inputNum-1] == 3) {

                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(null,
                                        "Max 3 azonos ertek!", "Hiba",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                squares[i][j] = textField;
                squaresPlay[i][j] = textFieldPlay;
            }
        }

        // hozzaadjuk a bankjegyPanel-hez a letrehozott negyzeteket
        // az utolso oszlopba a sorok osszegenek fenntartott JLabel kerul
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                bankjegyPanel.add(squares[i][j]); // ez tárolja az 5x5 matrixot
                gamePanel.add(squaresPlay[i][j]);
            }
            // jobb oszlop osszegek helye
            JLabel sum = new JLabel("", SwingConstants.CENTER);
            sum.putClientProperty("id", i*10);
            sum.setName(String.valueOf(i*10));
            bankjegyPanel.add(sum);

            JLabel sumPlay = new JLabel("", SwingConstants.CENTER);
            sumPlay.putClientProperty("id-play", i*10*10);
            sumPlay.setName(String.valueOf(i*10*10));
            gamePanel.add(sumPlay);
        }
        // also sorba az oszlopok osszege kerul
        for (int j = 0; j < 6; j++) {
            JLabel sum = new JLabel("", SwingConstants.CENTER);
            sum.setName(String.valueOf(50+j));
            sum.putClientProperty("id", 50+j);
            bankjegyPanel.add(sum);

            JLabel sumPlay = new JLabel("", SwingConstants.CENTER);
            sumPlay.setName(String.valueOf((50+j)*10));
            sumPlay.putClientProperty("id-play", (50+j)*10);
            gamePanel.add(sumPlay);
        }
    }

    public void print() {
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
