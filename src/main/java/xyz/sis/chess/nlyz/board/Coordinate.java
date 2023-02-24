package xyz.sis.chess.nlyz.board;

import java.util.Objects;

import static xyz.sis.chess.nlyz.util.NMath.inRange8;

public class Coordinate {

    public static final Coordinate INVALID = new Coordinate(-1, -1);

    private static final Coordinate[][] CACHE = new Coordinate[8][8];

    public static Coordinate get(String algebraic) {
        return get(algebraic.charAt(0) - 97, algebraic.charAt(1) - 49);
    }

    public static int fileChar2fileNum(char file) {
        return file - 97;
    }

    public static Coordinate get(int file, int rank) {
        if (!inRange8(file) || !inRange8(rank)) return INVALID;
        Coordinate c = CACHE[file][rank];
        if (c == null) {
            c = new Coordinate(file, rank);
            CACHE[file][rank] = c;
        }
        return c;
    }

    public final int f, r;

    public final boolean valid;

    private Coordinate(int file, int rank) {
        f = file;
        r = rank;
        valid = inRange8(f) && inRange8(r);
    }

    @Override
    public String toString() {
        return (char)(f+97) + "" + (char)(r + 49);// + "(" + f + "" + r + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return f == that.f && r == that.r && valid == that.valid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(f, r, valid);
    }
}
