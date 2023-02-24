package xyz.sis.chess.nlyz.msg;

import xyz.sis.chess.nlyz.board.Coordinate;

public class SquareChanged implements Action {

    public final Coordinate coordinate;

    public SquareChanged(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

}
