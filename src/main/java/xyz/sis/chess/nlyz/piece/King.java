package xyz.sis.chess.nlyz.piece;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;

public class King extends Piece {

    King(PieceType type, XColor color) {
        super(type, color);
    }

    @Override
    public CoordinateSet computePossibleDestinations(Board board, Coordinate currentCoo) {
        return null;
    }

}
