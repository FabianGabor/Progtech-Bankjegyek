package com.fabiangabor.bankjegyek;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

@TestMethodOrder(OrderAnnotation.class)
public class UtilsTest {
    Utils utils = new Utils();
    static JTextField[][] jTextFieldArray;
    static int[][] intArray;
    static Random rand = new Random();

    public static class DataContainer {
        JTextField[][] jTextFieldArray;
        int[][] intArray;
    }
    DataContainer dataContainer = new DataContainer();

    static ArrayList<DataContainer> dataArray = new ArrayList<>();

    private static IntStream range() {
        return IntStream.range(1, 20);
    }


    @Order(1)
    @ParameterizedTest
    @MethodSource("range")
    public void UtilsTestInit(int n) {
        jTextFieldArray = new JTextField[n][n];
        intArray = new int[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                int randInt = rand.nextInt(n);
                jTextFieldArray[i][j] = new JTextField();
                jTextFieldArray[i][j].setText(String.valueOf(randInt));
                intArray[i][j] = randInt;
            }
        }

        dataContainer.intArray = intArray;
        dataContainer.jTextFieldArray = jTextFieldArray;
        dataArray.add(dataContainer);

        //convertJTextFieldToInt(n);
        //calculateSumRows(n);
        //calculateSumCols(n);
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("range")
    public void convertJTextFieldToInt(int n) {
        Assertions.assertArrayEquals(dataArray.get(n-1).intArray, utils.convertJTextFieldToInt(dataArray.get(n-1).jTextFieldArray));
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("range")
    public void calculateSumRows(int n) {
        int[] sums = new int[n];

        for (int i=0; i<n; i++) {
            int[] count = new int[dataArray.get(n-1).intArray.length+1];
            for (int j=0; j<n; j++) {
                if (count[dataArray.get(n-1).intArray[i][j]] == 0) {
                    sums[i] += dataArray.get(n-1).intArray[i][j];
                    count[dataArray.get(n-1).intArray[i][j]]++;
                }
            }
        }
        Assertions.assertArrayEquals(sums, utils.calculateSumRows(dataArray.get(n-1).intArray));
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("range")
    public void calculateSumCols(int n) {
        int[] sums = new int[n];

        for (int j=0; j<n; j++) {
            int[] count = new int[dataArray.get(n-1).intArray.length+1];
            for (int i=0; i<n; i++) {
                if (count[dataArray.get(n-1).intArray[i][j]] == 0) {
                    sums[j] += dataArray.get(n-1).intArray[i][j];
                    count[dataArray.get(n-1).intArray[i][j]]++;
                }
            }
        }
        Assertions.assertArrayEquals(sums, utils.calculateSumCols(dataArray.get(n-1).intArray));
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