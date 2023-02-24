package xyz.sis.chess.nlyz.ui;

import xyz.sis.chess.nlyz.board.Board;
import xyz.sis.chess.nlyz.game.Game;
import xyz.sis.chess.nlyz.msg.ActionBus;
import xyz.sis.chess.nlyz.msg.HalfMoveApplied;

import javax.swing.*;
import java.awt.BorderLayout;
import java.io.IOException;

public class MainFrame {

    public static void main(String[] args) throws IOException {
        String gotc = "1. Nf3 Nf6 2. c4 g6 3. Nc3 Bg7 4. d4 O-O 5. Bf4 d5 6. Qb3 dxc4 7. Qxc4 c6 8. e4 Nbd7 9. Rd1 Nb6 10. Qc5 Bg4 11. Bg5 Na4 12. Qa3 Nxc3 13. bxc3 Nxe4 14. Bxe7 Qb6 15. Bc4 Nxc3 16. Bc5 Rfe8+ 17. Kf1 Be6 18. Bxb6 Bxc4+ 19. Kg1 Ne2+ 20. Kf1 Nxd4+ 21. Kg1 Ne2+ 22. Kf1 Nc3+ 23. Kg1 axb6 24. Qb4 Ra4 25. Qxb6 Nxd1 26. h3 Rxa2 27. Kh2 Nxf2 28. Re1 Rxe1 29. Qd8+ Bf8 30. Nxe1 Bd5 31. Nf3 Ne4 32. Qb8 b5 33. h4 h5 34. Ne5 Kg7 35. Kg1 Bc5+ 36. Kf1 Ng3+ 37. Ke1 Bb4+ 38. Kd1 Bb3+ 39. Kc1 Ne2+ 40. Kb1 Nc3+ 41. Kc1 Rc2# 0-1";

        JFrame frame = new JFrame();
        var board = new Board();
        Game g = new Game(board, gotc);

        JEditorPane logPane = new JEditorPane();
        ActionBus.BUS.register(HalfMoveApplied.class,
                a -> logPane.setText(((HalfMoveApplied)a).halfMove.toString()));

        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel bHolder = new JPanel();
        var box = new BoxLayout(bHolder, BoxLayout.LINE_AXIS);
        bHolder.setLayout(box);

        var nextB = new JButton(">");
        nextB.addActionListener(a -> board.applyHalfMove(g.next()));
        bHolder.add(nextB);
        bHolder.add(logPane);
        contentPane.add(bHolder, BorderLayout.SOUTH);

        contentPane.add(new BoardUI(board));
        frame.setContentPane(contentPane);

        frame.setBounds(1000, 400, 800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
    }


}
