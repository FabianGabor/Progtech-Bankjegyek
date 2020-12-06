package com.fabiangabor.bankjegyek;

import java.util.ArrayList;

/**
 * A bankjegyek értékeinek a koordinátáinak a tárolására
 */
public class Coordinates {

    /**
     * A bankjegy függőleges és vízszintes koordinátája
     */
    public static class Coord {
        /**
         * vízszintes koordináta
         */
        public int x;
        /**
         * vízszintes koordináta
         */
        public int y;
    }

    /**
     * A bankjegy értékeinek koordinátáit ArrayList-ben tárolja
     */
    static class Coords {
        ArrayList<Coord> coords = new ArrayList<>();
    }
}
