package com.fabiangabor.bankjegyek;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Utils {
    private static final Color[] colors = {
            Colors.pink,
            Colors.purple,
            Colors.blue,
            Colors.teal,
            Colors.green
    };

    private int[][] editorSquares;
    private int[] editorSumRows;
    private int[] editorSumCols;

    public Utils() {
    }

    public int[][] getEditorSquares() {
        return editorSquares;
    }

    public void setEditorSquares(int[][] editorSquares) {
        this.editorSquares = editorSquares;
    }

    public void convertJTextFieldToInt(JTextField[][] squares) {
        int[][] tmp = new int[squares.length][squares.length];
        for (int i=0; i<squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                try {
                    tmp[i][j] = Integer.parseInt(squares[i][j].getText());
                } catch (NumberFormatException numberFormatException) {
                    tmp[i][j] = 0;
                }

            }
        }
        setEditorSquares(tmp);
    }

    public boolean checkEditor(JTextField[][] squares) {
        convertJTextFieldToInt(squares);

        int size = squares.length;
        int[] count = new int[size];

        HashMap<Integer, ArrayList<Coordinates.Coord>> map = new HashMap<>();

        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                int value = this.editorSquares[i][j];

                if (value > 0) {
                    if (count[value-1] > 3) return false;
                    count[value-1]++;
                    Coordinates.Coord p = new Coordinates.Coord();
                    p.x = i;
                    p.y = j;

                    map.putIfAbsent(value, new Coordinates.Coords().coords);
                    map.get(value).add(p);
                }
            }
        }

        // ellenőrizzük, hogy minden táblán szerelő bankjegy értékéből 3 db van
        for (int i=0; i<count.length; i++) {
            if (!(count[i] == 0 || count[i] == 3)) {
                return false;
            }
        }

        return true;
    }
}
