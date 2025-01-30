package ro.sapientia.furniture.util;

import ro.sapientia.furniture.model.NightstandColor;

public class EnumConverter {

    public static NightstandColor toNightstandColor(String colorName) {
        colorName = colorName.toUpperCase().trim();

        switch (colorName) {
            case "OAK": return NightstandColor.OAK;
            case "WALNUT": return NightstandColor.WALNUT;
            case "MAPLE": return NightstandColor.MAPLE;
            case "MAHOGANY": return NightstandColor.MAHOGANY;
            case "CHERRY": return NightstandColor.CHERRY;
            case "WHITE": return NightstandColor.WHITE;
            case "BLACK": return NightstandColor.BLACK;
            case "GRAY": return NightstandColor.GRAY;
            case "BEIGE": return NightstandColor.BEIGE;
            case "NAVY_BLUE": return NightstandColor.NAVY_BLUE;
            case "DARK_GREEN": return NightstandColor.DARK_GREEN;
            case "ANTHRACITE": return NightstandColor.ANTHRACITE;

            default: throw new IllegalArgumentException("Invalid color name: " + colorName);
        }
    }

}
