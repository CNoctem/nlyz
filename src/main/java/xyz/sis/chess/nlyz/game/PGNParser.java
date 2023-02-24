package xyz.sis.chess.nlyz.game;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.log4j.Logger.getLogger;

public class PGNParser {

    private static final Logger log = getLogger(PGNParser.class);

    private List<String> parsed = new ArrayList<>();

    private final String algebraic;

    public PGNParser(String algebraic) {
        this.algebraic = algebraic;
        parse();
    }

    private void parse() {
        int prevIndex = -1, i, index = 0;
        do {
            if (index > 0 && algebraic.charAt(index) == '.') {
                for (i = index - 1; i >= 0 && Character.isDigit(algebraic.charAt(i)); i--);
                if (prevIndex > -1) {
//                    parsed.add(algebraic.substring(prevIndex, i).trim());
                    var pts = algebraic.substring(prevIndex, i).trim().split(" ");
                    Collections.addAll(parsed, pts);
                }
                prevIndex = index + 1;
            }
            index++;
        } while (index < algebraic.length());
        var pts = algebraic.substring(prevIndex).trim().split(" ");
        if (pts.length == 3) log.debug("The End? '" + pts[2]);
        Collections.addAll(parsed, pts);
    }

    public List<String> getParsed() {
        return parsed;
    }

}
