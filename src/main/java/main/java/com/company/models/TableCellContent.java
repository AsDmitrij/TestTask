package main.java.com.company.models;

import main.java.com.company.constants.TextStyleConstants;
import org.docx4j.wml.JcEnumeration;

public class TableCellContent {
    private String text;
    private String fontName;
    private TextStyleConstants fontStyle;
    private int fontSize;
    private JcEnumeration alignment;
    private String textLanguage;

    public String getTextLanguage() {
        return textLanguage;
    }

    public void setTextLanguage(String textLanguage) {
        this.textLanguage = textLanguage;
    }

    public JcEnumeration getAlignment() {
        return alignment;
    }

    public void setAlignment(JcEnumeration alignment) {
        this.alignment = alignment;
    }

    public TableCellContent(String text, String fontName, TextStyleConstants fontStyle, int fontSize, JcEnumeration alignment, String textLanguage) {
        this.text = text;
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.alignment = alignment;
        this.textLanguage = textLanguage;
    }

    public TableCellContent(String fontName, TextStyleConstants fontStyle, int fontSize, JcEnumeration alignment, String textLanguage) {
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.alignment = alignment;
        this.textLanguage = textLanguage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public TextStyleConstants getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(TextStyleConstants fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
