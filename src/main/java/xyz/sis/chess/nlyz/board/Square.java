package xyz.sis.chess.nlyz.board;

import xyz.sis.chess.nlyz.piece.Piece;
import xyz.sis.chess.nlyz.piece.PieceType;
import xyz.sis.chess.nlyz.piece.XColor;

public class Square {

    private Piece piece = PieceType.E.create(null);

    public final XColor color;
    public final Coordinate coordinate;

    public Square(Coordinate coordinate) {
        this.coordinate = coordinate;
        color = (coordinate.f + coordinate.r) % 2 == 1 ? XColor.WHITE : XColor.BLACK;
    }

    public Square(Coordinate coordinate, Piece piece) {
        this(coordinate);
        this.piece = piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
