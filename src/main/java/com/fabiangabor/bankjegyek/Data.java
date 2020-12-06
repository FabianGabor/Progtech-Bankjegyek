package com.fabiangabor.bankjegyek;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Pálya mérete, bankjegyek 2d tömbként tárolása, sorok és oszlopok összege, bankjegy értékeinek előfordulási száma
 */
public class Data {
    private int size;
    private int[][] squares;
    private int[] sumRows;
    private int[] sumCols;
    private int[] count;
    HashMap<Integer, ArrayList<Coordinates.Coord>> map = new HashMap<>();
    private HashMap<String, Component> componentMap = new HashMap<>();

    /**
     * Üres contructor
     */
    public Data() {
    }

    /**
     * Pálya méretének lekérése
     * @return pálya mérete
     */
    public int getSize() {
        return size;
    }

    /**
     * Pálya méretének megadása
     * @param size pálya mérete
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Pályán szereplő bankjegyek int 2d tömbként lekérése
     * @return pálya szövegdobozai 2d tömbként
     */
    public int[][] getSquares() {
        return squares;
    }

    /**
     * Pályán szereplő bankjegyek int 2d tömbként megadása
     * @param squares pálya szövegdobozai 2d tömbként
     */
    public void setSquares(int[][] squares) {
        this.squares = squares;
    }

    /**
     * Pályán szereplő bankjegyek sorösszegének lekérése
     * @return pálya sorösszegei tömbként
     */
    public int[] getSumRows() {
        return sumRows;
    }

    /**
     * Pályán szereplő bankjegyek sorösszegének megadása
     * @param sumRows pálya sorösszegei tömbként
     */
    public void setSumRows(int[] sumRows) {
        this.sumRows = sumRows;
    }

    /**
     * Pályán szereplő bankjegyek oszlopsszegének lekérése
     * @return pálya oszlopsszegei tömbként
     */
    public int[] getSumCols() {
        return sumCols;
    }

    /**
     * Pályán szereplő bankjegyek oszlopsszegének megadása
     * @param sumCols pálya oszlopsszegei tömbként
     */
    public void setSumCols(int[] sumCols) {
        this.sumCols = sumCols;
    }

    /**
     * Pályán szereplő bankjegyek értékeinek előfordulási számának lekérése
     * @return bankjegyek számértékeinek előfordulási száma
     */
    public int[] getCount() { return count; }

    /**
     * Pályán szereplő bankjegyek értékeinek előfordulási számának megadása
     * @param count bankjegyek számértékeinek előfordulási száma
     */
    public void setCount(int[] count) { this.count = count; }

    /**
     * Pályán szereplő bankjegyek értékeinek a koordinátáinak lekérése
     * @return bankjegyek értékeinek a koordinátái
     */
    public HashMap<Integer, ArrayList<Coordinates.Coord>> getMap() {
        return map;
    }

    /**
     * Pályán szereplő bankjegyek értékeinek a koordinátáinak megadása
     * @param map bankjegyek értékeinek a koordinátái
     */
    public void setMap(HashMap<Integer, ArrayList<Coordinates.Coord>> map) {
        this.map = map;
    }

    /**
     * a GUI elemeinek a listájának lekérése
     * @return a GUI elemeinek a listája
     */
    public HashMap<String, Component> getComponentMap() {
        return componentMap;
    }

    /**
     * a GUI elemeinek a listájának megadása
     * @param componentMap a GUI elemeinek a listája
     */
    public void setComponentMap(HashMap<String, Component> componentMap) {
        this.componentMap = componentMap;
    }
}
