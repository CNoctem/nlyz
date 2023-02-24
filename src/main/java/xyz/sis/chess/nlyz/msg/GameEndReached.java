package xyz.sis.chess.nlyz.msg;

public class GameEndReached implements Action {

    public final String end;

    public GameEndReached(String end) {
        this.end = end;
    }

}
