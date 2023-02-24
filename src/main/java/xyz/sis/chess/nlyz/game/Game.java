package xyz.sis.chess.nlyz.game;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.piece.XColor;

import java.util.ArrayList;
import java.util.List;

import static xyz.sis.chess.nlyz.piece.XColor.BLACK;
import static xyz.sis.chess.nlyz.piece.XColor.WHITE;

public class Game {

    private static final Logger log = LogManager.getLogger(Game.class);

    private final List<HalfMove> game = new ArrayList<>();
    private final Board owner;

    private List<String> parsed;

    public Game(Board owner, String pgn) {
        this.owner = owner;
        parsePgn(pgn);
    }

    private void parsePgn(String pgn) {
        parsed = new PGNParser(pgn).getParsed();
        log.debug("Parsed pgn: " + parsed);
//        parsed.forEach(m -> {
//            String[] pts = m.split(" ");
//            if (pts.length > 0) game.add(HalfMove.createFromAlgebraic(pts[0], owner, WHITE));
//            if (pts.length > 1) game.add(HalfMove.createFromAlgebraic(pts[1], owner, BLACK));
//        });
    }

    private void parseNext() {

    }

    private int index = 0;

    public HalfMove next() {
        if (index > parsed.size() - 1) return null;
        var hm = HalfMove
                .createFromAlgebraic(index, parsed.get(index), owner, index % 2 == 0 ? WHITE : BLACK);
        game.add(hm);
        index++;
        return hm;
    }

}
