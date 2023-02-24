package xyz.sis.chess.nlyz.piece;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;

public class Queen extends Piece {

    Queen(PieceType type, XColor color) {
        super(type, color);
    }

    @Override
    public CoordinateSet computePossibleDestinations(Board board, Coordinate currentCoo) {
        var set = new CoordinateSet();

        addDirection(board, currentCoo, set, -1, -1);
        addDirection(board, currentCoo, set, -1, 1);
        addDirection(board, currentCoo, set, 1, -1);
        addDirection(board, currentCoo, set, 1, 1);

        addDirection(board, currentCoo, set, 0, -1);
        addDirection(board, currentCoo, set, 0, 1);
        addDirection(board, currentCoo, set, 1, 0);
        addDirection(board, currentCoo, set, -1, 0);

        return set;

    }
}
