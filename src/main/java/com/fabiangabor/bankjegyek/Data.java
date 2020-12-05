package com.fabiangabor.bankjegyek;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    private int size;
    private int[][] squares;
    private int[] sumRows;
    private int[] sumCols;
    HashMap<Integer, ArrayList<Coordinates.Coord>> map = new HashMap<>();
    private HashMap<String, Component> componentMap = new HashMap<>();

    public Data() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[][] getSquares() {
        return squares;
    }

    public void setSquares(int[][] squares) {
        this.squares = squares;
    }

    public int[] getSumRows() {
        return sumRows;
    }

    public void setSumRows(int[] sumRows) {
        this.sumRows = sumRows;
    }

    public int[] getSumCols() {
        return sumCols;
    }

    public void setSumCols(int[] sumCols) {
        this.sumCols = sumCols;
    }

    public HashMap<Integer, ArrayList<Coordinates.Coord>> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, ArrayList<Coordinates.Coord>> map) {
        this.map = map;
    }

    public HashMap<String, Component> getComponentMap() {
        return componentMap;
    }

    public void setComponentMap(HashMap<String, Component> componentMap) {
        this.componentMap = componentMap;
    }
}
