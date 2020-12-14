package com.fabiangabor.bankjegyek;

import org.junit.jupiter.api.Assertions;

import java.io.FileWriter;
import java.io.IOException;
import static java.util.Arrays.deepToString;

public class JSON {
    int[][] data;
    int size;

    public JSON(int[][] data) {
        this.data = data;
        this.size = data.length;
    }

    public int[][] getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public void toFile(String path) {
        Assertions.assertNotEquals(0, path.length(), "File path is empty!");

        try (FileWriter file = new FileWriter(path)) {
            file.write(toJson());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fromFile(String path) {
        Assertions.assertNotEquals(0, path.length(), "File path is empty!");


    }

    private String toJson() {
        return
                "{" +
                "\"size\" : " + data.length + ", " +
                "\"data\" : " +
                        deepToString(data) +
                "}";
    }

    public int[][] fromJson() {
        int[][] data = new int[0][0];
        return data;
    }

    public static void main(String[] args) {
        int[][] data = new int[][]{{1, 2, 3}, {3, 4, 5}, {4, 5, 6}};
        JSON json = new JSON(data);
        json.toFile("output/array.json");
        System.out.println(json.toJson());
    }
}
