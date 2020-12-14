package com.fabiangabor.bankjegyek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

/**
 * Létrehozza a GUI-t és meghívja az adatfeldolgozó és ellenőrző metódusokat
 */
public class GUI {
    private final JPanel gui = new JPanel(new GridBagLayout());
    private final GridBagConstraints c = new GridBagConstraints();

    /**
     * @param size pálya mérete
     */
    public GUI(int size) {
        initializeGui(size);

        Runnable r = () -> {
            JFrame f = new JFrame("Bankjegyek");
            f.setMinimumSize(new Dimension(400, 400));
            f.setResizable(false);
            f.add(gui);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);
            f.pack();
            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }

    /**
     * @param size pálya mérete
     */
    public final void initializeGui(int size) {
        final JTextField[][] squaresEditor = new JTextField[size][size]; // pálya mérete
        final JTextField[][] squaresPlay = new JTextField[size][size]; // pálya mérete

        // alap ablak
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = (size+1) * 48;
        c.ipady = (size+1) * 48;
        c.weightx = 1;
        c.weighty = 0.9;
        c.gridy = 0;

        // (size+1)x(size+1) panel ( (size+1)x(size+1) tabla + 1 oszlop + 1 sor )
        JPanel mapEditor = new JPanel(new GridLayout(size+1,size+1)); // (size) oszlop + 1 az összegnek majd a pályán kivül.
        JPanel mapPlay = new JPanel(new GridLayout(size+1, size+1));
        mapEditor.setBackground(Colors.darkGray);
        mapPlay.setBackground(Colors.darkGray);

        JPanel editorPanel = new JPanel(new GridBagLayout());
        JPanel gamePanel = new JPanel(new GridBagLayout());
        editorPanel.setName("Editor");
        editorPanel.setBackground(Color.red);
        gamePanel.setName("Play");
        gamePanel.setBackground(Color.red);

        JPanel controlEditor = new JPanel();
        JPanel controlPlay = new JPanel();
        controlEditor.setName("Control");
        controlEditor.setBackground(Colors.darkGray);
        controlPlay.setName("Control");
        controlPlay.setBackground(Colors.darkGray);

        editorPanel.add(mapEditor, c);
        gamePanel.add(mapPlay, c);

        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridy = 1;

        editorPanel.add(controlEditor, c);
        gamePanel.add(controlPlay, c);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(editorPanel);
        tabbedPane.add(gamePanel);
        gui.add(tabbedPane, c);

        // letrehozzuk a negyzeteket, 64x64 meretet lefoglalunk es elmentjuk a squaresEditor matrixban
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JTextField textFieldEditor = new JTextField();
                JTextField textFieldPlay = new JTextField();
                textFieldEditor.setName("[" + i + "," + j + "]");
                textFieldPlay.setName("[" + i + "," + j + "]-play");
                textFieldEditor.putClientProperty("id", i*10+j); // id a sor és oszlopindexből lesz.
                textFieldPlay.putClientProperty("id-play", i*10+j); // id a sor és oszlopindexből lesz.
                textFieldEditor.setMargin(buttonMargin);
                textFieldPlay.setMargin(buttonMargin);
                textFieldEditor.setHorizontalAlignment(JTextField.CENTER);
                textFieldPlay.setHorizontalAlignment(JTextField.CENTER);

                textFieldEditor.setBackground(Color.WHITE);
                textFieldPlay.setBackground(Color.WHITE);

                squaresEditor[i][j] = textFieldEditor;
                squaresPlay[i][j] = textFieldPlay;

                Colors colors = new Colors();
                textFieldEditor.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
                    try {
                        textFieldEditor.setBackground(colors.getColors()[Integer.parseInt(textFieldEditor.getText())-1]);
                    }
                    catch (Exception exception) {
                        textFieldEditor.setBackground(Color.white);
                    }
                });
                textFieldPlay.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
                    try {
                        textFieldPlay.setBackground(colors.getColors()[Integer.parseInt(textFieldEditor.getText())-1]);
                    }
                    catch (Exception exception) {
                        textFieldPlay.setBackground(Color.white);
                    }
                });

            }
        }

        // hozzaadjuk a bankjegyPanel-hez a letrehozott negyzeteket
        // az utolso oszlopba a sorok osszegenek fenntartott JLabel kerul
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mapEditor.add(squaresEditor[i][j]); // ez tárolja az 5x5 matrixot
                mapPlay.add(squaresPlay[i][j]);
            }
            // jobb oszlop osszegek helye
            JLabel sum = new JLabel("", SwingConstants.CENTER);
            sum.setForeground(Color.white);
            sum.setName("sumRow" + "" + i + "");
            mapEditor.add(sum);

            JLabel sumPlay = new JLabel("", SwingConstants.CENTER);
            sumPlay.setForeground(Color.white);
            sumPlay.setName("sumRow" + "" + i + "");
            mapPlay.add(sumPlay);
        }
        // also sorba az oszlopok osszege kerul
        for (int j = 0; j < size+1; j++) {
            JLabel sum = new JLabel("", SwingConstants.CENTER);
            sum.setForeground(Color.white);
            sum.setName("sumCol" + "" + j + "");
            mapEditor.add(sum);            

            JLabel sumPlay = new JLabel("", SwingConstants.CENTER);
            sumPlay.setForeground(Color.white);
            sumPlay.setName("sumCol" + "" + j + "");
            mapPlay.add(sumPlay);
        }

        JButton checkEditorBtn = setButtonStyle("Check");
        JButton checkPlayBtn = setButtonStyle("Finish");
        JButton saveToFileBtn = setButtonStyle("Save to file");
        JButton saveToDbBtn = setButtonStyle("Save to DB");

        controlEditor.add(checkEditorBtn);
        controlEditor.add(saveToFileBtn);
        controlEditor.add(saveToDbBtn);
        controlPlay.add(checkPlayBtn);

        Utils utils = new Utils();
        checkEditorBtn.addActionListener(e -> {
            if (utils.checkEditor(squaresEditor)) {
                //JOptionPane.showMessageDialog(gui, "Minden rendben! Lehet játszani");
                utils.calculateSums(mapEditor, mapPlay);
            }
            else {
                JOptionPane.showMessageDialog(gui, "Baj van!");
            }
        });

        checkPlayBtn.addActionListener(e -> {
            if (utils.checkPlay(squaresPlay)) {
                JOptionPane.showMessageDialog(gui, "Minden rendben!");
            }
            else {
                JOptionPane.showMessageDialog(gui, "Baj van!");
            }
        });
        saveToFileBtn.addActionListener(e -> {
            JSON json = new JSON(utils.convertJTextFieldToInt(squaresEditor));
            json.toFile("output/array.json");
        });
        saveToDbBtn.addActionListener(e -> {
            Database db = new Database();
            try {
                db.insert(utils.convertJTextFieldToInt(squaresEditor));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public JButton setButtonStyle (String text) {
        JButton jButton = new JButton(text);
        jButton.setBackground(Colors.darkGray);
        jButton.setForeground(Color.white);
        return jButton;
    }
}