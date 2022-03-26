package main.java.com.company.models;

public class Resolution {
    private final int idFrom;
    private final int idTo;

    public int getIdFrom() {
        return idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public Resolution(int idFrom, int idTo) {
        this.idFrom = idFrom;
        this.idTo = idTo;
    }
}
