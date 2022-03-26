package main.java.com.company.constants;

public enum TextStyleConstants {

    ITALIC("Italic"), BOLD("Bold"), NONE("None");

    private String textStyleType;

    TextStyleConstants(String textStyleType) {
        this.textStyleType = textStyleType;
    }

}
