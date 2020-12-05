package com.fabiangabor.bankjegyek;

public class Bankjegyek {
    public static void main(String[] args) {
        int size = 5;
        if (args.length > 0)
            size = Integer.parseInt(args[0]);
        new GUI(size);
    }
}