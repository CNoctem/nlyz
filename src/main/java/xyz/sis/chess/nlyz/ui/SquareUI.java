package xyz.sis.chess.nlyz.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.sis.chess.nlyz.board.Square;
import xyz.sis.chess.nlyz.config.Configuration;
import xyz.sis.chess.nlyz.piece.Piece;
import xyz.sis.chess.nlyz.piece.XColor;

import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;

public class SquareUI extends JPanel {

    private static final Logger log = LogManager.getLogger(SquareUI.class);

    private static final double pieceScale = Configuration.getDouble("piece.image.scale");
    private static final Color highlightedColor = Color.YELLOW;

    private XColor color;
    private boolean highlighted;

    private final Square square;

    public SquareUI(Square square) {
        color = square.color;
        this.square = square;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(color.color);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (highlighted) {
            g2.setColor(highlightedColor);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(0, 0, getWidth(), getHeight());
        }

        try {
            var i = ImageCache.get(square.getPiece());
            if (i != null) {
                int h = (int) ((float) getHeight() * pieceScale);

                var image = ImageCache.getScaledKeepAspect(i, h);
                int x = (int) ((float) (getWidth() - image.getWidth(null))  / 2);
                int y = (int) ((float) getHeight() * (1 - pieceScale) / 2);
                g2.drawImage(image, x, y, null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.setColor(color == XColor.BLACK ? Color.WHITE : Color.BLACK);
        g2.drawString(square.coordinate.toString(), 10, 15);
    }

    public void update() {
        log.debug("Updating ... Piece at " + square.coordinate + " : " + square.getPiece());
        invalidate();
        repaint();
    }

    public Square getSquare() {
        return square;
    }
}
