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

    private PGNParser parser;

    public Game(Board owner, String pgn) {
        this.owner = owner;
        parsePgn(pgn);
    }

    private void parsePgn(String pgn) {
        parser = new PGNParser(pgn);
        parsed = parser.getParsed();
        log.debug("Parsed pgn: " + parsed);
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

    public boolean hasNext() {
        return index < parsed.size();
    }

    public String getEnd() {
        return parser.getEnd();
    }

}
