package xyz.sis.chess.nlyz.piece;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;

public class EmptyPiece extends Piece{
    EmptyPiece(PieceType type, XColor color) {
        super(type, XColor.EMPTY);
    }

    @Override
    public CoordinateSet computePossibleDestinations(Board board, Coordinate currentCoo) {
        return null;
    }
}
