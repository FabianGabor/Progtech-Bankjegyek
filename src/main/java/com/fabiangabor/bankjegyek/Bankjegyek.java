package com.fabiangabor.bankjegyek;

/**
 * itt hozzuk létre a main függvényben a GUI-t
 */
public class Bankjegyek {
    /**
     * @param args futtataskor bemeno parameterkent megadhato a palya merete.
     *             Alapesetben, ennek hianyaban 5x5
     */
    public static void main(String[] args) {
        int size = 5;
        if (args.length > 0)
            size = Integer.parseInt(args[0]);
        new GUI(size);
    }
}