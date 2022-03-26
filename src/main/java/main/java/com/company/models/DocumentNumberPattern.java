package main.java.com.company.models;

public class DocumentNumberPattern {
    private final String pattern;
    private final int keyNumberPosition;

    public DocumentNumberPattern(String pattern, int keyNumberPosition) {
        this.pattern = pattern;
        this.keyNumberPosition = keyNumberPosition;
    }

    public String getPattern() {
        return pattern;
    }

    public int getKeyNumberPosition() {
        return keyNumberPosition;
    }
}
