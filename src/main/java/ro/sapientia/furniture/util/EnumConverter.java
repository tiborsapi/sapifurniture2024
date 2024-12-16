package ro.sapientia.furniture.util;

import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;

public class EnumConverter {

    public static FurnitureColor tableColorEnumConverter(String name){
        name = name.toUpperCase().trim();

        switch(name){
            case "RED": return FurnitureColor.RED;
            case "BLUE": return FurnitureColor.BLUE;
            case "GREEN": return FurnitureColor.GREEN;
            case "WHITE": return FurnitureColor.WHITE;
            case "BROWN": return FurnitureColor.BROWN;
            case "BLACK": return FurnitureColor.BLACK;
            case "GRAY" : return FurnitureColor.GRAY;
            default: throw new RuntimeException("Unsupported color: " + name);
        }
    }

    public static TableType tableTypeEnumConverter(String name){

        name = name.toUpperCase().trim();

        switch(name){
            case "DINING": return TableType.DINING;
            case "COFFEE": return TableType.COFFEE;
            case "OFFICE": return TableType.OFFICE;
            case "SIDE": return TableType.SIDE;
            case "CONFERENCE": return TableType.CONFERENCE;

            default: throw new RuntimeException("Unsupported table type: " + name);
        }
    }
}
