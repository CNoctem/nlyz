package xyz.sis.chess.nlyz.config;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private static Map<String, Double> DConf = new HashMap<>();

    static {
        DConf.put("piece.image.scale", 0.8);
    }

    public static double getDouble(String key) {
        return DConf.get(key);
    }

}
