package xyz.sis.chess.nlyz.board;

import org.apache.log4j.Logger;
import xyz.sis.chess.nlyz.game.HalfMove;
import xyz.sis.chess.nlyz.msg.ActionBus;
import xyz.sis.chess.nlyz.msg.HalfMoveApplied;
import xyz.sis.chess.nlyz.piece.Piece;
import xyz.sis.chess.nlyz.piece.PieceType;
import xyz.sis.chess.nlyz.piece.XColor;

import static org.apache.log4j.Logger.getLogger;
import static xyz.sis.chess.nlyz.util.NMath.*;


public class SquareMatrix {

    private static final Logger log = getLogger(SquareMatrix.class);

    private Square[][] mx = new Square[8][8];

    SquareMatrix() {
        initialize();
    }

    void initialize() {
        for (int f : range8())
            for (int r : range8()) {
                mx[f][r] = new Square(Coordinate.get(f, r));
            }
    }

    void set(Coordinate c, Piece p) {
        mx[c.f][c.r].setPiece(p);
    }

    void set(int file, int rank, Piece p) {
        mx[file][rank].setPiece(p);
    }

    Square get(Coordinate c) {
        return get(c.f, c.r);
    }

    Square get(int f, int r) {
        return mx[f][r];
    }

    public CoordinateSet getAll(PieceType type, XColor color) {
        var set = new CoordinateSet();
        for (int f : range8()) for (int r : range8()) {
            Piece p = mx[f][r].getPiece();
            if (p.type == type && p.color == color) set.add(Coordinate.get(f, r));
        }
        return set;
    }



    public void applyHalfMove(HalfMove hm) {
        log.debug("applyHalfMove " + hm);
        set(hm.dest, get(hm.orig).getPiece());
        set(hm.orig, PieceType.E.create(null));
        log.debug("hm.orig " + hm.orig + ": " + get(hm.orig).getPiece());
        log.debug("hm.dest " + hm.dest + ": " + get(hm.dest).getPiece());
        ActionBus.BUS.fireAction(new HalfMoveApplied(hm));
        if (hm.hasSecondHM()) applyHalfMove(hm.getSecondHalfMove());
    }

}
