package xyz.sis.chess.nlyz.piece;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;

public class Pawn extends Piece {

    Pawn(PieceType type, XColor color) {
        super(type, color);
    }

    @Override
    public CoordinateSet computePossibleDestinations(Board board, Coordinate currentCoo) {
        var set = new CoordinateSet();
        var oneAhead = Coordinate.get(currentCoo.f, currentCoo.r + color.direction);
        if (board.isEmpty(oneAhead)) {
            set.add(oneAhead);
            if (atBase(currentCoo)) {
                var twoAhead = Coordinate.get(currentCoo.f, currentCoo.r + 2 * color.direction);
                if (board.isEmpty(twoAhead)) {
                    set.add(twoAhead);
                }
            }
        }

        var oneAhLeft = Coordinate.get(currentCoo.f - 1, currentCoo.r + color.direction);
        if (board.areEnemies(currentCoo, oneAhLeft)) {
            set.add(oneAhLeft);
        }

        var oneAhRight = Coordinate.get(currentCoo.f + 1, currentCoo.r + color.direction);
        if (board.areEnemies(currentCoo, oneAhRight)) {
            set.add(oneAhRight);
        }

        return set;
    }


    private boolean atBase(Coordinate c) {
        return color == XColor.BLACK && c.r == 6 || color == XColor.WHITE && c.r == 1;
    }

}
