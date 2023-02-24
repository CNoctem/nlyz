package xyz.sis.chess.nlyz.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.board.Coordinate;
import xyz.sis.chess.nlyz.board.Square;
import xyz.sis.chess.nlyz.game.Game;
import xyz.sis.chess.nlyz.msg.ActionBus;
import xyz.sis.chess.nlyz.msg.HalfMoveApplied;
import xyz.sis.chess.nlyz.msg.SquareChanged;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BoardUI extends JPanel {

    private static final Logger log = LogManager.getLogger(BoardUI.class);

    private final SquareUI[][] squares = new SquareUI[8][8];

    private final Board board;

    private Set<Coordinate> markedSquares = new HashSet<>();

    public BoardUI(Board board) throws IOException {
        this.board = board;
        initBoardUI();
    }

    private void initBoardUI() throws IOException {
        var layout = new GridBagLayout();
        layout.columnWeights = new double[]{0, 1, 1, 1, 1, 1, 1, 1, 1};
        layout.rowWeights = new double[]{1, 1, 1, 1, 1, 1, 1, 1};
        setLayout(layout);
        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        for (int i = 0; i < 8; i++) {
            c.gridx = 0;
            c.gridy = i;

            JLabel l = new JLabel("" + (8 - i));
            l.setBorder(new EmptyBorder(0, 5, 0, 8));
            add(l, c);

            c.gridx = i + 1;
            c.gridy = 8;
            JLabel l2 = new JLabel("" + (char)(65 + i));
            l2.setHorizontalAlignment(SwingConstants.CENTER);
            l2.setBorder(new EmptyBorder(8, 0, 5, 0));
            add(l2, c);
        }

        for (int f = 0; f < 8; f++) {
            for (int r = 0; r < 8; r++) {
                c.gridx = f + 1;
                c.gridy = 7 - r;
                squares[f][r] = new SquareUI(board.get(f, r));
                addSquareListener(squares[f][r], Coordinate.get(f,r));
                add(squares[f][r], c);
            }
        }

        registerForEvents();
    }

    private void registerForEvents() {
        ActionBus.BUS.register(HalfMoveApplied.class, a -> {
            if (a instanceof HalfMoveApplied hma) {
                clearMarkedSquares();
                markSquare(hma.orig, true);
                markSquare(hma.dest, true);
                updateSquare(hma.orig);
                updateSquare(hma.dest);
            }
        });
    }

    private void updateSquare(Coordinate c) {
        log.debug("Repainting " + c);
        squares[c.f][c.r].update();
    }

    private void markSquare(Coordinate c, boolean marked) {
        squares[c.f][c.r].setHighlighted(marked);
        if (marked) markedSquares.add(c);
    }

    private void clearMarkedSquares() {
        markedSquares.forEach(c -> {
            markSquare(c, false);
            updateSquare(c);
        });
        markedSquares.clear();
    }

    private void addSquareListener(SquareUI sui, Coordinate coo) {
        sui.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    for (Coordinate c :
                            sui.getSquare().getPiece().computePossibleDestinations(board, coo)) {
                        squares[c.f][c.r].setHighlighted(true);
                        squares[c.f][c.r].repaint();
                    }

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    for (Coordinate c :
                            sui.getSquare().getPiece().computePossibleDestinations(board, coo)) {
                        squares[c.f][c.r].setHighlighted(false);
                        squares[c.f][c.r].repaint();
                    }

                }
            }

        });
    }


}
