package xyz.sis.chess.nlyz.piece;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;

import static xyz.sis.chess.nlyz.util.NMath.inRange8;

public abstract class Piece {

    public final PieceType type;
    public final XColor color;
    public final String fullName;

    Piece(PieceType type, XColor color) {
        this.type = type;
        this.color = color;
        fullName = color.name().charAt(0) + type.name();
    }

    public abstract CoordinateSet computePossibleDestinations(Board board, Coordinate currentCoo);

    protected void addDirection(Board board, Coordinate currentCoo, CoordinateSet set, int fDir, int rDir) {
        var f = currentCoo.f;
        var r = currentCoo.r;
        while (inRange8(f, r)) {
            f += fDir;
            r += rDir;
            var newCoo = Coordinate.get(f, r);
            if (board.isEmpty(newCoo)) {
                set.add(newCoo);
            } else if (board.areEnemies(currentCoo, newCoo)) {
                set.add(newCoo);
                break;
            } else break;
        }
    }

    protected void addIfNotFriend(Board b, Coordinate currentCoo, Coordinate coo, CoordinateSet set) {
        if (!b.areFriends(currentCoo, coo)) set.add(coo);
    }

    @Override
    public String toString() {
        return fullName;
    }
}
