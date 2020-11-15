package com.fabiangabor.bankjegyek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Bankjegyek {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] squares = new JButton[5][5];
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
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);

        // 6x6 panel (5x5 tabla + 1 oszlop + 1 sor)
        bankjegyPanel = new JPanel(new GridLayout(0, 6));
        gui.add(bankjegyPanel);

        // letrehozzuk a negyzeteket, 64x64 meretet lefoglalunk es elmentjuk a squares matrixban
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);

                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setBackground(Color.WHITE);

                squares[j][i] = b;
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
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            Bankjegyek bankjegyek = new Bankjegyek();

            JFrame f = new JFrame("Bankjegyek");
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
