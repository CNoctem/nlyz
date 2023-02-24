package xyz.sis.chess.nlyz.util;

public class Util {

    public static boolean in(char ch, char... seq) {
        for (char c : seq) if (ch == c) return true;
        return false;
    }

    public static char lastChar(String s) {
        if (s.length() == 0) return '\0';
        return s.charAt(s.length() - 1);
    }

    public static int lengthWithoutSpecial(String algebraic) {
        if ('+' == lastChar(algebraic)) return algebraic.length() - 1;
        return algebraic.length();
    }

}
