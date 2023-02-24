package xyz.sis.chess.nlyz.board;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.sis.chess.nlyz.game.HalfMove;
import xyz.sis.chess.nlyz.piece.PieceType;
import xyz.sis.chess.nlyz.piece.XColor;

import static xyz.sis.chess.nlyz.util.NMath.range8;
import static xyz.sis.chess.nlyz.piece.PieceType.*;
import static xyz.sis.chess.nlyz.piece.XColor.BLACK;
import static xyz.sis.chess.nlyz.piece.XColor.WHITE;

public class Board {

    private static final Logger log = LogManager.getLogger(Board.class);
    
    private SquareMatrix smx = new SquareMatrix();

    public Board() {
        init();
    }

    public Square get(Coordinate coordinate) {
        return smx.get(coordinate.f, coordinate.r);
    }

    public Square get(int file, int rank) {
        return smx.get(file, rank);
    }

    public boolean isEmpty(Coordinate c) {
        return c.valid && smx.get(c.f, c.r).getPiece().type == E;
    }

    public boolean areEnemies(Coordinate c1, Coordinate c2) {
        if (c1.valid && c2.valid) {
            var p1 = smx.get(c1.f, c1.r).getPiece();
            var p2 = smx.get(c2.f, c2.r).getPiece();
            return p1.color != p2.color && p1.type != E && p2.type != E;
        }
        return false;
    }

    public boolean areFriends(Coordinate c1, Coordinate c2) {
        if (c1.valid && c2.valid) {
            var p1 = smx.get(c1.f, c1.r).getPiece();
            var p2 = smx.get(c2.f, c2.r).getPiece();
            return p1.type != E && p2.color == p1.color;
        }
        return false;
    }

    public CoordinateSet getPossibleOrigins(Coordinate dest, XColor color, PieceType type) {
        var set = new CoordinateSet();
        for (Coordinate possibleOrigin : smx.getAll(type, color)) {
            var destinationsFromPossibleOrig = smx.get(possibleOrigin)
                    .getPiece()
                    .computePossibleDestinations(this, possibleOrigin);

            destinationsFromPossibleOrig.forEach(c -> {
                if (c.equals(dest)) set.add(possibleOrigin);
            });
        }
        return set;
    }

    public CoordinateSet getPossibleOrigins(CoordinateSet origFile, Coordinate dest, XColor color) {
        var set = new CoordinateSet();
        for (var possibleOrigin : origFile) {
            var destinationsFromPossibleOrig = smx.get(possibleOrigin)
                    .getPiece()
                    .computePossibleDestinations(this, possibleOrigin);
            if (destinationsFromPossibleOrig != null) {
                destinationsFromPossibleOrig.forEach(c -> {
                    if (c.equals(dest)) set.add(possibleOrigin);
                });
            }
        }
        return set;
    }

    public CoordinateSet getAll(PieceType type, XColor color) {
        return smx.getAll(type, color);
    }

    public void applyHalfMove(HalfMove hm) {
        log.debug("Applying half move " + hm);
        if (hm != null) smx.applyHalfMove(hm);
    }

    private void init() {
        for (int f : range8()) smx.set(f, 1, P.create(WHITE));
        for (int f : range8()) smx.set(f, 6, P.create(BLACK));

        smx.set(0, 0, R.create(WHITE));
        smx.set(7, 0, R.create(WHITE));

        smx.set(0, 7, R.create(BLACK));
        smx.set(7, 7, R.create(BLACK));

        smx.set(1, 0, N.create(WHITE));
        smx.set(6, 0, N.create(WHITE));

        smx.set(1, 7, N.create(BLACK));
        smx.set(6, 7, N.create(BLACK));

        smx.set(2, 0, B.create(WHITE));
        smx.set(5, 0, B.create(WHITE));

        smx.set(2, 7, B.create(BLACK));
        smx.set(5, 7, B.create(BLACK));

        smx.set(3, 0, Q.create(WHITE));
        smx.set(4, 0, K.create(WHITE));

        smx.set(3, 7, Q.create(BLACK));
        smx.set(4, 7, K.create(BLACK));

//        smx.set(4, 1, E.create(null));
//        smx.set(6, 6, E.create(null));

// ---
//        smx.set(2,2, P.create(BLACK));
//        smx.set(4,4, B.create(BLACK));
//
//        smx.set(1,2, Q.create(WHITE));
//        smx.set(5,2, P.create(WHITE));
//        smx.set(2,5, Q.create(WHITE));
//
//        smx.set(4,5, Q.create(BLACK));
//        smx.set(4,3, N.create(WHITE));

        log.debug("Board initialized.");
    }
    
}
