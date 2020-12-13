package com.fabiangabor.bankjegyek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class UtilsTest {
    Utils utils = new Utils();
    static JTextField[][] jTextFieldArray;
    static int[][] intArray;
    static Random rand = new Random();

    public static class DataContainer {
        JTextField[][] jTextFieldArray;
        int[][] intArray;
    }
    static ArrayList<DataContainer> dataArray = new ArrayList<>();

    private static IntStream range() {
        return IntStream.range(1, 20);
    }

    @ParameterizedTest
    @MethodSource("range")
    public void UtilsTestInit(int n) {
        jTextFieldArray = new JTextField[n][n];
        intArray = new int[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                int randInt = rand.nextInt(1000);
                jTextFieldArray[i][j] = new JTextField();
                jTextFieldArray[i][j].setText(String.valueOf(randInt));
                intArray[i][j] = randInt;
            }
        }

        DataContainer dataContainer = new DataContainer();
        dataContainer.intArray = intArray;
        dataContainer.jTextFieldArray = jTextFieldArray;
        dataArray.add(dataContainer);
    }

    @Test
    public void convertJTextFieldToInt() {
    }

    @Test
    public void calculateSumRows() {
    }

    @Test
    public void calculateSumCols() {
    }

    @Test
    public void checkCount() {
    }

    @Test
    public void collinear() {
    }

    @Test
    public void checkCollinear() {
    }

    @Test
    public void buildDataMap() {
    }

    @Test
    public void checkEditor() {
    }

    @Test
    public void checkRowColSums() {
    }

    @Test
    public void checkPlay() {
    }

    @Test
    public void calculateSums() {
    }

    @Test
    public void createComponentMap() {
    }

    @Test
    public void getComponentByName() {
    }
}