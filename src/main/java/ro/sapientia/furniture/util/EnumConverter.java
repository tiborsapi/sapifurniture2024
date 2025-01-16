package ro.sapientia.furniture.util;

import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.WoodType;

public class EnumConverter {
    public static WoodType woodTypeEnumConverter(String name) {
        name = name.toUpperCase().trim();

        switch (name) {
            case "OAK": return WoodType.OAK;
            case "PINE": return WoodType.PINE;
            case "MAPLE": return WoodType.MAPLE;
            case "TEAK": return WoodType.TEAK;
            case "WALNUT": return WoodType.WALNUT;
            case "MAHOGANY": return WoodType.MAHOGANY;
            case "BIRCH": return WoodType.BIRCH;
            default: throw new RuntimeException("Unsupported wood type: " + name);
        }
    }

    public static BedType bedTypeEnumConverter(String name) {
        name = name.toUpperCase().trim();

        switch (name) {
            case "TWIN": return BedType.TWIN;
            case "FULL": return BedType.FULL;
            case "QUEEN": return BedType.QUEEN;
            case "KING": return BedType.KING;
            case "CALIFORNIA_KING": return BedType.CALIFORNIA_KING;
            default: throw new RuntimeException("Unsupported bed type: " + name);
        }
    }
}
