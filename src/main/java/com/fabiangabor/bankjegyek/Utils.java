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

    public boolean collinear(Coordinates.Coord c1, Coordinates.Coord c2, Coordinates.Coord c3) {
        return (c1.y - c2.y) * (c1.x - c3.x) == (c1.y - c3.y) * (c1.x - c2.x);
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

        // ellenőrizzük, hogy minden bankjegy értékei egy vonalban legyenek
        for (int i=0; i<map.size(); i++) {
            System.out.println(map.entrySet());
            for (Map.Entry<Integer, ArrayList<Coordinates.Coord>> entry : map.entrySet()) {
                // nem alkotnak háromszöget, azaz egy vonalon vannak, de lehetnek átlósak
                if (!collinear(entry.getValue().get(0), entry.getValue().get(1), entry.getValue().get(2))) return false;

                // így biztosan csak függőlegese vagy vízszintesen vannak egy vonalban
                if (!((entry.getValue().get(0).x==entry.getValue().get(1).x) && (entry.getValue().get(0).x == entry.getValue().get(2).x) ||
                        (entry.getValue().get(0).y==entry.getValue().get(1).y) && (entry.getValue().get(0).y == entry.getValue().get(2).y))) {
                    return false;
                }
            }
        }
        return true;
    }
}
