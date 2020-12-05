package com.fabiangabor.bankjegyek;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Utils {
    Data dataEditor = new Data();

    public Utils() {
    }

    public int[][] convertJTextFieldToInt(JTextField[][] squares) {
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
        return tmp;
    }

    public int[] calculateSumRows(int[][] squares) {
        int[] tmp = new int[squares.length];

        for (int i=0; i<squares.length; i++) {
            int[] count = new int[squares.length+1];
            for (int j=0; j<squares.length; j++) {
                if (count[squares[i][j]] == 0) {
                    tmp[i] += squares[i][j];
                }
                count[squares[i][j]]++;
            }
        }
        return tmp;
    }

    public int[] calculateSumCols(int[][] squares) {
        int[] tmp = new int[squares.length];

        for (int i=0; i<squares.length; i++) {
            int[] count = new int[squares.length+1];
            for (int[] square : squares) {
                if (count[square[i]] == 0) {
                    tmp[i] += square[i];
                }
                count[square[i]]++;
            }
        }
        return tmp;
    }


    public boolean collinear(Coordinates.Coord c1, Coordinates.Coord c2, Coordinates.Coord c3) {
        return (c1.y - c2.y) * (c1.x - c3.x) == (c1.y - c3.y) * (c1.x - c2.x);
    }


    public boolean checkEditor(JTextField[][] squares) {
        dataEditor.setSquares(convertJTextFieldToInt(squares));
        dataEditor.setSize(squares.length);

        int[] count = new int[dataEditor.getSize()];

        HashMap<Integer, ArrayList<Coordinates.Coord>> map = new HashMap<>();

        for (int i=0; i<dataEditor.getSize(); i++) {
            for (int j = 0; j < dataEditor.getSize(); j++) {
                int value = dataEditor.getSquares()[i][j];

                if (value > 0) {
                    if (count[value-1] > 3) return false; // ha egy bankjegy értékeiből több, mint 3 van
                    count[value-1]++;
                    Coordinates.Coord p = new Coordinates.Coord();
                    p.x = i;
                    p.y = j;

                    map.putIfAbsent(value, new Coordinates.Coords().coords);
                    map.get(value).add(p);
                }
            }
        }
        dataEditor.setMap(map);

        // ellenőrizzük, hogy minden táblán szerelő bankjegy értékéből 3 db van
        for (int i : count) {
            if (!(i == 0 || i == 3)) {
                return false;
            }
        }

        // ellenőrizzük, hogy minden bankjegy értékei egy vonalban legyenek
        for (int i=0; i<dataEditor.getMap().size(); i++) {
            for (Map.Entry<Integer, ArrayList<Coordinates.Coord>> entry : dataEditor.getMap().entrySet()) {
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
        dataEditor.setSumRows(this.calculateSumRows(dataEditor.getSquares()));
        dataEditor.setSumCols(this.calculateSumCols(dataEditor.getSquares()));

        dataEditor.setComponentMap(createComponentMap(mapEditor));

        // frissítsük a GUI-ban a sorok összegét
        for (int i=0; i<dataEditor.getSumRows().length; i++) {
            Component component = getComponentByName(dataEditor.getComponentMap(), "sumRow"+i+"");

            if (component instanceof JLabel) {
                JLabel myLabel = (JLabel) component;
                myLabel.setText(String.valueOf(dataEditor.getSumRows()[i]));
            }
        }

        // frissítsük a GUI-ban az oszlopok összegét
        for (int i=0; i<dataEditor.getSumCols().length; i++) {
            Component component = getComponentByName(dataEditor.getComponentMap(),"sumCol"+i+"");

            if (component instanceof JLabel) {
                JLabel myLabel = (JLabel) component;
                myLabel.setText(String.valueOf(dataEditor.getSumCols()[i]));
            }
        }
    }

    public HashMap<String, Component> createComponentMap(JPanel frame) {
        HashMap<String, Component> componentMap = new HashMap<>();
        Component[] components = frame.getComponents();
        for (Component component : components) {
            componentMap.put(component.getName(), component);
        }
        return componentMap;
    }

    public Component getComponentByName(HashMap<String, Component> componentMap, String name) {
        return componentMap.getOrDefault(name, null);
    }

}
