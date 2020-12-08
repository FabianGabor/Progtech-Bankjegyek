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
        int size;
        if (args.length > 0) {
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException numberFormatException){
                size = 5;
            }
        }
        new GUI(size);
    }
}