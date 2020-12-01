package com.fabiangabor.bankjegyek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import static com.fabiangabor.bankjegyek.Utils.*;

public class Bankjegyek {
    private final JPanel gui = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    private JPanel gamePanel;

    private final JTextField[][] squares = new JTextField[5][5]; // pálya mérete
    private final JTextField[][] squaresPlay = new JTextField[5][5]; // pálya mérete

    private final int[] editorRowSums = new int[5];
    private final int[] editorColSums = new int[5];
    private final int[] playRowSums = new int[5];
    private final int[] playColSums = new int[5];
    
    private final int[] countBankjegyek = new int[5];
    private final int[] countBankjegyekPlay = new int[5];

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
        JPanel editorPanel = new JPanel(new GridLayout(0, 6)); // 5 oszlop + 1 az összegnek majd a pályán kivül.
        editorPanel.setName("Editor");

        gamePanel = new JPanel(new GridLayout(0, 6));
        gamePanel.setName("Play");

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(editorPanel);
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
                textFieldPlay.setBackground(Color.WHITE);

                textField.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        //System.out.println("changedUpdate: " + e);
                        check(textField, countBankjegyek, squares);
                        //calculateRowColSum(editorPanel, squares, editorRowSums, editorColSums, textField, true);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        check(textField, countBankjegyek, squares);
                        //calculateRowColSum(editorPanel, squares, editorRowSums, editorColSums, textField, true);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        //System.out.println("insertUpdate: " + e);
                        check(textField, countBankjegyek, squares);
                        //calculateRowColSum(editorPanel, squares, editorRowSums, editorColSums, textField, true);
                    }
                });

                textFieldPlay.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        check(textFieldPlay, countBankjegyekPlay, squaresPlay);
                        calculateRowColSum(gamePanel, squaresPlay, playRowSums, playColSums, textFieldPlay, false);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        check(textFieldPlay, countBankjegyekPlay, squaresPlay);
                        calculateRowColSum(gamePanel, squaresPlay, playRowSums, playColSums, textFieldPlay, false);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        check(textFieldPlay, countBankjegyekPlay, squaresPlay);
                        calculateRowColSum(gamePanel, squaresPlay, playRowSums, playColSums, textFieldPlay, false);
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
                editorPanel.add(squares[i][j]); // ez tárolja az 5x5 matrixot
                gamePanel.add(squaresPlay[i][j]);
            }
            // jobb oszlop osszegek helye
            JLabel sum = new JLabel("", SwingConstants.CENTER);
            sum.putClientProperty("id", i*10);
            sum.setName(String.valueOf(i*10));
            editorPanel.add(sum);

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
            editorPanel.add(sum);

            JLabel sumPlay = new JLabel("", SwingConstants.CENTER);
            sumPlay.setName(String.valueOf((50+j)*10));
            sumPlay.putClientProperty("id-play", (50+j)*10);
            gamePanel.add(sumPlay);
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
