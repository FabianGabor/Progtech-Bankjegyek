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
                        System.out.println(countRowSum(Integer.parseInt(textField.getClientProperty("id").toString())/10));
                        //System.out.println(textField.getName() + " : " + textField.getText());
                    }
                    public void removeUpdate(DocumentEvent e) {
                        //check();
                        //System.out.println(textField.getName() + " : " + textField.getText());
                    }
                    public void insertUpdate(DocumentEvent e) {
                        check();

                        int sum = countRowSum(Integer.parseInt(textField.getClientProperty("id").toString())/10);

                        int getId = Integer.parseInt(textField.getClientProperty("id").toString())/10 * 6 + 5;
                        System.out.println(getId);

                        String getName = String.valueOf(Integer.parseInt(textField.getClientProperty("id").toString())/10 * 10);

                        findLabelByName(bankjegyPanel, getName ).setText(String.valueOf(sum));
                        System.out.println(bankjegyPanel.getComponent( getId ));

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
                                Integer mProperty = (Integer) textField.getClientProperty("id");
                                countBankjegyek[inputNum - 1]++;
                                //System.out.println(inputNum + ": " + countBankjegyek[inputNum - 1]);

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
            JLabel sum = new JLabel("", SwingConstants.CENTER);
            sum.putClientProperty("id", i*10);
            sum.setName(String.valueOf(i*10));
            bankjegyPanel.add(sum);
        }
        // also sorba az oszlopok osszege kerul
        for (int j = 0; j < 6; j++) {
            JLabel sum = new JLabel("", SwingConstants.CENTER);
            sum.setName(String.valueOf(5*10+j));
            sum.putClientProperty("id", 5*10+j);
            bankjegyPanel.add(sum);
        }
    }

    public void print() {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                String text = squares[i][j].getText();
                System.out.print((text.length() > 0) ? text : " ");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    public Integer countRowSum(int row) {
        Integer sum = 0;
        Integer num;
        Integer countNum[] = {0,0,0,0,0};
        String squareText;

        for (int j=0; j<5; j++)
        {
            squareText = squares[row][j].getText();
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

    public JLabel findLabelByName(Container parent, String name) {
        JLabel found = null;
        if (name != null) {
            for (Component child : parent.getComponents()) {
                if (child instanceof JLabel) {
                    JLabel label = (JLabel)child;
                    if (name.equals(label.getName())) {
                        found = label;
                        return found;
                    }
                }
            }
        }
        return found;
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
