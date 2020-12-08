package com.fabiangabor.bankjegyek;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Adatfeldolgozó és ellenőrző metódusok
 */
public class Utils {
    Data dataEditor = new Data();
    Data dataPlay = new Data();

    /**
     * üres contructor
     */
    public Utils() {
    }

    /**
     * @param squares JTextField típusú 2d tömb, elemei a GUI szövegdobozai
     * @return int típusú 2d tömbként visszaadja a bevitt bankjegyek értékeit
     */
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

    /**
     * @param squares bevitt bankjegyek értékei
     * @return sorok összegei. Ha egy bankjegynek több értéke szerepel a sorban, csak egyszer számolja
     */
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

    /**
     * @param squares bevitt bankjegyek értékei
     * @return oszlopok összegei. Ha egy bankjegynek több értéke szerepel az oszlopban, csak egyszer számolja
     */
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

    /**
     * @param count bankjegy értékeinek a számát tartalmazó tömb
     * @return igaz, ha egy bankjegy értékei egyszer sem vagy háromszor szerepelnek. Különben hamis
     */
    public boolean checkCount(int[] count) {
        for (int i : count) {
            if (!(i == 0 || i == 3)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param c1 bankjegy 1. értékének a koordinátái
     * @param c2 bankjegy 2. értékének a koordinátái
     * @param c3 bankjegy 3. értékének a koordinátái
     * @return igaz, ha a bankjegy mindhárom számértékének koordinátái egy sorban vannak (nem alkotnak háromszöget)
     */
    public boolean collinear(Coordinates.Coord c1, Coordinates.Coord c2, Coordinates.Coord c3) {
        return (c1.y - c2.y) * (c1.x - c3.x) == (c1.y - c3.y) * (c1.x - c2.x);
    }

    /**
     * @param data tábla adatai
     * @return igaz, ha a bankjegyek mindhárom számértékének koordinátái egy sorban vannak, különben hamis
     */
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

    /**
     * @param data tábla adatai (méret, int[][] típusként tárolt bankegyek értékei
     * @return HashMapként visszakapjuk minden bankjegy értékének a koordinátáit
     */
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

    /**
     * @param squares a GUI input szövegdobozai
     * @return igaz, ha a táblán minden bankjegyből 3 darab szerepel és minden bankjegy értékei egy sorban
     * vagy oszlopban vannak
     */
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

    /**
     * @param data a pálya adatai
     * @return igaz, ha a játékos által megadott bankjegyek összegei egyeznek a szerkesztő összegeivel
     */
    public boolean checkRowColSums(Data data) {
        int[] rowSums = calculateSumRows(data.getSquares());
        int[] colSums = calculateSumCols(data.getSquares());

        if (!Arrays.equals(rowSums, data.getSumRows())) return false;
        if (!Arrays.equals(colSums, data.getSumCols())) return false;

        return true;
    }

    /**
     * @param squares játékos pálya szövegdobozai 2d tömbként
     * @return igaz, ha a bevitt bankjegyek megfelelnek a játékszabályoknak
     */
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

    /**
     * Kiszámolja a pályák sor és oszlop összegeit
     * @param mapEditor pályaszerkesztő
     * @param mapPlay játékos pálya
     */
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

    /**
     * Létrehozzuk a keret GUI elemeinek a listáját (térképét)
     * @param frame a keret, amiben a GUI elemek találhatók
     * @return a GUI elemei HashMapként: név-komponens pár
     */
    public HashMap<String, Component> createComponentMap(JPanel frame) {
        HashMap<String, Component> componentMap = new HashMap<>();
        Component[] components = frame.getComponents();
        for (Component component : components) {
            componentMap.put(component.getName(), component);
        }
        return componentMap;
    }

    /**
     * Név alapján kereshetők a GUI komponensei
     * @param componentMap a keret GUI elemeinek a listája (térképe)
     * @param name a keresett név
     * @return a megtalált elem
     */
    public Component getComponentByName(HashMap<String, Component> componentMap, String name) {
        return componentMap.getOrDefault(name, null);
    }
}