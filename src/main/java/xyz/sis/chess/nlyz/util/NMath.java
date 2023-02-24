package xyz.sis.chess.nlyz.util;

public class NMath {

    public static int[] range8() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    public static boolean inRange8(int... i) {
        for (int i1 : i) {
            if (!inRange8(i1)) return false;
        }
        return true;
    }

    public static boolean inRange8(int i) {
        return i >= 0 && i < 8;
    }

}
