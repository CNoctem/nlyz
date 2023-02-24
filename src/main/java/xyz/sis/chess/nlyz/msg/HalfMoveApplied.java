package xyz.sis.chess.nlyz.msg;

import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.game.HalfMove;

public class HalfMoveApplied implements Action {

    public final Coordinate orig, dest;
    public final HalfMove halfMove;

    public HalfMoveApplied(HalfMove hm) {
        this.halfMove = hm;
        this.orig = hm.orig;
        this.dest = hm.dest;
    }
}
