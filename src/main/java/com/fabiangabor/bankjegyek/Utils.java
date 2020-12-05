package com.fabiangabor.bankjegyek;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Utils {
    Data dataEditor = new Data();
    Data dataPlay = new Data();

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

    public boolean checkCount(int[] count) {
        for (int i : count) {
            if (!(i == 0 || i == 3)) {
                return false;
            }
        }
        return true;
    }

    public boolean collinear(Coordinates.Coord c1, Coordinates.Coord c2, Coordinates.Coord c3) {
        return (c1.y - c2.y) * (c1.x - c3.x) == (c1.y - c3.y) * (c1.x - c2.x);
    }

    public boolean checkCollinear(Data data) {
        for (int i=0; i<data.getMap().size(); i++) {
            for (Map.Entry<Integer, ArrayList<Coordinates.Coord>> entry : data.getMap().entrySet()) {
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

    public HashMap<Integer, ArrayList<Coordinates.Coord>> buildDataMap (Data data) {
        HashMap<Integer, ArrayList<Coordinates.Coord>> map = new HashMap<>();
        int[] count = new int[5];
        data.setCount(count);

        for (int i=0; i<data.getSize(); i++) {
            for (int j = 0; j < data.getSize(); j++) {
                int value = data.getSquares()[i][j];

                if (value > 0) {
                    if (data.getCount()[value-1] > 3) return null; // ha egy bankjegy értékeiből több, mint 3 van
                    data.getCount()[value-1]++;
                    Coordinates.Coord p = new Coordinates.Coord();
                    p.x = i;
                    p.y = j;

                    map.putIfAbsent(value, new Coordinates.Coords().coords);
                    map.get(value).add(p);
                }
            }
        }
        return map;
    }

    public boolean checkEditor(JTextField[][] squares) {
        dataEditor.setSquares(convertJTextFieldToInt(squares));
        dataEditor.setSize(squares.length);
        dataEditor.setMap(buildDataMap(dataEditor));

        // ellenőrizzük, hogy minden táblán szerelő bankjegy értékéből 3 db van
        if (!checkCount(dataEditor.getCount())) return false;

        // ellenőrizzük, hogy minden bankjegy értékei egy vonalban legyenek
        if (!checkCollinear(dataEditor)) return false;

        return true;
    }

    public boolean checkRowColSums(Data data) {
        int[] rowSums = calculateSumRows(data.getSquares());
        int[] colSums = calculateSumCols(data.getSquares());        

        if (!Arrays.equals(rowSums, data.getSumRows())) return false;
        if (!Arrays.equals(colSums, data.getSumCols())) return false;

        return true;
    }

    public boolean checkPlay(JTextField[][] squares) {
        dataPlay.setSquares(convertJTextFieldToInt(squares));
        dataPlay.setSize(squares.length);
        dataPlay.setMap(buildDataMap(dataPlay));

        // ellenőrizzük, hogy minden táblán szerelő bankjegy értékéből 3 db van
        if (!checkCount(dataPlay.getCount())) return false;

        // ellenőrizzük, hogy minden bankjegy értékei egy vonalban legyenek
        if (!checkCollinear(dataPlay)) return false;

        if (!(checkRowColSums(dataPlay)))
            return false;

        //System.out.println(dataPlay.getSquares());

        return true;
    }

    public void calculateSums(JPanel mapEditor, JPanel mapPlay) {
        dataEditor.setSumRows(this.calculateSumRows(dataEditor.getSquares()));
        dataEditor.setSumCols(this.calculateSumCols(dataEditor.getSquares()));
        dataEditor.setComponentMap(createComponentMap(mapEditor));

        dataPlay.setSumRows(dataEditor.getSumRows());
        dataPlay.setSumCols(dataEditor.getSumCols());
        dataPlay.setComponentMap(createComponentMap(mapPlay));

        // frissítsük a GUI-ban a sorok összegét
        for (int i=0; i<dataEditor.getSumRows().length; i++) {
            Component componentEditor = getComponentByName(dataEditor.getComponentMap(), "sumRow"+i+"");
            Component componentPlay = getComponentByName(dataPlay.getComponentMap(), "sumRow"+i+"");

            if (componentEditor instanceof JLabel) {
                JLabel myLabel = (JLabel) componentEditor;
                myLabel.setText(String.valueOf(dataEditor.getSumRows()[i]));
            }

            if (componentPlay instanceof JLabel) {
                JLabel myLabel = (JLabel) componentPlay;
                myLabel.setText(String.valueOf(dataPlay.getSumRows()[i]));
            }
        }

        // frissítsük a GUI-ban az oszlopok összegét
        for (int i=0; i<dataEditor.getSumCols().length; i++) {
            Component componentEditor = getComponentByName(dataEditor.getComponentMap(),"sumCol"+i+"");
            Component componentPlay = getComponentByName(dataPlay.getComponentMap(), "sumCol"+i+"");

            if (componentEditor instanceof JLabel) {
                JLabel myLabel = (JLabel) componentEditor;
                myLabel.setText(String.valueOf(dataEditor.getSumCols()[i]));
            }

            if (componentPlay instanceof JLabel) {
                JLabel myLabel = (JLabel) componentPlay;
                myLabel.setText(String.valueOf(dataPlay.getSumCols()[i]));
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

    /*
    public void buildPlay(JPanel mapEditor) {
        // frissítsük a GUI-ban a sorok összegét
        for (int i=0; i<dataEditor.getSumRows().length; i++) {
            Component component = getComponentByName(dataPlay.getComponentMap(), "playsumRow"+i+"");

            if (component instanceof JLabel) {
                JLabel myLabel = (JLabel) component;
                myLabel.setText(String.valueOf(dataEditor.getSumRows()[i]));
            }
        }

        // frissítsük a GUI-ban az oszlopok összegét
        for (int i=0; i<dataEditor.getSumCols().length; i++) {
            Component component = getComponentByName(dataPlay.getComponentMap(),"playsumCol"+i+"");

            if (component instanceof JLabel) {
                JLabel myLabel = (JLabel) component;
                myLabel.setText(String.valueOf(dataEditor.getSumCols()[i]));
            }
        }
    }
     */
}
