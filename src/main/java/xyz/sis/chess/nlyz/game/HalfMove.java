package xyz.sis.chess.nlyz.game;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;
import xyz.sis.chess.nlyz.piece.PieceType;
import xyz.sis.chess.nlyz.piece.XColor;
import xyz.sis.chess.nlyz.util.Util;

import java.util.stream.Collectors;

import static xyz.sis.chess.nlyz.piece.PieceType.*;

public class HalfMove {

    private static final Logger log = LogManager.getLogger(HalfMove.class);

    public static HalfMove createFromAlgebraic(int index, String algebraic, Board board, XColor color) {
        log.debug("createFromAlgebraic <- " + algebraic);
        HalfMove hm = null;
        var l = Util.lengthWithoutSpecial(algebraic);
        //pawn
        if (l == 2) {
            var dest = Coordinate.get(algebraic);
            var orig = computeOrig(board, color, dest, P);
            hm = new HalfMove(orig, dest, algebraic);
        } else if (l == 3) {
            var ch0 = algebraic.charAt(0);
            if ("O-O".equals(algebraic) || "0-0".equals(algebraic) || "o-o".equals(algebraic)) {
                hm = createShortCastle(index, board, color);
            } else if (Util.in(ch0, 'N', 'B', 'Q', 'R')) {
                var pType = PieceType.valueOf(String.valueOf(ch0));
                var dest = Coordinate.get(algebraic.substring(1));
                var orig = computeOrig(board, color, dest, pType);
                hm = new HalfMove(orig, dest, algebraic);
            }
        } else if (l == 4) {
            int idx = algebraic.indexOf('x');
            //Qxc4
            if (idx != -1) {
                hm = getHalfMove(algebraic, board, color, idx);
            } else {
                //Nbd7
                var piece = PieceType.valueOf(String.valueOf(algebraic.charAt(0)));
                var file = Coordinate.fileChar2fileNum(algebraic.charAt(1));
                var dest = Coordinate.get(algebraic.substring(2));
                log.debug("Moving a " + piece + " on file " + file + "(" + algebraic.charAt(1) + ") to " + dest);
                var possibleOrigins = board.getAll(piece, color)
                        .stream()
                        .filter(coo -> coo.f == file)
                        .toList();

                if (possibleOrigins.size() != 1) throw new IllegalArgumentException("not one");

                hm = new HalfMove(possibleOrigins.get(0), dest, algebraic);
            }
        }

        if (hm != null) {
            hm.color = color;
            hm.pgnIndex = (index) / 2 + 1;
        }
        return hm;
    }

    /**
     * Qxc4
     */
    private static HalfMove getHalfMove(String algebraic, Board board, XColor color, int idx) {
        HalfMove hm = null;
        var from = algebraic.substring(0, idx);
        var to = algebraic.substring(idx + 1);
        var dest = Coordinate.get(to);
        log.debug(algebraic + ": " + from + " -> " + to);
        if (from.length() == 1) {
            if (Character.isLowerCase(from.charAt(0))) {
                var origFile = CoordinateSet.createFile(from.charAt(0));
                var orig = computeOrig(board, color, origFile, dest);
                hm = new HalfMove(orig, dest, algebraic);
            } else if (Character.isUpperCase(from.charAt(0))) {
                var pType = PieceType.valueOf(String.valueOf(from.charAt(0)));
                var orig = computeOrig(board, color, dest, pType);
                hm = new HalfMove(orig, dest, algebraic);
            }
        }
        return hm;
    }

    public static Coordinate computeOrig(Board board, XColor color, Coordinate dest, PieceType type) {
        return board.getPossibleOrigins(dest, color, type).getOnly();
    }

    //dxc4
    public static Coordinate computeOrig(Board board, XColor color, CoordinateSet origFile, Coordinate dest) {
        log.debug("computeOrig " + origFile + ", " + dest + ", " + color);
        return board.getPossibleOrigins(origFile, dest, color).getOnly();
    }


    private static HalfMove createShortCastle(int index, Board board, XColor color) {
        var r = color == XColor.WHITE ? 0 : 7;
        var second = new HalfMove(Coordinate.get(7, r), Coordinate.get(5, r), "O-O");
        second.pgnIndex = index / 2 + 1;
        second.color = color;
        var hm = new HalfMove(
                Coordinate.get(4, r),
                Coordinate.get(6, r),
                "O-O", second);
//        hm.pgnIndex = index / 2 + 1;
//        hm.color = color;
        return hm;
    }

    public final Coordinate orig, dest;

    public final String algebraic;

    private int pgnIndex;
    private XColor color;

    private HalfMove secondHalfMove = null;

    protected HalfMove(Coordinate orig, Coordinate dest, String algebraic) {
        this.orig = orig;
        this.dest = dest;
        this.algebraic = algebraic;
    }

    protected HalfMove(Coordinate orig, Coordinate dest, String algebraic, HalfMove secondHalfMove) {
        this(orig, dest, algebraic);
        this.secondHalfMove = secondHalfMove;
    }

    public boolean hasSecondHM() {
        return secondHalfMove != null;
    }

    public HalfMove getSecondHalfMove() {
        return secondHalfMove;
    }

    @Override
    public String toString() {
        return pgnIndex + (color == XColor.WHITE ? ". " : ". ...") + algebraic;
    }



}
