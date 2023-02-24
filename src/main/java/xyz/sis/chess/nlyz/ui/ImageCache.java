package xyz.sis.chess.nlyz.ui;

import xyz.sis.chess.nlyz.piece.EmptyPiece;
import xyz.sis.chess.nlyz.piece.Piece;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private static final Map<String, Image> CACHE = new HashMap<>();

    public static Image get(Piece piece) throws IOException {
        if (piece.getClass() == EmptyPiece.class) return null;
        var i = CACHE.get(piece.fullName);
        if (i == null) {
            File f = readFile(piece.fullName + ".png");
            i = ImageIO.read(f);
            CACHE.put(piece.fullName, i);
        }
        return i;
    }

    private static File readFile(String name) {
        try {
            URL resource = ImageCache.class.getClassLoader().getResource(name);
            if (resource == null) {
                System.out.println("file not found " + name);
                return null;
            } else {
                return new File(resource.toURI());
            }
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static Image getScaledKeepAspect(Image i, int h) {
        float a = (float) i.getWidth(null) / (float) i.getHeight(null);
        int w = (int) (a * h);
        return i.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);
    }

    public static Map<String, Image> getCACHE() {
        return CACHE;
    }
}
