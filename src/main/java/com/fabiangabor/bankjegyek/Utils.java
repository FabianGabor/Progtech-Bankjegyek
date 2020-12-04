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

    HashMap<String, Component> componentMap = new HashMap<>();

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

    public int[] getEditorSumRows() {
        return editorSumRows;
    }

    public void setEditorSumRows() {
        this.editorSumRows = calculateEditorSumRows();
    }

    public int[] calculateEditorSumRows() {
        int[] tmp = new int[editorSquares.length];

        for (int i=0; i<editorSquares.length; i++) {
            int[] count = new int[editorSquares.length+1];
            for (int j=0; j<editorSquares.length; j++) {
                if (count[editorSquares[i][j]] == 0) {
                    tmp[i] += editorSquares[i][j];
                }
                count[editorSquares[i][j]]++;
            }
        }
        return tmp;
    }

    public int[] getEditorSumCols() {
        return editorSumCols;
    }

    public void setEditorSumCols() {
        this.editorSumCols = calculateEditorSumCols();
    }

    public int[] calculateEditorSumCols() {
        int[] tmp = new int[editorSquares.length];

        for (int i=0; i<editorSquares.length; i++) {
            int[] count = new int[editorSquares.length+1];
            for (int j=0; j<editorSquares.length; j++) {
                if (count[editorSquares[j][i]] == 0) {
                    tmp[i] += editorSquares[j][i];
                }
                count[editorSquares[j][i]]++;
            }
        }
        return tmp;
    }


    public boolean collinear(Coordinates.Coord c1, Coordinates.Coord c2, Coordinates.Coord c3) {
        return (c1.y - c2.y) * (c1.x - c3.x) == (c1.y - c3.y) * (c1.x - c2.x);
    }

    public int[] count() {
        return null;
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
        for (int i : count) {
            if (!(i == 0 || i == 3)) {
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
                if (!((entry.getValue().get(0).x == entry.getValue().get(1).x) && (entry.getValue().get(0).x == entry.getValue().get(2).x) ||
                        (entry.getValue().get(0).y == entry.getValue().get(1).y) && (entry.getValue().get(0).y == entry.getValue().get(2).y))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void buildMap(JPanel mapEditor) {
        System.out.println(editorSquares);
        setEditorSumRows();
        setEditorSumCols();

        createComponentMap(mapEditor);
        // frissítsük a GUI-ban a sorok összegét
        for (int i=0; i<getEditorSumRows().length; i++) {
            Component component = getComponentByName("sumRow"+i+"");
            int index = Integer.parseInt(component.getName().replaceAll("[^0-9]", ""));

            if (component instanceof JLabel) {
                JLabel myLabel = (JLabel) component;
                myLabel.setText(String.valueOf(editorSumRows[i]));
            }
        }

        // frissítsük a GUI-ban az oszlopok összegét
        for (int i=0; i<getEditorSumCols().length; i++) {
            Component component = getComponentByName("sumCol"+i+"");
            int index = Integer.parseInt(component.getName().replaceAll("[^0-9]", ""));

            if (component instanceof JLabel) {
                JLabel myLabel = (JLabel) component;
                myLabel.setText(String.valueOf(editorSumCols[i]));
            }
        }
    }

    public void createComponentMap(JPanel frame) {
        Component[] components = frame.getComponents();
        for (int i=0; i < components.length; i++) {
            componentMap.put(components[i].getName(), components[i]);
        }
    }

    public Component getComponentByName(String name) {
        if (componentMap.containsKey(name)) {
            return (Component) componentMap.get(name);
        }
        else return null;
    }
}
