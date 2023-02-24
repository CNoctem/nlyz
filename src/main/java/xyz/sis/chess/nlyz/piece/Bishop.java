package xyz.sis.chess.nlyz.piece;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;

import static xyz.sis.chess.nlyz.util.NMath.inRange8;

public class Bishop extends Piece{
    Bishop(PieceType type, XColor color) {
        super(type, color);
    }

    @Override
    public CoordinateSet computePossibleDestinations(Board board, Coordinate currentCoo) {
        var set = new CoordinateSet();

        addDirection(board, currentCoo, set, -1, -1);
        addDirection(board, currentCoo, set, -1, 1);
        addDirection(board, currentCoo, set, 1, -1);
        addDirection(board, currentCoo, set, 1, 1);

        return set;
    }

}
