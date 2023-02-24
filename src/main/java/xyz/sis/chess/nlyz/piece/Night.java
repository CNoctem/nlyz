package xyz.sis.chess.nlyz.piece;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.CoordinateSet;

public class Night extends Piece {
    Night(PieceType type, XColor color) {
        super(type, color);
    }

    @Override
    public CoordinateSet computePossibleDestinations(Board board, Coordinate c) {
        var set = new CoordinateSet();
        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f + 2, c.r + 1), set);
        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f + 2, c.r - 1), set);
        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f - 2, c.r + 1), set);
        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f - 2, c.r - 1), set);

        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f + 1, c.r + 2), set);
        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f + 1, c.r - 2), set);
        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f - 1, c.r + 2), set);
        addIfEnemyOrEmpty(board, c, Coordinate.get(c.f - 1, c.r - 2), set);

        return set;
    }

    private void addIfEnemyOrEmpty(Board board, Coordinate mine, Coordinate dest, CoordinateSet set) {
        if (board.isEmpty(dest) || board.areEnemies(mine, dest)) set.add(dest);
    }
}
