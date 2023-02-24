package xyz.sis.chess.nlyz.piece;

import java.awt.Color;

public enum XColor {

    WHITE(Color.WHITE, 1), BLACK(Color.BLACK, -1), EMPTY(Color.RED, Integer.MIN_VALUE);

    public final Color color;
    public final int direction;

    XColor(Color color, int direction) {
        this.color = color;
        this.direction = direction;
    }

}
