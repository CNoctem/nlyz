package xyz.sis.chess.nlyz.board;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.sis.chess.nlyz.util.NMath;

import java.util.Collection;
import java.util.HashSet;

public class CoordinateSet extends HashSet<Coordinate> {

    private static final Logger log = LogManager.getLogger(CoordinateSet.class);

    public static CoordinateSet createFile(char file) {
        var set = new CoordinateSet();
        for (int r : NMath.range8()) set.add(Coordinate.get(file + "" + r));
        return set;
    }

    @Override
    public boolean add(Coordinate coordinate) {
        if (coordinate.valid)
            return super.add(coordinate);
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Coordinate> c) {
        boolean[] changed = new boolean[]{false};
        c.forEach(coo -> {
            if (coo.valid) {
                add(coo);
                changed[0] = true;
            }
        });
        return changed[0];
    }

    public Coordinate getOnly() {
        if (size() == 1) for (var c : this) return c;
        if (size() == 0) {
            log.error("Empty : " + this);
            throw new RuntimeException("Empty...");
        }
        log.error("More than one : " + this);
        throw new RuntimeException("More than one...");
    }

}
